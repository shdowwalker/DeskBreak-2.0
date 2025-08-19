package com.s23010285.desk.ui.profile;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.s23010285.desk.R;
import com.s23010285.desk.database.DatabaseHelper;
import com.s23010285.desk.model.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.util.Base64;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import java.util.Calendar;
import java.util.Random;

/**
 * Profile fragment displaying user information, stats, goals, and settings
 */
public class ProfileFragment extends Fragment {

    private ShapeableImageView profileImage;
    private TextView profileName, totalWorkouts, totalSteps, streakDays;
    private TextView stepGoal, workoutGoal;
    private TextView editProfilePicText, editNameText, editStepGoal, editWorkoutGoal;
    private LinearLayout notificationSettings, breakReminders, privacySettings;
    private LinearLayout accountInfo, helpSupport, about;
    private TextView btnLogout;

    // Progress tracking views
    private TextView currentSteps, stepGoalDisplay, stepProgressPercent;
    private TextView currentWorkouts, workoutGoalDisplay, workoutProgressPercent;
    private LinearProgressIndicator stepProgressBar, workoutProgressBar;
    private LinearLayout weeklyStepsChart;

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private User currentUser;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(view);
        setupClickListeners();
        loadUserData();
        setupProgressTracking();
        generateWeeklyChart();

