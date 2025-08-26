package com.s23010285.desk.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.s23010285.desk.R;
import com.s23010285.desk.utils.SettingsManager;

/**
 * Settings fragment for managing app preferences and configurations
 * This screen lets users customize how the app works and looks
 */
public class SettingsFragment extends Fragment {

    // These switches control different notification settings
    // switchNotifications turns all notifications on or off
    private Switch switchNotifications, switchStepTracking, switchWorkoutReminders, switchDarkMode;
    // These switches control different app features and behaviors
    // switchDataSync controls whether the app syncs data with the cloud
    private Switch switchDataSync, switchLocationServices, switchSoundEffects, switchVibration;
    // These text views let users access account and legal information
    // tvAccountSettings lets users manage their account details
    private TextView tvAccountSettings, tvPrivacyPolicy, tvTermsOfService, tvAboutApp;
    // These text views let users manage their data and get help
    // tvDataExport lets users download their workout data
    private TextView tvDataExport, tvDataDelete, tvAppVersion, tvContactSupport;
    // These text views let users customize their fitness goals and reminders
    // tvWorkoutGoals lets users set how many workouts they want to do each day
    private TextView tvWorkoutGoals, tvBreakReminders, tvAchievementAlerts;

    // This manager helps save and load user settings
    private SettingsManager settingsManager;

    /**
     * This method is called when the settings screen is created
     * It sets up the screen and loads the user's current settings
     * @param inflater Helps create the view from the layout file
     * @param container The parent view that will contain this fragment
     * @param savedInstanceState Any saved state from previous instances
     * @return The view that will be displayed to the user
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create the view from our layout file
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        
        // Get the settings manager to handle user preferences
        settingsManager = SettingsManager.getInstance(requireContext());
        // Set up all the UI elements and prepare them for use
        setupViews(view);
        // Set up what happens when users tap buttons or toggle switches
        setupClickListeners();
        // Load the user's current settings and display them
        loadCurrentSettings();
        
        // Return the view so it can be displayed
        return view;
    }

    /**
     * This method finds and connects all the UI elements we need
     * It's like setting up all the switches and buttons before the user can use them
     * @param view The view that contains all our UI elements
     */
    private void setupViews(View view) {
        // Find all the notification setting switches
        // These control different types of notifications the app can send
        switchNotifications = view.findViewById(R.id.switchNotifications);
        switchStepTracking = view.findViewById(R.id.switchStepTracking);
        switchWorkoutReminders = view.findViewById(R.id.switchWorkoutReminders);
        switchDarkMode = view.findViewById(R.id.switchDarkMode);
        
        // Find all the feature toggle switches
        // These control different app features and behaviors
        switchDataSync = view.findViewById(R.id.switchDataSync);
        switchLocationServices = view.findViewById(R.id.switchLocationServices);
        switchSoundEffects = view.findViewById(R.id.switchSoundEffects);
        switchVibration = view.findViewById(R.id.switchVibration);
        
        // Find all the goal and reminder setting text views
        // These let users customize their fitness goals and reminders
        tvWorkoutGoals = view.findViewById(R.id.tvWorkoutGoals);
        tvBreakReminders = view.findViewById(R.id.tvBreakReminders);
        tvAchievementAlerts = view.findViewById(R.id.tvAchievementAlerts);
        
        // Find all the account and legal information text views
        // These let users access account settings and legal documents
        tvAccountSettings = view.findViewById(R.id.tvAccountSettings);
        tvPrivacyPolicy = view.findViewById(R.id.tvPrivacyPolicy);
        tvTermsOfService = view.findViewById(R.id.tvTermsOfService);
        tvAboutApp = view.findViewById(R.id.tvAboutApp);
        
        // Find all the data management and help text views
        // These let users manage their data and get support
        tvDataExport = view.findViewById(R.id.tvDataExport);
        tvDataDelete = view.findViewById(R.id.tvDataDelete);
        tvAppVersion = view.findViewById(R.id.tvAppVersion);
        tvContactSupport = view.findViewById(R.id.tvContactSupport);
    }

