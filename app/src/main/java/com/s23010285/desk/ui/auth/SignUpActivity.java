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

/**
 * This class handles new user account creation
 * It collects user information and creates new accounts in the database
 */
public class SignUpActivity extends AppCompatActivity {

    // These variables hold references to the input fields and buttons on the sign up screen
    // tilName, tilEmail, tilPassword, tilConfirmPassword are containers that hold the input fields
    private com.google.android.material.textfield.TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;
    // etName, etEmail, etPassword, etConfirmPassword are the actual input fields where users type their information
    private com.google.android.material.textfield.TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    // btnSignUp is the button users tap to create their account
    private com.google.android.material.button.MaterialButton btnSignUp;
    // btnSignIn is text that users can tap to go to the login screen instead
    private android.widget.TextView btnSignIn;
    
    // This helps us talk to the database to create new user accounts
    private DatabaseHelper databaseHelper;
    // This stores user preferences and login status
    private SharedPreferences sharedPreferences;

    /**
     * This method is called when the sign up screen first appears
     * It sets up the sign up screen and prepares it for user interaction
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line tells the app which layout file to use for the sign up screen
        setContentView(R.layout.auth_activity_sign_up);

        // Initialize database helper to create new user accounts
        databaseHelper = new DatabaseHelper(this);
        
        // Initialize shared preferences to store user login status
        sharedPreferences = getSharedPreferences("DeskBreakPrefs", MODE_PRIVATE);

        // Set up all the UI elements and prepare them for use
        setupViews();
        // Set up what happens when users tap buttons or try to sign up
        setupClickListeners();
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the input fields and buttons before the user can use them
     */
    private void setupViews() {
        // Find all the UI elements in our layout
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

    /**
     * This method sets up what happens when users tap buttons
     * It's like telling the app "when someone taps this button, do this action"
     */
    private void setupClickListeners() {
        // When users tap the sign up button, try to create their account
        btnSignUp.setOnClickListener(v -> attemptSignUp());
        
        // When users tap the sign in text, take them to the login screen
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * This method tries to create a new user account
     * It checks if the user entered valid information and then creates their account
     */
    private void attemptSignUp() {
        // Reset any previous error messages
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);

        // Get the values that the user typed into the input fields
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Check if the user entered their name
        if (TextUtils.isEmpty(name)) {
            tilName.setError("Name is required");
            etName.requestFocus();
            return;
        }

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

        // Check if the user entered a password
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // Check if the password is at least 6 characters long
        if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        // Check if the user confirmed their password
        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
            return;
        }

        // Check if the password and confirmation password match
        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // Show loading state - disable the button and change the text
        btnSignUp.setEnabled(false);
        btnSignUp.setText("Creating Account...");

        // Try to create the user account with the information they provided
        performSignUp(name, email, password);
    }

    /**
     * This method actually creates the new user account
     * It checks if the email is already taken and then creates the account in the database
     * @param name The user's full name
     * @param email The user's email address
     * @param password The user's chosen password
     */
    private void performSignUp(String name, String email, String password) {
        // Check if a user with this email already exists
        if (databaseHelper.getUserByEmail(email) != null) {
            tilEmail.setError("Email already registered");
            etEmail.requestFocus();
            
            // Reset the button back to its normal state
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Sign Up");
            return;
        }

        // Create a new user object with the information provided
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        // Add the new user to the database
        long userId = databaseHelper.addUser(newUser);
        
        if (userId != -1) {
            // Sign up successful - set the user ID and save their session
            newUser.setId(userId);
            
            // Save the user's login session so they're automatically logged in
            saveUserSession(newUser);
            
            // Take the user to the main app
            navigateToMain();
            
            // Show a welcome message
            Toast.makeText(this, "Welcome to DeskBreak, " + name + "!", Toast.LENGTH_SHORT).show();
        } else {
            // Sign up failed - show an error message
            Toast.makeText(this, "Failed to create account. Please try again.", Toast.LENGTH_SHORT).show();
            
            // Reset the button back to its normal state
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Sign Up");
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
     * This method takes the user to the main app
     * It creates a new intent to open the main activity and clears the back stack
     */
    private void navigateToMain() {
        // Create an intent to open the main activity
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        // Clear the back stack so users can't go back to the sign up screen
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Start the main activity
        startActivity(intent);
        // Close this sign up screen
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