        return view;
    }

    private void initializeViews(View view) {
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        totalWorkouts = view.findViewById(R.id.totalWorkouts);
        totalSteps = view.findViewById(R.id.totalSteps);
        streakDays = view.findViewById(R.id.streakDays);
        stepGoal = view.findViewById(R.id.stepGoal);
        workoutGoal = view.findViewById(R.id.workoutGoal);

        editProfilePicText = view.findViewById(R.id.editProfilePicText);
        editNameText = view.findViewById(R.id.editNameText);
        editStepGoal = view.findViewById(R.id.editStepGoal);
        editWorkoutGoal = view.findViewById(R.id.editWorkoutGoal);

        notificationSettings = view.findViewById(R.id.notificationSettings);
        breakReminders = view.findViewById(R.id.breakReminders);
        privacySettings = view.findViewById(R.id.privacySettings);

        accountInfo = view.findViewById(R.id.accountInfo);
        helpSupport = view.findViewById(R.id.helpSupport);
        about = view.findViewById(R.id.about);

        btnLogout = view.findViewById(R.id.btnLogout);

        // Progress tracking views
        currentSteps = view.findViewById(R.id.currentSteps);
        stepGoalDisplay = view.findViewById(R.id.stepGoalDisplay);
        stepProgressPercent = view.findViewById(R.id.stepProgressPercent);
        currentWorkouts = view.findViewById(R.id.currentWorkouts);
        workoutGoalDisplay = view.findViewById(R.id.workoutGoalDisplay);
        workoutProgressPercent = view.findViewById(R.id.workoutProgressPercent);
        stepProgressBar = view.findViewById(R.id.stepProgressBar);
        workoutProgressBar = view.findViewById(R.id.workoutProgressBar);
        weeklyStepsChart = view.findViewById(R.id.weeklyStepsChart);
    }

    private void setupClickListeners() {
        // Profile picture
        profileImage.setOnClickListener(v -> showImagePickerDialog());
        editProfilePicText.setOnClickListener(v -> showImagePickerDialog());

        // Edit name
        editNameText.setOnClickListener(v -> showEditNameDialog());

        // Edit goals
        editStepGoal.setOnClickListener(v -> showEditStepGoalDialog());
        editWorkoutGoal.setOnClickListener(v -> showEditWorkoutGoalDialog());

        // Settings
        notificationSettings.setOnClickListener(v -> showNotificationSettings());
        breakReminders.setOnClickListener(v -> showBreakReminders());
        privacySettings.setOnClickListener(v -> showPrivacySettings());

        // Account
        accountInfo.setOnClickListener(v -> showAccountInfo());
        helpSupport.setOnClickListener(v -> showHelpSupport());
        about.setOnClickListener(v -> showAbout());

        // Logout
        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void loadUserData() {
        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireContext().getSharedPreferences("DeskBreakPrefs", 0);

        // Load user data from SharedPreferences
        String userEmail = sharedPreferences.getString("user_email", "");
        if (!userEmail.isEmpty()) {
            currentUser = databaseHelper.getUserByEmail(userEmail);
            if (currentUser != null) {
                updateProfileUI();
            }
        }

        // Load profile picture if exists
        loadProfilePicture();

        // Load stats
        loadUserStats();
    }

    private void updateProfileUI() {
        if (currentUser != null) {
            profileName.setText(currentUser.getName());
            stepGoal.setText(String.valueOf(currentUser.getDailyStepGoal()));
            workoutGoal.setText(String.valueOf(currentUser.getDailyWorkoutGoal()));
            
            // Update progress displays
            stepGoalDisplay.setText(String.valueOf(currentUser.getDailyStepGoal()));
            workoutGoalDisplay.setText(String.valueOf(currentUser.getDailyWorkoutGoal()));
        }
    }

    private void setupProgressTracking() {
        // Load current progress from SharedPreferences or simulate it
        int currentStepCount = sharedPreferences.getInt("current_daily_steps", 0);
        int currentWorkoutCount = sharedPreferences.getInt("current_daily_workouts", 0);
        
        updateStepProgress(currentStepCount);
        updateWorkoutProgress(currentWorkoutCount);
        
        // Simulate real-time updates (in a real app, this would come from sensors/services)
        simulateProgressUpdates();
    }

    private void updateStepProgress(int currentSteps) {
        int goal = currentUser != null ? currentUser.getDailyStepGoal() : 10000;
        int progress = Math.min(currentSteps, goal);
        int percentage = goal > 0 ? (progress * 100) / goal : 0;
        
        this.currentSteps.setText(String.valueOf(currentSteps));
        stepProgressPercent.setText(percentage + "%");
        stepProgressBar.setProgress(percentage);
        
        // Update progress bar color based on completion
        if (percentage >= 100) {
            stepProgressBar.setIndicatorColor(requireContext().getColor(R.color.accent_green));
        } else if (percentage >= 75) {
            stepProgressBar.setIndicatorColor(requireContext().getColor(R.color.accent_orange));
        } else {
            stepProgressBar.setIndicatorColor(requireContext().getColor(R.color.teal_600));
        }
    }

    private void updateWorkoutProgress(int currentWorkouts) {
        int goal = currentUser != null ? currentUser.getDailyWorkoutGoal() : 3;
        int progress = Math.min(currentWorkouts, goal);
        int percentage = goal > 0 ? (progress * 100) / goal : 0;
        
        this.currentWorkouts.setText(String.valueOf(currentWorkouts));
        workoutProgressPercent.setText(percentage + "%");
        workoutProgressBar.setProgress(percentage);
        
        // Update progress bar color based on completion
        if (percentage >= 100) {
            workoutProgressBar.setIndicatorColor(requireContext().getColor(R.color.accent_green));
        } else if (percentage >= 75) {
            workoutProgressBar.setIndicatorColor(requireContext().getColor(R.color.accent_orange));
        } else {
            workoutProgressBar.setIndicatorColor(requireContext().getColor(R.color.purple_600));
        }
    }

    private void simulateProgressUpdates() {
        // Simulate step counting updates every 5 seconds
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    requireActivity().runOnUiThread(() -> {
                        // Simulate random step increments
                        int currentSteps = sharedPreferences.getInt("current_daily_steps", 0);
                        int newSteps = currentSteps + new Random().nextInt(50) + 10;
                        sharedPreferences.edit().putInt("current_daily_steps", newSteps).apply();
                        updateStepProgress(newSteps);
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    private void generateWeeklyChart() {
        weeklyStepsChart.removeAllViews();
        
        // Generate sample weekly data (in a real app, this would come from database)
        int[] weeklySteps = generateSampleWeeklyData();
        int maxSteps = getMaxValue(weeklySteps);
        
        for (int i = 0; i < weeklySteps.length; i++) {
            View bar = createProgressBar(weeklySteps[i], maxSteps, i);
            weeklyStepsChart.addView(bar);
            
            // Add spacing between bars
            if (i < weeklySteps.length - 1) {
                View spacer = new View(requireContext());
                spacer.setLayoutParams(new LinearLayout.LayoutParams(16, 1));
                weeklyStepsChart.addView(spacer);
            }
        }
    }

    private int[] generateSampleWeeklyData() {
        // Generate realistic weekly step data
        Random random = new Random();
        int[] steps = new int[7];
        
        // Weekdays typically have more steps
        for (int i = 0; i < 5; i++) {
            steps[i] = 8000 + random.nextInt(4000); // 8000-12000 steps
        }
        
        // Weekend typically has fewer steps
        for (int i = 5; i < 7; i++) {
            steps[i] = 5000 + random.nextInt(3000); // 5000-8000 steps
        }
        
        return steps;
    }

    private int getMaxValue(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) max = value;
        }
        return max;
    }

    private View createProgressBar(int steps, int maxSteps, int dayIndex) {
        FrameLayout barContainer = new FrameLayout(requireContext());
        barContainer.setLayoutParams(new LinearLayout.LayoutParams(0, 120, 1));
        
        // Background bar
        View backgroundBar = new View(requireContext());
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        backgroundBar.setLayoutParams(bgParams);
        backgroundBar.setBackgroundColor(requireContext().getColor(R.color.teal_100));
        barContainer.addView(backgroundBar);
        
        // Progress bar
        View progressBar = new View(requireContext());
        int barHeight = maxSteps > 0 ? (steps * 120) / maxSteps : 0;
        FrameLayout.LayoutParams progressParams = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, barHeight);
        progressParams.gravity = android.view.Gravity.BOTTOM;
        progressBar.setLayoutParams(progressParams);
        progressBar.setBackgroundColor(requireContext().getColor(R.color.teal_600));
        barContainer.addView(progressBar);
        
        // Step count label
        TextView stepLabel = new TextView(requireContext());
        FrameLayout.LayoutParams labelParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        labelParams.gravity = android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL;
        labelParams.topMargin = 8;
        stepLabel.setLayoutParams(labelParams);
        stepLabel.setText(String.valueOf(steps));
        stepLabel.setTextColor(requireContext().getColor(R.color.text_primary));
        stepLabel.setTextSize(10);
        stepLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        barContainer.addView(stepLabel);
        
        return barContainer;
    }

    private void loadProfilePicture() {
        String profilePicData = sharedPreferences.getString("profile_picture", "");
        if (!profilePicData.isEmpty()) {
            try {
                byte[] imageBytes = Base64.decode(profilePicData, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                if (bitmap != null) {
                    profileImage.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUserStats() {
        totalWorkouts.setText("12");
        totalSteps.setText("45,230");
        streakDays.setText("7");
    }

    private void showImagePickerDialog() {
        String[] options = {"Take Photo", "Choose from Gallery", "Remove Photo"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Change Profile Picture")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            Toast.makeText(requireContext(), "Camera feature coming soon!", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            openGallery();
                            break;
                        case 2:
                            removeProfilePicture();
                            break;
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void removeProfilePicture() {
        profileImage.setImageResource(R.drawable.ic_profile);
        sharedPreferences.edit().remove("profile_picture").apply();
        Toast.makeText(requireContext(), "Profile picture removed", Toast.LENGTH_SHORT).show();
    }

    private void showEditNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Name");

        final EditText input = new EditText(requireContext());
        input.setText(profileName.getText());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                profileName.setText(newName);
                if (currentUser != null) {
                    currentUser.setName(newName);
                    // databaseHelper.updateUser(currentUser);
                }
                Toast.makeText(requireContext(), "Name updated!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditStepGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Daily Step Goal");

        final EditText input = new EditText(requireContext());
        input.setText(stepGoal.getText());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newGoal = input.getText().toString().trim();
            if (!newGoal.isEmpty()) {
                int goal = Integer.parseInt(newGoal);
                stepGoal.setText(String.valueOf(goal));
                stepGoalDisplay.setText(String.valueOf(goal));
                if (currentUser != null) {
                    currentUser.setDailyStepGoal(goal);
                    // databaseHelper.updateUser(currentUser);
                }
                // Update progress with new goal
                int currentSteps = sharedPreferences.getInt("current_daily_steps", 0);
                updateStepProgress(currentSteps);
                Toast.makeText(requireContext(), "Step goal updated!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditWorkoutGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Daily Workout Goal");

        final EditText input = new EditText(requireContext());
        input.setText(workoutGoal.getText());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newGoal = input.getText().toString().trim();
            if (!newGoal.isEmpty()) {
                int goal = Integer.parseInt(newGoal);
                workoutGoal.setText(String.valueOf(goal));
                workoutGoalDisplay.setText(String.valueOf(goal));
                if (currentUser != null) {
                    currentUser.setDailyWorkoutGoal(goal);
                    // databaseHelper.updateUser(currentUser);
                }
                // Update progress with new goal
                int currentWorkouts = sharedPreferences.getInt("current_daily_workouts", 0);
                updateWorkoutProgress(currentWorkouts);
                Toast.makeText(requireContext(), "Workout goal updated!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showNotificationSettings() {
        new AlertDialog.Builder(requireContext())
                .setTitle("🔔 Notification Settings")
                .setMessage("Configure your notification preferences:\n\n• Workout reminders\n• Break reminders\n• Achievement notifications\n• Weekly progress reports")
                .setPositiveButton("Configure", (dialog, which) -> {
                    Toast.makeText(requireContext(), "Notification settings coming soon!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showBreakReminders() {
        new AlertDialog.Builder(requireContext())
                .setTitle("⏰ Break Reminders")
                .setMessage("Set up reminders to take breaks:\n\n• Reminder interval\n• Break duration\n• Quiet hours\n• Custom messages")
                .setPositiveButton("Configure", (dialog, which) -> {
                    Toast.makeText(requireContext(), "Break reminder settings coming soon!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showPrivacySettings() {
        new AlertDialog.Builder(requireContext())
                .setTitle("🔒 Privacy Settings")
                .setMessage("Manage your privacy preferences:\n\n• Data sharing\n• Location tracking\n• Analytics\n• Third-party access")
                .setPositiveButton("Configure", (dialog, which) -> {
                    Toast.makeText(requireContext(), "Privacy settings coming soon!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showAccountInfo() {
        if (currentUser != null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("📧 Account Information")
                    .setMessage("Email: " + currentUser.getEmail() + "\n" +
                              "Name: " + currentUser.getName() + "\n" +
                              "Member since: " + new java.text.SimpleDateFormat("MMM dd, yyyy")
                              .format(new java.util.Date(currentUser.getCreatedAt())))
                    .setPositiveButton("Edit", (dialog, which) -> {
                        Toast.makeText(requireContext(), "Account editing coming soon!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Close", null)
                    .show();
        }
    }

    private void showHelpSupport() {
        new AlertDialog.Builder(requireContext())
                .setTitle("❓ Help & Support")
                .setMessage("Need help? Here are your options:\n\n• FAQ\n• Contact support\n• User guide\n• Troubleshooting")
                .setPositiveButton("Get Help", (dialog, which) -> {
                    Toast.makeText(requireContext(), "Help & support coming soon!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showAbout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("ℹ️ About DeskBreak")
                .setMessage("DeskBreak v1.0.0\n\n" +
                          "Your personal wellness companion for a healthier workday.\n\n" +
                          "Features:\n• Desk-friendly workouts\n• Step tracking\n• Break reminders\n• Progress monitoring\n\n" +
                          "Made with ❤️ for your health")
                .setPositiveButton("Close", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("🚪 Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    // Clear user session
                    sharedPreferences.edit().clear().apply();

                    // Navigate back to login
                    requireActivity().finish();
                    Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    if (bitmap != null) {
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

                        profileImage.setImageBitmap(resizedBitmap);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                        String imageData = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                        sharedPreferences.edit().putString("profile_picture", imageData).apply();

                        Toast.makeText(requireContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
