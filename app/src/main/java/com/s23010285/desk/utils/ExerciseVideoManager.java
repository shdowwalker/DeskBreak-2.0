package com.s23010285.desk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages YouTube video tutorials with timestamps for specific exercises
 * Provides curated workout videos with precise time-based URLs for each exercise
 * This class is like a personal fitness instructor that provides video guidance for exercises
 */
public class ExerciseVideoManager {
    
    // Video data structure - this class holds information about a single exercise video
    // It's like a digital video card that contains everything needed to watch the exercise
    public static class VideoData {
        // These variables store the video's basic information
        // videoId is the unique identifier for the YouTube video
        public final String videoId;
        // startTimeSeconds is when in the video the specific exercise begins
        public final int startTimeSeconds;
        // title is the display name of the video tutorial
        public final String title;
        
        /**
         * Constructor for the VideoData class
         * This method creates a new video data object with all its information
         * @param videoId The unique identifier for the YouTube video
         * @param startTimeSeconds When in the video the specific exercise begins
         * @param title The display name of the video tutorial
         */
        public VideoData(String videoId, int startTimeSeconds, String title) {
            this.videoId = videoId;
            this.startTimeSeconds = startTimeSeconds;
            this.title = title;
        }
        
        /**
         * Get the complete YouTube URL with timestamp
         * This method creates a URL that starts the video at the right time
         * @return A YouTube URL that starts at the specific exercise time
         */
        public String getTimestampUrl() {
            return "https://www.youtube.com/watch?v=" + videoId + "&t=" + startTimeSeconds + "s";
        }
    }
    
    // This map stores all the exercise videos and their data
    // Each exercise name maps to its corresponding video information
    private static final Map<String, VideoData> exerciseVideos = new HashMap<>();
    
    // Static initialization block - this runs once when the class is first loaded
    // It sets up all the exercise videos with their timestamps
    static {
        // Initialize exercise videos with timestamps
        // Format: "exercise_name" -> VideoData("video_id", start_seconds, "title")
        
        // Push-up variations - these videos show different types of push-ups
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("push ups", new VideoData("IODxDxX7oi4", 45, "Perfect Push-Up Form Tutorial"));
        exerciseVideos.put("pushups", new VideoData("IODxDxX7oi4", 45, "Perfect Push-Up Form Tutorial"));
        exerciseVideos.put("push up", new VideoData("IODxDxX7oi4", 45, "Perfect Push-Up Form Tutorial"));
        exerciseVideos.put("wall pushups", new VideoData("M7yKkzR6hDo", 30, "Wall Push-Ups for Beginners"));
        
        // Squat variations - these videos show different types of squats
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("squats", new VideoData("aclHkVaku9U", 60, "How to Squat with Perfect Form"));
        exerciseVideos.put("squat", new VideoData("aclHkVaku9U", 60, "How to Squat with Perfect Form"));
        exerciseVideos.put("wall sits", new VideoData("y-wV4Venusw", 25, "Wall Sit Exercise Tutorial"));
        exerciseVideos.put("chair squats", new VideoData("JBHopUHSlVs", 40, "Chair Squats for Seniors"));
        
        // Stretching and flexibility - these videos show stretching exercises
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("desk stretches", new VideoData("RqcOCBb4arc", 15, "10-Minute Desk Stretches"));
        exerciseVideos.put("neck stretches", new VideoData("RqcOCBb4arc", 120, "Neck and Shoulder Stretches"));
        exerciseVideos.put("shoulder rolls", new VideoData("RqcOCBb4arc", 180, "Shoulder Roll Exercise"));
        exerciseVideos.put("back stretches", new VideoData("4BOTvaRaDjI", 30, "Office Back Stretches"));
        exerciseVideos.put("wrist stretches", new VideoData("EiRC80FJbHU", 20, "Wrist Stretches for Computer Users"));
        exerciseVideos.put("stretching", new VideoData("RqcOCBb4arc", 15, "Full Body Desk Stretches"));
        exerciseVideos.put("flexibility", new VideoData("RqcOCBb4arc", 15, "Flexibility Routine"));
        exerciseVideos.put("posture improvement", new VideoData("RqcOCBb4arc", 300, "Posture Improvement Exercises"));
        
        // Cardio exercises - these videos show cardiovascular exercises
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("lite cardio", new VideoData("gC_L9qAHVJ8", 30, "Low Impact Cardio for Beginners"));
        exerciseVideos.put("cardio", new VideoData("gC_L9qAHVJ8", 30, "Cardio Workout"));
        exerciseVideos.put("walking", new VideoData("gC_L9qAHVJ8", 120, "Walking in Place Exercise"));
        exerciseVideos.put("marching", new VideoData("gC_L9qAHVJ8", 150, "Marching in Place"));
        exerciseVideos.put("step ups", new VideoData("dV5sGrJuxPE", 45, "Step-Up Exercise Tutorial"));
        exerciseVideos.put("jumping jacks", new VideoData("iSSAk4XCsRA", 25, "How to Do Jumping Jacks"));
        
        // Core and strength exercises - these videos show core and strength training
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("planks", new VideoData("pSHjTRCQxIw", 30, "Perfect Plank Form"));
        exerciseVideos.put("plank", new VideoData("pSHjTRCQxIw", 30, "Perfect Plank Form"));
        exerciseVideos.put("lunges", new VideoData("QOVaHwm-Q6U", 40, "How to Do Lunges Correctly"));
        exerciseVideos.put("lunge", new VideoData("QOVaHwm-Q6U", 40, "How to Do Lunges Correctly"));
        exerciseVideos.put("burpees", new VideoData("dZgVxmf6jkA", 20, "Burpee Exercise Tutorial"));
        exerciseVideos.put("burpee", new VideoData("dZgVxmf6jkA", 20, "Burpee Exercise Tutorial"));
        
        // Meditation and breathing - these videos show meditation and breathing exercises
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("meditation", new VideoData("inpok4MKVLM", 0, "5-Minute Meditation for Beginners"));
        exerciseVideos.put("breathing exercises", new VideoData("tybOi4hjZFQ", 15, "Deep Breathing Techniques"));
        exerciseVideos.put("mental recharge", new VideoData("inpok4MKVLM", 0, "Quick Meditation Break"));
        exerciseVideos.put("relaxation", new VideoData("inpok4MKVLM", 180, "Relaxation Techniques"));
        exerciseVideos.put("mindfulness", new VideoData("inpok4MKVLM", 60, "Mindfulness Exercise"));
        
        // Office-specific exercises - these videos show exercises designed for office workers
        // Each exercise gets its own video with a specific start time
        exerciseVideos.put("desk exercise", new VideoData("RqcOCBb4arc", 0, "Complete Desk Exercise Routine"));
        exerciseVideos.put("office workout", new VideoData("RqcOCBb4arc", 0, "Office Workout"));
        exerciseVideos.put("sitting exercises", new VideoData("RqcOCBb4arc", 240, "Exercises You Can Do While Sitting"));
        
        // Default fallback - this video is used when no specific exercise video is found
        exerciseVideos.put("default", new VideoData("RqcOCBb4arc", 0, "General Office Exercises"));
    }
    
