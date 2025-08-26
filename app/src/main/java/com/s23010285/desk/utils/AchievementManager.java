package com.s23010285.desk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Achievement Manager for DeskBreak App
 * Implements gamification with badges, rewards, and progress tracking
 * This class is like a game system that rewards users for reaching fitness milestones
 */
public class AchievementManager {
    
    // Achievement types - these define different categories of achievements
    // WORKOUT_STREAK rewards users for doing workouts multiple days in a row
    public enum AchievementType {
        WORKOUT_STREAK,
        STEP_GOAL,
        EXERCISE_COUNT,
        MEDITATION_SESSIONS,
        PERFECT_FORM,
        TIME_GOAL,
        SOCIAL_SHARING,
        THEME_UNLOCK
    }
    
    // Achievement levels - these define how prestigious an achievement is
    // BRONZE is the easiest to get, DIAMOND is the hardest
    public enum AchievementLevel {
        BRONZE,
        SILVER,
        GOLD,
        PLATINUM,
        DIAMOND
    }
    
    // Achievement data structure - this class holds all the information about a single achievement
    // It's like a digital badge that users can earn
    public static class Achievement {
        // These variables store the achievement's basic information
        // id is a unique identifier for this achievement
        public final String id;
        // title is the display name of the achievement (e.g., "Week Warrior")
        public final String title;
        // description explains what the user needs to do to earn this achievement
        public final String description;
        // type tells us what category this achievement belongs to
        public final AchievementType type;
        // level indicates how prestigious this achievement is
        public final AchievementLevel level;
        // requirement is the number the user needs to reach to earn this achievement
        public final int requirement;
        // iconResId points to the icon image that represents this achievement
        public final int iconResId;
        // points is how many points this achievement is worth
        public final int points;
        
        // These variables track the user's progress toward this achievement
        // isUnlocked indicates whether the user has earned this achievement
        public boolean isUnlocked;
        // currentProgress shows how close the user is to earning this achievement
        public int currentProgress;
        
