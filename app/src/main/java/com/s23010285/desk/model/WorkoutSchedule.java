package com.s23010285.desk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Workout schedule model for DeskBreak App
 * Defines complete workout programs with exercises, timing, and instructions
 * This class is like a digital workout plan that contains everything needed for a complete workout session
 */
public class WorkoutSchedule {
    
    // These variables store the workout schedule's basic information
    // id is a unique identifier for this workout schedule
    private String id;
    // name is the display name of the workout program (e.g., "Morning Energy Boost")
    private String name;
    // description explains what the workout is designed to do
    private String description;
    // category tells us what type of workout this is (cardio, strength, meditation, etc.)
    private String category;
    // purpose explains the specific goal of this workout
    private String purpose;
    // schedule tells us when this workout should be done (e.g., "Daily", "3x per week")
    private String schedule;
    // durationMinutes is how long the workout takes in minutes
    private int durationMinutes;
    // difficulty indicates how challenging the workout is
    private String difficulty;
    // targetArea specifies which part of the body the workout focuses on
    private String targetArea;
    
    // These variables store the workout's content and structure
    // exercises is the list of individual exercises in this workout
    private List<WorkoutExercise> exercises;
    // equipment lists what tools or items are needed for the workout
    private List<String> equipment;
    // instructions provides general guidance for the entire workout
    private String instructions;
    
    /**
     * Default constructor - creates a new workout schedule with empty lists
     * This is used when we want to create a new workout program
     */
    public WorkoutSchedule() {
        // Initialize empty lists for exercises and equipment
        this.exercises = new ArrayList<>();
        this.equipment = new ArrayList<>();
    }
    
