package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data model representing a workout session in the DeskBreak app
 */
@Entity(tableName = "workout_sessions")
public class WorkoutSession {
    
    @PrimaryKey(autoGenerate = true)
    private long id = 0L;
    private long userId = 0L;
    private String workoutId = "";
    private String type = ""; // workout type/category
    private long startTime = System.currentTimeMillis();
    private Long endTime = null;
    private long duration = 0; // in milliseconds
    private int steps = 0;
    private double distance = 0.0; // in meters
    private int calories = 0;
    private String status = "active"; // active, paused, completed, cancelled
    
    // Default constructor
    public WorkoutSession() {}
    
    // Constructor with parameters
    public WorkoutSession(long id, long userId, String workoutId) {
        this.id = id;
        this.userId = userId;
        this.workoutId = workoutId;
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
    
    public String getWorkoutId() {
        return workoutId;
    }
    
    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public Long getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
