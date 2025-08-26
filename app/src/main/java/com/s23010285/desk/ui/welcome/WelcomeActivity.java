package com.s23010285.desk.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.s23010285.desk.R;
import com.s23010285.desk.ui.auth.LoginActivity;
import com.s23010285.desk.ui.auth.SignUpActivity;

/**
 * Welcome activity that serves as the entry point for the DeskBreak app
 * Features an appealing welcome screen with logo and friendly illustration
 */
public class WelcomeActivity extends AppCompatActivity {

    // Temporarily using findViewById instead of View Binding
    // These variables hold references to the buttons on the welcome screen
    private Button btnNext, btnSkip;

    /**
     * This method is called when the welcome screen first appears
     * It sets up the welcome screen and prepares the buttons for user interaction
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line tells the app which layout file to use for the welcome screen
        setContentView(R.layout.welcome_activity_welcome);

        // Set up all the UI elements and prepare them for use
        setupViews();
        setupUI();
        // Set up what happens when users tap the buttons
        setupClickListeners();
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the buttons before the user can interact with them
     */
    private void setupViews() {
        // Initialize views using findViewById
        // Find the Next button that takes users to sign up
        btnNext = findViewById(R.id.btnNext);
        // Find the Skip button that takes users directly to login
        btnSkip = findViewById(R.id.btnSkip);
    }

    /**
     * This method sets up the welcome screen UI elements
     * Note: These are now set in the layout XML, so no need to set them programmatically
     */
    private void setupUI() {
        // Set up the welcome screen UI elements
        // Note: These are now set in the layout XML, so no need to set them programmatically
    }

    /**
     * This method sets up what happens when users tap the buttons
     * It's like telling the app "when someone taps this button, do this action"
     */
    private void setupClickListeners() {
        // Next button - navigate to sign up
        // When users tap the Next button, take them to the sign up screen
        btnNext.setOnClickListener(v -> {
            // Create an intent to open the sign up screen
            Intent intent = new Intent(this, SignUpActivity.class);
            // Start the sign up screen
            startActivity(intent);
            // Close this welcome screen so users can't go back to it
            finish();
        });

        // Skip button - navigate to login
        // When users tap the Skip button, take them directly to the login screen
        btnSkip.setOnClickListener(v -> {
            // Create an intent to open the login screen
            Intent intent = new Intent(this, LoginActivity.class);
            // Start the login screen
            startActivity(intent);
            // Close this welcome screen so users can't go back to it
            finish();
        });
    }
}
