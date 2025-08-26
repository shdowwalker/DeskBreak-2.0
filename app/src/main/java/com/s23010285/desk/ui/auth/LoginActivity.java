package com.s23010285.desk.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.s23010285.desk.R;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import com.s23010285.desk.ui.main.MainActivity;

/**
 * This class handles user login and authentication
 * It checks if users are who they say they are and lets them into the app
 */
public class LoginActivity extends AppCompatActivity {

    // These variables hold references to the input fields and buttons on the login screen
    // tilEmail and tilPassword are the containers that hold the input fields
    private com.google.android.material.textfield.TextInputLayout tilEmail, tilPassword;
    // etEmail and etPassword are the actual input fields where users type their information
    private com.google.android.material.textfield.TextInputEditText etEmail, etPassword;
    // btnLogin is the button users tap to log in
    private com.google.android.material.button.MaterialButton btnLogin;
    // These are text views that users can tap to navigate to other screens
    private android.widget.TextView btnSignUp, tvForgotPassword;
    
    // This helps us talk to the database to check user credentials
    private DatabaseHelper databaseHelper;
    // This stores user preferences and login status
    private SharedPreferences sharedPreferences;

    /**
     * This method is called when the login screen first appears
     * It sets up the login screen and checks if the user is already logged in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line tells the app which layout file to use for the login screen
        setContentView(R.layout.auth_activity_login);

        // Initialize database helper to check user credentials
        databaseHelper = new DatabaseHelper(this);
        
        // Initialize shared preferences to store user login status
        sharedPreferences = getSharedPreferences("DeskBreakPrefs", MODE_PRIVATE);

        // Check if user is already logged in
        // If they are, take them directly to the main app
        if (isUserLoggedIn()) {
            navigateToMain();
            return;
        }

        // Set up all the UI elements and prepare them for use
        setupViews();
        // Set up what happens when users tap buttons or try to log in
        setupClickListeners();
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the input fields and buttons before the user can use them
     */
    private void setupViews() {
        // Find all the UI elements in our layout
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    /**
     * This method sets up what happens when users tap buttons
     * It's like telling the app "when someone taps this button, do this action"
     */
    private void setupClickListeners() {
        // When users tap the login button, try to log them in
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        // When users tap the sign up text, take them to the sign up screen
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        
        // When users tap the forgot password text, take them to the password recovery screen
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    /**
     * This method tries to log the user in
     * It checks if the user entered valid information and then verifies their credentials
     */
    private void attemptLogin() {
        // Reset any previous error messages
        tilEmail.setError(null);
        tilPassword.setError(null);

        // Get the values that the user typed into the input fields
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if the user entered an email address
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        // Check if the user entered a password
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // Check if the email address looks like a real email address
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        // Show loading state - disable the button and change the text
        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        // Try to log the user in with the credentials they provided
        performLogin(email, password);
    }

    /**
     * This method actually performs the login process
     * It checks the user's credentials against the database and logs them in if they're correct
     * @param email The email address the user entered
     * @param password The password the user entered
     */
    private void performLogin(String email, String password) {
        // Check if the email and password match what's stored in the database
        if (databaseHelper.checkUserCredentials(email, password)) {
            // Login successful - get the user's information from the database
            User user = databaseHelper.getUserByEmail(email);
            if (user != null) {
                // Save the user's login session so they stay logged in
                saveUserSession(user);
                
                // Take the user to the main app
                navigateToMain();
                
                // Show a welcome message
                Toast.makeText(this, "Welcome back, " + user.getName() + "!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Login failed - show an error message
            tilPassword.setError("Invalid email or password");
            etPassword.requestFocus();
            
            // Reset the button back to its normal state
            btnLogin.setEnabled(true);
            btnLogin.setText("Login");
        }
    }

    /**
     * This method saves the user's login information
     * It stores the user's ID, name, and email so the app knows who is logged in
     * @param user The user object containing the user's information
     */
    private void saveUserSession(User user) {
        // Get an editor to save user preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Save the user's ID, name, email, and login status
        editor.putLong("user_id", user.getId());
        editor.putString("user_name", user.getName());
        editor.putString("user_email", user.getEmail());
        editor.putBoolean("is_logged_in", true);
        // Apply the changes
        editor.apply();
    }

    /**
     * This method checks if a user is already logged in
     * It looks at the stored preferences to see if there's an active login session
     * @return true if the user is logged in, false otherwise
     */
    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    /**
     * This method takes the user to the main app
     * It creates a new intent to open the main activity and clears the back stack
     */
    private void navigateToMain() {
        // Create an intent to open the main activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // Clear the back stack so users can't go back to the login screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Start the main activity
        startActivity(intent);
        // Close this login screen
        finish();
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
