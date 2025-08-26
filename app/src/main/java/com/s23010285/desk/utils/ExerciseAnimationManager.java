package com.s23010285.desk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.s23010285.desk.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages exercise GIF animations from web sources
 * Provides high-quality exercise demonstration GIFs for different exercise types
 */
public class ExerciseAnimationManager {
    
    private static final Map<String, String> exerciseGifUrls = new HashMap<>();
    private static final Map<String, String> animationLabels = new HashMap<>();
    
    static {
        // Initialize exercise animations with GIF URLs from reliable sources
        
        // Push-up variations - High quality exercise demonstration GIFs
        exerciseGifUrls.put("push ups", "https://media.giphy.com/media/l4FGGafcOHmrlQxG0/giphy.gif");
        exerciseGifUrls.put("pushups", "https://media.giphy.com/media/l4FGGafcOHmrlQxG0/giphy.gif");
        exerciseGifUrls.put("push up", "https://media.giphy.com/media/l4FGGafcOHmrlQxG0/giphy.gif");
        exerciseGifUrls.put("wall pushups", "https://media.giphy.com/media/l4FGGafcOHmrlQxG0/giphy.gif");
        
        // Squat variations
        exerciseGifUrls.put("squats", "https://media.giphy.com/media/l4FGI0y8rxc2eFa8M/giphy.gif");
        exerciseGifUrls.put("squat", "https://media.giphy.com/media/l4FGI0y8rxc2eFa8M/giphy.gif");
        exerciseGifUrls.put("wall sits", "https://media.giphy.com/media/l4FGI0y8rxc2eFa8M/giphy.gif");
        exerciseGifUrls.put("chair squats", "https://media.giphy.com/media/l4FGI0y8rxc2eFa8M/giphy.gif");
        
        // Stretching exercises
        exerciseGifUrls.put("desk stretches", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("neck stretches", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("shoulder rolls", "https://media.giphy.com/media/l4FGBkJJwxERSN4TC/giphy.gif");
        exerciseGifUrls.put("back stretches", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("wrist stretches", "https://media.giphy.com/media/l4FGBkJJwxERSN4TC/giphy.gif");
        exerciseGifUrls.put("stretching", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("flexibility", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("mobility", "https://media.giphy.com/media/l4FGICAqHy7wH4gKY/giphy.gif");
        exerciseGifUrls.put("posture improvement", "https://media.giphy.com/media/l4FGBkJJwxERSN4TC/giphy.gif");
        
        // Cardio exercises
        exerciseGifUrls.put("lite cardio", "https://media.giphy.com/media/l4FGIqDX7afmJ7jGg/giphy.gif");
        exerciseGifUrls.put("cardio", "https://media.giphy.com/media/l4FGIqDX7afmJ7jGg/giphy.gif");
        exerciseGifUrls.put("walking", "https://media.giphy.com/media/l4FGIqDX7afmJ7jGg/giphy.gif");
        exerciseGifUrls.put("marching", "https://media.giphy.com/media/l4FGIqDX7afmJ7jGg/giphy.gif");
        exerciseGifUrls.put("step ups", "https://media.giphy.com/media/l4FGIqDX7afmJ7jGg/giphy.gif");
        exerciseGifUrls.put("jumping jacks", "https://media.giphy.com/media/l4FGBdCHaQl4R5xNS/giphy.gif");
        
        // Meditation and breathing
        exerciseGifUrls.put("meditation", "https://media.giphy.com/media/l4FGI0WKfgWfeDvUY/giphy.gif");
        exerciseGifUrls.put("breathing exercises", "https://media.giphy.com/media/l4FGI0WKfgWfeDvUY/giphy.gif");
        exerciseGifUrls.put("mental recharge", "https://media.giphy.com/media/l4FGI0WKfgWfeDvUY/giphy.gif");
        exerciseGifUrls.put("relaxation", "https://media.giphy.com/media/l4FGI0WKfgWfeDvUY/giphy.gif");
        exerciseGifUrls.put("mindfulness", "https://media.giphy.com/media/l4FGI0WKfgWfeDvUY/giphy.gif");
        
        // Specific movements
        exerciseGifUrls.put("planks", "https://media.giphy.com/media/l4FGGkUbdOLb6tLfq/giphy.gif");
        exerciseGifUrls.put("lunges", "https://media.giphy.com/media/l4FGHfGd2F3k2k1ri/giphy.gif");
        exerciseGifUrls.put("burpees", "https://media.giphy.com/media/l4FGGdchNTMoVf2Te/giphy.gif");
        
        // Default fallback
        exerciseGifUrls.put("default", "https://media.giphy.com/media/l4FGGafcOHmrlQxG0/giphy.gif");
        
        // Animation labels
        animationLabels.put("push ups", "Follow the push-up motion");
        animationLabels.put("squats", "Follow the squat movement");
        animationLabels.put("desk stretches", "Gentle stretching motion");
        animationLabels.put("lite cardio", "Keep moving with energy");
        animationLabels.put("meditation", "Breathe slowly and deeply");
        animationLabels.put("default", "Follow the exercise pattern");
    }
    
    /**
     * Get GIF URL for a specific exercise
     * @param exerciseName Name of the exercise
     * @return GIF URL string
     */
    public static String getGifUrl(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            return exerciseGifUrls.get("default");
        }
        
        String normalizedName = exerciseName.toLowerCase().trim();
        
        // Direct match
        if (exerciseGifUrls.containsKey(normalizedName)) {
            return exerciseGifUrls.get(normalizedName);
        }
        
        // Partial match search
        for (Map.Entry<String, String> entry : exerciseGifUrls.entrySet()) {
            if (normalizedName.contains(entry.getKey()) || entry.getKey().contains(normalizedName)) {
                return entry.getValue();
            }
        }
        
        // Category-based fallback
        if (normalizedName.contains("push") || normalizedName.contains("press")) {
            return exerciseGifUrls.get("push ups");
        } else if (normalizedName.contains("squat") || normalizedName.contains("sit")) {
            return exerciseGifUrls.get("squats");
        } else if (normalizedName.contains("stretch") || normalizedName.contains("neck") || 
                   normalizedName.contains("shoulder") || normalizedName.contains("back") ||
                   normalizedName.contains("wrist") || normalizedName.contains("posture")) {
            return exerciseGifUrls.get("desk stretches");
        } else if (normalizedName.contains("cardio") || normalizedName.contains("walking") || 
                   normalizedName.contains("marching") || normalizedName.contains("step") ||
                   normalizedName.contains("jumping")) {
            return exerciseGifUrls.get("lite cardio");
        } else if (normalizedName.contains("meditation") || normalizedName.contains("breathing") ||
                   normalizedName.contains("mental") || normalizedName.contains("relax") ||
                   normalizedName.contains("mindful")) {
            return exerciseGifUrls.get("meditation");
        } else if (normalizedName.contains("plank")) {
            return exerciseGifUrls.get("planks");
        } else if (normalizedName.contains("lunge")) {
            return exerciseGifUrls.get("lunges");
        } else if (normalizedName.contains("burpee")) {
            return exerciseGifUrls.get("burpees");
        }
        
        // Ultimate fallback
        return exerciseGifUrls.get("default");
    }
    
    /**
     * Get animation label for exercise
     * @param exerciseName Name of the exercise
     * @return Animation guidance text
     */
    public static String getAnimationLabel(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            return animationLabels.get("default");
        }
        
        String normalizedName = exerciseName.toLowerCase().trim();
        
        // Check for specific labels
        for (Map.Entry<String, String> entry : animationLabels.entrySet()) {
            if (normalizedName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        
        // Category-based labels
        if (normalizedName.contains("push") || normalizedName.contains("press")) {
            return "Follow the push-up motion";
        } else if (normalizedName.contains("squat") || normalizedName.contains("sit")) {
            return "Follow the squat movement";
        } else if (normalizedName.contains("stretch") || normalizedName.contains("flexibility")) {
            return "Gentle stretching motion";
        } else if (normalizedName.contains("cardio") || normalizedName.contains("walking")) {
            return "Keep moving with energy";
        } else if (normalizedName.contains("meditation") || normalizedName.contains("breathing")) {
            return "Breathe slowly and deeply";
        }
        
        return "Follow the exercise pattern";
    }
    
    /**
     * Load and display GIF animation for exercise
     * @param context Application context
     * @param imageView ImageView to display GIF
     * @param labelView TextView to display animation label
     * @param exerciseName Name of the exercise
     */
    public static void loadExerciseAnimation(Context context, ImageView imageView, 
                                           TextView labelView, String exerciseName) {
        // Get GIF URL
        String gifUrl = getGifUrl(exerciseName);
        
        // Load GIF using Glide
        Glide.with(context)
                .asGif()
                .load(gifUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.exercise_default) // Show static image while loading
                .error(R.drawable.exercise_default) // Show static image if GIF fails to load
                .into(imageView);
        
        // Set animation label
        if (labelView != null) {
            String label = getAnimationLabel(exerciseName);
            labelView.setText(label);
        }
    }
    
    /**
     * Pause GIF animation (Note: GIFs auto-loop, this method is for future compatibility)
     * @param imageView ImageView containing the GIF
     */
    public static void stopAnimation(ImageView imageView) {
        // GIFs loaded via Glide automatically loop
        // This method is maintained for compatibility with existing code
        if (imageView != null) {
            // Could implement pause functionality if needed in the future
        }
    }
    
    /**
     * Resume GIF animation (Note: GIFs auto-loop, this method is for future compatibility)
     * @param imageView ImageView containing the GIF
     */
    public static void startAnimation(ImageView imageView) {
        // GIFs loaded via Glide automatically loop
        // This method is maintained for compatibility with existing code
        if (imageView != null) {
            // Could implement resume functionality if needed in the future
        }
    }
    
    /**
     * Check if exercise has custom GIF animation
     * @param exerciseName Name of the exercise
     * @return true if custom GIF exists
     */
    public static boolean hasCustomAnimation(String exerciseName) {
        return !getGifUrl(exerciseName).equals(exerciseGifUrls.get("default"));
    }
    
    /**
     * Get GIF URL for workout category
     * @param category Workout category
     * @return GIF URL string
     */
    public static String getGifUrlForCategory(String category) {
        if (category == null) {
            return exerciseGifUrls.get("default");
        }
        
        String normalizedCategory = category.toLowerCase().trim();
        
        switch (normalizedCategory) {
            case "cardio":
            case "lite cardio":
                return exerciseGifUrls.get("lite cardio");
            case "strength":
            case "strength training":
                return exerciseGifUrls.get("push ups");
            case "mental recharge":
            case "meditation":
                return exerciseGifUrls.get("meditation");
            case "flexibility":
            case "stretching":
                return exerciseGifUrls.get("desk stretches");
            default:
                return exerciseGifUrls.get("default");
        }
    }
    
    /**
     * Get all available exercise types with GIF animations
     * @return Array of exercise names that have GIFs
     */
    public static String[] getAvailableAnimations() {
        return exerciseGifUrls.keySet().toArray(new String[0]);
    }
}
