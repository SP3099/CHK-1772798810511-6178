package com.smartai.parentalcontrol;

import android.app.ProgressDialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartai.parentalcontrol.models.User;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    
    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private RadioGroup roleRadioGroup;
    private RadioButton parentRadioButton;
    private RadioButton studentRadioButton;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(TAG, "RegisterActivity onCreate");

        // Initialize Firebase
        try {
            mAuth = FirebaseAuth.getInstance();
            // Set database URL explicitly
            mDatabase = FirebaseDatabase.getInstance("https://smartparentalcontrolsystem-default-rtdb.firebaseio.com/").getReference();
            Log.d(TAG, "Firebase initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Firebase initialization failed", e);
            Toast.makeText(this, "Firebase initialization error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);

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

        // Show progress dialog
        progressDialog.show();

        // Register user with Firebase Authentication
        registerWithFirebase(name, email, password, role);
    }

    private void registerWithFirebase(final String name, String email, String password, final String role) {
        Log.d(TAG, "Attempting Firebase registration...");
        
        // Add timeout handler (30 seconds)
        final android.os.Handler timeoutHandler = new android.os.Handler();
        final Runnable timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, 
                            "Registration timeout. Please check your internet connection and try again.", 
                            Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Registration timeout after 30 seconds");
                }
            }
        };
        timeoutHandler.postDelayed(timeoutRunnable, 30000); // 30 second timeout
        
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        timeoutHandler.removeCallbacks(timeoutRunnable); // Cancel timeout
                        
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Firebase registration successful");
                            // Registration success
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                Log.d(TAG, "User ID: " + firebaseUser.getUid());
                                // Save user data to Realtime Database
                                saveUserToDatabase(firebaseUser.getUid(), name, firebaseUser.getEmail(), role);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, 
                                        "Registration failed: User is null", 
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // Registration failed
                            progressDialog.dismiss();
                            Log.e(TAG, "Firebase registration failed", task.getException());
                            String errorMessage = "Registration failed";
                            if (task.getException() != null) {
                                errorMessage = task.getException().getMessage();
                                Log.e(TAG, "Error details: " + errorMessage);
                            }
                            Toast.makeText(RegisterActivity.this, 
                                    errorMessage, 
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        timeoutHandler.removeCallbacks(timeoutRunnable);
                        progressDialog.dismiss();
                        Log.e(TAG, "Firebase registration exception", e);
                        Toast.makeText(RegisterActivity.this, 
                                "Error: " + e.getMessage(), 
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToDatabase(String userId, String name, String email, String role) {
        Log.d(TAG, "Saving user to database...");
        
        // Create user object
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);
        userMap.put("name", name);
        userMap.put("email", email);
        userMap.put("role", role);
        userMap.put("createdAt", System.currentTimeMillis());

        // Save to Firebase Realtime Database
        mDatabase.child("users").child(userId).setValue(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User data saved successfully");
                            Toast.makeText(RegisterActivity.this, 
                                    "Registration successful!", Toast.LENGTH_SHORT).show();
                            
                            // Save user info to SharedPreferences
                            SettingsActivity.saveUserInfo(RegisterActivity.this, name, email, role);
                            
                            Log.d(TAG, "Navigating to dashboard for role: " + role);
                            // Navigate to appropriate dashboard
                            navigateToDashboard(role);
                        } else {
                            Log.e(TAG, "Failed to save user data", task.getException());
                            String errorMessage = "Failed to save user data";
                            if (task.getException() != null) {
                                errorMessage = task.getException().getMessage();
                            }
                            Toast.makeText(RegisterActivity.this, 
                                    errorMessage, 
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void navigateToDashboard(String role) {
        Log.d(TAG, "navigateToDashboard called with role: " + role);
        
        Intent intent;
        if (role.equals("Parent")) {
            Log.d(TAG, "Creating intent for ParentDashboardActivity");
            intent = new Intent(RegisterActivity.this, ParentDashboardActivity.class);
        } else {
            Log.d(TAG, "Creating intent for StudentDashboardActivity");
            intent = new Intent(RegisterActivity.this, StudentDashboardActivity.class);
        }
        
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        Log.d(TAG, "Starting activity...");
        startActivity(intent);
        finish();
        
        Log.d(TAG, "Navigation complete");
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
