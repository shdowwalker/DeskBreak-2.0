package com.s23010285.desk.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s23010285.desk.R;
import com.s23010285.desk.ui.workout.WorkoutLibraryFragment;
import com.s23010285.desk.ui.progress.ProgressFragment;
import com.s23010285.desk.ui.profile.ProfileFragment;

/**
 * Main activity that serves as the container for the DeskBreak app
 * Features bottom navigation to switch between different screens
 */
public class MainActivity extends AppCompatActivity {

    // This variable holds the bottom navigation bar that lets users switch between different screens
    private BottomNavigationView bottomNavigation;

    /**
     * This method is called when the app starts up
     * It sets up the main screen and prepares everything the user needs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line tells the app which layout file to use for the main screen
        setContentView(R.layout.main_activity_main);

        // Set up all the views and navigation
        setupViews();
        setupBottomNavigation();
        // Show the dashboard screen by default when the app opens
        loadDashboardFragment();
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the buttons and screens before the user sees them
     */
    private void setupViews() {
        // Find the bottom navigation bar in our layout
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    /**
     * This method sets up the bottom navigation bar
     * It tells the app what to do when users tap on different navigation items
     */
    private void setupBottomNavigation() {
        // Listen for when users tap on different navigation items
        bottomNavigation.setOnItemSelectedListener(item -> {
            // This variable will hold the screen we want to show
            Fragment selectedFragment = null;
            // Get the ID of the item the user tapped
            int itemId = item.getItemId();

            // Check which navigation item was tapped and create the right screen
            if (itemId == R.id.nav_dashboard) {
                // User tapped Dashboard - show the main dashboard screen
                selectedFragment = new DashboardFragment();
            } else if (itemId == R.id.nav_workouts) {
                // User tapped Workouts - show the workout library screen
                selectedFragment = new WorkoutLibraryFragment();
            } else if (itemId == R.id.nav_progress) {
                // User tapped Progress - show the progress tracking screen
                selectedFragment = new ProgressFragment();
            } else if (itemId == R.id.nav_profile) {
                // User tapped Profile - show the user profile screen
                selectedFragment = new ProfileFragment();
            }

            // If we found a screen to show, display it
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

        // Set the Dashboard as the default selected item when the app starts
        bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
    }

    /**
     * This method loads the dashboard screen when the app first opens
     * It's like showing the main page by default
     */
    private void loadDashboardFragment() {
        // Load the default dashboard fragment
        loadFragment(new DashboardFragment());
    }

    /**
     * This method switches between different screens in the app
     * It's like changing pages in a book - we replace the current page with a new one
     * @param fragment The screen we want to show to the user
     */
    private void loadFragment(Fragment fragment) {
        // This code replaces the current screen with the new one
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit();
    }
}
