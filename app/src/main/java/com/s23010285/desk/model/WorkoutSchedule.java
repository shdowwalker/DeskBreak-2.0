package com.s23010285.desk.model;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSchedule {
    private String id;
    private String name;
    private String purpose;
    private int durationMinutes;
    private String schedule;
    private List<WorkoutExercise> exercises;
    private String category;
    
    public WorkoutSchedule() {}
    
    public WorkoutSchedule(String id, String name, String purpose, int durationMinutes, 
                          String schedule, String category) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
        this.durationMinutes = durationMinutes;
        this.schedule = schedule;
        this.category = category;
        this.exercises = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    
    public List<WorkoutExercise> getExercises() { return exercises; }
    public void setExercises(List<WorkoutExercise> exercises) { this.exercises = exercises; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public void addExercise(WorkoutExercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        exercises.add(exercise);
    }
    
    // Static method to get all workout schedules
    public static List<WorkoutSchedule> getAllWorkoutSchedules() {
        List<WorkoutSchedule> schedules = new ArrayList<>();
        
        // 1. Lite Cardio Break (5 Minutes)
        WorkoutSchedule liteCardio = new WorkoutSchedule(
            "lite_cardio", "Lite Cardio Break", 
            "Boost heart rate, combat sluggishness, and refresh focus",
            5, "Every morning and mid-afternoon (e.g., 10:30am, 3:00pm)", "Cardio"
        );
        
        liteCardio.addExercise(new WorkoutExercise("March in place", 60, "March in place with high knees"));
        liteCardio.addExercise(new WorkoutExercise("Arm swings", 30, "Swing arms forward and backward"));
        liteCardio.addExercise(new WorkoutExercise("Side steps or step taps", 60, "Step side to side or tap feet"));
        liteCardio.addExercise(new WorkoutExercise("Standing knee raises", 30, "Lift knees alternately"));
        liteCardio.addExercise(new WorkoutExercise("Shoulder rolls", 30, "Roll shoulders forward and backward"));
        liteCardio.addExercise(new WorkoutExercise("Light stretching", 60, "Gentle stretching movements"));
        schedules.add(liteCardio);
        
        // 2. Desk Stretch Routine (5 Minutes)
        WorkoutSchedule deskStretch = new WorkoutSchedule(
            "desk_stretch", "Desk Stretch Routine",
            "Relieve tension in neck, shoulders, wrists, and back",
            5, "After every 2 hours of sitting, or before lunch/end of day", "Stretching"
        );
        
        deskStretch.addExercise(new WorkoutExercise("Neck side stretches", 30, "Gently tilt head to each side"));
        deskStretch.addExercise(new WorkoutExercise("Seated torso twists", 60, "Twist upper body from side to side"));
        deskStretch.addExercise(new WorkoutExercise("Shoulder shrugs", 30, "Lift and lower shoulders"));
        deskStretch.addExercise(new WorkoutExercise("Wrist circles", 30, "Rotate wrists in circular motion"));
        deskStretch.addExercise(new WorkoutExercise("Seated forward fold", 60, "Bend forward from hips"));
        deskStretch.addExercise(new WorkoutExercise("Chest opener stretch", 60, "Arms behind back, open chest"));
        schedules.add(deskStretch);
        
        // 3. Mobility & Flexibility (7 Minutes)
        WorkoutSchedule mobility = new WorkoutSchedule(
            "mobility_flexibility", "Mobility & Flexibility",
            "Improve joint range, reduce stiffness",
            7, "Once daily, ideally post-lunch or before bed", "Mobility"
        );
        
        mobility.addExercise(new WorkoutExercise("Cat-cow stretch", 60, "Alternate between cat and cow poses"));
        mobility.addExercise(new WorkoutExercise("Seated hip marches", 60, "Lift knees while seated"));
        mobility.addExercise(new WorkoutExercise("Ankle circles", 30, "Rotate ankles in circular motion"));
        mobility.addExercise(new WorkoutExercise("Overhead side bends", 60, "Reach overhead and bend side to side"));
        mobility.addExercise(new WorkoutExercise("Seated figure-four", 60, "Cross ankle over opposite knee"));
        mobility.addExercise(new WorkoutExercise("Seated hamstring stretch", 60, "Extend legs and reach forward"));
        mobility.addExercise(new WorkoutExercise("Full-body shake-out", 60, "Shake out all body parts"));
        schedules.add(mobility);
        
        // 4. Posture Correction Routine (5 Minutes)
        WorkoutSchedule posture = new WorkoutSchedule(
            "posture_correction", "Posture Correction Routine",
            "Combat poor posture; open chest, strengthen back",
            5, "Mid-morning and mid-afternoon (11:30am, 4:00pm)", "Posture"
        );
        
        posture.addExercise(new WorkoutExercise("Wall angels or desk angels", 60, "Slide arms up and down wall/desk"));
        posture.addExercise(new WorkoutExercise("Seated scapular retractions", 60, "Pull shoulder blades together"));
        posture.addExercise(new WorkoutExercise("Chin tucks", 30, "Tuck chin to chest"));
        posture.addExercise(new WorkoutExercise("Reverse arm circles", 60, "Circle arms backward"));
        posture.addExercise(new WorkoutExercise("Standing Y stretch", 60, "Reach arms overhead in Y shape"));
        posture.addExercise(new WorkoutExercise("Gentle spinal rolls", 60, "Roll spine forward and back"));
        schedules.add(posture);
        
        // 5. Quick Desk Yoga (10 Minutes)
        WorkoutSchedule deskYoga = new WorkoutSchedule(
            "desk_yoga", "Quick Desk Yoga",
            "Reduce stress, improve flexibility, mental recharge",
            10, "As needed, especially after stressful meetings or at the end of the workday", "Yoga"
        );
        
        deskYoga.addExercise(new WorkoutExercise("Seated spinal twist", 60, "Twist upper body from side to side"));
        deskYoga.addExercise(new WorkoutExercise("Seated side bend", 60, "Bend to each side"));
        deskYoga.addExercise(new WorkoutExercise("Chair pigeon pose", 60, "Cross ankle over opposite knee"));
        deskYoga.addExercise(new WorkoutExercise("Seated backbend", 60, "Hold desk, arch back"));
        deskYoga.addExercise(new WorkoutExercise("Seated neck stretch", 30, "Stretch neck in all directions"));
        deskYoga.addExercise(new WorkoutExercise("Seated chest opener", 60, "Hold hands behind chair"));
        deskYoga.addExercise(new WorkoutExercise("Forward fold or reach", 60, "Bend forward from hips"));
        deskYoga.addExercise(new WorkoutExercise("Mindful breathing", 120, "Slow inhale/exhale"));
        schedules.add(deskYoga);
        
        // 6. Mental Recharge (3 Minutes)
        WorkoutSchedule mentalRecharge = new WorkoutSchedule(
            "mental_recharge", "Mental Recharge",
            "Refresh focus and reduce screen fatigue",
            3, "Whenever feeling mentally tired or before returning to work", "Wellness"
        );
        
        mentalRecharge.addExercise(new WorkoutExercise("Eyes closed, deep breathing", 60, "Close eyes and breathe deeply"));
        mentalRecharge.addExercise(new WorkoutExercise("Simple hand and finger stretches", 60, "Stretch hands and fingers"));
        mentalRecharge.addExercise(new WorkoutExercise("Roll shoulders, relax jaw, gentle face massage", 60, "Relax facial muscles and shoulders"));
        schedules.add(mentalRecharge);
        
        return schedules;
    }
    
    // Inner class for workout exercises
    public static class WorkoutExercise {
        private String name;
        private int durationSeconds;
        private String description;
        
        public WorkoutExercise(String name, int durationSeconds, String description) {
            this.name = name;
            this.durationSeconds = durationSeconds;
            this.description = description;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getDurationSeconds() { return durationSeconds; }
        public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
