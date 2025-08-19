package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing daily activity records in the DeskBreak app
 */
@Entity(tableName = "activity_records")
public class ActivityRecord {
    
    @PrimaryKey(autoGenerate = true)
    private long id = 0L;
    private long userId = 0L;
    private String date = ""; // YYYY-MM-DD format
    private int totalSteps = 0;
    private double totalDistance = 0.0; // in meters
    private int totalCalories = 0;
    private int activeMinutes = 0;
    private int workoutCount = 0;
    private boolean goalAchieved = false;
    private long createdAt = System.currentTimeMillis();
    
    // Default constructor
    public ActivityRecord() {}
    
    // Constructor with parameters
    public ActivityRecord(long id, long userId, String date) {
        this.id = id;
        this.userId = userId;
        this.date = date;
    }
    
    // Getters and Setters
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getUserId() {
        return userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public int getTotalSteps() {
        return totalSteps;
    }
    
    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }
    
    public double getTotalDistance() {
        return totalDistance;
    }
    
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    
    // Methods that DatabaseHelper expects
    public int getSteps() {
        return totalSteps;
    }
    
    public void setSteps(int steps) {
        this.totalSteps = steps;
    }
    
    public double getDistance() {
        return totalDistance;
    }
    
    public void setDistance(double distance) {
        this.totalDistance = distance;
    }
    
    public int getTotalCalories() {
        return totalCalories;
    }
    
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
    
    public int getActiveMinutes() {
        return activeMinutes;
    }
    
    public void setActiveMinutes(int activeMinutes) {
        this.activeMinutes = activeMinutes;
    }
    
    public int getWorkoutCount() {
        return workoutCount;
    }
    
    public void setWorkoutCount(int workoutCount) {
        this.workoutCount = workoutCount;
    }
    
    public boolean isGoalAchieved() {
        return goalAchieved;
    }
    
    public void setGoalAchieved(boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
