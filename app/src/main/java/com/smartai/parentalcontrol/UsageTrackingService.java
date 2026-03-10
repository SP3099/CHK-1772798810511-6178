package com.smartai.parentalcontrol;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UsageTrackingService extends Service {

    private static final String TAG = "UsageTrackingService";
    private static final String CHANNEL_ID = "usage_tracking_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final long UPDATE_INTERVAL = 60000; // 1 minute

    private UsageStatsManager usageStatsManager;
    private Handler handler;
    private Runnable trackingRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        handler = new Handler();

        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());

        startTracking();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        stopTracking();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTracking() {
        trackingRunnable = new Runnable() {
            @Override
            public void run() {
                trackAppUsage();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        handler.post(trackingRunnable);
    }

    private void stopTracking() {
        if (handler != null && trackingRunnable != null) {
            handler.removeCallbacks(trackingRunnable);
        }
    }

    private void trackAppUsage() {
        if (usageStatsManager == null) {
            Log.e(TAG, "UsageStatsManager is null");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startTime = calendar.getTimeInMillis();

        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        if (usageStatsList != null && !usageStatsList.isEmpty()) {
            Map<String, UsageStats> usageStatsMap = new TreeMap<>();
            
            for (UsageStats usageStats : usageStatsList) {
                String packageName = usageStats.getPackageName();
                long totalTimeInForeground = usageStats.getTotalTimeInForeground();
                
                if (totalTimeInForeground > 0) {
                    usageStatsMap.put(packageName, usageStats);
                }
            }

            logUsageData(usageStatsMap);
        } else {
            Log.w(TAG, "No usage stats available");
        }
    }

    private void logUsageData(Map<String, UsageStats> usageStatsMap) {
        Log.d(TAG, "=== App Usage Statistics ===");
        long totalScreenTime = 0;

        for (Map.Entry<String, UsageStats> entry : usageStatsMap.entrySet()) {
            UsageStats stats = entry.getValue();
            long timeInForeground = stats.getTotalTimeInForeground();
            totalScreenTime += timeInForeground;

            String appName = stats.getPackageName();
            long minutes = (timeInForeground / 1000) / 60;
            long hours = minutes / 60;
            minutes = minutes % 60;

            Log.d(TAG, String.format("%s: %dh %dm", appName, hours, minutes));
        }

        long totalMinutes = (totalScreenTime / 1000) / 60;
        long totalHours = totalMinutes / 60;
        totalMinutes = totalMinutes % 60;
        Log.d(TAG, String.format("Total Screen Time: %dh %dm", totalHours, totalMinutes));
        Log.d(TAG, "===========================");

        // TODO: Store usage data in database or shared preferences
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Usage Tracking Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Tracks app usage in background");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Parental Control")
                .setContentText("Monitoring app usage")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }
}
