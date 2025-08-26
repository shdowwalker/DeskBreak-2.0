package com.s23010285.desk.ui.progress;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.s23010285.desk.R;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import com.s23010285.desk.utils.ProgressTracker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Progress fragment displaying comprehensive fitness tracking and analytics
 * This screen shows detailed information about the user's fitness progress over time
 */
public class ProgressFragment extends Fragment {

    // These variables hold references to the UI elements that show today's summary
    // todaySteps shows how many steps the user has taken today
    private TextView todaySteps, todayWorkouts, todayActiveMinutes;
    
    // This layout container will hold a chart showing the user's weekly progress
    private LinearLayout weeklyChartContainer;
    
    // This layout container will show the user's recent workout history
    private LinearLayout recentWorkoutsContainer;
    
    // This layout container will display the user's achievements and badges
    private LinearLayout achievementsContainer;
    
    // These variables hold references to the UI elements that show monthly statistics
    // monthlySteps shows the total steps for the current month
    private TextView monthlySteps, monthlyWorkouts, avgDailySteps;
    
    // These variables help manage user data and progress
    // databaseHelper helps us talk to the database to get user information
    private DatabaseHelper databaseHelper;
    // sharedPreferences stores user settings and login information
    private SharedPreferences sharedPreferences;
    // currentUser holds all the information about the logged-in user
    private User currentUser;
    // progressTracker helps calculate and display the user's fitness progress
    private ProgressTracker progressTracker;

    /**
     * This method is called when the progress screen is created
     * It sets up the screen and loads all the user's progress data
     * @param inflater Helps create the view from the layout file
     * @param container The parent view that will contain this fragment
     * @param savedInstanceState Any saved state from previous instances
     * @return The view that will be displayed to the user
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create the view from our layout file
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        
        // Set up all the UI elements and prepare them for use
        initializeViews(view);
        // Load the user's information from the database
        loadUserData();
        // Set up the progress tracking system and display current data
        setupProgressData();
        // Generate a chart showing the user's weekly progress
        generateWeeklyChart();
        // Show the user's recent workout history
        populateRecentWorkouts();
        // Display the user's achievements and badges
        populateAchievements();
        // Update the monthly statistics display
        updateMonthlyStats();
        
        // Return the view so it can be displayed
        return view;
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the text views and layout containers before the user can see them
     * @param view The view that contains all our UI elements
     */
    private void initializeViews(View view) {
        // Find all the today's summary UI elements
        // These show the user's current day progress
        todaySteps = view.findViewById(R.id.todaySteps);
        todayWorkouts = view.findViewById(R.id.todayWorkouts);
        todayActiveMinutes = view.findViewById(R.id.todayActiveMinutes);
        
        // Find all the chart and container layout elements
        // These will hold different types of progress information
        weeklyChartContainer = view.findViewById(R.id.weeklyChartContainer);
        recentWorkoutsContainer = view.findViewById(R.id.recentWorkoutsContainer);
        achievementsContainer = view.findViewById(R.id.achievementsContainer);
        
        // Find all the monthly statistics UI elements
        // These show the user's progress over the current month
        monthlySteps = view.findViewById(R.id.monthlySteps);
        monthlyWorkouts = view.findViewById(R.id.monthlyWorkouts);
        avgDailySteps = view.findViewById(R.id.avgDailySteps);
    }

    /**
     * This method loads the user's information from the database and shared preferences
     * It gets all the data needed to display the user's progress
     */
    private void loadUserData() {
        // Create a database helper to access user data
        databaseHelper = new DatabaseHelper(requireContext());
        // Get the shared preferences that store user settings and login info
        sharedPreferences = requireContext().getSharedPreferences("DeskBreakPrefs", 0);
        // Create a progress tracker to calculate fitness statistics
        progressTracker = new ProgressTracker(requireContext());
        
        // Get the user's email from shared preferences
        String userEmail = sharedPreferences.getString("user_email", "");
        // If we have an email, try to get the user from the database
        if (!userEmail.isEmpty()) {
            currentUser = databaseHelper.getUserByEmail(userEmail);
        }
    }

