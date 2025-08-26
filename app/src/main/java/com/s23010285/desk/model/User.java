package com.s23010285.desk.model;

/**
 * User model representing a user in the system
 * This class stores all the information about a user's account and preferences
 * It's like a digital profile card that contains everything we know about the user
 */
public class User {
    // These variables store the user's basic account information
    // id is a unique number that identifies this user in the database
    private long id;
    // name is the user's full name (e.g., "John Smith")
    private String name;
    // email is the user's email address, which they use to log in
    private String email;
    // password is the user's login password (stored securely)
    private String password;
    // createdAt stores when the user's account was created (as a timestamp)
    private long createdAt;
    
    // These variables store the user's fitness goals and preferences
    // dailyStepGoal is how many steps the user wants to take each day (default: 10,000)
    private int dailyStepGoal;
    // dailyWorkoutGoal is how many workouts the user wants to do each day (default: 3)
    private int dailyWorkoutGoal;

    /**
     * Default constructor - creates a new user with default goals
     * This is used when we want to create a new user account
     */
    public User() {
        // Set default fitness goals for new users
        this.dailyStepGoal = 10000; // Default 10,000 steps per day
        this.dailyWorkoutGoal = 3;  // Default 3 workouts per day
    }

    /**
     * Constructor with basic user information
     * This is used when we have the user's name, email, and password
     * @param id A unique number that identifies this user
     * @param name The user's full name
     * @param email The user's email address
     * @param password The user's chosen password
     */
    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        // Set the creation time to right now
        this.createdAt = System.currentTimeMillis();
        // Set default fitness goals for new users
        this.dailyStepGoal = 10000; // Default 10,000 steps per day
        this.dailyWorkoutGoal = 3;  // Default 3 workouts per day
    }

    /**
     * Constructor with all user information including creation time
     * This is used when we have complete user information from the database
     * @param id A unique number that identifies this user
     * @param name The user's full name
     * @param email The user's email address
     * @param password The user's chosen password
     * @param createdAt When the user's account was created
     */
    public User(long id, String name, String email, String password, long createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        // Set default fitness goals for new users
        this.dailyStepGoal = 10000; // Default 10,000 steps per day
        this.dailyWorkoutGoal = 3;  // Default 3 workouts per day
    }

    // Getters and Setters - these methods let other parts of the app access and change user information
    
    /**
     * Get the user's unique ID number
     * @return The user's ID
     */
    public long getId() {
        return id;
    }

    /**
     * Set the user's unique ID number
     * @param id The new ID to assign to this user
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the user's full name
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the user's full name
     * @param name The new name to assign to this user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the user's email address
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email address
     * @param email The new email to assign to this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user's password
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user's password
     * @param password The new password to assign to this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get when the user's account was created
     * @return The creation timestamp
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Set when the user's account was created
     * @param createdAt The new creation timestamp
     */
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get the user's daily step goal
     * @return How many steps the user wants to take each day
     */
    public int getDailyStepGoal() {
        return dailyStepGoal;
    }

    /**
     * Set the user's daily step goal
     * @param dailyStepGoal How many steps the user wants to take each day
     */
    public void setDailyStepGoal(int dailyStepGoal) {
        this.dailyStepGoal = dailyStepGoal;
    }

    /**
     * Get the user's daily workout goal
     * @return How many workouts the user wants to do each day
     */
    public int getDailyWorkoutGoal() {
        return dailyWorkoutGoal;
    }

    /**
     * Set the user's daily workout goal
     * @param dailyWorkoutGoal How many workouts the user wants to do each day
     */
    public void setDailyWorkoutGoal(int dailyWorkoutGoal) {
        this.dailyWorkoutGoal = dailyWorkoutGoal;
    }

    /**
     * This method creates a text representation of the user object
     * It's useful for debugging and logging
     * @return A string containing all the user's information
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", dailyStepGoal=" + dailyStepGoal +
                ", dailyWorkoutGoal=" + dailyWorkoutGoal +
                '}';
    }
}