        /**
         * Constructor for the Achievement class
         * This method creates a new achievement with all its information
         * @param id A unique identifier for this achievement
         * @param title The display name of the achievement
         * @param description What the user needs to do to earn this achievement
         * @param type What category this achievement belongs to
         * @param level How prestigious this achievement is
         * @param requirement The number the user needs to reach
         * @param iconResId The icon image for this achievement
         * @param points How many points this achievement is worth
         */
        public Achievement(String id, String title, String description, 
                         AchievementType type, AchievementLevel level, 
                         int requirement, int iconResId, int points) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.type = type;
            this.level = level;
            this.requirement = requirement;
            this.iconResId = iconResId;
            this.points = points;
            // New achievements start as locked with no progress
            this.isUnlocked = false;
            this.currentProgress = 0;
        }
    }
    
    // These variables help manage the achievement system
    // TAG is used for logging messages to help with debugging
    private static final String TAG = "AchievementManager";
    // context helps us access the app's resources and preferences
    private Context context;
    // preferences stores which achievements the user has unlocked
    private SharedPreferences preferences;
    // achievements stores all available achievements and their unlock status
    private Map<String, Achievement> achievements;
    // totalPoints tracks the user's total achievement points
    private int totalPoints = 0;
    // userLevel tracks the user's current level based on their achievement points
    private int userLevel = 1;
    
    /**
     * Constructor for the AchievementManager
     * This method sets up the achievement system and loads the user's progress
     * @param context The app's context, which helps us access system resources
     */
    public AchievementManager(Context context) {
        this.context = context;
        // Get access to the preferences file where we store achievement progress
        this.preferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE);
        // Set up all available achievements
        initializeAchievements();
        // Load the user's current achievement progress
        loadProgress();
    }
    
    /**
     * Initialize all available achievements
     * This method creates all the achievements that users can earn
     */
    private void initializeAchievements() {
        // Create a map to store all achievements
        achievements = new HashMap<>();
        
        // Workout streak achievements - reward users for consistent workouts
        // "Getting Started" - complete 3 workouts in a row
        achievements.put("streak_3", new Achievement(
            "streak_3", "Getting Started", "Complete 3 workouts in a row",
            AchievementType.WORKOUT_STREAK, AchievementLevel.BRONZE, 3, 
            android.R.drawable.ic_menu_myplaces, 10));
        
        // "Week Warrior" - complete 7 workouts in a row
        achievements.put("streak_7", new Achievement(
            "streak_7", "Week Warrior", "Complete 7 workouts in a row",
            AchievementType.WORKOUT_STREAK, AchievementLevel.SILVER, 7,
            android.R.drawable.ic_menu_myplaces, 25));
        
        // "Monthly Master" - complete 30 workouts in a row
        achievements.put("streak_30", new Achievement(
            "streak_30", "Monthly Master", "Complete 30 workouts in a row",
            AchievementType.WORKOUT_STREAK, AchievementLevel.GOLD, 30,
            android.R.drawable.ic_menu_myplaces, 100));
        
        // Step goal achievements
        achievements.put("steps_1000", new Achievement(
            "steps_1000", "First Steps", "Reach 1,000 steps in a day",
            AchievementType.STEP_GOAL, AchievementLevel.BRONZE, 1000,
            android.R.drawable.ic_menu_directions, 15));
        
        achievements.put("steps_10000", new Achievement(
            "steps_10000", "Step Master", "Reach 10,000 steps in a day",
            AchievementType.STEP_GOAL, AchievementLevel.SILVER, 10000,
            android.R.drawable.ic_menu_directions, 50));
        
        // Exercise count achievements
        achievements.put("exercises_50", new Achievement(
            "exercises_50", "Exercise Enthusiast", "Complete 50 exercises",
            AchievementType.EXERCISE_COUNT, AchievementLevel.BRONZE, 50,
            android.R.drawable.ic_menu_myplaces, 20));
        
        achievements.put("exercises_500", new Achievement(
            "exercises_500", "Exercise Expert", "Complete 500 exercises",
            AchievementType.EXERCISE_COUNT, AchievementLevel.GOLD, 500,
            android.R.drawable.ic_menu_myplaces, 150));
        
        // Meditation achievements
        achievements.put("meditation_10", new Achievement(
            "meditation_10", "Mindful Beginner", "Complete 10 meditation sessions",
            AchievementType.MEDITATION_SESSIONS, AchievementLevel.BRONZE, 10,
            android.R.drawable.ic_menu_myplaces, 30));
        
        achievements.put("meditation_100", new Achievement(
            "meditation_100", "Zen Master", "Complete 100 meditation sessions",
            AchievementType.MEDITATION_SESSIONS, AchievementLevel.PLATINUM, 100,
            android.R.drawable.ic_menu_myplaces, 300));
        
        // Perfect form achievements
        achievements.put("perfect_form_10", new Achievement(
            "perfect_form_10", "Form Focus", "Complete 10 exercises with perfect form",
            AchievementType.PERFECT_FORM, AchievementLevel.SILVER, 10,
            android.R.drawable.ic_menu_myplaces, 40));
        
        // Time goal achievements
        achievements.put("workout_time_30", new Achievement(
            "workout_time_30", "Quick Workout", "Complete a 30-minute workout",
            AchievementType.TIME_GOAL, AchievementLevel.BRONZE, 30,
            android.R.drawable.ic_menu_myplaces, 25));
        
        achievements.put("workout_time_60", new Achievement(
            "workout_time_60", "Hour Hero", "Complete a 60-minute workout",
            AchievementType.TIME_GOAL, AchievementLevel.SILVER, 60,
            android.R.drawable.ic_menu_myplaces, 75));
        
        // Social sharing achievements
        achievements.put("share_5", new Achievement(
            "share_5", "Social Butterfly", "Share 5 workout achievements",
            AchievementType.SOCIAL_SHARING, AchievementLevel.BRONZE, 5,
            android.R.drawable.ic_menu_share, 20));
        
        // Theme unlock achievements
        achievements.put("theme_unlock_3", new Achievement(
            "theme_unlock_3", "Style Seeker", "Unlock 3 different themes",
            AchievementType.THEME_UNLOCK, AchievementLevel.SILVER, 3,
            android.R.drawable.ic_menu_myplaces, 35));
    }
    
    /**
     * Load achievement progress from preferences
     */
    private void loadProgress() {
        totalPoints = preferences.getInt("total_points", 0);
        userLevel = preferences.getInt("user_level", 1);
        
        for (Achievement achievement : achievements.values()) {
            String key = "achievement_" + achievement.id;
            achievement.isUnlocked = preferences.getBoolean(key + "_unlocked", false);
            achievement.currentProgress = preferences.getInt(key + "_progress", 0);
        }
        
        Log.d(TAG, "Loaded achievements - Total points: " + totalPoints + ", Level: " + userLevel);
    }
    
    /**
     * Save achievement progress to preferences
     */
    private void saveProgress() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("total_points", totalPoints);
        editor.putInt("user_level", userLevel);
        
        for (Achievement achievement : achievements.values()) {
            String key = "achievement_" + achievement.id;
            editor.putBoolean(key + "_unlocked", achievement.isUnlocked);
            editor.putInt(key + "_progress", achievement.currentProgress);
        }
        
        editor.apply();
        Log.d(TAG, "Saved achievement progress");
    }
    
    /**
     * Update progress for a specific achievement type
     */
    public void updateProgress(AchievementType type, int progress) {
        List<Achievement> typeAchievements = getAchievementsByType(type);
        
        for (Achievement achievement : typeAchievements) {
            if (!achievement.isUnlocked) {
                achievement.currentProgress += progress;
                
                if (achievement.currentProgress >= achievement.requirement) {
                    unlockAchievement(achievement);
                }
            }
        }
        
        saveProgress();
    }
    
    /**
     * Unlock an achievement
     */
    private void unlockAchievement(Achievement achievement) {
        if (!achievement.isUnlocked) {
            achievement.isUnlocked = true;
            totalPoints += achievement.points;
            
            // Check for level up
            int newLevel = calculateUserLevel();
            if (newLevel > userLevel) {
                userLevel = newLevel;
                Log.d(TAG, "Level up! New level: " + userLevel);
            }
            
            Log.d(TAG, "Achievement unlocked: " + achievement.title + " (+" + achievement.points + " points)");
            
            // Here you could trigger notifications, animations, etc.
        }
    }
    
    /**
     * Calculate user level based on total points
     */
    private int calculateUserLevel() {
        // Simple level calculation: every 100 points = 1 level
        return (totalPoints / 100) + 1;
    }
    
    /**
     * Get all achievements
     */
    public List<Achievement> getAllAchievements() {
        return new ArrayList<>(achievements.values());
    }
    
    /**
     * Get achievements by type
     */
    public List<Achievement> getAchievementsByType(AchievementType type) {
        List<Achievement> typeAchievements = new ArrayList<>();
        for (Achievement achievement : achievements.values()) {
            if (achievement.type == type) {
                typeAchievements.add(achievement);
            }
        }
        return typeAchievements;
    }
    
    /**
     * Get unlocked achievements
     */
    public List<Achievement> getUnlockedAchievements() {
        List<Achievement> unlocked = new ArrayList<>();
        for (Achievement achievement : achievements.values()) {
            if (achievement.isUnlocked) {
                unlocked.add(achievement);
            }
        }
        return unlocked;
    }
    
    /**
     * Get locked achievements
     */
    public List<Achievement> getLockedAchievements() {
        List<Achievement> locked = new ArrayList<>();
        for (Achievement achievement : achievements.values()) {
            if (!achievement.isUnlocked) {
                locked.add(achievement);
            }
        }
        return locked;
    }
    
    /**
     * Get total points
     */
    public int getTotalPoints() {
        return totalPoints;
    }
    
    /**
     * Get user level
     */
    public int getUserLevel() {
        return userLevel;
    }
    
    /**
     * Get achievement by ID
     */
    public Achievement getAchievement(String id) {
        return achievements.get(id);
    }
    
    /**
     * Check if achievement is unlocked
     */
    public boolean isAchievementUnlocked(String id) {
        Achievement achievement = achievements.get(id);
        return achievement != null && achievement.isUnlocked;
    }
    
    /**
     * Get achievement progress percentage
     */
    public float getAchievementProgress(String id) {
        Achievement achievement = achievements.get(id);
        if (achievement != null) {
            return Math.min(100.0f, (float) achievement.currentProgress / achievement.requirement * 100);
        }
        return 0.0f;
    }
    
    /**
     * Get achievements count by level
     */
    public Map<AchievementLevel, Integer> getAchievementCountByLevel() {
        Map<AchievementLevel, Integer> countMap = new HashMap<>();
        for (AchievementLevel level : AchievementLevel.values()) {
            countMap.put(level, 0);
        }
        
        for (Achievement achievement : achievements.values()) {
            if (achievement.isUnlocked) {
                int current = countMap.get(achievement.level);
                countMap.put(achievement.level, current + 1);
            }
        }
        
        return countMap;
    }
    
    /**
     * Reset all achievements (for testing)
     */
    public void resetAchievements() {
        for (Achievement achievement : achievements.values()) {
            achievement.isUnlocked = false;
            achievement.currentProgress = 0;
        }
        totalPoints = 0;
        userLevel = 1;
        saveProgress();
        Log.d(TAG, "All achievements reset");
    }
}
