package com.s23010285.desk.model;

/**
 * User model representing a user in the system
 */
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private long createdAt;
    private int dailyStepGoal;
    private int dailyWorkoutGoal;

    public User() {
        // Default constructor
        this.dailyStepGoal = 10000; // Default 10,000 steps
        this.dailyWorkoutGoal = 3;  // Default 3 workouts
    }

    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = System.currentTimeMillis();
        this.dailyStepGoal = 10000; // Default 10,000 steps
        this.dailyWorkoutGoal = 3;  // Default 3 workouts
    }

    public User(long id, String name, String email, String password, long createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.dailyStepGoal = 10000; // Default 10,000 steps
        this.dailyWorkoutGoal = 3;  // Default 3 workouts
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getDailyStepGoal() {
        return dailyStepGoal;
    }

    public void setDailyStepGoal(int dailyStepGoal) {
        this.dailyStepGoal = dailyStepGoal;
    }

    public int getDailyWorkoutGoal() {
        return dailyWorkoutGoal;
    }

    public void setDailyWorkoutGoal(int dailyWorkoutGoal) {
        this.dailyWorkoutGoal = dailyWorkoutGoal;
    }

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
