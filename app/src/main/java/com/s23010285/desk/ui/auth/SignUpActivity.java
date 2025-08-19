package com.s23010285.desk.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.s23010285.desk.R;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import com.s23010285.desk.ui.main.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;
    private com.google.android.material.textfield.TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private com.google.android.material.button.MaterialButton btnSignUp;
    private android.widget.TextView btnSignIn;
    
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_sign_up);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        
        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("DeskBreakPrefs", MODE_PRIVATE);

        setupViews();
        setupClickListeners();
    }

    private void setupViews() {
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setupClickListeners() {
        btnSignUp.setOnClickListener(v -> attemptSignUp());
        
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void attemptSignUp() {
        // Reset errors
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);

        // Get values from input fields
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Check for valid input
        if (TextUtils.isEmpty(name)) {
            tilName.setError("Name is required");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // Show loading state
        btnSignUp.setEnabled(false);
        btnSignUp.setText("Creating Account...");

        // Perform sign up
        performSignUp(name, email, password);
    }

    private void performSignUp(String name, String email, String password) {
        // Check if user already exists
        if (databaseHelper.getUserByEmail(email) != null) {
            tilEmail.setError("Email already registered");
            etEmail.requestFocus();
            
            // Reset button state
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Sign Up");
            return;
        }

        // Create new user
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        // Add user to database
        long userId = databaseHelper.addUser(newUser);
        
        if (userId != -1) {
            // Sign up successful
            newUser.setId(userId);
            
            // Save user session
            saveUserSession(newUser);
            
            // Navigate to main activity
            navigateToMain();
            
            Toast.makeText(this, "Welcome to DeskBreak, " + name + "!", Toast.LENGTH_SHORT).show();
        } else {
            // Sign up failed
            Toast.makeText(this, "Failed to create account. Please try again.", Toast.LENGTH_SHORT).show();
            
            // Reset button state
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Sign Up");
        }
    }

    private void saveUserSession(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("user_id", user.getId());
        editor.putString("user_name", user.getName());
        editor.putString("user_email", user.getEmail());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }

    private void navigateToMain() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
