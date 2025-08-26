package com.s23010285.desk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utility class for tracking and managing user progress across the app
 * Handles steps, workouts, streaks, and other activity metrics
 * This class is like a personal fitness coach that keeps track of all the user's activities
 */
public class ProgressTracker {
    
    // These constants define the names of the preferences we store
    // PREFS_NAME is the name of the file where we save progress data
    private static final String PREFS_NAME = "progress_tracker";
    // These keys help us store and retrieve different types of progress data
    // KEY_DAILY_STEPS stores step counts for each day
    private static final String KEY_DAILY_STEPS = "daily_steps_";
    // KEY_DAILY_WORKOUTS stores workout counts for each day
    private static final String KEY_DAILY_WORKOUTS = "daily_workouts_";
    // KEY_LAST_ACTIVITY_DATE stores when the user was last active
    private static final String KEY_LAST_ACTIVITY_DATE = "last_activity_date";
    // KEY_CURRENT_STREAK stores how many days in a row the user has been active
    private static final String KEY_CURRENT_STREAK = "current_streak";
    // KEY_LONGEST_STREAK stores the user's best streak ever
    private static final String KEY_LONGEST_STREAK = "longest_streak";
    // KEY_TOTAL_WORKOUTS stores the total number of workouts the user has completed
    private static final String KEY_TOTAL_WORKOUTS = "total_workouts";
    // KEY_WEEKLY_STEPS stores step counts for each week
    private static final String KEY_WEEKLY_STEPS = "weekly_steps_";
    // KEY_MONTHLY_WORKOUTS stores workout counts for each month
    private static final String KEY_MONTHLY_WORKOUTS = "monthly_workouts_";
    
    // These variables help us manage progress data
    // context helps us access the app's resources and preferences
    private final Context context;
    // prefs is where we store all the progress data permanently
    private final SharedPreferences prefs;
    // databaseHelper helps us talk to the database to get user information
    private final DatabaseHelper databaseHelper;
    // dateFormat helps us format dates in a consistent way (YYYY-MM-DD)
    private final SimpleDateFormat dateFormat;
    
