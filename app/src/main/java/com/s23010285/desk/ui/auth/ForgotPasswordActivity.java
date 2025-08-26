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

    // These variables hold references to the UI elements on the password reset screen
    // tilEmail is the container that holds the email input field
    private TextInputLayout tilEmail;
    // etEmail is the actual input field where users type their email address
    private TextInputEditText etEmail;
    // These are the buttons users can tap to reset their password or go back to login
    private MaterialButton btnResetPassword, btnBackToLogin;
    // These text views show status messages and instructions to the user
    private TextView tvStatus, tvInstructions;
    // These views show different states of the password reset process
    private View progressBar, successCard, resetFormCard;
    
    // This helps us talk to the database to check if users exist
    private DatabaseHelper databaseHelper;
    // This helps us delay actions to simulate processing time
    private Handler handler;

    /**
     * This method is called when the password reset screen first appears
     * It sets up the screen and prepares it for user interaction
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line tells the app which layout file to use for the password reset screen
        setContentView(R.layout.auth_activity_forgot_password);

        // Initialize database helper to check if users exist
        databaseHelper = new DatabaseHelper(this);
        // Initialize handler to delay actions and simulate processing
        handler = new Handler(Looper.getMainLooper());

        // Set up all the UI elements and prepare them for use
        setupViews();
        // Set up what happens when users tap buttons or try to reset their password
        setupClickListeners();
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the input fields and buttons before the user can use them
     */
    private void setupViews() {
        // Find all the UI elements in our layout
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
        // This text view lets users go back to the login screen
        TextView tvBackToLoginLink = findViewById(R.id.tvBackToLogin);
        tvBackToLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    /**
     * This method sets up what happens when users tap buttons
     * It's like telling the app "when someone taps this button, do this action"
     */
    private void setupClickListeners() {
        // When users tap the reset password button, try to reset their password
        btnResetPassword.setOnClickListener(v -> attemptPasswordReset());
        
        // When users tap the back to login button, take them to the login screen
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    /**
     * This method tries to reset the user's password
     * It checks if the user entered a valid email and then starts the reset process
     */
    private void attemptPasswordReset() {
        // Reset any previous error messages
        tilEmail.setError(null);

        // Get the email address that the user typed
        String email = etEmail.getText().toString().trim();

        // Check if the user entered an email address
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        // Check if the email address looks like a real email address
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        // Check if a user with this email exists in our database
        if (!databaseHelper.doesUserExist(email)) {
            tilEmail.setError("No account found with this email address");
            etEmail.requestFocus();
            return;
        }

        // Show loading state - display a progress bar and hide other elements
        showLoadingState();

        // Simulate the password reset process
        simulatePasswordReset(email);
    }

    /**
     * This method simulates the password reset process
     * It delays for a few seconds to show processing, then generates a new password
     * @param email The email address of the user who wants to reset their password
     */
    private void simulatePasswordReset(String email) {
        // Simulate network delay - wait 2 seconds to show processing
        handler.postDelayed(() -> {
            // Get the user from the database using their email
            User user = databaseHelper.getUserByEmail(email);
            if (user != null) {
                // Generate a new secure password for the user
                String newPassword = generateSecurePassword();
                
                // Update the user's password in the database
                boolean passwordUpdated = databaseHelper.updateUserPassword(user.getId(), newPassword);
                
                if (passwordUpdated) {
                    // Show success state - display the new password to the user
                    showSuccessState(email, newPassword);
                } else {
                    // Show error state - something went wrong updating the password
                    showErrorState("Failed to reset password. Please try again.");
                }
            } else {
                showErrorState("User not found. Please check your email address.");
            }
        }, 2000); // 2 second delay to simulate processing
    }

    /**
     * This method generates a secure random password
     * It creates an 8-character password with letters and numbers
     * @return A new secure password string
     */
    private String generateSecurePassword() {
        // Generate a secure 8-character password
        // Use a mix of uppercase letters, lowercase letters, and numbers
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        java.util.Random random = new java.util.Random();
        
        // Pick 8 random characters to create the password
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }

    /**
     * This method shows the loading state while processing the password reset
     * It displays a progress bar and hides the form and success card
     */
    private void showLoadingState() {
        // Show the progress bar and hide other elements
        progressBar.setVisibility(View.VISIBLE);
        resetFormCard.setVisibility(View.GONE);
        successCard.setVisibility(View.GONE);
        
        // Disable the reset button and change its text to show processing
        btnResetPassword.setEnabled(false);
        btnResetPassword.setText("Resetting Password...");
        
        // Show a status message to let the user know we're processing
        tvStatus.setText("Processing your request...");
        tvStatus.setTextColor(getResources().getColor(R.color.teal_600));
    }

    /**
     * This method shows the success state after the password has been reset
     * It displays the new password and instructions to the user
     * @param email The email address of the user
     * @param newPassword The new password that was generated
     */
    private void showSuccessState(String email, String newPassword) {
        // Hide the progress bar and show the success card
        progressBar.setVisibility(View.GONE);
        resetFormCard.setVisibility(View.GONE);
        successCard.setVisibility(View.VISIBLE);
        
        // Show success message in green
        tvStatus.setText("Password Reset Successful!");
        tvStatus.setTextColor(getResources().getColor(R.color.success_green));
        
        // Show the new password to the user
        TextView tvNewPassword = findViewById(R.id.tvNewPassword);
        tvNewPassword.setText("Your new password is: " + newPassword);
        
        // Show instructions on what to do next
        tvInstructions.setText("Please use this password to log in. You can change it later in your profile settings.");
        
        // Update the button to say "Back to Login" and make it green
        btnBackToLogin.setText("Back to Login");
        btnBackToLogin.setBackgroundTintList(getResources().getColorStateList(R.color.success_green));
        
        // Show a success message to confirm the password was reset
        Toast.makeText(this, "Password reset successful! Check the new password below.", Toast.LENGTH_LONG).show();
    }

    /**
     * This method shows the error state when something goes wrong
     * It displays an error message and resets the form for the user to try again
     * @param errorMessage The error message to display to the user
     */
    private void showErrorState(String errorMessage) {
        // Hide the progress bar and show the reset form again
        progressBar.setVisibility(View.GONE);
        resetFormCard.setVisibility(View.VISIBLE);
        successCard.setVisibility(View.GONE);
        
        // Re-enable the reset button and reset its text
        btnResetPassword.setEnabled(true);
        btnResetPassword.setText("Reset Password");
        
        // Show error message in red
        tvStatus.setText("Password Reset Failed");
        tvStatus.setTextColor(getResources().getColor(R.color.error_red));
        
        // Show the error message to the user
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * This method is called when the activity is destroyed
     * It cleans up resources to prevent memory leaks
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection if it exists
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
