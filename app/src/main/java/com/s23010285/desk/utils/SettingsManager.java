package com.s23010285.desk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing app settings and preferences
 * This class helps save and retrieve user preferences like notifications, themes, and goals
 * It's like a digital settings manager that remembers how the user wants the app to work
 */
public class SettingsManager {
    
    // This constant defines the name of the preferences file where we store settings
    private static final String PREF_NAME = "DeskBreakSettings";
    
    // Notification settings - these control what notifications the app sends to the user
    // KEY_NOTIFICATIONS_ENABLED controls whether any notifications are shown
    public static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    // KEY_STEP_TRACKING_ENABLED controls whether step tracking notifications are shown
    public static final String KEY_STEP_TRACKING_ENABLED = "step_tracking_enabled";
    // KEY_WORKOUT_REMINDERS_ENABLED controls whether workout reminder notifications are shown
    public static final String KEY_WORKOUT_REMINDERS_ENABLED = "workout_reminders_enabled";
    
    // Feature settings - these control different app features and behaviors
    // KEY_DARK_MODE_ENABLED controls whether the app uses dark theme or light theme
    public static final String KEY_DARK_MODE_ENABLED = "dark_mode_enabled";
    // KEY_DATA_SYNC_ENABLED controls whether the app syncs data with the cloud
    public static final String KEY_DATA_SYNC_ENABLED = "data_sync_enabled";
    // KEY_LOCATION_SERVICES_ENABLED controls whether the app can access the user's location
    public static final String KEY_LOCATION_SERVICES_ENABLED = "location_services_enabled";
    // KEY_SOUND_EFFECTS_ENABLED controls whether the app plays sound effects
    public static final String KEY_SOUND_EFFECTS_ENABLED = "sound_effects_enabled";
    // KEY_VIBRATION_ENABLED controls whether the app vibrates the device
    public static final String KEY_VIBRATION_ENABLED = "vibration_enabled";
    
    // Goal settings - these store the user's fitness goals and preferences
    // KEY_DAILY_STEPS_GOAL stores how many steps the user wants to take each day
    public static final String KEY_DAILY_STEPS_GOAL = "daily_steps_goal";
    // KEY_DAILY_WORKOUT_GOAL stores how many workouts the user wants to do each day
    public static final String KEY_DAILY_WORKOUT_GOAL = "daily_workout_goal";
    // KEY_BREAK_REMINDER_INTERVAL stores how often the app reminds the user to take breaks
    public static final String KEY_BREAK_REMINDER_INTERVAL = "break_reminder_interval";
    
    // Achievement settings - these control achievement-related notifications
    // KEY_ACHIEVEMENT_NOTIFICATIONS controls whether achievement notifications are shown
    public static final String KEY_ACHIEVEMENT_NOTIFICATIONS = "achievement_notifications";
    // KEY_MILESTONE_NOTIFICATIONS controls whether milestone notifications are shown
    public static final String KEY_MILESTONE_NOTIFICATIONS = "milestone_notifications";
    
    // These variables help manage the settings manager
    // instance is the single instance of this class (singleton pattern)
    private static SettingsManager instance;
    // sharedPreferences is where we store all the settings permanently
    private SharedPreferences sharedPreferences;
    
    /**
     * Private constructor for the SettingsManager
     * This method sets up the preferences file where we store settings
     * @param context The app's context, which helps us access system resources
     */
    private SettingsManager(Context context) {
        // Get access to the preferences file where we store settings
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    /**
     * Get the single instance of the SettingsManager
     * This method uses the singleton pattern to ensure only one settings manager exists
     * @param context The app's context, which helps us access system resources
     * @return The SettingsManager instance
     */
    public static synchronized SettingsManager getInstance(Context context) {
        // If no instance exists yet, create one
        if (instance == null) {
            instance = new SettingsManager(context.getApplicationContext());
        }
        // Return the existing instance
        return instance;
    }
    
    // Notification settings methods - these control what notifications the app shows
    
    /**
     * Check if notifications are enabled
     * @return true if notifications are enabled, false if they're disabled
     */
    public boolean areNotificationsEnabled() {
        return sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }
    
    /**
     * Enable or disable notifications
     * @param enabled true to enable notifications, false to disable them
     */
    public void setNotificationsEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }
    
    /**
     * Check if step tracking notifications are enabled
     * @return true if step tracking notifications are enabled, false if they're disabled
     */
    public boolean isStepTrackingEnabled() {
        return sharedPreferences.getBoolean(KEY_STEP_TRACKING_ENABLED, true);
    }
    
