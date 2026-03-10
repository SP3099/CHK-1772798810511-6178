package com.smartai.parentalcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class ParentDashboardActivity extends AppCompatActivity {

    private MaterialCardView viewUsageCard;
    private MaterialCardView manageAppLimitsCard;
    private MaterialCardView viewRequestsCard;
    private MaterialCardView lockDeviceCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        viewUsageCard = findViewById(R.id.viewUsageCard);
        manageAppLimitsCard = findViewById(R.id.manageAppLimitsCard);
        viewRequestsCard = findViewById(R.id.viewRequestsCard);
        lockDeviceCard = findViewById(R.id.lockDeviceCard);

        viewUsageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleViewUsage();
            }
        });

        manageAppLimitsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleManageAppLimits();
            }
        });

        viewRequestsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleViewRequests();
            }
        });

        lockDeviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLockDevice();
            }
        });
    }

    private void handleViewUsage() {
        // Navigate to StudentDashboardActivity to view usage
        Intent intent = new Intent(ParentDashboardActivity.this, StudentDashboardActivity.class);
        startActivity(intent);
    }

    private void handleManageAppLimits() {
        // Navigate to LimitManagementActivity
        Intent intent = new Intent(ParentDashboardActivity.this, LimitManagementActivity.class);
        startActivity(intent);
    }

    private void handleViewRequests() {
        // Navigate to RequestsActivity
        Intent intent = new Intent(ParentDashboardActivity.this, RequestsActivity.class);
        startActivity(intent);
    }

    private void handleLockDevice() {
        // Navigate to DeviceLockedActivity
        Intent intent = new Intent(ParentDashboardActivity.this, DeviceLockedActivity.class);
        startActivity(intent);
    }
}