    /**
     * Get video data for a specific exercise
     * This method finds the right video tutorial for the exercise the user wants to do
     * @param exerciseName The name of the exercise to find a video for
     * @return VideoData containing video ID, timestamp, and title, or default if not found
     */
    public static VideoData getVideoData(String exerciseName) {
        // Check if the exercise name is valid
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            // If no exercise name is provided, return the default video
            return exerciseVideos.get("default");
        }
        
        // Normalize the exercise name to make searching easier
        // Convert to lowercase and remove extra spaces
        String normalizedName = exerciseName.toLowerCase().trim();
        
        // Direct match
        if (exerciseVideos.containsKey(normalizedName)) {
            return exerciseVideos.get(normalizedName);
        }
        
        // Partial match search
        for (Map.Entry<String, VideoData> entry : exerciseVideos.entrySet()) {
            if (normalizedName.contains(entry.getKey()) || entry.getKey().contains(normalizedName)) {
                return entry.getValue();
            }
        }
        
        // Category-based fallback
        if (normalizedName.contains("push") || normalizedName.contains("press")) {
            return exerciseVideos.get("push ups");
        } else if (normalizedName.contains("squat") || normalizedName.contains("sit")) {
            return exerciseVideos.get("squats");
        } else if (normalizedName.contains("stretch") || normalizedName.contains("neck") || 
                   normalizedName.contains("shoulder") || normalizedName.contains("back") ||
                   normalizedName.contains("wrist") || normalizedName.contains("posture")) {
            return exerciseVideos.get("desk stretches");
        } else if (normalizedName.contains("cardio") || normalizedName.contains("walking") || 
                   normalizedName.contains("marching") || normalizedName.contains("step") ||
                   normalizedName.contains("jumping")) {
            return exerciseVideos.get("lite cardio");
        } else if (normalizedName.contains("meditation") || normalizedName.contains("breathing") ||
                   normalizedName.contains("mental") || normalizedName.contains("relax") ||
                   normalizedName.contains("mindful")) {
            return exerciseVideos.get("meditation");
        } else if (normalizedName.contains("plank")) {
            return exerciseVideos.get("planks");
        } else if (normalizedName.contains("lunge")) {
            return exerciseVideos.get("lunges");
        } else if (normalizedName.contains("burpee")) {
            return exerciseVideos.get("burpees");
        }
        
        // Ultimate fallback
        return exerciseVideos.get("default");
    }
    
    /**
     * Get video data for workout category
     * @param category Workout category
     * @return VideoData for the category
     */
    public static VideoData getVideoDataForCategory(String category) {
        if (category == null) {
            return exerciseVideos.get("default");
        }
        
        String normalizedCategory = category.toLowerCase().trim();
        
        switch (normalizedCategory) {
            case "cardio":
            case "lite cardio":
                return exerciseVideos.get("lite cardio");
            case "strength":
            case "strength training":
                return exerciseVideos.get("push ups");
            case "mental recharge":
            case "meditation":
                return exerciseVideos.get("meditation");
            case "flexibility":
            case "stretching":
                return exerciseVideos.get("desk stretches");
            default:
                return exerciseVideos.get("default");
        }
    }
    
    /**
     * Get all available exercise names with videos
     * @return Array of exercise names that have videos
     */
    public static String[] getAvailableExercises() {
        return exerciseVideos.keySet().toArray(new String[0]);
    }
    
    /**
     * Check if exercise has a specific video tutorial
     * @param exerciseName Name of the exercise
     * @return true if specific video exists
     */
    public static boolean hasSpecificVideo(String exerciseName) {
        VideoData videoData = getVideoData(exerciseName);
        return !videoData.equals(exerciseVideos.get("default"));
    }
}