    /**
     * This method sets up the progress data and displays it to the user
     * It shows today's steps, workouts, and active minutes
     */
    private void setupProgressData() {
        // Load real data from ProgressTracker
        // Get the user's current step count for today
        int currentSteps = progressTracker.getTodaySteps();
        // Get the user's current workout count for today
        int currentWorkouts = progressTracker.getTodayWorkouts();
        // Calculate how many minutes the user has been active today
        int activeMinutes = calculateActiveMinutes(currentSteps, currentWorkouts);
        
        // Display the current progress data to the user
        todaySteps.setText(formatNumber(currentSteps));
        todayWorkouts.setText(String.valueOf(currentWorkouts));
        todayActiveMinutes.setText(String.valueOf(activeMinutes));
    }

    /**
     * This method calculates how many minutes the user has been active today
     * It uses their step count and workout count to estimate active time
     * @param steps How many steps the user has taken today
     * @param workouts How many workouts the user has completed today
     * @return The estimated number of active minutes
     */
    private int calculateActiveMinutes(int steps, int workouts) {
        // Calculate active minutes based on steps and workouts
        int stepMinutes = steps / 100; // Roughly 1 minute per 100 steps
        int workoutMinutes = workouts * 15; // Assume 15 minutes per workout
        return Math.min(stepMinutes + workoutMinutes, 120); // Cap at 2 hours
    }

    private String formatNumber(int number) {
        if (number >= 1000) {
            return String.format(Locale.getDefault(), "%,d", number);
        }
        return String.valueOf(number);
    }

