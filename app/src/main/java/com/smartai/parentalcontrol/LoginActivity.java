package com.smartai.parentalcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.smartai.parentalcontrol.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "LoginActivity onCreate");

        // Initialize Database
        databaseHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateInput(email, password)) {
            return;
        }

        Log.d(TAG, "Attempting login for: " + email);

        // Login with local database
        DatabaseHelper.User user = databaseHelper.loginUser(email, password);

        if (user != null) {
            Log.d(TAG, "Login successful");
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            
            // Save user info to SharedPreferences
            SettingsActivity.saveUserInfo(this, user.getName(), user.getEmail(), user.getRole());
            
            // Navigate to appropriate dashboard
            navigateToDashboard(user.getRole());
        } else {
            Log.e(TAG, "Login failed");
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToDashboard(String role) {
        Log.d(TAG, "Navigating to dashboard for role: " + role);
        
        Intent intent;
        if (role.equals("Parent")) {
            intent = new Intent(LoginActivity.this, ParentDashboardActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
        }
        
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void handleRegister() {
        // Navigate to RegisterActivity
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }
}
