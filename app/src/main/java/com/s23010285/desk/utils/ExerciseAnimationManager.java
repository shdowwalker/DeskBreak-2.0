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
 * Manages animated GIFs for exercises with fallback animations
 * Provides visual guidance for proper exercise form and movement
 * This class is like a digital exercise guide that shows users exactly how to do each exercise
 */
public class ExerciseAnimationManager {
    
    // Animation data structure - this class holds information about a single exercise animation
    // It's like a digital animation card that contains everything needed to show the exercise
    public static class AnimationData {
        // These variables store the animation's basic information
        // gifUrl points to the animated GIF file that shows the exercise
        public final String gifUrl;
        // fallbackUrl points to a static image if the GIF can't be loaded
        public final String fallbackUrl;
        // title is the display name of the exercise animation
        public final String title;
        // description explains what the animation shows
        public final String description;
        
        /**
         * Constructor for the AnimationData class
         * This method creates a new animation data object with all its information
         * @param gifUrl The URL to the animated GIF file
         * @param fallbackUrl The URL to a static image as backup
         * @param title The display name of the exercise animation
         * @param description What the animation shows
         */
        public AnimationData(String gifUrl, String fallbackUrl, String title, String description) {
            this.gifUrl = gifUrl;
            this.fallbackUrl = fallbackUrl;
            this.title = title;
            this.description = description;
        }
    }
    
    // This map stores all the exercise animations and their data
    // Each exercise name maps to its corresponding animation information
    private static final Map<String, AnimationData> exerciseAnimations = new HashMap<>();
    
    // Static initialization block - this runs once when the class is first loaded
    // It sets up all the exercise animations with their GIF URLs and fallback images
    static {
        // Initialize exercise animations with GIF URLs and fallback images
        // Format: "exercise_name" -> AnimationData("gif_url", "fallback_url", "title", "description")
        
        // Push-up variations - these animations show different types of push-ups
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("push ups", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/pushup.jpg",
            "Push-Up Animation",
            "Shows proper push-up form with full body movement"));
        
