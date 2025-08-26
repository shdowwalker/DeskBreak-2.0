package com.s23010285.desk.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

/**
 * Activity record model for DeskBreak App
 * Tracks daily activity summaries including steps, workouts, and other fitness metrics
 * This class is like a daily fitness diary that records all the user's activities for each day
 */
@Entity(tableName = "activity_records")
public class ActivityRecord {
    
    // These variables store the activity record's basic information
    // id is a unique identifier for this activity record
    @PrimaryKey(autoGenerate = true)
    private long id;
    // userId links this activity record to the user it belongs to
    private long userId;
    // date stores which day this record is for (YYYY-MM-DD format)
    private Date date;
    
    // These variables store the daily activity metrics
    // steps records the total number of steps taken on this day
    private int steps;
    // distance records the total distance traveled on this day (in meters)
    private double distance;
    // activeMinutes records how many minutes the user was active on this day
    private int activeMinutes;
    // workoutsCompleted records how many workouts the user finished on this day
    private int workoutsCompleted;
    // caloriesBurned estimates how many calories the user burned on this day
    private int caloriesBurned;
    
    // These variables store goal achievement information
    // stepGoal is the user's daily step goal for this day
    private int stepGoal;
    // workoutGoal is the user's daily workout goal for this day
    private int workoutGoal;
    // stepGoalAchieved indicates whether the user reached their step goal
    private boolean stepGoalAchieved;
    // workoutGoalAchieved indicates whether the user reached their workout goal
    private boolean workoutGoalAchieved;
    
    // These variables store additional activity information
    // notes allows users to add personal comments about their day
    private String notes;
    // moodRating allows users to rate how they felt during the day (1-10)
    private int moodRating;
    // energyLevel allows users to rate their energy level during the day (1-10)
    private int energyLevel;
    
    /**
     * Default constructor - creates a new activity record with default values
     * This is used when we want to create a new daily activity record
     */
    public ActivityRecord() {
        // Set default values for new activity records
        this.date = new Date();
        this.steps = 0;
        this.distance = 0.0;
        this.activeMinutes = 0;
        this.workoutsCompleted = 0;
        this.caloriesBurned = 0;
        this.stepGoal = 10000; // Default 10,000 steps per day
        this.workoutGoal = 3;   // Default 3 workouts per day
        this.stepGoalAchieved = false;
        this.workoutGoalAchieved = false;
        this.moodRating = 5;    // Default neutral mood (5 out of 10)
        this.energyLevel = 5;   // Default neutral energy (5 out of 10)
    }
    
    /**
     * Constructor with basic activity information
     * This is used when we have the user ID and date for the activity record
     * @param userId The ID of the user this activity record belongs to
     * @param date Which day this activity record is for
     */
    public ActivityRecord(long userId, Date date) {
        this.userId = userId;
        this.date = date;
        // Set default values for new activity records
        this.steps = 0;
        this.distance = 0.0;
        this.activeMinutes = 0;
        this.workoutsCompleted = 0;
        this.caloriesBurned = 0;
        this.stepGoal = 10000; // Default 10,000 steps per day
        this.workoutGoal = 3;   // Default 3 workouts per day
        this.stepGoalAchieved = false;
        this.workoutGoalAchieved = false;
        this.moodRating = 5;    // Default neutral mood (5 out of 10)
        this.energyLevel = 5;   // Default neutral energy (5 out of 10)
    }
    
    // Getters and Setters - these methods let other parts of the app access and change activity record information
    
    /**
     * Get the activity record's unique identifier
     * @return The activity record's ID
     */
    public long getId() {
        return id;
    }
    
    /**
     * Set the activity record's unique identifier
     * @param id The new ID to assign to this activity record
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Get the user ID this activity record belongs to
     * @return The user's ID
     */
    public long getUserId() {
        return userId;
    }
    
    /**
     * Set the user ID this activity record belongs to
     * @param userId The new user ID to assign to this activity record
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    /**
     * Get which day this activity record is for
     * @return The date of the activity record
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Set which day this activity record is for
     * @param date The new date to assign to this activity record
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Get the total number of steps taken on this day
     * @return The step count
     */
    public int getSteps() {
        return steps;
    }
    
    /**
     * Set the total number of steps taken on this day
     * @param steps The new step count to assign to this day
     */
    public void setSteps(int steps) {
        this.steps = steps;
        // Check if the step goal has been achieved
        checkStepGoalAchievement();
    }
    
    /**
     * Get the total distance traveled on this day
     * @return The distance in meters
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * Set the total distance traveled on this day
     * @param distance The new distance in meters to assign to this day
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    /**
     * Get how many minutes the user was active on this day
     * @return The active minutes
     */
    public int getActiveMinutes() {
        return activeMinutes;
    }
    
    /**
     * Set how many minutes the user was active on this day
     * @param activeMinutes The new active minutes to assign to this day
     */
    public void setActiveMinutes(int activeMinutes) {
        this.activeMinutes = activeMinutes;
    }
    
    /**
     * Get how many workouts the user completed on this day
     * @return The number of workouts completed
     */
    public int getWorkoutsCompleted() {
        return workoutsCompleted;
    }
    
    /**
     * Set how many workouts the user completed on this day
     * @param workoutsCompleted The new number of workouts completed to assign to this day
     */
    public void setWorkoutsCompleted(int workoutsCompleted) {
        this.workoutsCompleted = workoutsCompleted;
        // Check if the workout goal has been achieved
        checkWorkoutGoalAchievement();
    }
    
    /**
     * Get how many calories the user burned on this day
     * @return The calories burned
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    
    /**
     * Set how many calories the user burned on this day
     * @param caloriesBurned The new calories burned to assign to this day
     */
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
    
