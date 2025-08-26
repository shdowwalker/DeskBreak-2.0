package com.s23010285.desk.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Social Features Manager for DeskBreak App
 * Handles workout sharing, social media integration, and community features
 * This class is like a social media assistant that helps users share their fitness achievements
 */
public class SocialFeaturesManager {
    
    // These variables help manage social sharing features
    // TAG is used for logging messages to help with debugging
    private static final String TAG = "SocialFeaturesManager";
    // context helps us access the app's resources and create sharing intents
    private Context context;
    
    // Social media platforms - these define which social networks we can share to
    // Each platform has different requirements for sharing content
    public enum SocialPlatform {
        INSTAGRAM,      // Instagram - good for visual content like workout photos
        FACEBOOK,       // Facebook - good for sharing with friends and family
        TWITTER,        // Twitter - good for short workout updates
        WHATSAPP,       // WhatsApp - good for sharing with close contacts
        TELEGRAM,       // Telegram - good for sharing with groups
        EMAIL,          // Email - good for sharing detailed workout reports
        GENERAL_SHARE   // General sharing - lets the user choose the app
    }
    
    /**
     * Constructor for the SocialFeaturesManager
     * This method sets up the social sharing system
     * @param context The app's context, which helps us access system resources
     */
    public SocialFeaturesManager(Context context) {
        this.context = context;
    }
    
    /**
     * Share workout achievement to social media
     * This method helps users share their fitness accomplishments with friends
     * @param achievementTitle The name of the achievement the user earned
     * @param workoutDetails Details about the workout that earned the achievement
     * @param platform Which social media platform to share to
     */
    public void shareWorkoutAchievement(String achievementTitle, String workoutDetails, 
                                      SocialPlatform platform) {
        try {
            // Create the text that will be shared
            String shareText = createAchievementShareText(achievementTitle, workoutDetails);
            // Add hashtags to make the post more discoverable
            String hashtags = "#DeskBreak #Fitness #Workout #Achievement";
            
            // Share to different platforms based on user's choice
            switch (platform) {
                case INSTAGRAM:
                    // Share to Instagram with hashtags
                    shareToInstagram(shareText + " " + hashtags);
                    break;
                case FACEBOOK:
                    // Share to Facebook with hashtags
                    shareToFacebook(shareText + " " + hashtags);
                    break;
                case TWITTER:
                    // Share to Twitter with hashtags
                    shareToTwitter(shareText + " " + hashtags);
                    break;
                case WHATSAPP:
                    // Share to WhatsApp with hashtags
                    shareToWhatsApp(shareText + " " + hashtags);
                    break;
                case TELEGRAM:
                    // Share to Telegram with hashtags
                    shareToTelegram(shareText + " " + hashtags);
                    break;
                case EMAIL:
                    // Share via email with a subject line
                    shareViaEmail(shareText, "Workout Achievement - DeskBreak");
                    break;
                case GENERAL_SHARE:
                    // Let the user choose which app to share to
                    shareGeneral(shareText + " " + hashtags, "Workout Achievement");
                    break;
            }
            
            // Log that the sharing was successful
            Log.d(TAG, "Shared workout achievement: " + achievementTitle);
            
        } catch (Exception e) {
            // Log any errors that occur during sharing
            Log.e(TAG, "Error sharing workout achievement: " + e.getMessage());
        }
    }
    
    /**
     * Share workout summary
     * This method helps users share details about their completed workouts
     * @param workoutType What kind of workout the user did (e.g., "Cardio", "Strength")
     * @param duration How long the workout lasted in minutes
     * @param calories How many calories the workout burned
     * @param exercisesCompleted How many exercises the user completed
     */
    public void shareWorkoutSummary(String workoutType, int duration, int calories, 
                                   int exercisesCompleted) {
        try {
            // Create the text that will be shared
            String shareText = createWorkoutSummaryText(workoutType, duration, calories, exercisesCompleted);
            // Add hashtags to make the post more discoverable
            String hashtags = "#DeskBreak #Workout #Fitness #Health";
            
            // Share using the general sharing method
            shareGeneral(shareText + " " + hashtags, "Workout Summary");
            
            // Log that the sharing was successful
            Log.d(TAG, "Shared workout summary: " + workoutType);
            
        } catch (Exception e) {
            // Log any errors that occur during sharing
            Log.e(TAG, "Error sharing workout summary: " + e.getMessage());
        }
