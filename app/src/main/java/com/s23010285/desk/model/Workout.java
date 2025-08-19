package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.List;

/**
 * Data model representing a workout in the DeskBreak app
 */
@Entity(tableName = "workouts")
public class Workout {
    
    @PrimaryKey
    private String id = "";
    private String name = "";
    private String description = "";
    private int duration = 0; // in minutes
    private String targetArea = ""; // e.g., "Upper Body", "Core", "Stretching"
    private String difficulty = "Beginner"; // Beginner, Intermediate, Advanced
    private List<String> instructions;
    private String imageUrl = null;
    private String videoUrl = null;
    private int caloriesBurnRate = 0; // calories per minute
    private List<String> equipment; // empty for desk workouts
    private boolean isActive = true;
    
    // Default constructor
    public Workout() {
        this.instructions = new java.util.ArrayList<>();
        this.equipment = new java.util.ArrayList<>();
    }
    
    // Constructor with parameters
    public Workout(String id, String name, String description, int duration, String targetArea) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.targetArea = targetArea;
        this.instructions = new java.util.ArrayList<>();
        this.equipment = new java.util.ArrayList<>();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public String getTargetArea() {
        return targetArea;
    }
    
    public void setTargetArea(String targetArea) {
        this.targetArea = targetArea;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public List<String> getInstructions() {
        return instructions;
    }
    
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public int getCaloriesBurnRate() {
        return caloriesBurnRate;
    }
    
    public void setCaloriesBurnRate(int caloriesBurnRate) {
        this.caloriesBurnRate = caloriesBurnRate;
    }
    
    public List<String> getEquipment() {
        return equipment;
    }
    
    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
}