    /**
     * Constructor for the ProgressTracker
     * This method sets up everything we need to track user progress
     * @param context The app's context, which helps us access system resources
     */
    public ProgressTracker(Context context) {
        this.context = context;
        // Get access to the preferences file where we store progress data
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Create a database helper to access user information
        this.databaseHelper = new DatabaseHelper(context);
        // Create a date formatter that uses the YYYY-MM-DD format
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
    
    // Step tracking methods - these help us count and store the user's daily steps
    
    /**
     * Add steps to the user's daily count
     * This method is called whenever the user takes steps (from sensors or manual input)
     * @param steps The number of steps to add to today's total
     */
    public void addSteps(int steps) {
        // Get today's date in YYYY-MM-DD format
        String today = dateFormat.format(new Date());
        // Get the current step count for today
        int currentSteps = getDailySteps(today);
        // Add the new steps to today's total
        setDailySteps(today, currentSteps + steps);
        // Update the last activity date to today
        updateLastActivityDate();
    }
    
    /**
     * Get how many steps the user took on a specific date
     * @param date The date to check, in YYYY-MM-DD format
     * @return The number of steps for that date
     */
    public int getDailySteps(String date) {
        return prefs.getInt(KEY_DAILY_STEPS + date, 0);
    }
    
    /**
     * Get how many steps the user has taken today
     * @return The number of steps for today
     */
    public int getTodaySteps() {
        // Get today's date and return the step count for that date
        String today = dateFormat.format(new Date());
        return getDailySteps(today);
    }
    
    /**
     * Store the step count for a specific date
     * This is a private method that other methods use to save step data
     * @param date The date to store steps for, in YYYY-MM-DD format
     * @param steps The number of steps for that date
     */
    private void setDailySteps(String date, int steps) {
        prefs.edit().putInt(KEY_DAILY_STEPS + date, steps).apply();
    }
    
    // Workout tracking methods - these help us count and store the user's workout activities
    
    /**
     * Record that the user completed a workout
     * This method is called whenever a workout session is finished
     * @param workoutName The name of the workout that was completed
     * @param durationMinutes How long the workout lasted in minutes
     */
    public void completeWorkout(String workoutName, int durationMinutes) {
        // Get today's date in YYYY-MM-DD format
        String today = dateFormat.format(new Date());
        
        // Update daily workout count
        // Get how many workouts the user has already done today
        int currentWorkouts = getDailyWorkouts(today);
        // Add 1 to today's workout count
        setDailyWorkouts(today, currentWorkouts + 1);
        
        // Update total workouts
        // Get the user's total workout count across all time
        int totalWorkouts = getTotalWorkouts();
        // Add 1 to the total workout count
        setTotalWorkouts(totalWorkouts + 1);
        
        // Update monthly workouts
        // Get the current month in YYYY-MM format
        String monthKey = today.substring(0, 7); // yyyy-MM format
        // Get how many workouts the user has done this month
        int monthlyWorkouts = getMonthlyWorkouts(monthKey);
        // Add 1 to this month's workout count
        setMonthlyWorkouts(monthKey, monthlyWorkouts + 1);
        
        // Add recent workout
        addRecentWorkout(workoutName, durationMinutes);
        
        // Update streak
        // Mark today as an active day
        updateLastActivityDate();
        // Check if this extends the user's current streak
        updateStreak();
    }
    
    /**
     * Get how many workouts the user did on a specific date
     * @param date The date to check, in YYYY-MM-DD format
     * @return The number of workouts for that date
     */
    public int getDailyWorkouts(String date) {
        return prefs.getInt(KEY_DAILY_WORKOUTS + date, 0);
    }
    
    /**
     * Get how many workouts the user has done today
     * @return The number of workouts for today
     */
    public int getTodayWorkouts() {
        // Get today's date and return the workout count for that date
        String today = dateFormat.format(new Date());
        return getDailyWorkouts(today);
    }
    
    /**
     * Store the workout count for a specific date
     * This is a private method that other methods use to save workout data
     * @param date The date to store workouts for, in YYYY-MM-DD format
     * @param workouts The number of workouts for that date
     */
    private void setDailyWorkouts(String date, int workouts) {
        prefs.edit().putInt(KEY_DAILY_WORKOUTS + date, workouts).apply();
    }
    
    /**
     * Get the total number of workouts the user has completed across all time
     * @return The total workout count
     */
    public int getTotalWorkouts() {
        return prefs.getInt(KEY_TOTAL_WORKOUTS, 0);
    }
    
    private void setTotalWorkouts(int total) {
        prefs.edit().putInt(KEY_TOTAL_WORKOUTS, total).apply();
    }
    
    public int getMonthlyWorkouts(String monthKey) {
        return prefs.getInt(KEY_MONTHLY_WORKOUTS + monthKey, 0);
    }
    
    private void setMonthlyWorkouts(String monthKey, int workouts) {
        prefs.edit().putInt(KEY_MONTHLY_WORKOUTS + monthKey, workouts).apply();
    }
    
    // Streak tracking methods
    public int getCurrentStreak() {
        return prefs.getInt(KEY_CURRENT_STREAK, 0);
    }
    
    public int getLongestStreak() {
        return prefs.getInt(KEY_LONGEST_STREAK, 0);
    }
    
    private void updateStreak() {
        String today = dateFormat.format(new Date());
        String lastActivityDate = prefs.getString(KEY_LAST_ACTIVITY_DATE, "");
        
        if (lastActivityDate.equals(today)) {
            // Activity already recorded today, don't update streak
            return;
        }
        
        int currentStreak = getCurrentStreak();
        
        if (lastActivityDate.isEmpty()) {
            // First activity ever
            currentStreak = 1;
        } else {
            try {
                Date lastDate = dateFormat.parse(lastActivityDate);
                Date todayDate = dateFormat.parse(today);
                long diffInMillies = todayDate.getTime() - lastDate.getTime();
                long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
                
                if (diffInDays == 1) {
                    // Consecutive day
                    currentStreak++;
                } else if (diffInDays > 1) {
                    // Streak broken
                    currentStreak = 1;
                }
                // If diffInDays == 0, it's the same day, no change needed
                
            } catch (Exception e) {
                // Parse error, reset streak
                currentStreak = 1;
            }
        }
        
        // Update current streak
        prefs.edit().putInt(KEY_CURRENT_STREAK, currentStreak).apply();
        
        // Update longest streak if necessary
        int longestStreak = getLongestStreak();
        if (currentStreak > longestStreak) {
            prefs.edit().putInt(KEY_LONGEST_STREAK, currentStreak).apply();
        }
    }
    
    private void updateLastActivityDate() {
        String today = dateFormat.format(new Date());
        prefs.edit().putString(KEY_LAST_ACTIVITY_DATE, today).apply();
    }
    
    // Weekly data methods
    public List<Integer> getWeeklySteps() {
        List<Integer> weeklySteps = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        // Start from 6 days ago to today (7 days total)
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            String date = dateFormat.format(calendar.getTime());
            weeklySteps.add(getDailySteps(date));
        }
        
        return weeklySteps;
    }
    
