package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.List;

/**
 * Data model representing a workout in the DeskBreak app
 * This class stores all the information about a specific workout routine
 * It's like a digital workout card that contains everything needed to do the workout
 */
@Entity(tableName = "workouts")
public class Workout {
    
    // These variables store the workout's basic information
    // id is a unique identifier for this workout
    @PrimaryKey
    private String id = "";
    // name is the display name of the workout (e.g., "Morning Cardio")
    private String name = "";
    // description explains what the workout is and what it's good for
    private String description = "";
    // duration is how long the workout takes in minutes
    private int duration = 0; // in minutes
    // targetArea specifies which part of the body the workout focuses on
    private String targetArea = ""; // e.g., "Upper Body", "Core", "Stretching"
    // difficulty indicates how challenging the workout is
    private String difficulty = "Beginner"; // Beginner, Intermediate, Advanced
    
    // These variables store the workout's content and media
    // instructions is a list of step-by-step directions for each exercise
    private List<String> instructions;
    // imageUrl points to a picture that shows the workout
    private String imageUrl = null;
    // videoUrl points to a video tutorial for the workout
    private String videoUrl = null;
    
    // These variables store workout performance information
    // caloriesBurnRate estimates how many calories the workout burns per minute
    private int caloriesBurnRate = 0; // calories per minute
    // equipment lists what tools or items are needed for the workout
    private List<String> equipment; // empty for desk workouts
    // isActive indicates whether this workout is currently available
    private boolean isActive = true;
    
    /**
     * Default constructor - creates a new workout with empty lists
     * This is used when we want to create a new workout
     */
    public Workout() {
        // Initialize empty lists for instructions and equipment
        this.instructions = new java.util.ArrayList<>();
        this.equipment = new java.util.ArrayList<>();
    }
    
    /**
     * Constructor with basic workout information
     * This is used when we have the workout's name, description, duration, and target area
     * @param id A unique identifier for this workout
     * @param name The display name of the workout
     * @param description What the workout is and what it's good for
     * @param duration How long the workout takes in minutes
     * @param targetArea Which part of the body the workout focuses on
     */
    public Workout(String id, String name, String description, int duration, String targetArea) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.targetArea = targetArea;
        // Initialize empty lists for instructions and equipment
        this.instructions = new java.util.ArrayList<>();
        this.equipment = new java.util.ArrayList<>();
    }
    
    // Getters and Setters - these methods let other parts of the app access and change workout information
    
    /**
     * Get the workout's unique identifier
     * @return The workout's ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set the workout's unique identifier
     * @param id The new ID to assign to this workout
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get the workout's display name
     * @return The workout's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the workout's display name
     * @param name The new name to assign to this workout
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the workout's description
     * @return What the workout is and what it's good for
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the workout's description
     * @param description The new description to assign to this workout
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get how long the workout takes
     * @return The workout duration in minutes
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Set how long the workout takes
     * @param duration The new duration in minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    /**
     * Get which part of the body the workout focuses on
     * @return The target area (e.g., "Upper Body", "Core")
     */
    public String getTargetArea() {
        return targetArea;
    }
    
    /**
     * Set which part of the body the workout focuses on
     * @param targetArea The new target area
     */
    public void setTargetArea(String targetArea) {
        this.targetArea = targetArea;
    }
    
    /**
     * Get how challenging the workout is
     * @return The difficulty level (Beginner, Intermediate, Advanced)
     */
    public String getDifficulty() {
        return difficulty;
    }
    
    /**
     * Set how challenging the workout is
     * @param difficulty The new difficulty level
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    /**
     * Get the step-by-step instructions for the workout
     * @return A list of exercise directions
     */
    public List<String> getInstructions() {
        return instructions;
    }
    
    /**
     * Set the step-by-step instructions for the workout
     * @param instructions The new list of exercise directions
     */
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
    
    /**
     * Get the URL of the workout's image
     * @return The image URL, or null if no image is available
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Set the URL of the workout's image
     * @param imageUrl The new image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Get the URL of the workout's video tutorial
     * @return The video URL, or null if no video is available
     */
    public String getVideoUrl() {
        return videoUrl;
    }
    
    /**
     * Set the URL of the workout's video tutorial
     * @param videoUrl The new video URL
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    /**
     * Get how many calories the workout burns per minute
     * @return The calories burned per minute
     */
    public int getCaloriesBurnRate() {
        return caloriesBurnRate;
    }
    
    /**
     * Set how many calories the workout burns per minute
     * @param caloriesBurnRate The new calories burned per minute
     */
    public void setCaloriesBurnRate(int caloriesBurnRate) {
        this.caloriesBurnRate = caloriesBurnRate;
    }
    
    /**
     * Get what equipment is needed for the workout
     * @return A list of required equipment
     */
    public List<String> getEquipment() {
        return equipment;
    }
    
    /**
     * Set what equipment is needed for the workout
     * @param equipment The new list of required equipment
     */
    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }
    
    /**
     * Check if this workout is currently available
     * @return true if the workout is active, false if it's disabled
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Set whether this workout is currently available
     * @param active true to make the workout available, false to disable it
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}
