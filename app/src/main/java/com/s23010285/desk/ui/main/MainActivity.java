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

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        setupViews();
        setupBottomNavigation();
        loadDashboardFragment();
    }

    private void setupViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (itemId == R.id.nav_workouts) {
                selectedFragment = new WorkoutLibraryFragment();
            } else if (itemId == R.id.nav_progress) {
                selectedFragment = new ProgressFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

        // Set default selection
        bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
    }

    private void loadDashboardFragment() {
        // Load the default dashboard fragment
        loadFragment(new DashboardFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit();
    }
}
