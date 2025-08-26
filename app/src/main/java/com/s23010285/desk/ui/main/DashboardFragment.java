package com.s23010285.desk.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.s23010285.desk.R;
import com.s23010285.desk.ui.workout.WorkoutSessionActivity;

/**
 * Dashboard fragment displaying user's fitness stats and quick actions
 * This is the main screen users see when they open the app
 */
public class DashboardFragment extends Fragment {

    // These variables hold references to the UI elements that show user statistics
    // tvSteps shows how many steps the user has taken today
    private TextView tvSteps, tvActiveMinutes, tvTodayProgress;
    // These buttons let users start workouts or take breaks
    private Button btnStartWorkout, btnStartBreak;

    /**
     * This method is called when the dashboard screen is created
     * It sets up the screen and prepares it for user interaction
     * @param inflater Helps create the view from the layout file
     * @param container The parent view that will contain this fragment
     * @param savedInstanceState Any saved state from previous instances
     * @return The view that will be displayed to the user
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create the view from our layout file
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        // Set up all the UI elements and prepare them for use
        setupViews(view);
        // Set up what happens when users tap buttons
        setupClickListeners();
        
        // Return the view so it can be displayed
        return view;
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the text views and buttons before the user can use them
     * @param view The view that contains all our UI elements
     */
    private void setupViews(View view) {
        // Find all the UI elements in our layout
        // These text views will show the user's daily statistics
        tvSteps = view.findViewById(R.id.tvSteps);
        tvActiveMinutes = view.findViewById(R.id.tvActiveMinutes);
        tvTodayProgress = view.findViewById(R.id.tvTodayProgress);
        // These buttons let users start activities
        btnStartWorkout = view.findViewById(R.id.btnStartWorkout);
        btnStartBreak = view.findViewById(R.id.btnStartBreak);
    }

    /**
     * This method sets up what happens when users tap buttons
     * It's like telling the app "when someone taps this button, do this action"
     */
    private void setupClickListeners() {
        // Start Workout button
        // When users tap this button, take them to the workout session screen
        btnStartWorkout.setOnClickListener(v -> {
            // Create an intent to open the workout session activity
            Intent intent = new Intent(getActivity(), WorkoutSessionActivity.class);
            // Start the workout session
            startActivity(intent);
        });

        // Start Break button
        // When users tap this button, start a break session
        btnStartBreak.setOnClickListener(v -> {
            // TODO: Implement break functionality
            // For now, just show a message that the break has started
            tvTodayProgress.setText("Break started! Take a walk.");
        });
    }
}