    /**
     * Constructor with basic workout information
     * This is used when we have the workout's name, description, and other details
     * @param id A unique identifier for this workout schedule
     * @param name The display name of the workout program
     * @param description What the workout is designed to do
     * @param category What type of workout this is
     * @param purpose The specific goal of this workout
     * @param schedule When this workout should be done
     * @param durationMinutes How long the workout takes in minutes
     * @param difficulty How challenging the workout is
     * @param targetArea Which part of the body the workout focuses on
     */
    public WorkoutSchedule(String id, String name, String description, String category,
                          String purpose, String schedule, int durationMinutes,
                          String difficulty, String targetArea) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.purpose = purpose;
        this.schedule = schedule;
        this.durationMinutes = durationMinutes;
        this.difficulty = difficulty;
        this.targetArea = targetArea;
        // Initialize empty lists for exercises and equipment
        this.exercises = new ArrayList<>();
        this.equipment = new ArrayList<>();
    }
    
    // Getters and Setters - these methods let other parts of the app access and change workout schedule information
    
    /**
     * Get the workout schedule's unique identifier
     * @return The workout schedule's ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set the workout schedule's unique identifier
     * @param id The new ID to assign to this workout schedule
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get the workout program's display name
     * @return The workout's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the workout program's display name
     * @param name The new name to assign to this workout
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the workout's description
     * @return What the workout is designed to do
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
     * Get the workout's category
     * @return What type of workout this is
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Set the workout's category
     * @param category The new category to assign to this workout
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Get the workout's purpose
     * @return The specific goal of this workout
     */
    public String getPurpose() {
        return purpose;
    }
    
    /**
     * Set the workout's purpose
     * @param purpose The new purpose to assign to this workout
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    /**
     * Get the workout's schedule
     * @return When this workout should be done
     */
    public String getSchedule() {
        return schedule;
    }
    
    /**
     * Set the workout's schedule
     * @param schedule The new schedule to assign to this workout
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    /**
     * Get how long the workout takes
     * @return The workout duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    /**
     * Set how long the workout takes
     * @param durationMinutes The new duration in minutes
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    /**
     * Get how challenging the workout is
     * @return The difficulty level
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
     * Get which part of the body the workout focuses on
     * @return The target area
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
     * Get the list of exercises in this workout
     * @return The list of workout exercises
     */
    public List<WorkoutExercise> getExercises() {
        return exercises;
    }
    
    /**
     * Set the list of exercises in this workout
     * @param exercises The new list of workout exercises
     */
    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }
    
    /**
     * Get what equipment is needed for the workout
     * @return The list of required equipment
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
     * Get the general instructions for the workout
     * @return The workout instructions
     */
    public String getInstructions() {
        return instructions;
    }
    
    /**
     * Set the general instructions for the workout
     * @param instructions The new workout instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    /**
     * Add an exercise to this workout
     * This method adds a new exercise to the workout's exercise list
     * @param exercise The exercise to add to the workout
     */
    public void addExercise(WorkoutExercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        exercises.add(exercise);
    }
    
    /**
     * Add equipment needed for this workout
     * This method adds a new piece of equipment to the workout's equipment list
     * @param equipmentItem The equipment item to add to the workout
     */
    public void addEquipment(String equipmentItem) {
        if (equipment == null) {
            equipment = new ArrayList<>();
        }
        equipment.add(equipmentItem);
    }
    
    /**
     * Get all available workout schedules
     * This method returns a list of predefined workout programs
     * @return A list of all available workout schedules
     */
    public static List<WorkoutSchedule> getAllWorkoutSchedules() {
        // Create a list to store all available workout schedules
        List<WorkoutSchedule> schedules = new ArrayList<>();
        
        // Morning Energy Boost - a quick morning workout to start the day
        WorkoutSchedule morningBoost = new WorkoutSchedule(
            "morning_boost",
            "Morning Energy Boost",
            "Quick morning workout to energize your day",
            "Cardio",
            "Increase energy and metabolism",
            "Daily",
            15,
            "Beginner",
            "Full Body"
        );
        // Add exercises to the morning boost workout
        morningBoost.addExercise(new WorkoutExercise("Light Stretching", 3, "Gentle stretching to wake up your muscles"));
        morningBoost.addExercise(new WorkoutExercise("Marching in Place", 5, "March in place to get your heart rate up"));
        morningBoost.addExercise(new WorkoutExercise("Arm Circles", 3, "Circular arm movements to loosen shoulders"));
        morningBoost.addExercise(new WorkoutExercise("Deep Breathing", 4, "Deep breathing exercises for mental clarity"));
        schedules.add(morningBoost);
        
        // Desk Stretching - exercises you can do while sitting at your desk
        WorkoutSchedule deskStretching = new WorkoutSchedule(
            "desk_stretching",
            "Desk Stretching",
            "Stretching exercises you can do at your desk",
            "Flexibility",
            "Relieve tension and improve posture",
            "Every 2 hours",
            10,
            "Beginner",
            "Upper Body"
        );
        // Add exercises to the desk stretching workout
        deskStretching.addExercise(new WorkoutExercise("Neck Stretches", 2, "Gentle neck stretches to relieve tension"));
        deskStretching.addExercise(new WorkoutExercise("Shoulder Rolls", 2, "Circular shoulder movements"));
        deskStretching.addExercise(new WorkoutExercise("Wrist Stretches", 2, "Wrist and hand stretches"));
        deskStretching.addExercise(new WorkoutExercise("Back Twists", 2, "Gentle back twisting movements"));
        deskStretching.addExercise(new WorkoutExercise("Deep Breathing", 2, "Breathing exercises for relaxation"));
        schedules.add(deskStretching);
        
        // Quick Cardio - a short cardio workout for energy
        WorkoutSchedule quickCardio = new WorkoutSchedule(
            "quick_cardio",
            "Quick Cardio",
            "Short cardio workout for energy boost",
            "Cardio",
            "Increase heart rate and energy",
            "2-3 times per day",
            12,
            "Beginner",
            "Cardiovascular"
        );
        // Add exercises to the quick cardio workout
        quickCardio.addExercise(new WorkoutExercise("Walking in Place", 4, "Walk in place to get moving"));
        quickCardio.addExercise(new WorkoutExercise("Jumping Jacks", 3, "Jumping jacks for cardio"));
        quickCardio.addExercise(new WorkoutExercise("Step Ups", 3, "Step up and down on a chair"));
        quickCardio.addExercise(new WorkoutExercise("Cool Down", 2, "Gentle stretching to cool down"));
        schedules.add(quickCardio);
        
        // Strength Training - basic strength exercises
        WorkoutSchedule strengthTraining = new WorkoutSchedule(
            "strength_training",
            "Strength Training",
            "Basic strength exercises for muscle tone",
            "Strength",
            "Build muscle strength and tone",
            "3 times per week",
            20,
            "Intermediate",
            "Full Body"
        );
        // Add exercises to the strength training workout
        strengthTraining.addExercise(new WorkoutExercise("Push-ups", 5, "Standard push-ups for upper body strength"));
        strengthTraining.addExercise(new WorkoutExercise("Squats", 5, "Body weight squats for leg strength"));
        strengthTraining.addExercise(new WorkoutExercise("Planks", 5, "Hold plank position for core strength"));
        strengthTraining.addExercise(new WorkoutExercise("Lunges", 5, "Alternating lunges for leg strength"));
        schedules.add(strengthTraining);
        
        // Meditation Break - mental wellness exercises
        WorkoutSchedule meditationBreak = new WorkoutSchedule(
            "meditation_break",
            "Meditation Break",
            "Mental wellness and relaxation exercises",
            "Meditation",
            "Reduce stress and improve focus",
            "Daily",
            10,
            "Beginner",
            "Mental Wellness"
        );
        // Add exercises to the meditation break workout
        meditationBreak.addExercise(new WorkoutExercise("Breathing Exercise", 4, "Deep breathing for relaxation"));
        meditationBreak.addExercise(new WorkoutExercise("Mindfulness", 3, "Present moment awareness"));
        meditationBreak.addExercise(new WorkoutExercise("Progressive Relaxation", 3, "Tense and relax muscle groups"));
        schedules.add(meditationBreak);
        
        // Return all the workout schedules
        return schedules;
    }
    
    /**
     * Workout exercise class
     * This class represents a single exercise within a workout
     */
    public static class WorkoutExercise {
        // These variables store the exercise's basic information
        // name is the display name of the exercise
        private String name;
        // durationMinutes is how long this exercise should be done
        private int durationMinutes;
        // description explains how to do the exercise
        private String description;
        
        /**
         * Constructor for the WorkoutExercise class
         * This method creates a new exercise with its information
         * @param name The display name of the exercise
         * @param durationMinutes How long this exercise should be done
         * @param description How to do the exercise
         */
        public WorkoutExercise(String name, int durationMinutes, String description) {
            this.name = name;
            this.durationMinutes = durationMinutes;
            this.description = description;
        }
        
        // Getters and Setters for the WorkoutExercise class
        
        /**
         * Get the exercise's name
         * @return The exercise's name
         */
        public String getName() {
            return name;
        }
        
        /**
         * Set the exercise's name
         * @param name The new name to assign to this exercise
         */
        public void setName(String name) {
            this.name = name;
        }
        
        /**
         * Get how long the exercise should be done
         * @return The exercise duration in minutes
         */
        public int getDurationMinutes() {
            return durationMinutes;
        }
        
        /**
         * Set how long the exercise should be done
         * @param durationMinutes The new duration in minutes
         */
        public void setDurationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
        }
        
        /**
         * Get how to do the exercise
         * @return The exercise description
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Set how to do the exercise
         * @param description The new description to assign to this exercise
         */
        public void setDescription(String description) {
            this.description = description;
        }
    }
}
