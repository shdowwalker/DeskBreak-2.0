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
    private Button btnNext, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_welcome);

        setupViews();
        setupUI();
        setupClickListeners();
    }

    private void setupViews() {
        // Initialize views using findViewById
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
    }

    private void setupUI() {
        // Set up the welcome screen UI elements
        // Note: These are now set in the layout XML, so no need to set them programmatically
    }

    private void setupClickListeners() {
        // Next button - navigate to sign up
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Skip button - navigate to login
        btnSkip.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