    /**
     * Get the user's daily step goal for this day
     * @return The step goal
     */
    public int getStepGoal() {
        return stepGoal;
    }
    
    /**
     * Set the user's daily step goal for this day
     * @param stepGoal The new step goal to assign to this day
     */
    public void setStepGoal(int stepGoal) {
        this.stepGoal = stepGoal;
        // Check if the step goal has been achieved with the new goal
        checkStepGoalAchievement();
    }
    
    /**
     * Get the user's daily workout goal for this day
     * @return The workout goal
     */
    public int getWorkoutGoal() {
        return workoutGoal;
    }
    
    /**
     * Set the user's daily workout goal for this day
     * @param workoutGoal The new workout goal to assign to this day
     */
    public void setWorkoutGoal(int workoutGoal) {
        this.workoutGoal = workoutGoal;
        // Check if the workout goal has been achieved with the new goal
        checkWorkoutGoalAchievement();
    }
    
    /**
     * Check if the user achieved their step goal for this day
     * @return true if the step goal was achieved, false otherwise
     */
    public boolean isStepGoalAchieved() {
        return stepGoalAchieved;
    }
    
    /**
     * Set whether the user achieved their step goal for this day
     * @param stepGoalAchieved The new step goal achievement status
     */
    public void setStepGoalAchieved(boolean stepGoalAchieved) {
        this.stepGoalAchieved = stepGoalAchieved;
    }
    
    /**
     * Check if the user achieved their workout goal for this day
     * @return true if the workout goal was achieved, false otherwise
     */
    public boolean isWorkoutGoalAchieved() {
        return workoutGoalAchieved;
    }
    
    /**
     * Set whether the user achieved their workout goal for this day
     * @param workoutGoalAchieved The new workout goal achievement status
     */
    public void setWorkoutGoalAchieved(boolean workoutGoalAchieved) {
        this.workoutGoalAchieved = workoutGoalAchieved;
    }
    
    /**
     * Get any personal notes about the day
     * @return The daily notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Set any personal notes about the day
     * @param notes The new notes to assign to this day
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * Get the user's mood rating for this day
     * @return The mood rating (1-10)
     */
    public int getMoodRating() {
        return moodRating;
    }
    
    /**
     * Set the user's mood rating for this day
     * @param moodRating The new mood rating (1-10) to assign to this day
     */
    public void setMoodRating(int moodRating) {
        // Ensure the mood rating is within the valid range (1-10)
        if (moodRating >= 1 && moodRating <= 10) {
            this.moodRating = moodRating;
        }
    }
    
    /**
     * Get the user's energy level for this day
     * @return The energy level (1-10)
     */
    public int getEnergyLevel() {
        return energyLevel;
    }
    
    /**
     * Set the user's energy level for this day
     * @param energyLevel The new energy level (1-10) to assign to this day
     */
    public void setEnergyLevel(int energyLevel) {
        // Ensure the energy level is within the valid range (1-10)
        if (energyLevel >= 1 && energyLevel <= 10) {
            this.energyLevel = energyLevel;
        }
    }
    
    // Helper methods for managing goal achievements
    
    /**
     * Check if the step goal has been achieved
     * This method automatically updates the step goal achievement status
     */
    private void checkStepGoalAchievement() {
        this.stepGoalAchieved = (this.steps >= this.stepGoal);
    }
    
    /**
     * Check if the workout goal has been achieved
     * This method automatically updates the workout goal achievement status
     */
    private void checkWorkoutGoalAchievement() {
        this.workoutGoalAchieved = (this.workoutsCompleted >= this.workoutGoal);
    }
    
    /**
     * Add steps to the daily total
     * This method increases the step count for the current day
     * @param additionalSteps The number of steps to add
     */
    public void addSteps(int additionalSteps) {
        this.steps += additionalSteps;
        // Check if the step goal has been achieved
        checkStepGoalAchievement();
    }
    
    /**
     * Add distance to the daily total
     * This method increases the distance traveled for the current day
     * @param additionalDistance The additional distance in meters to add
     */
    public void addDistance(double additionalDistance) {
        this.distance += additionalDistance;
    }
    
    /**
     * Add active minutes to the daily total
     * This method increases the active minutes for the current day
     * @param additionalMinutes The additional active minutes to add
     */
    public void addActiveMinutes(int additionalMinutes) {
        this.activeMinutes += additionalMinutes;
    }
    
    /**
     * Complete a workout for the day
     * This method increases the workout count and checks goal achievement
     */
    public void completeWorkout() {
        this.workoutsCompleted++;
        // Check if the workout goal has been achieved
        checkWorkoutGoalAchievement();
    }
    
    /**
     * Calculate the step goal progress percentage
     * This method shows how close the user is to their daily step goal
     * @return The progress percentage (0-100), or 100 if the goal is exceeded
     */
    public int getStepGoalProgress() {
        if (stepGoal > 0) {
            int progress = (steps * 100) / stepGoal;
            return Math.min(progress, 100); // Cap at 100%
        }
        return 0;
    }
    
    /**
     * Calculate the workout goal progress percentage
     * This method shows how close the user is to their daily workout goal
     * @return The progress percentage (0-100), or 100 if the goal is exceeded
     */
    public int getWorkoutGoalProgress() {
        if (workoutGoal > 0) {
            int progress = (workoutsCompleted * 100) / workoutGoal;
            return Math.min(progress, 100); // Cap at 100%
        }
        return 0;
    }
    
    /**
     * Check if all daily goals were achieved
     * This method returns true if both step and workout goals were met
     * @return true if all goals were achieved, false otherwise
     */
    public boolean areAllGoalsAchieved() {
        return stepGoalAchieved && workoutGoalAchieved;
    }
}