    /**
     * This method sets up what happens when users tap buttons or toggle switches
     * It's like telling the app "when someone changes this setting, do this action"
     */
    private void setupClickListeners() {
        // Set up notification setting switches
        // When users toggle the main notifications switch
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the user's choice to the settings manager
            settingsManager.setNotificationsEnabled(isChecked);
            // Update other notification-related settings based on this choice
            updateNotificationDependencies(isChecked);
            // Show a message confirming the change
            showToast("Notifications " + (isChecked ? "enabled" : "disabled"));
        });

        // When users toggle the step tracking switch
        switchStepTracking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the user's choice to the settings manager
            settingsManager.setStepTrackingEnabled(isChecked);
            // Show a message confirming the change
            showToast("Step tracking " + (isChecked ? "enabled" : "disabled"));
        });

        // When users toggle the workout reminders switch
        switchWorkoutReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the user's choice to the settings manager
            settingsManager.setWorkoutRemindersEnabled(isChecked);
            // Show a message confirming the change
            showToast("Workout reminders " + (isChecked ? "enabled" : "disabled"));
        });

        // When users toggle the dark mode switch
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the user's choice to the settings manager
            settingsManager.setDarkModeEnabled(isChecked);
            // Show a message confirming the change
            showToast("Dark mode " + (isChecked ? "enabled" : "disabled"));
            // TODO: Implement theme change
        });

        // Feature toggles
        switchDataSync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDataSyncEnabled(isChecked);
            showToast("Data sync " + (isChecked ? "enabled" : "disabled"));
        });

        switchLocationServices.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setLocationServicesEnabled(isChecked);
            showToast("Location services " + (isChecked ? "enabled" : "disabled"));
        });

        switchSoundEffects.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setSoundEffectsEnabled(isChecked);
            showToast("Sound effects " + (isChecked ? "enabled" : "disabled"));
        });

        switchVibration.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setVibrationEnabled(isChecked);
            showToast("Vibration " + (isChecked ? "enabled" : "disabled"));
        });

        // Goal settings
        tvWorkoutGoals.setOnClickListener(v -> showWorkoutGoalsDialog());
        tvBreakReminders.setOnClickListener(v -> showBreakRemindersDialog());
        tvAchievementAlerts.setOnClickListener(v -> showAchievementAlertsDialog());

        // Account and legal
        tvAccountSettings.setOnClickListener(v -> showAccountSettingsDialog());
        tvPrivacyPolicy.setOnClickListener(v -> openPrivacyPolicy());
        tvTermsOfService.setOnClickListener(v -> openTermsOfService());
        tvAboutApp.setOnClickListener(v -> showAboutAppDialog());

        // Data management
        tvDataExport.setOnClickListener(v -> exportUserData());
        tvDataDelete.setOnClickListener(v -> showDataDeleteConfirmation());
        tvContactSupport.setOnClickListener(v -> contactSupport());
    }

    private void loadCurrentSettings() {
        // Load saved preferences
        switchNotifications.setChecked(settingsManager.areNotificationsEnabled());
        switchStepTracking.setChecked(settingsManager.isStepTrackingEnabled());
        switchWorkoutReminders.setChecked(settingsManager.areWorkoutRemindersEnabled());
        switchDarkMode.setChecked(settingsManager.isDarkModeEnabled());
        switchDataSync.setChecked(settingsManager.isDataSyncEnabled());
        switchLocationServices.setChecked(settingsManager.isLocationServicesEnabled());
        switchSoundEffects.setChecked(settingsManager.areSoundEffectsEnabled());
        switchVibration.setChecked(settingsManager.isVibrationEnabled());

        // Update notification dependencies
        updateNotificationDependencies(switchNotifications.isChecked());
        
        // Set app version
        tvAppVersion.setText("Version 1.0.0");
    }

    private void updateNotificationDependencies(boolean notificationsEnabled) {
        switchStepTracking.setEnabled(notificationsEnabled);
        switchWorkoutReminders.setEnabled(notificationsEnabled);
        
        if (!notificationsEnabled) {
            switchStepTracking.setChecked(false);
            switchWorkoutReminders.setChecked(false);
        }
    }

    private void showWorkoutGoalsDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Workout Goals")
            .setMessage("Set your daily workout goals and targets")
            .setPositiveButton("Configure", (dialog, which) -> {
                // TODO: Open workout goals configuration
                showToast("Workout goals configuration coming soon!");
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showBreakRemindersDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Break Reminders")
            .setMessage("Configure when and how often you want break reminders")
            .setPositiveButton("Configure", (dialog, which) -> {
                // TODO: Open break reminders configuration
                showToast("Break reminders configuration coming soon!");
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showAchievementAlertsDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Achievement Alerts")
            .setMessage("Choose which achievements you want to be notified about")
            .setPositiveButton("Configure", (dialog, which) -> {
                // TODO: Open achievement alerts configuration
                showToast("Achievement alerts configuration coming soon!");
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showAccountSettingsDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Account Settings")
            .setItems(new String[]{"Change Password", "Update Profile", "Delete Account"}, (dialog, which) -> {
                switch (which) {
                    case 0:
                        showToast("Change password functionality coming soon!");
                        break;
                    case 1:
                        showToast("Update profile functionality coming soon!");
                        break;
                    case 2:
                        showDeleteAccountConfirmation();
                        break;
                }
            })
            .show();
    }

    private void showDeleteAccountConfirmation() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete", (dialog, which) -> {
                // TODO: Implement account deletion
                showToast("Account deletion functionality coming soon!");
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void openPrivacyPolicy() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://deskbreak.app/privacy"));
            startActivity(intent);
        } catch (Exception e) {
            showToast("Unable to open privacy policy");
        }
    }

    private void openTermsOfService() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://deskbreak.app/terms"));
            startActivity(intent);
        } catch (Exception e) {
            showToast("Unable to open terms of service");
        }
    }

    private void showAboutAppDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("About DeskBreak")
            .setMessage("DeskBreak v1.0.0\n\nYour personal wellness companion for a healthier workday.\n\nDeveloped with ❤️ for better workplace wellness.")
            .setPositiveButton("OK", null)
            .show();
    }

    private void exportUserData() {
        // TODO: Implement data export functionality
        showToast("Data export functionality coming soon!");
    }

    private void showDataDeleteConfirmation() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Delete All Data")
            .setMessage("This will permanently delete all your workout data, progress, and settings. This action cannot be undone.")
            .setPositiveButton("Delete All Data", (dialog, which) -> {
                // TODO: Implement data deletion
                showToast("Data deletion functionality coming soon!");
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void contactSupport() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@deskbreak.app"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "DeskBreak Support Request");
            intent.putExtra(Intent.EXTRA_TEXT, "Hello, I need help with DeskBreak...");
            startActivity(intent);
        } catch (Exception e) {
            showToast("Unable to open email app");
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
