package com.smartai.parentalcontrol;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;
import java.util.List;

public class LimitManagementActivity extends AppCompatActivity {

    private Spinner appSpinner;
    private Slider timeLimitSlider;
    private TextView timeLimitTextView;
    private Button setLimitButton;
    private RecyclerView limitsRecyclerView;
    private AppLimitAdapter appLimitAdapter;

    private List<AppInfo> installedApps;
    private List<AppLimit> currentLimits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_management);

        appSpinner = findViewById(R.id.appSpinner);
        timeLimitSlider = findViewById(R.id.timeLimitSlider);
        timeLimitTextView = findViewById(R.id.timeLimitTextView);
        setLimitButton = findViewById(R.id.setLimitButton);
        limitsRecyclerView = findViewById(R.id.limitsRecyclerView);

        currentLimits = new ArrayList<>();
        
        setupRecyclerView();
        loadInstalledApps();
        setupSlider();
        loadCurrentLimits();

        setLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSetLimit();
            }
        });
    }

    private void setupRecyclerView() {
        limitsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appLimitAdapter = new AppLimitAdapter(new AppLimitAdapter.OnRemoveLimitListener() {
            @Override
            public void onRemove(AppLimit appLimit) {
                handleRemoveLimit(appLimit);
            }
        });
        limitsRecyclerView.setAdapter(appLimitAdapter);
    }

    private void loadInstalledApps() {
        installedApps = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = pm.getApplicationLabel(app).toString();
                String packageName = app.packageName;
                Drawable icon = pm.getApplicationIcon(app);
                installedApps.add(new AppInfo(appName, packageName, icon));
            }
        }

        List<String> appNames = new ArrayList<>();
        for (AppInfo app : installedApps) {
            appNames.add(app.getAppName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, appNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appSpinner.setAdapter(adapter);
    }

    private void setupSlider() {
        timeLimitSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                updateTimeLimitDisplay((int) value);
            }
        });
        
        updateTimeLimitDisplay((int) timeLimitSlider.getValue());
    }

    private void updateTimeLimitDisplay(int minutes) {
        if (minutes == 0) {
            timeLimitTextView.setText("No limit");
        } else if (minutes < 60) {
            timeLimitTextView.setText(minutes + " minutes");
        } else {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                timeLimitTextView.setText(hours + " hour" + (hours > 1 ? "s" : ""));
            } else {
                timeLimitTextView.setText(hours + "h " + remainingMinutes + "m");
            }
        }
    }

    private void loadCurrentLimits() {
        // TODO: Load actual limits from database
        currentLimits.clear();
        appLimitAdapter.setAppLimits(currentLimits);
    }

    private void handleSetLimit() {
        int selectedPosition = appSpinner.getSelectedItemPosition();
        if (selectedPosition < 0 || selectedPosition >= installedApps.size()) {
            Toast.makeText(this, "Please select an app", Toast.LENGTH_SHORT).show();
            return;
        }

        AppInfo selectedApp = installedApps.get(selectedPosition);
        int limitMinutes = (int) timeLimitSlider.getValue();

        AppLimit newLimit = new AppLimit(
                selectedApp.getAppName(),
                selectedApp.getPackageName(),
                limitMinutes,
                selectedApp.getIcon()
        );

        currentLimits.add(newLimit);
        appLimitAdapter.setAppLimits(currentLimits);

        // TODO: Save limit to database
        Toast.makeText(this, "Limit set for " + selectedApp.getAppName(), Toast.LENGTH_SHORT).show();
    }

    private void handleRemoveLimit(AppLimit appLimit) {
        appLimitAdapter.removeLimit(appLimit);
        currentLimits.remove(appLimit);
        
        // TODO: Remove limit from database
        Toast.makeText(this, "Limit removed", Toast.LENGTH_SHORT).show();
    }

    public static class AppInfo {
        private String appName;
        private String packageName;
        private Drawable icon;

        public AppInfo(String appName, String packageName, Drawable icon) {
            this.appName = appName;
            this.packageName = packageName;
            this.icon = icon;
        }

        public String getAppName() {
            return appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public Drawable getIcon() {
            return icon;
        }
    }

    public static class AppLimit {
        private String appName;
        private String packageName;
        private int limitMinutes;
        private Drawable appIcon;

        public AppLimit(String appName, String packageName, int limitMinutes, Drawable appIcon) {
            this.appName = appName;
            this.packageName = packageName;
            this.limitMinutes = limitMinutes;
            this.appIcon = appIcon;
        }

        public String getAppName() {
            return appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public int getLimitMinutes() {
            return limitMinutes;
        }

        public Drawable getAppIcon() {
            return appIcon;
        }
    }
}
