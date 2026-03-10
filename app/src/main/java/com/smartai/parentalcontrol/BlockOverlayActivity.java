package com.smartai.parentalcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BlockOverlayActivity extends AppCompatActivity {

    private TextView appNameTextView;
    private Button requestTimeButton;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_overlay);

        // Make activity full screen and prevent dismissal
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        appNameTextView = findViewById(R.id.appNameTextView);
        requestTimeButton = findViewById(R.id.requestTimeButton);
        goBackButton = findViewById(R.id.goBackButton);

        // Get blocked app name from intent
        String blockedAppName = getIntent().getStringExtra("APP_NAME");
        if (blockedAppName != null) {
            appNameTextView.setText(blockedAppName);
        }

        requestTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRequestTime();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGoBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Prevent back button from dismissing the overlay
        handleGoBack();
    }

    private void handleRequestTime() {
        // TODO: Navigate to RequestTimeActivity
        Intent intent = new Intent(this, RequestTimeActivity.class);
        startActivity(intent);
    }

    private void handleGoBack() {
        // Return to home screen
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
