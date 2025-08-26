package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

/**
 * Workout session model for DeskBreak App
 * Tracks individual workout sessions with timing, performance, and completion data
 * This class is like a digital workout log that records everything about each workout session
 */
@Entity(tableName = "workout_sessions")
public class WorkoutSession {
    
    // These variables store the workout session's basic information
    // id is a unique identifier for this workout session
    @PrimaryKey(autoGenerate = true)
    private long id;
    // userId links this workout session to the user who did it
    private long userId;
    // workoutType tells us what kind of workout this was (cardio, strength, meditation, etc.)
    private String workoutType;
    // workoutName is the specific name of the workout program used
    private String workoutName;
    
    // These variables store the session's timing information
    // startTime records when the workout session began
    private Date startTime;
    // endTime records when the workout session ended
    private Date endTime;
    // durationMinutes is how long the workout actually lasted
    private int durationMinutes;
    
    // These variables store the session's performance metrics
    // steps records how many steps the user took during the workout
    private int steps;
    // distance records how far the user moved during the workout (in meters)
    private double distance;
    // caloriesBurned estimates how many calories the workout burned
    private int caloriesBurned;
    // exercisesCompleted records how many exercises the user finished
    private int exercisesCompleted;
    
    // These variables store the session's status and progress
    // status tells us whether the workout was completed, paused, or abandoned
    private String status; // "in_progress", "completed", "paused", "abandoned"
    // progressPercentage shows how much of the workout was completed (0-100)
    private int progressPercentage;
    // notes allows users to add personal comments about the workout
    private String notes;
    
    /**
     * Default constructor - creates a new workout session with default values
     * This is used when we want to create a new workout session
     */
    public WorkoutSession() {
        // Set default values for new workout sessions
        this.startTime = new Date();
        this.status = "in_progress";
        this.progressPercentage = 0;
        this.steps = 0;
        this.distance = 0.0;
        this.caloriesBurned = 0;
        this.exercisesCompleted = 0;
    }
    
    /**
     * Constructor with basic workout session information
     * This is used when we have the workout's type, name, and user information
     * @param userId The ID of the user doing the workout
     * @param workoutType What kind of workout this is
     * @param workoutName The specific name of the workout program
     */
    public WorkoutSession(long userId, String workoutType, String workoutName) {
        this.userId = userId;
        this.workoutType = workoutType;
        this.workoutName = workoutName;
        // Set default values for new workout sessions
        this.startTime = new Date();
        this.status = "in_progress";
        this.progressPercentage = 0;
        this.steps = 0;
        this.distance = 0.0;
        this.caloriesBurned = 0;
        this.exercisesCompleted = 0;
    }
    
    // Getters and Setters - these methods let other parts of the app access and change workout session information
    
    /**
     * Get the workout session's unique identifier
     * @return The workout session's ID
     */
    public long getId() {
        return id;
    }
    
    /**
     * Set the workout session's unique identifier
     * @param id The new ID to assign to this workout session
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Get the user ID who did this workout
     * @return The user's ID
     */
    public long getUserId() {
        return userId;
    }
    
    /**
     * Set the user ID who did this workout
     * @param userId The new user ID to assign to this workout session
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    /**
     * Get what type of workout this was
     * @return The workout type
     */
    public String getWorkoutType() {
        return workoutType;
    }
    
    /**
     * Set what type of workout this was
     * @param workoutType The new workout type to assign to this session
     */
    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }
    
    /**
     * Get the name of the workout program used
     * @return The workout name
     */
    public String getWorkoutName() {
        return workoutName;
    }
    
    /**
     * Set the name of the workout program used
     * @param workoutName The new workout name to assign to this session
     */
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    
    /**
     * Get when the workout session began
     * @return The start time
     */
    public Date getStartTime() {
        return startTime;
    }
    
    /**
     * Set when the workout session began
     * @param startTime The new start time to assign to this session
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * Get when the workout session ended
     * @return The end time, or null if the workout is still in progress
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * Set when the workout session ended
     * @param endTime The new end time to assign to this session
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * Get how long the workout actually lasted
     * @return The duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    /**
     * Set how long the workout actually lasted
     * @param durationMinutes The new duration in minutes
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    /**
     * Get how many steps the user took during the workout
     * @return The step count
     */
    public int getSteps() {
        return steps;
    }
    
    /**
     * Set how many steps the user took during the workout
     * @param steps The new step count to assign to this session
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    /**
     * Get how far the user moved during the workout
     * @return The distance in meters
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * Set how far the user moved during the workout
     * @param distance The new distance in meters to assign to this session
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    /**
     * Get how many calories the workout burned
     * @return The calories burned
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    
    /**
     * Set how many calories the workout burned
     * @param caloriesBurned The new calories burned to assign to this session
     */
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
    
    /**
     * Get how many exercises the user completed
     * @return The number of exercises completed
     */
    public int getExercisesCompleted() {
        return exercisesCompleted;
    }
    
    /**
     * Set how many exercises the user completed
     * @param exercisesCompleted The new number of exercises completed to assign to this session
     */
    public void setExercisesCompleted(int exercisesCompleted) {
        this.exercisesCompleted = exercisesCompleted;
    }
    
    /**
     * Get the current status of the workout session
     * @return The workout status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Set the current status of the workout session
     * @param status The new status to assign to this session
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Get how much of the workout was completed
     * @return The progress percentage (0-100)
     */
    public int getProgressPercentage() {
        return progressPercentage;
    }
    
    /**
     * Set how much of the workout was completed
     * @param progressPercentage The new progress percentage (0-100) to assign to this session
     */
    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    
    /**
     * Get any personal notes about the workout
     * @return The workout notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Set any personal notes about the workout
     * @param notes The new notes to assign to this session
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * Calculate the actual duration of the workout session
     * This method calculates how long the workout lasted based on start and end times
     * @return The duration in minutes, or 0 if the workout hasn't ended yet
     */
    public int calculateActualDuration() {
        if (startTime != null && endTime != null) {
            // Calculate the difference between end time and start time
            long durationInMillis = endTime.getTime() - startTime.getTime();
            // Convert milliseconds to minutes
            return (int) (durationInMillis / (1000 * 60));
        }
        return 0;
    }
    
    /**
     * Mark the workout session as completed
     * This method sets the end time and updates the status to completed
     */
    public void markAsCompleted() {
        this.endTime = new Date();
        this.status = "completed";
        this.progressPercentage = 100;
        // Calculate the actual duration
        this.durationMinutes = calculateActualDuration();
    }
    
    /**
     * Update the workout progress
     * This method updates the progress percentage based on exercises completed
     * @param totalExercises The total number of exercises in the workout
     */
    public void updateProgress(int totalExercises) {
        if (totalExercises > 0) {
            // Calculate progress as a percentage of exercises completed
            this.progressPercentage = (exercisesCompleted * 100) / totalExercises;
        }
    }
    
    /**
     * Add steps to the workout session
     * This method increases the step count for the current workout
     * @param additionalSteps The number of steps to add
     */
    public void addSteps(int additionalSteps) {
        this.steps += additionalSteps;
    }
    
    /**
     * Add distance to the workout session
     * This method increases the distance traveled for the current workout
     * @param additionalDistance The additional distance in meters to add
     */
    public void addDistance(double additionalDistance) {
        this.distance += additionalDistance;
    }
    
    /**
     * Complete an exercise in the workout
     * This method increases the exercise count and updates progress
     * @param totalExercises The total number of exercises in the workout
     */
    public void completeExercise(int totalExercises) {
        this.exercisesCompleted++;
        updateProgress(totalExercises);
    }
}