    private void generateWeeklyChart() {
        weeklyChartContainer.removeAllViews();
        
        // Get real weekly data from ProgressTracker
        java.util.List<Integer> weeklyStepsList = progressTracker.getWeeklySteps();
        int[] weeklySteps = weeklyStepsList.stream().mapToInt(Integer::intValue).toArray();
        int maxSteps = getMaxValue(weeklySteps);
        
        // Create horizontal chart layout
        LinearLayout chartRow = new LinearLayout(requireContext());
        chartRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.MATCH_PARENT));
        chartRow.setOrientation(LinearLayout.HORIZONTAL);
        chartRow.setGravity(android.view.Gravity.BOTTOM);
        
        for (int i = 0; i < weeklySteps.length; i++) {
            View bar = createProgressBar(weeklySteps[i], maxSteps, i);
            chartRow.addView(bar);
            
            // Add spacing between bars
            if (i < weeklySteps.length - 1) {
                View spacer = new View(requireContext());
                spacer.setLayoutParams(new LinearLayout.LayoutParams(16, 1));
                chartRow.addView(spacer);
            }
        }
        
        weeklyChartContainer.addView(chartRow);
    }

    private int[] generateSampleWeeklyData() {
        Random random = new Random();
        int[] steps = new int[7];
        
        // Generate realistic weekly step data
        for (int i = 0; i < 5; i++) {
            steps[i] = 8000 + random.nextInt(4000); // Weekdays: 8000-12000
        }
        for (int i = 5; i < 7; i++) {
            steps[i] = 5000 + random.nextInt(3000); // Weekends: 5000-8000
        }
        
        return steps;
    }

    private int getMaxValue(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) max = value;
        }
        return max;
    }

    private View createProgressBar(int steps, int maxSteps, int dayIndex) {
        LinearLayout barContainer = new LinearLayout(requireContext());
        barContainer.setLayoutParams(new LinearLayout.LayoutParams(0, 180, 1));
        barContainer.setOrientation(LinearLayout.VERTICAL);
        barContainer.setGravity(android.view.Gravity.BOTTOM);
        
        // Progress bar
        View progressBar = new View(requireContext());
        int barHeight = maxSteps > 0 ? (steps * 160) / maxSteps : 0;
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, barHeight);
        progressBar.setLayoutParams(progressParams);
        progressBar.setBackgroundColor(requireContext().getColor(R.color.teal_600));
        
        // Add rounded corners
        progressBar.setClipToOutline(true);
        
        barContainer.addView(progressBar);
        
        // Step count label
        TextView stepLabel = new TextView(requireContext());
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
        labelParams.topMargin = 8;
        stepLabel.setLayoutParams(labelParams);
        stepLabel.setText(String.valueOf(steps));
        stepLabel.setTextColor(requireContext().getColor(R.color.text_primary));
        stepLabel.setTextSize(10);
        stepLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        barContainer.addView(stepLabel);
        
        return barContainer;
    }

    private void populateRecentWorkouts() {
        recentWorkoutsContainer.removeAllViews();
        
        // Get real recent workout data from ProgressTracker
        java.util.List<ProgressTracker.RecentWorkout> recentWorkouts = progressTracker.getRecentWorkouts();
        
        if (recentWorkouts.isEmpty()) {
            // Show message if no workouts yet
            TextView noWorkoutsMsg = new TextView(requireContext());
            noWorkoutsMsg.setText("No recent workouts yet. Start a workout to see your progress!");
            noWorkoutsMsg.setTextColor(requireContext().getColor(R.color.text_secondary));
            noWorkoutsMsg.setTextSize(14);
            noWorkoutsMsg.setPadding(16, 16, 16, 16);
            noWorkoutsMsg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            recentWorkoutsContainer.addView(noWorkoutsMsg);
            return;
        }
        
        for (int i = 0; i < Math.min(recentWorkouts.size(), 5); i++) {
            ProgressTracker.RecentWorkout workout = recentWorkouts.get(i);
            View workoutItem = createWorkoutItem(
                workout.getName(), 
                workout.getFormattedTime(), 
                workout.getDurationMinutes()
            );
            recentWorkoutsContainer.addView(workoutItem);
            
            // Add divider
            if (i < Math.min(recentWorkouts.size(), 5) - 1) {
                View divider = new View(requireContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(requireContext().getColor(R.color.divider));
                recentWorkoutsContainer.addView(divider);
            }
        }
    }

    private View createWorkoutItem(String type, String date, int duration) {
        LinearLayout itemContainer = new LinearLayout(requireContext());
        itemContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        itemContainer.setOrientation(LinearLayout.HORIZONTAL);
        itemContainer.setPadding(16, 12, 16, 12);
        itemContainer.setGravity(android.view.Gravity.CENTER_VERTICAL);
        
        // Workout icon/emoji
        TextView workoutIcon = new TextView(requireContext());
        workoutIcon.setText("💪");
        workoutIcon.setTextSize(20);
        workoutIcon.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // Workout details
        LinearLayout detailsContainer = new LinearLayout(requireContext());
        detailsContainer.setLayoutParams(new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        detailsContainer.setOrientation(LinearLayout.VERTICAL);
        detailsContainer.setPadding(16, 0, 0, 0);
        
        TextView workoutType = new TextView(requireContext());
        workoutType.setText(type);
        workoutType.setTextColor(requireContext().getColor(R.color.text_primary));
        workoutType.setTextSize(16);
        workoutType.setTypeface(null, android.graphics.Typeface.BOLD);
        
        TextView workoutInfo = new TextView(requireContext());
        workoutInfo.setText(date + " • " + duration + " min");
        workoutInfo.setTextColor(requireContext().getColor(R.color.text_secondary));
        workoutInfo.setTextSize(14);
        
        detailsContainer.addView(workoutType);
        detailsContainer.addView(workoutInfo);
        
        // Duration badge
        TextView durationBadge = new TextView(requireContext());
        durationBadge.setText(duration + "m");
        durationBadge.setTextColor(requireContext().getColor(R.color.white));
        durationBadge.setTextSize(12);
        durationBadge.setTypeface(null, android.graphics.Typeface.BOLD);
        durationBadge.setPadding(12, 6, 12, 6);
        durationBadge.setBackgroundColor(requireContext().getColor(R.color.teal_600));
        durationBadge.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        
        itemContainer.addView(workoutIcon);
        itemContainer.addView(detailsContainer);
        itemContainer.addView(durationBadge);
        
        return itemContainer;
    }

    private void populateAchievements() {
        achievementsContainer.removeAllViews();
        
        // Real achievement data based on user progress
        String[] achievementNames = {
            "First Steps", "Week Warrior", "Goal Crusher", "Consistency King"
        };
        String[] achievementDescriptions = {
            "Complete your first workout", "Work out 7 days in a row", 
            "Exceed daily step goal 5 times", "Maintain 30-day streak"
        };
        String[] achievementIcons = {"🥇", "🏆", "🎯", "👑"};
        
        // Check real achievement status
        boolean[] isUnlocked = {
            progressTracker.getTotalWorkouts() > 0,  // First workout
            progressTracker.getCurrentStreak() >= 7,  // 7-day streak
            progressTracker.isStepGoalReached(),      // Step goal reached
            progressTracker.getCurrentStreak() >= 30  // 30-day streak
        };
        
        for (int i = 0; i < achievementNames.length; i++) {
            View achievementItem = createAchievementItem(
                achievementNames[i],
                achievementDescriptions[i],
                achievementIcons[i],
                isUnlocked[i]
            );
            achievementsContainer.addView(achievementItem);
            
            // Add divider
            if (i < achievementNames.length - 1) {
                View divider = new View(requireContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(requireContext().getColor(R.color.divider));
                achievementsContainer.addView(divider);
            }
        }
    }

    private View createAchievementItem(String name, String description, String icon, boolean unlocked) {
        LinearLayout itemContainer = new LinearLayout(requireContext());
        itemContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        itemContainer.setOrientation(LinearLayout.HORIZONTAL);
        itemContainer.setPadding(16, 12, 16, 12);
        itemContainer.setGravity(android.view.Gravity.CENTER_VERTICAL);
        
        // Achievement icon
        TextView achievementIcon = new TextView(requireContext());
        achievementIcon.setText(icon);
        achievementIcon.setTextSize(24);
        achievementIcon.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // Achievement details
        LinearLayout detailsContainer = new LinearLayout(requireContext());
        detailsContainer.setLayoutParams(new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        detailsContainer.setOrientation(LinearLayout.VERTICAL);
        detailsContainer.setPadding(16, 0, 0, 0);
        
        TextView achievementName = new TextView(requireContext());
        achievementName.setText(name);
        achievementName.setTextColor(requireContext().getColor(
            unlocked ? R.color.text_primary : R.color.text_hint));
        achievementName.setTextSize(16);
        achievementName.setTypeface(null, android.graphics.Typeface.BOLD);
        
        TextView achievementDesc = new TextView(requireContext());
        achievementDesc.setText(description);
        achievementDesc.setTextColor(requireContext().getColor(
            unlocked ? R.color.text_secondary : R.color.text_hint));
        achievementDesc.setTextSize(14);
        
        detailsContainer.addView(achievementName);
        detailsContainer.addView(achievementDesc);
        
        // Status indicator
        TextView statusIndicator = new TextView(requireContext());
        statusIndicator.setText(unlocked ? "✓" : "🔒");
        statusIndicator.setTextColor(requireContext().getColor(
            unlocked ? R.color.accent_green : R.color.text_hint));
        statusIndicator.setTextSize(18);
        statusIndicator.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        
        itemContainer.addView(achievementIcon);
        itemContainer.addView(detailsContainer);
        itemContainer.addView(statusIndicator);
        
        return itemContainer;
    }

    private void updateMonthlyStats() {
        // Calculate monthly statistics
        int totalMonthlySteps = calculateMonthlySteps();
        int totalMonthlyWorkouts = calculateMonthlyWorkouts();
        int averageDailySteps = totalMonthlySteps / getDaysInCurrentMonth();
        
        monthlySteps.setText(formatNumber(totalMonthlySteps));
        monthlyWorkouts.setText(String.valueOf(totalMonthlyWorkouts));
        avgDailySteps.setText(formatNumber(averageDailySteps));
    }

    private int calculateMonthlySteps() {
        // Simulate monthly step calculation
        Random random = new Random();
        int baseSteps = 8000;
        int daysInMonth = getDaysInCurrentMonth();
        int totalSteps = 0;
        
        for (int i = 0; i < daysInMonth; i++) {
            totalSteps += baseSteps + random.nextInt(4000);
        }
        
        return totalSteps;
    }

    private int calculateMonthlyWorkouts() {
        // Simulate monthly workout calculation
        Random random = new Random();
        int daysInMonth = getDaysInCurrentMonth();
        int totalWorkouts = 0;
        
        for (int i = 0; i < daysInMonth; i++) {
            if (random.nextDouble() < 0.7) { // 70% chance of working out
                totalWorkouts += 1 + random.nextInt(2); // 1-2 workouts per day
            }
        }
        
        return totalWorkouts;
    }

    private int getDaysInCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
