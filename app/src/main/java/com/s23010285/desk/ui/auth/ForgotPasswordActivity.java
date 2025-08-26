package com.s23010285.desk.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.s23010285.desk.R;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;

/**
 * ForgotPasswordActivity - Handles password reset functionality
 * Provides a professional and user-friendly password recovery experience
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private MaterialButton btnResetPassword, btnBackToLogin;
    private TextView tvStatus, tvInstructions;
    private View progressBar, successCard, resetFormCard;
    
    private DatabaseHelper databaseHelper;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_forgot_password);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        handler = new Handler(Looper.getMainLooper());

        setupViews();
        setupClickListeners();
    }

    private void setupViews() {
        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        tvStatus = findViewById(R.id.tvStatus);
        tvInstructions = findViewById(R.id.tvInstructions);
        progressBar = findViewById(R.id.progressBar);
        successCard = findViewById(R.id.successCard);
        resetFormCard = findViewById(R.id.resetFormCard);
        
        // Setup back to login link
        TextView tvBackToLoginLink = findViewById(R.id.tvBackToLogin);
        tvBackToLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupClickListeners() {
        btnResetPassword.setOnClickListener(v -> attemptPasswordReset());
        
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void attemptPasswordReset() {
        // Reset errors
        tilEmail.setError(null);

        // Get email from input field
        String email = etEmail.getText().toString().trim();

        // Validate email
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

        // Check if user exists
        if (!databaseHelper.doesUserExist(email)) {
            tilEmail.setError("No account found with this email address");
            etEmail.requestFocus();
            return;
        }

        // Show loading state
        showLoadingState();

        // Simulate password reset process
        simulatePasswordReset(email);
    }

    private void simulatePasswordReset(String email) {
        // Simulate network delay
        handler.postDelayed(() -> {
            // Get user from database
            User user = databaseHelper.getUserByEmail(email);
            if (user != null) {
                // Generate new password
                String newPassword = generateSecurePassword();
                
                // Update user's password in database
                boolean passwordUpdated = databaseHelper.updateUserPassword(user.getId(), newPassword);
                
                if (passwordUpdated) {
                    // Show success state
                    showSuccessState(email, newPassword);
                } else {
                    // Show error state
                    showErrorState("Failed to reset password. Please try again.");
                }
            } else {
                showErrorState("User not found. Please check your email address.");
            }
        }, 2000); // 2 second delay to simulate processing
    }

    private String generateSecurePassword() {
        // Generate a secure 8-character password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }

    private void showLoadingState() {
        progressBar.setVisibility(View.VISIBLE);
        resetFormCard.setVisibility(View.GONE);
        successCard.setVisibility(View.GONE);
        
        btnResetPassword.setEnabled(false);
        btnResetPassword.setText("Resetting Password...");
        
        tvStatus.setText("Processing your request...");
        tvStatus.setTextColor(getResources().getColor(R.color.teal_600));
    }

    private void showSuccessState(String email, String newPassword) {
        progressBar.setVisibility(View.GONE);
        resetFormCard.setVisibility(View.GONE);
        successCard.setVisibility(View.VISIBLE);
        
        tvStatus.setText("Password Reset Successful!");
        tvStatus.setTextColor(getResources().getColor(R.color.success_green));
        
        // Show new password to user
        TextView tvNewPassword = findViewById(R.id.tvNewPassword);
        tvNewPassword.setText("Your new password is: " + newPassword);
        
        // Show instructions
        tvInstructions.setText("Please use this password to log in. You can change it later in your profile settings.");
        
        // Update button
        btnBackToLogin.setText("Back to Login");
        btnBackToLogin.setBackgroundTintList(getResources().getColorStateList(R.color.success_green));
        
        // Show success message
        Toast.makeText(this, "Password reset successful! Check the new password below.", Toast.LENGTH_LONG).show();
    }

    private void showErrorState(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        resetFormCard.setVisibility(View.VISIBLE);
        successCard.setVisibility(View.GONE);
        
        btnResetPassword.setEnabled(true);
        btnResetPassword.setText("Reset Password");
        
        tvStatus.setText("Password Reset Failed");
        tvStatus.setTextColor(getResources().getColor(R.color.error_red));
        
        // Show error message
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
