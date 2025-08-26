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
 * This class is like a personal stylist that helps customize how the app looks
 */
public class ThemeManager {
    
    // Theme types - these define different visual styles for the app
    // Each theme has its own color scheme and background images
    public enum ThemeType {
        MODERN_FITNESS,      // Modern fitness theme with energetic colors
        CALM_MEDITATION,     // Calm meditation theme with soothing colors
        ENERGETIC_CARDIO,    // Energetic cardio theme with vibrant colors
        PROFESSIONAL_OFFICE, // Professional office theme with business colors
        NATURE_INSPIRED,     // Nature-inspired theme with earth tones
        DARK_MODE,           // Dark theme for low-light environments
        LIGHT_MODE           // Light theme for bright environments
    }
    
    // Background image categories - these define where different background images are used
    // Each screen can have its own background image
    public enum BackgroundCategory {
        WORKOUT_SESSION,     // Background for workout screens
        MEDITATION,          // Background for meditation screens
        PROGRESS_SCREEN,     // Background for progress tracking screens
        PROFILE,             // Background for user profile screens
        WELCOME,             // Background for welcome and onboarding screens
        DASHBOARD            // Background for main dashboard screens
    }
    
    // These maps store the relationship between themes and their visual elements
    // themeColors maps each theme to its primary color
    private static final Map<ThemeType, Integer> themeColors = new HashMap<>();
    // backgroundImages maps each background category to its image resource
    private static final Map<BackgroundCategory, Integer> backgroundImages = new HashMap<>();
    // colorSchemes maps each theme to its complete color scheme
    private static final Map<ThemeType, Integer> colorSchemes = new HashMap<>();
    
    // Static initialization block - this runs once when the class is first loaded
    // It sets up all the theme colors, background images, and color schemes
    static {
        // Initialize theme colors - each theme gets its own primary color
        themeColors.put(ThemeType.MODERN_FITNESS, R.color.accent_green);
        themeColors.put(ThemeType.CALM_MEDITATION, R.color.teal_600);
        themeColors.put(ThemeType.ENERGETIC_CARDIO, R.color.accent_orange);
        themeColors.put(ThemeType.PROFESSIONAL_OFFICE, R.color.primary_blue);
        themeColors.put(ThemeType.NATURE_INSPIRED, R.color.accent_green);
        themeColors.put(ThemeType.DARK_MODE, R.color.background_dark);
        themeColors.put(ThemeType.LIGHT_MODE, R.color.background_light);
        
        // Initialize background images - each screen category gets its own background
        backgroundImages.put(BackgroundCategory.WORKOUT_SESSION, R.drawable.workout_background);
        backgroundImages.put(BackgroundCategory.MEDITATION, R.drawable.meditation_background);
        backgroundImages.put(BackgroundCategory.PROGRESS_SCREEN, R.drawable.progress_background);
        backgroundImages.put(BackgroundCategory.PROFILE, R.drawable.profile_background);
        backgroundImages.put(BackgroundCategory.WELCOME, R.drawable.welcome_background);
        backgroundImages.put(BackgroundCategory.DASHBOARD, R.drawable.dashboard_background);
        
        // Initialize color schemes - each theme gets its complete color palette
        colorSchemes.put(ThemeType.MODERN_FITNESS, R.color.scheme_fitness);
        colorSchemes.put(ThemeType.CALM_MEDITATION, R.color.scheme_meditation);
        colorSchemes.put(ThemeType.ENERGETIC_CARDIO, R.color.scheme_cardio);
        colorSchemes.put(ThemeType.PROFESSIONAL_OFFICE, R.color.scheme_office);
    }
    
    // These variables help manage the current theme
    // context helps us access the app's resources and preferences
    private Context context;
    // preferences stores which theme the user has selected
    private SharedPreferences preferences;
    // currentTheme tracks which theme is currently active
    private ThemeType currentTheme = ThemeType.MODERN_FITNESS;
    
    /**
     * Constructor for the ThemeManager
     * This method sets up the theme system and loads the user's saved theme
     * @param context The app's context, which helps us access system resources
     */
    public ThemeManager(Context context) {
        this.context = context;
        // Get access to the preferences file where we store theme choices
        this.preferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        // Load the theme the user previously selected
        loadSavedTheme();
    }
    
    /**
     * Load saved theme from preferences
     * This method reads the user's theme choice from storage
     */
    private void loadSavedTheme() {
        // Get the theme name from preferences, default to MODERN_FITNESS if none saved
        String themeName = preferences.getString("current_theme", ThemeType.MODERN_FITNESS.name());
        try {
            // Convert the theme name string to a ThemeType enum value
            currentTheme = ThemeType.valueOf(themeName);
        } catch (IllegalArgumentException e) {
            // If the saved theme name is invalid, use the default theme
            currentTheme = ThemeType.MODERN_FITNESS;
        }
    }
    
    /**
     * Set current theme
     * This method changes the app's theme and saves the user's choice
     * @param theme The new theme to apply
     */
    public void setTheme(ThemeType theme) {
        // Update the current theme
        this.currentTheme = theme;
        // Save the user's choice to preferences so it persists
        preferences.edit().putString("current_theme", theme.name()).apply();
    }
    
    /**
     * Get current theme
     * This method returns which theme is currently active
     * @return The currently active theme
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
