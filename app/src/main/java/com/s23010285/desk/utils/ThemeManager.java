package com.s23010285.desk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.s23010285.desk.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Premium Theme Manager for DeskBreak App
 * Manages background images, color schemes, and UI themes
 */
public class ThemeManager {
    
    // Theme types
    public enum ThemeType {
        MODERN_FITNESS,
        CALM_MEDITATION,
        ENERGETIC_CARDIO,
        PROFESSIONAL_OFFICE,
        NATURE_INSPIRED,
        DARK_MODE,
        LIGHT_MODE
    }
    
    // Background image categories
    public enum BackgroundCategory {
        WORKOUT_SESSION,
        MEDITATION,
        PROGRESS_SCREEN,
        PROFILE,
        WELCOME,
        DASHBOARD
    }
    
    private static final Map<ThemeType, Integer> themeColors = new HashMap<>();
    private static final Map<BackgroundCategory, Integer> backgroundImages = new HashMap<>();
    private static final Map<ThemeType, Integer> colorSchemes = new HashMap<>();
    
    static {
        // Initialize theme colors
        themeColors.put(ThemeType.MODERN_FITNESS, R.color.accent_green);
        themeColors.put(ThemeType.CALM_MEDITATION, R.color.teal_600);
        themeColors.put(ThemeType.ENERGETIC_CARDIO, R.color.accent_orange);
        themeColors.put(ThemeType.PROFESSIONAL_OFFICE, R.color.primary_blue);
        themeColors.put(ThemeType.NATURE_INSPIRED, R.color.accent_green);
        themeColors.put(ThemeType.DARK_MODE, R.color.background_dark);
        themeColors.put(ThemeType.LIGHT_MODE, R.color.background_light);
        
        // Initialize background images
        backgroundImages.put(BackgroundCategory.WORKOUT_SESSION, R.drawable.workout_background);
        backgroundImages.put(BackgroundCategory.MEDITATION, R.drawable.meditation_background);
        backgroundImages.put(BackgroundCategory.PROGRESS_SCREEN, R.drawable.progress_background);
        backgroundImages.put(BackgroundCategory.PROFILE, R.drawable.profile_background);
        backgroundImages.put(BackgroundCategory.WELCOME, R.drawable.welcome_background);
        backgroundImages.put(BackgroundCategory.DASHBOARD, R.drawable.dashboard_background);
        
        // Initialize color schemes
        colorSchemes.put(ThemeType.MODERN_FITNESS, R.color.scheme_fitness);
        colorSchemes.put(ThemeType.CALM_MEDITATION, R.color.scheme_meditation);
        colorSchemes.put(ThemeType.ENERGETIC_CARDIO, R.color.scheme_cardio);
        colorSchemes.put(ThemeType.PROFESSIONAL_OFFICE, R.color.scheme_office);
    }
    
    private Context context;
    private SharedPreferences preferences;
    private ThemeType currentTheme = ThemeType.MODERN_FITNESS;
    
    public ThemeManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        loadSavedTheme();
    }
    
    /**
     * Load saved theme from preferences
     */
    private void loadSavedTheme() {
        String themeName = preferences.getString("current_theme", ThemeType.MODERN_FITNESS.name());
        try {
            currentTheme = ThemeType.valueOf(themeName);
        } catch (IllegalArgumentException e) {
            currentTheme = ThemeType.MODERN_FITNESS;
        }
    }
    
    /**
     * Set current theme
     */
    public void setTheme(ThemeType theme) {
        this.currentTheme = theme;
        preferences.edit().putString("current_theme", theme.name()).apply();
    }
    
    /**
     * Get current theme
     */
    public ThemeType getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Get background image for category
     */
    public Drawable getBackgroundImage(BackgroundCategory category) {
        Integer imageRes = backgroundImages.get(category);
        if (imageRes != null) {
            return ContextCompat.getDrawable(context, imageRes);
        }
        return null;
    }
    
    /**
     * Get theme color
     */
    public int getThemeColor() {
        Integer colorRes = themeColors.get(currentTheme);
        if (colorRes != null) {
            return ContextCompat.getColor(context, colorRes);
        }
        return ContextCompat.getColor(context, R.color.accent_green);
    }
    
    /**
     * Get color scheme for current theme
     */
    public int getColorScheme() {
        Integer schemeRes = colorSchemes.get(currentTheme);
        if (schemeRes != null) {
            return ContextCompat.getColor(context, schemeRes);
        }
        return ContextCompat.getColor(context, R.color.primary_blue);
    }
    
    /**
     * Get workout session background
     */
    public Drawable getWorkoutBackground() {
        return getBackgroundImage(BackgroundCategory.WORKOUT_SESSION);
    }
    
    /**
     * Get meditation background
     */
    public Drawable getMeditationBackground() {
        return getBackgroundImage(BackgroundCategory.MEDITATION);
    }
    
    /**
     * Get progress screen background
     */
    public Drawable getProgressBackground() {
        return getBackgroundImage(BackgroundCategory.PROGRESS_SCREEN);
    }
    
    /**
     * Get profile background
     */
    public Drawable getProfileBackground() {
        return getBackgroundImage(BackgroundCategory.PROFILE);
    }
    
    /**
     * Get welcome screen background
     */
    public Drawable getWelcomeBackground() {
        return getBackgroundImage(BackgroundCategory.WELCOME);
    }
    
    /**
     * Get dashboard background
     */
    public Drawable getDashboardBackground() {
        return getBackgroundImage(BackgroundCategory.DASHBOARD);
    }
    
    /**
     * Check if dark mode is enabled
     */
    public boolean isDarkMode() {
        return currentTheme == ThemeType.DARK_MODE;
    }
    
    /**
     * Check if light mode is enabled
     */
    public boolean isLightMode() {
        return currentTheme == ThemeType.LIGHT_MODE;
    }
    
    /**
     * Get all available themes
     */
    public ThemeType[] getAvailableThemes() {
        return ThemeType.values();
    }
    
    /**
     * Get theme display name
     */
    public String getThemeDisplayName(ThemeType theme) {
        switch (theme) {
            case MODERN_FITNESS:
                return "Modern Fitness";
            case CALM_MEDITATION:
                return "Calm Meditation";
            case ENERGETIC_CARDIO:
                return "Energetic Cardio";
            case PROFESSIONAL_OFFICE:
                return "Professional Office";
            case NATURE_INSPIRED:
                return "Nature Inspired";
            case DARK_MODE:
                return "Dark Mode";
            case LIGHT_MODE:
                return "Light Mode";
            default:
                return "Default";
        }
    }
    
    /**
     * Get theme description
     */
    public String getThemeDescription(ThemeType theme) {
        switch (theme) {
            case MODERN_FITNESS:
                return "Dynamic and energetic theme for intense workouts";
            case CALM_MEDITATION:
                return "Peaceful and serene theme for meditation sessions";
            case ENERGETIC_CARDIO:
                return "High-energy theme for cardio workouts";
            case PROFESSIONAL_OFFICE:
                return "Clean and professional theme for office environments";
            case NATURE_INSPIRED:
                return "Natural and organic theme inspired by nature";
            case DARK_MODE:
                return "Dark theme for low-light environments";
            case LIGHT_MODE:
                return "Light theme for bright environments";
            default:
                return "Default theme";
        }
    }
}
