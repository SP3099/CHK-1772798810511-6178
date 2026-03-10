package com.smartai.parentalcontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class InstallReceiver extends BroadcastReceiver {

    private static final String TAG = "InstallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        
        if (action == null) {
            return;
        }

        String packageName = intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;

        switch (action) {
            case Intent.ACTION_PACKAGE_ADDED:
                handlePackageAdded(context, packageName);
                break;
                
            case Intent.ACTION_PACKAGE_REMOVED:
                handlePackageRemoved(context, packageName);
                break;
                
            case Intent.ACTION_PACKAGE_REPLACED:
                handlePackageReplaced(context, packageName);
                break;
        }
    }

    private void handlePackageAdded(Context context, String packageName) {
        if (packageName == null) {
            return;
        }

        Log.d(TAG, "New app installed: " + packageName);

        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            String appName = pm.getApplicationLabel(appInfo).toString();

            // Check if it's a user-installed app (not system app)
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                Log.d(TAG, "User app installed: " + appName);
                
                // TODO: Notify parent about new app installation
                // TODO: Store app info in database
                // TODO: Send notification to parent
                
                notifyParent(context, appName, packageName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package not found: " + packageName, e);
        }
    }

    private void handlePackageRemoved(Context context, String packageName) {
        if (packageName == null) {
            return;
        }

        Log.d(TAG, "App removed: " + packageName);
        
        // TODO: Remove app limits from database
        // TODO: Notify parent about app removal
    }

    private void handlePackageReplaced(Context context, String packageName) {
        if (packageName == null) {
            return;
        }

        Log.d(TAG, "App updated: " + packageName);
        
        // TODO: Update app info in database if needed
    }

    private void notifyParent(Context context, String appName, String packageName) {
        // TODO: Implement actual notification logic
        // This could send a push notification, save to database, or trigger an alert
        
        Log.d(TAG, "Notifying parent about new app: " + appName);
        
        // For now, just log the event
        // In production, you would:
        // 1. Save to local database
        // 2. Send notification to parent's device
        // 3. Update Firebase/backend
    }
}