        exerciseAnimations.put("wall pushups", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/wall-pushup.jpg",
            "Wall Push-Up Animation",
            "Shows wall push-ups for beginners"));
        
        // Squat variations - these animations show different types of squats
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("squats", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/squat.jpg",
            "Squat Animation",
            "Shows proper squat form with knee alignment"));
        
        exerciseAnimations.put("wall sits", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/wall-sit.jpg",
            "Wall Sit Animation",
            "Shows wall sit position and form"));
        
        // Stretching exercises - these animations show stretching movements
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("desk stretches", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/desk-stretch.jpg",
            "Desk Stretch Animation",
            "Shows stretching exercises you can do at your desk"));
        
        exerciseAnimations.put("neck stretches", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/neck-stretch.jpg",
            "Neck Stretch Animation",
            "Shows neck and shoulder stretching movements"));
        
        exerciseAnimations.put("shoulder rolls", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/shoulder-roll.jpg",
            "Shoulder Roll Animation",
            "Shows circular shoulder movements"));
        
        exerciseAnimations.put("back stretches", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/back-stretch.jpg",
            "Back Stretch Animation",
            "Shows back stretching and flexibility exercises"));
        
        exerciseAnimations.put("wrist stretches", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/wrist-stretch.jpg",
            "Wrist Stretch Animation",
            "Shows wrist and hand stretching exercises"));
        
        // Cardio exercises - these animations show cardiovascular movements
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("lite cardio", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/lite-cardio.jpg",
            "Light Cardio Animation",
            "Shows low-impact cardio exercises"));
        
        exerciseAnimations.put("walking", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/walking.jpg",
            "Walking Animation",
            "Shows walking in place exercise"));
        
        exerciseAnimations.put("marching", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/marching.jpg",
            "Marching Animation",
            "Shows marching in place exercise"));
        
        exerciseAnimations.put("jumping jacks", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/jumping-jacks.jpg",
            "Jumping Jacks Animation",
            "Shows jumping jacks with proper form"));
        
        // Core exercises - these animations show core strengthening movements
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("planks", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/plank.jpg",
            "Plank Animation",
            "Shows proper plank position and form"));
        
        exerciseAnimations.put("lunges", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/lunge.jpg",
            "Lunge Animation",
            "Shows proper lunge form and movement"));
        
        exerciseAnimations.put("burpees", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/burpee.jpg",
            "Burpee Animation",
            "Shows full burpee movement sequence"));
        
        // Meditation and breathing - these animations show breathing patterns
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("meditation", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/meditation.jpg",
            "Meditation Animation",
            "Shows breathing patterns and meditation posture"));
        
        exerciseAnimations.put("breathing exercises", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/breathing.jpg",
            "Breathing Exercise Animation",
            "Shows deep breathing techniques"));
        
        exerciseAnimations.put("mental recharge", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/mental-recharge.jpg",
            "Mental Recharge Animation",
            "Shows quick mental break exercises"));
        
        // Office-specific exercises - these animations show exercises designed for office workers
        // Each exercise gets its own animated GIF with a fallback static image
        exerciseAnimations.put("desk exercise", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/desk-exercise.jpg",
            "Desk Exercise Animation",
            "Shows complete desk exercise routine"));
        
        exerciseAnimations.put("office workout", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/office-workout.jpg",
            "Office Workout Animation",
            "Shows office-friendly workout routine"));
        
        exerciseAnimations.put("sitting exercises", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/sitting-exercises.jpg",
            "Sitting Exercises Animation",
            "Shows exercises you can do while seated"));
        
        // Default fallback - this animation is used when no specific exercise animation is found
        exerciseAnimations.put("default", new AnimationData(
            "https://media.giphy.com/media/3o7TKxClh6HIDfFbI4/giphy.gif",
            "https://example.com/fallback/default.jpg",
            "General Exercise Animation",
            "Shows general office exercise movements"));
    }
    
    /**
     * Get animation data for a specific exercise
     * This method finds the right animation for the exercise the user wants to do
     * @param exerciseName The name of the exercise to find an animation for
     * @return AnimationData containing GIF URL, fallback URL, title, and description, or default if not found
     */
    public static AnimationData getAnimationData(String exerciseName) {
        // Check if the exercise name is valid
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            // If no exercise name is provided, return the default animation
            return exerciseAnimations.get("default");
        }
        
        // Normalize the exercise name to make searching easier
        // Convert to lowercase and remove extra spaces
        String normalizedName = exerciseName.toLowerCase().trim();
        
        // Direct match
        if (exerciseAnimations.containsKey(normalizedName)) {
            return exerciseAnimations.get(normalizedName);
        }
        
        // Partial match search
        for (Map.Entry<String, AnimationData> entry : exerciseAnimations.entrySet()) {
            if (normalizedName.contains(entry.getKey()) || entry.getKey().contains(normalizedName)) {
                return entry.getValue();
            }
        }
        
        // Category-based fallback
        if (normalizedName.contains("push") || normalizedName.contains("press")) {
            return exerciseAnimations.get("push ups");
        } else if (normalizedName.contains("squat") || normalizedName.contains("sit")) {
            return exerciseAnimations.get("squats");
        } else if (normalizedName.contains("stretch") || normalizedName.contains("neck") || 
                   normalizedName.contains("shoulder") || normalizedName.contains("back") ||
                   normalizedName.contains("wrist") || normalizedName.contains("posture")) {
            return exerciseAnimations.get("desk stretches");
        } else if (normalizedName.contains("cardio") || normalizedName.contains("walking") || 
                   normalizedName.contains("marching") || normalizedName.contains("step") ||
                   normalizedName.contains("jumping")) {
            return exerciseAnimations.get("lite cardio");
        } else if (normalizedName.contains("meditation") || normalizedName.contains("breathing") ||
                   normalizedName.contains("mental") || normalizedName.contains("relax") ||
                   normalizedName.contains("mindful")) {
            return exerciseAnimations.get("meditation");
        } else if (normalizedName.contains("plank")) {
            return exerciseAnimations.get("planks");
        } else if (normalizedName.contains("lunge")) {
            return exerciseAnimations.get("lunges");
        } else if (normalizedName.contains("burpee")) {
            return exerciseAnimations.get("burpees");
        }
        
        // Ultimate fallback
        return exerciseAnimations.get("default");
    }
    
    /**
     * Get animation label for exercise
     * @param exerciseName Name of the exercise
     * @return Animation guidance text
     */
    public static String getAnimationLabel(String exerciseName) {
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            return "Follow the exercise pattern";
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
