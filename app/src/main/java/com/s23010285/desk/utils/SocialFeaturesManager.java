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
 */
public class SocialFeaturesManager {
    
    private static final String TAG = "SocialFeaturesManager";
    private Context context;
    
    // Social media platforms
    public enum SocialPlatform {
        INSTAGRAM,
        FACEBOOK,
        TWITTER,
        WHATSAPP,
        TELEGRAM,
        EMAIL,
        GENERAL_SHARE
    }
    
    public SocialFeaturesManager(Context context) {
        this.context = context;
    }
    
    /**
     * Share workout achievement to social media
     */
    public void shareWorkoutAchievement(String achievementTitle, String workoutDetails, 
                                      SocialPlatform platform) {
        try {
            String shareText = createAchievementShareText(achievementTitle, workoutDetails);
            String hashtags = "#DeskBreak #Fitness #Workout #Achievement";
            
            switch (platform) {
                case INSTAGRAM:
                    shareToInstagram(shareText + " " + hashtags);
                    break;
                case FACEBOOK:
                    shareToFacebook(shareText + " " + hashtags);
                    break;
                case TWITTER:
                    shareToTwitter(shareText + " " + hashtags);
                    break;
                case WHATSAPP:
                    shareToWhatsApp(shareText + " " + hashtags);
                    break;
                case TELEGRAM:
                    shareToTelegram(shareText + " " + hashtags);
                    break;
                case EMAIL:
                    shareViaEmail(shareText, "Workout Achievement - DeskBreak");
                    break;
                case GENERAL_SHARE:
                    shareGeneral(shareText + " " + hashtags, "Workout Achievement");
                    break;
            }
            
            Log.d(TAG, "Shared workout achievement: " + achievementTitle);
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing workout achievement: " + e.getMessage());
        }
    }
    
    /**
     * Share workout summary
     */
    public void shareWorkoutSummary(String workoutType, int duration, int calories, 
                                   int exercisesCompleted) {
        try {
            String shareText = createWorkoutSummaryText(workoutType, duration, calories, exercisesCompleted);
            String hashtags = "#DeskBreak #Workout #Fitness #Health";
            
            shareGeneral(shareText + " " + hashtags, "Workout Summary");
            
            Log.d(TAG, "Shared workout summary: " + workoutType);
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing workout summary: " + e.getMessage());
        }
    }
    
    /**
     * Share progress milestone
     */
    public void shareProgressMilestone(String milestone, int value, String unit) {
        try {
            String shareText = createMilestoneShareText(milestone, value, unit);
            String hashtags = "#DeskBreak #Progress #Milestone #Fitness";
            
            shareGeneral(shareText + " " + hashtags, "Progress Milestone");
            
            Log.d(TAG, "Shared progress milestone: " + milestone);
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing progress milestone: " + e.getMessage());
        }
    }
    
    /**
     * Create achievement share text
     */
    private String createAchievementShareText(String achievementTitle, String workoutDetails) {
        return "🎉 Just unlocked '" + achievementTitle + "' on DeskBreak! " +
               workoutDetails + " 💪 Keep pushing towards your fitness goals!";
    }
    
    /**
     * Create workout summary text
     */
    private String createWorkoutSummaryText(String workoutType, int duration, int calories, 
                                          int exercisesCompleted) {
        return "🔥 Completed " + workoutType + " workout on DeskBreak! " +
               "Duration: " + duration + " minutes, " +
               "Calories: " + calories + ", " +
               "Exercises: " + exercisesCompleted + " 💪";
    }
    
    /**
     * Create milestone share text
     */
    private String createMilestoneShareText(String milestone, int value, String unit) {
        return "🏆 Reached " + milestone + " milestone on DeskBreak! " +
               "Achieved " + value + " " + unit + " 🎯";
    }
    
    /**
     * Share to Instagram
     */
    private void shareToInstagram(String text) {
        try {
            Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("text", text);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback to general share
            shareGeneral(text, "Share to Instagram");
        }
    }
    
