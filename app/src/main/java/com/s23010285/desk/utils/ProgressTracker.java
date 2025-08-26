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
 */
public class ProgressTracker {
    
    private static final String PREFS_NAME = "progress_tracker";
    private static final String KEY_DAILY_STEPS = "daily_steps_";
    private static final String KEY_DAILY_WORKOUTS = "daily_workouts_";
    private static final String KEY_LAST_ACTIVITY_DATE = "last_activity_date";
    private static final String KEY_CURRENT_STREAK = "current_streak";
    private static final String KEY_LONGEST_STREAK = "longest_streak";
    private static final String KEY_TOTAL_WORKOUTS = "total_workouts";
    private static final String KEY_WEEKLY_STEPS = "weekly_steps_";
    private static final String KEY_MONTHLY_WORKOUTS = "monthly_workouts_";
    
    private final Context context;
    private final SharedPreferences prefs;
    private final DatabaseHelper databaseHelper;
    private final SimpleDateFormat dateFormat;
    
    public ProgressTracker(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.databaseHelper = new DatabaseHelper(context);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
    
    // Step tracking methods
    public void addSteps(int steps) {
        String today = dateFormat.format(new Date());
        int currentSteps = getDailySteps(today);
        setDailySteps(today, currentSteps + steps);
        updateLastActivityDate();
    }
    
    public int getDailySteps(String date) {
        return prefs.getInt(KEY_DAILY_STEPS + date, 0);
    }
    
    public int getTodaySteps() {
        String today = dateFormat.format(new Date());
        return getDailySteps(today);
    }
    
    private void setDailySteps(String date, int steps) {
        prefs.edit().putInt(KEY_DAILY_STEPS + date, steps).apply();
    }
    
    // Workout tracking methods
    public void completeWorkout(String workoutName, int durationMinutes) {
        String today = dateFormat.format(new Date());
        
        // Update daily workout count
        int currentWorkouts = getDailyWorkouts(today);
        setDailyWorkouts(today, currentWorkouts + 1);
        
        // Update total workouts
        int totalWorkouts = getTotalWorkouts();
        setTotalWorkouts(totalWorkouts + 1);
        
        // Update monthly workouts
        String monthKey = today.substring(0, 7); // yyyy-MM format
        int monthlyWorkouts = getMonthlyWorkouts(monthKey);
        setMonthlyWorkouts(monthKey, monthlyWorkouts + 1);
        
        // Add recent workout
        addRecentWorkout(workoutName, durationMinutes);
        
        // Update streak
        updateLastActivityDate();
        updateStreak();
    }
    
    public int getDailyWorkouts(String date) {
        return prefs.getInt(KEY_DAILY_WORKOUTS + date, 0);
    }
    
    public int getTodayWorkouts() {
        String today = dateFormat.format(new Date());
        return getDailyWorkouts(today);
    }
    
    private void setDailyWorkouts(String date, int workouts) {
        prefs.edit().putInt(KEY_DAILY_WORKOUTS + date, workouts).apply();
    }
    
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