    public List<Integer> getWeeklyWorkouts() {
        List<Integer> weeklyWorkouts = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        // Start from 6 days ago to today (7 days total)
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            String date = dateFormat.format(calendar.getTime());
            weeklyWorkouts.add(getDailyWorkouts(date));
        }
        
        return weeklyWorkouts;
    }
    
    // Recent workouts methods
    public void addRecentWorkout(String workoutName, int durationMinutes) {
        String recentWorkouts = prefs.getString("recent_workouts", "");
        String newWorkout = workoutName + ":" + durationMinutes + ":" + System.currentTimeMillis();
        
        if (recentWorkouts.isEmpty()) {
            recentWorkouts = newWorkout;
        } else {
            recentWorkouts = newWorkout + "," + recentWorkouts;
        }
        
        // Keep only the last 10 workouts
        String[] workouts = recentWorkouts.split(",");
        if (workouts.length > 10) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                if (i > 0) sb.append(",");
                sb.append(workouts[i]);
            }
            recentWorkouts = sb.toString();
        }
        
        prefs.edit().putString("recent_workouts", recentWorkouts).apply();
    }
    
    public List<RecentWorkout> getRecentWorkouts() {
        String recentWorkouts = prefs.getString("recent_workouts", "");
        List<RecentWorkout> workouts = new ArrayList<>();
        
        if (!recentWorkouts.isEmpty()) {
            String[] workoutArray = recentWorkouts.split(",");
            for (String workout : workoutArray) {
                String[] parts = workout.split(":");
                if (parts.length == 3) {
                    try {
                        String name = parts[0];
                        int duration = Integer.parseInt(parts[1]);
                        long timestamp = Long.parseLong(parts[2]);
                        workouts.add(new RecentWorkout(name, duration, timestamp));
                    } catch (NumberFormatException e) {
                        // Skip invalid entries
                    }
                }
            }
        }
        
        return workouts;
    }
    
    // Goal tracking methods
    public int getDailyStepGoal() {
        User currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getDailyStepGoal() : 10000; // Default goal
    }
    
    public int getDailyWorkoutGoal() {
        User currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getDailyWorkoutGoal() : 1; // Default goal
    }
    
    public boolean isStepGoalReached() {
        return getTodaySteps() >= getDailyStepGoal();
    }
    
    public boolean isWorkoutGoalReached() {
        return getTodayWorkouts() >= getDailyWorkoutGoal();
    }
    
    public int getStepProgress() {
        int steps = getTodaySteps();
        int goal = getDailyStepGoal();
        return Math.min(100, (steps * 100) / Math.max(1, goal));
    }
    
    public int getWorkoutProgress() {
        int workouts = getTodayWorkouts();
        int goal = getDailyWorkoutGoal();
        return Math.min(100, (workouts * 100) / Math.max(1, goal));
    }
    
    // Utility methods
    private User getCurrentUser() {
        // Get current user from database or SharedPreferences
        SharedPreferences userPrefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        long userId = userPrefs.getLong("current_user_id", -1);
        if (userId != -1) {
            return databaseHelper.getUserById(userId);
        }
        return null;
    }
    
    // Inner class for recent workouts
    public static class RecentWorkout {
        private String name;
        private int durationMinutes;
        private long timestamp;
        
        public RecentWorkout(String name, int durationMinutes, long timestamp) {
            this.name = name;
            this.durationMinutes = durationMinutes;
            this.timestamp = timestamp;
        }
        
        public String getName() { return name; }
        public int getDurationMinutes() { return durationMinutes; }
        public long getTimestamp() { return timestamp; }
        
        public String getFormattedTime() {
            return new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                    .format(new Date(timestamp));
        }
    }
}
