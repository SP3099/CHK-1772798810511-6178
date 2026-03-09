package com.smartai.parentalcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
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
        // TODO: Navigate to student usage activity
        Toast.makeText(this, "View Student Usage", Toast.LENGTH_SHORT).show();
    }

    private void handleManageAppLimits() {
        // TODO: Navigate to app limits management activity
        Toast.makeText(this, "Manage App Limits", Toast.LENGTH_SHORT).show();
    }

    private void handleViewRequests() {
        // TODO: Navigate to requests activity
        Toast.makeText(this, "View Requests", Toast.LENGTH_SHORT).show();
    }

    private void handleLockDevice() {
        // TODO: Implement device lock functionality
        Toast.makeText(this, "Lock Device", Toast.LENGTH_SHORT).show();
    }
}
