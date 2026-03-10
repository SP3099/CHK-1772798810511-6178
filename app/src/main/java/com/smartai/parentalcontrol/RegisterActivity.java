package com.smartai.parentalcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.smartai.parentalcontrol.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    
    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private RadioGroup roleRadioGroup;
    private RadioButton parentRadioButton;
    private RadioButton studentRadioButton;
    private Button registerButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(TAG, "RegisterActivity onCreate");

        // Initialize Database
        databaseHelper = new DatabaseHelper(this);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        parentRadioButton = findViewById(R.id.parentRadioButton);
        studentRadioButton = findViewById(R.id.studentRadioButton);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void handleRegister() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateInput(name, email, password)) {
            return;
        }

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        if (selectedRoleId == -1) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        String role = selectedRoleId == R.id.parentRadioButton ? "Parent" : "Student";

        Log.d(TAG, "Starting registration for: " + email + " as " + role);

        // Check if email already exists
        if (databaseHelper.checkEmailExists(email)) {
            Toast.makeText(this, "Email already registered. Please login.", Toast.LENGTH_LONG).show();
            return;
        }

        // Register user in local database
        boolean success = databaseHelper.registerUser(name, email, password, role);

        if (success) {
            Log.d(TAG, "Registration successful");
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            
            // Save user info to SharedPreferences
            SettingsActivity.saveUserInfo(this, name, email, role);
            
            // Navigate to appropriate dashboard
            navigateToDashboard(role);
        } else {
            Log.e(TAG, "Registration failed");
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToDashboard(String role) {
        Log.d(TAG, "Navigating to dashboard for role: " + role);
        
        Intent intent;
        if (role.equals("Parent")) {
            intent = new Intent(RegisterActivity.this, ParentDashboardActivity.class);
        } else {
            intent = new Intent(RegisterActivity.this, StudentDashboardActivity.class);
        }
        
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private boolean validateInput(String name, String email, String password) {
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            nameEditText.requestFocus();
            return false;
        }

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