    /**
     * Enable or disable step tracking notifications
     * @param enabled true to enable step tracking notifications, false to disable them
     */
    public void setStepTrackingEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_STEP_TRACKING_ENABLED, enabled).apply();
    }
    
    /**
     * Check if workout reminder notifications are enabled
     * @return true if workout reminder notifications are enabled, false if they're disabled
     */
    public boolean areWorkoutRemindersEnabled() {
        return sharedPreferences.getBoolean(KEY_WORKOUT_REMINDERS_ENABLED, true);
    }
    
    /**
     * Enable or disable workout reminder notifications
     * @param enabled true to enable workout reminder notifications, false to disable them
     */
    public void setWorkoutRemindersEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_WORKOUT_REMINDERS_ENABLED, enabled).apply();
    }
    
    // Feature settings methods - these control different app features
    
    /**
     * Check if dark mode is enabled
     * @return true if dark mode is enabled, false if light mode is used
     */
    public boolean isDarkModeEnabled() {
        return sharedPreferences.getBoolean(KEY_DARK_MODE_ENABLED, false);
    }
    
    /**
     * Enable or disable dark mode
     * @param enabled true to enable dark mode, false to use light mode
     */
    public void setDarkModeEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE_ENABLED, enabled).apply();
    }
    
    /**
     * Check if data sync is enabled
     * @return true if data sync is enabled, false if it's disabled
     */
    public boolean isDataSyncEnabled() {
        return sharedPreferences.getBoolean(KEY_DATA_SYNC_ENABLED, true);
    }
    
    /**
     * Enable or disable data sync
     * @param enabled true to enable data sync, false to disable it
     */
    public void setDataSyncEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_DATA_SYNC_ENABLED, enabled).apply();
    }
    
    /**
     * Check if location services are enabled
     * @return true if location services are enabled, false if they're disabled
     */
    public boolean isLocationServicesEnabled() {
        return sharedPreferences.getBoolean(KEY_LOCATION_SERVICES_ENABLED, false);
    }
    
    /**
     * Enable or disable location services
     * @param enabled true to enable location services, false to disable them
     */
    public void setLocationServicesEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_LOCATION_SERVICES_ENABLED, enabled).apply();
    }
    
    /**
     * Check if sound effects are enabled
     * @return true if sound effects are enabled, false if they're disabled
     */
    public boolean areSoundEffectsEnabled() {
        return sharedPreferences.getBoolean(KEY_SOUND_EFFECTS_ENABLED, true);
    }
    
    /**
     * Enable or disable sound effects
     * @param enabled true to enable sound effects, false to disable them
     */
    public void setSoundEffectsEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_SOUND_EFFECTS_ENABLED, enabled).apply();
    }
    
    /**
     * Check if vibration is enabled
     * @return true if vibration is enabled, false if it's disabled
     */
    public boolean isVibrationEnabled() {
        return sharedPreferences.getBoolean(KEY_VIBRATION_ENABLED, true);
    }
    
    /**
     * Enable or disable vibration
     * @param enabled true to enable vibration, false to disable it
     */
    public void setVibrationEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_VIBRATION_ENABLED, enabled).apply();
    }
    
    // Goal settings
    public int getDailyStepsGoal() {
        return sharedPreferences.getInt(KEY_DAILY_STEPS_GOAL, 10000);
    }
    
    public void setDailyStepsGoal(int goal) {
        sharedPreferences.edit().putInt(KEY_DAILY_STEPS_GOAL, goal).apply();
    }
    
    public int getDailyWorkoutGoal() {
        return sharedPreferences.getInt(KEY_DAILY_WORKOUT_GOAL, 30);
    }
    
    public void setDailyWorkoutGoal(int minutes) {
        sharedPreferences.edit().putInt(KEY_DAILY_WORKOUT_GOAL, minutes).apply();
    }
    
    public int getBreakReminderInterval() {
        return sharedPreferences.getInt(KEY_BREAK_REMINDER_INTERVAL, 60);
    }
    
    public void setBreakReminderInterval(int minutes) {
        sharedPreferences.edit().putInt(KEY_BREAK_REMINDER_INTERVAL, minutes).apply();
    }
    
    // Achievement settings
    public boolean areAchievementNotificationsEnabled() {
        return sharedPreferences.getBoolean(KEY_ACHIEVEMENT_NOTIFICATIONS, true);
    }
    
    public void setAchievementNotificationsEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_ACHIEVEMENT_NOTIFICATIONS, enabled).apply();
    }
    
    public boolean areMilestoneNotificationsEnabled() {
        return sharedPreferences.getBoolean(KEY_MILESTONE_NOTIFICATIONS, true);
    }
    
    public void setMilestoneNotificationsEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_MILESTONE_NOTIFICATIONS, enabled).apply();
    }
    
    // Utility methods
    public void resetToDefaults() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    
    public void exportSettings() {
        // TODO: Implement settings export functionality
    }
    
    public void importSettings(String settingsData) {
        // TODO: Implement settings import functionality
    }
}