    /**
     * Share to Facebook
     */
    private void shareToFacebook(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setPackage("com.facebook.katana");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback to general share
            shareGeneral(text, "Share to Facebook");
        }
    }
    
    /**
     * Share to Twitter
     */
    private void shareToTwitter(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setPackage("com.twitter.android");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback to general share
            shareGeneral(text, "Share to Twitter");
        }
    }
    
    /**
     * Share to WhatsApp
     */
    private void shareToWhatsApp(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setPackage("com.whatsapp");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback to general share
            shareGeneral(text, "Share to WhatsApp");
        }
    }
    
    /**
     * Share to Telegram
     */
    private void shareToTelegram(String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setPackage("org.telegram.messenger");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // Fallback to general share
            shareGeneral(text, "Share to Telegram");
        }
    }
    
    /**
     * Share via email
     */
    private void shareViaEmail(String text, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        try {
            context.startActivity(Intent.createChooser(intent, "Send email..."));
        } catch (Exception e) {
            Log.e(TAG, "Error sharing via email: " + e.getMessage());
        }
    }
    
    /**
     * General share intent
     */
    private void shareGeneral(String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        try {
            context.startActivity(Intent.createChooser(intent, title));
        } catch (Exception e) {
            Log.e(TAG, "Error with general share: " + e.getMessage());
        }
    }
    
    /**
     * Create workout achievement image for sharing
     */
    public File createAchievementImage(String achievementTitle, String achievementDescription, 
                                     int points, String date) {
        try {
            // Create a bitmap with achievement design
            int width = 1080;
            int height = 1080;
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            
            // Background
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.parseColor("#1A237E"));
            canvas.drawRect(0, 0, width, height, backgroundPaint);
            
            // Gradient overlay
            Paint gradientPaint = new Paint();
            gradientPaint.setColor(Color.parseColor("#3949AB"));
            canvas.drawRect(0, height/2, width, height, gradientPaint);
            
            // Title text
            Paint titlePaint = new Paint();
            titlePaint.setColor(Color.WHITE);
            titlePaint.setTextSize(60);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextAlign(Paint.Align.CENTER);
            
            canvas.drawText("🏆 ACHIEVEMENT UNLOCKED! 🏆", width/2, 200, titlePaint);
            
            // Achievement title
            Paint achievementPaint = new Paint();
            achievementPaint.setColor(Color.parseColor("#FFD23F"));
            achievementPaint.setTextSize(50);
            achievementPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            achievementPaint.setTextAlign(Paint.Align.CENTER);
            
            canvas.drawText(achievementTitle, width/2, 300, achievementPaint);
            
            // Description
            Paint descPaint = new Paint();
            descPaint.setColor(Color.WHITE);
            descPaint.setTextSize(35);
            descPaint.setTextAlign(Paint.Align.CENTER);
            
            // Wrap text if too long
            String[] words = achievementDescription.split(" ");
            StringBuilder line = new StringBuilder();
            int y = 400;
            
            for (String word : words) {
                if (line.length() + word.length() > 25) {
                    canvas.drawText(line.toString(), width/2, y, descPaint);
                    line = new StringBuilder(word + " ");
                    y += 50;
                } else {
                    line.append(word).append(" ");
                }
            }
            if (line.length() > 0) {
                canvas.drawText(line.toString(), width/2, y, descPaint);
            }
            
            // Points
            Paint pointsPaint = new Paint();
            pointsPaint.setColor(Color.parseColor("#4CAF50"));
            pointsPaint.setTextSize(45);
            pointsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            pointsPaint.setTextAlign(Paint.Align.CENTER);
            
            canvas.drawText("+" + points + " POINTS", width/2, 600, pointsPaint);
            
            // Date
            Paint datePaint = new Paint();
            datePaint.setColor(Color.LTGRAY);
            datePaint.setTextSize(30);
            datePaint.setTextAlign(Paint.Align.CENTER);
            
            canvas.drawText("Earned on " + date, width/2, 700, datePaint);
            
            // App branding
            Paint brandPaint = new Paint();
            brandPaint.setColor(Color.parseColor("#FF9800"));
            brandPaint.setTextSize(40);
            brandPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            brandPaint.setTextAlign(Paint.Align.CENTER);
            
            canvas.drawText("DeskBreak", width/2, 800, brandPaint);
            
            // Save to file
            File file = new File(context.getCacheDir(), "achievement_" + System.currentTimeMillis() + ".png");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            
            return file;
            
        } catch (IOException e) {
            Log.e(TAG, "Error creating achievement image: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Share achievement image
     */
    public void shareAchievementImage(File imageFile, String achievementTitle) {
        try {
            Uri imageUri = FileProvider.getUriForFile(context, 
                context.getPackageName() + ".provider", imageFile);
            
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(Intent.EXTRA_TEXT, "Just unlocked '" + achievementTitle + 
                          "' on DeskBreak! 🎉💪");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            context.startActivity(Intent.createChooser(intent, "Share Achievement"));
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing achievement image: " + e.getMessage());
        }
    }
    
    /**
     * Get current date string
     */
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
    
    /**
     * Share workout completion with image
     */
    public void shareWorkoutCompletion(String workoutType, int duration, int calories) {
        try {
            String shareText = "💪 Just completed " + workoutType + " workout on DeskBreak! " +
                              "Duration: " + duration + " minutes, Calories: " + calories + 
                              " 🔥 #DeskBreak #Fitness #Workout";
            
            shareGeneral(shareText, "Share Workout Completion");
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing workout completion: " + e.getMessage());
        }
    }
    
    /**
     * Share step goal achievement
     */
    public void shareStepGoal(int steps, int goal) {
        try {
            String shareText = "👟 Reached " + steps + " steps today on DeskBreak! " +
                              "Goal: " + goal + " steps 🎯 #DeskBreak #Steps #Fitness";
            
            shareGeneral(shareText, "Share Step Goal");
            
        } catch (Exception e) {
            Log.e(TAG, "Error sharing step goal: " + e.getMessage());
        }
    }
}
