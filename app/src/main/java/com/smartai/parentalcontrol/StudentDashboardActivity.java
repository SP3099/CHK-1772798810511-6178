package com.smartai.parentalcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView screenTimeTextView;
    private RecyclerView appsRecyclerView;
    private Button requestExtraTimeButton;
    private AppUsageAdapter appUsageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        screenTimeTextView = findViewById(R.id.screenTimeTextView);
        appsRecyclerView = findViewById(R.id.appsRecyclerView);
        requestExtraTimeButton = findViewById(R.id.requestExtraTimeButton);

        setupRecyclerView();
        loadScreenTime();
        loadUsedApps();

        requestExtraTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRequestExtraTime();
            }
        });
    }

    private void setupRecyclerView() {
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appUsageAdapter = new AppUsageAdapter();
        appsRecyclerView.setAdapter(appUsageAdapter);
    }

    private void loadScreenTime() {
        // TODO: Fetch actual screen time data
        String screenTime = "2h 35m";
        screenTimeTextView.setText(screenTime);
    }

    private void loadUsedApps() {
        // TODO: Fetch actual app usage data
        List<AppUsage> appUsageList = new ArrayList<>();
        appUsageList.add(new AppUsage("YouTube", "45m"));
        appUsageList.add(new AppUsage("Instagram", "30m"));
        appUsageList.add(new AppUsage("Chrome", "25m"));
        appUsageList.add(new AppUsage("WhatsApp", "20m"));
        appUsageList.add(new AppUsage("Games", "35m"));
        
        appUsageAdapter.setAppUsageList(appUsageList);
    }

    private void handleRequestExtraTime() {
        // TODO: Implement request extra time functionality
        Toast.makeText(this, "Request sent to parent", Toast.LENGTH_SHORT).show();
    }

    public static class AppUsage {
        private String appName;
        private String usageTime;

        public AppUsage(String appName, String usageTime) {
            this.appName = appName;
            this.usageTime = usageTime;
        }

        public String getAppName() {
            return appName;
        }

        public String getUsageTime() {
            return usageTime;
        }
    }
}
