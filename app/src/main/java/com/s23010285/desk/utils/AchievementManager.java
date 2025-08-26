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
 */
public class AchievementManager {
    
    // Achievement types
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
    
    // Achievement levels
    public enum AchievementLevel {
        BRONZE,
        SILVER,
        GOLD,
        PLATINUM,
        DIAMOND
    }
    
    // Achievement data structure
    public static class Achievement {
        public final String id;
        public final String title;
        public final String description;
        public final AchievementType type;
        public final AchievementLevel level;
        public final int requirement;
        public final int iconResId;
        public final int points;
        public boolean isUnlocked;
        public int currentProgress;
        
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
            this.isUnlocked = false;
            this.currentProgress = 0;
        }
    }
    
    private static final String TAG = "AchievementManager";
    private Context context;
    private SharedPreferences preferences;
    private Map<String, Achievement> achievements;
    private int totalPoints = 0;
    private int userLevel = 1;
    
    public AchievementManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE);
        initializeAchievements();
        loadProgress();
    }
    
    /**
     * Initialize all available achievements
     */
    private void initializeAchievements() {
        achievements = new HashMap<>();
        
        // Workout streak achievements
        achievements.put("streak_3", new Achievement(
            "streak_3", "Getting Started", "Complete 3 workouts in a row",
            AchievementType.WORKOUT_STREAK, AchievementLevel.BRONZE, 3, 
            android.R.drawable.ic_menu_myplaces, 10));
        
        achievements.put("streak_7", new Achievement(
            "streak_7", "Week Warrior", "Complete 7 workouts in a row",
            AchievementType.WORKOUT_STREAK, AchievementLevel.SILVER, 7,
            android.R.drawable.ic_menu_myplaces, 25));
        
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
