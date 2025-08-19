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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Progress fragment displaying comprehensive fitness tracking and analytics
 */
public class ProgressFragment extends Fragment {

    // Today's summary views
    private TextView todaySteps, todayWorkouts, todayActiveMinutes;
    
    // Weekly chart container
    private LinearLayout weeklyChartContainer;
    
    // Recent workouts container
    private LinearLayout recentWorkoutsContainer;
    
    // Achievements container
    private LinearLayout achievementsContainer;
    
    // Monthly stats views
    private TextView monthlySteps, monthlyWorkouts, avgDailySteps;
    
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        
        initializeViews(view);
        loadUserData();
        setupProgressData();
        generateWeeklyChart();
        populateRecentWorkouts();
        populateAchievements();
        updateMonthlyStats();
        
        return view;
    }

    private void initializeViews(View view) {
        todaySteps = view.findViewById(R.id.todaySteps);
        todayWorkouts = view.findViewById(R.id.todayWorkouts);
        todayActiveMinutes = view.findViewById(R.id.todayActiveMinutes);
        
        weeklyChartContainer = view.findViewById(R.id.weeklyChartContainer);
        recentWorkoutsContainer = view.findViewById(R.id.recentWorkoutsContainer);
        achievementsContainer = view.findViewById(R.id.achievementsContainer);
        
        monthlySteps = view.findViewById(R.id.monthlySteps);
        monthlyWorkouts = view.findViewById(R.id.monthlyWorkouts);
        avgDailySteps = view.findViewById(R.id.avgDailySteps);
    }

    private void loadUserData() {
        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireContext().getSharedPreferences("DeskBreakPrefs", 0);
        
        String userEmail = sharedPreferences.getString("user_email", "");
        if (!userEmail.isEmpty()) {
            currentUser = databaseHelper.getUserByEmail(userEmail);
        }
    }

    private void setupProgressData() {
        // Load today's data from SharedPreferences or simulate it
        int currentSteps = sharedPreferences.getInt("current_daily_steps", 0);
        int currentWorkouts = sharedPreferences.getInt("current_daily_workouts", 0);
        int activeMinutes = calculateActiveMinutes(currentSteps, currentWorkouts);
        
        todaySteps.setText(formatNumber(currentSteps));
        todayWorkouts.setText(String.valueOf(currentWorkouts));
        todayActiveMinutes.setText(String.valueOf(activeMinutes));
    }

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
        
        // Generate sample weekly data
        int[] weeklySteps = generateSampleWeeklyData();
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
        
        // Sample recent workout data
        String[] workoutTypes = {"Lite Cardio", "Desk Stretches", "Quick Core", "Arm Toning"};
        String[] workoutDates = {"Today", "Yesterday", "2 days ago", "3 days ago"};
        int[] workoutDurations = {15, 10, 12, 18};
        
        for (int i = 0; i < workoutTypes.length; i++) {
            View workoutItem = createWorkoutItem(
                workoutTypes[i], 
                workoutDates[i], 
                workoutDurations[i]
            );
            recentWorkoutsContainer.addView(workoutItem);
            
            // Add divider
            if (i < workoutTypes.length - 1) {
                View divider = new View(requireContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(requireContext().getColor(R.color.divider));
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
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
        
        // Sample achievement data
        String[] achievementNames = {
            "First Steps", "Week Warrior", "Goal Crusher", "Consistency King"
        };
        String[] achievementDescriptions = {
            "Complete your first workout", "Work out 7 days in a row", 
            "Exceed daily step goal 5 times", "Maintain 30-day streak"
        };
        String[] achievementIcons = {"🥇", "🏆", "🎯", "👑"};
        boolean[] isUnlocked = {true, true, false, false};
        
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
