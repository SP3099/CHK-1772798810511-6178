package com.smartai.parentalcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartai.parentalcontrol.network.AuthInterceptor;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "ParentalControlPrefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";
    private static final String KEY_AUTO_SYNC = "auto_sync_enabled";
    private static final String KEY_DAILY_REPORTS = "daily_reports_enabled";

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userRoleTextView;
    private SwitchMaterial notificationsSwitch;
    private SwitchMaterial autoSyncSwitch;
    private SwitchMaterial dailyReportsSwitch;
    private Button changePasswordButton;
    private Button clearCacheButton;
    private Button logoutButton;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        initializeViews();
        loadUserInfo();
        loadPreferences();
        setupListeners();
    }

    private void initializeViews() {
        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        userRoleTextView = findViewById(R.id.userRoleTextView);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        autoSyncSwitch = findViewById(R.id.autoSyncSwitch);
        dailyReportsSwitch = findViewById(R.id.dailyReportsSwitch);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        clearCacheButton = findViewById(R.id.clearCacheButton);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void loadUserInfo() {
        String userName = preferences.getString(KEY_USER_NAME, "User");
        String userEmail = preferences.getString(KEY_USER_EMAIL, "user@example.com");
        String userRole = preferences.getString(KEY_USER_ROLE, "Parent");

        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);
        userRoleTextView.setText("Role: " + userRole);
    }

    private void loadPreferences() {
        notificationsSwitch.setChecked(preferences.getBoolean(KEY_NOTIFICATIONS, true));
        autoSyncSwitch.setChecked(preferences.getBoolean(KEY_AUTO_SYNC, true));
        dailyReportsSwitch.setChecked(preferences.getBoolean(KEY_DAILY_REPORTS, false));
    }

    private void setupListeners() {
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(KEY_NOTIFICATIONS, isChecked);
            Toast.makeText(this, "Notifications " + (isChecked ? "enabled" : "disabled"), 
                    Toast.LENGTH_SHORT).show();
        });

        autoSyncSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(KEY_AUTO_SYNC, isChecked);
            Toast.makeText(this, "Auto-sync " + (isChecked ? "enabled" : "disabled"), 
                    Toast.LENGTH_SHORT).show();
        });

        dailyReportsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(KEY_DAILY_REPORTS, isChecked);
            Toast.makeText(this, "Daily reports " + (isChecked ? "enabled" : "disabled"), 
                    Toast.LENGTH_SHORT).show();
        });

        changePasswordButton.setOnClickListener(v -> handleChangePassword());
        clearCacheButton.setOnClickListener(v -> handleClearCache());
        logoutButton.setOnClickListener(v -> handleLogout());
    }

    private void savePreference(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    private void handleChangePassword() {
        // TODO: Implement change password functionality
        Toast.makeText(this, "Change password feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void handleClearCache() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Cache")
                .setMessage("Are you sure you want to clear all cached data?")
                .setPositiveButton("Clear", (dialog, which) -> {
                    clearCache();
                    Toast.makeText(this, "Cache cleared successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void clearCache() {
        // Clear app cache
        try {
            deleteCache(getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCache(java.io.File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    deleteCache(new java.io.File(dir, child));
                }
            }
        }
        if (dir != null) {
            dir.delete();
        }
    }

    private void handleLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    performLogout();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        // Clear auth token
        AuthInterceptor.clearAuthToken();

        // Clear user preferences
        preferences.edit()
                .remove(KEY_USER_NAME)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_ROLE)
                .apply();

        // Stop tracking service if running
        Intent serviceIntent = new Intent(this, UsageTrackingService.class);
        stopService(serviceIntent);

        // Navigate to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    public static void saveUserInfo(Context context, String name, String email, String role) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_ROLE, role)
                .apply();
    }
}
