# DeskBreak Settings Screen

## Overview
The settings screen provides users with comprehensive control over their app preferences, notifications, and account settings. It's designed with a clean, organized interface that groups related settings into logical sections.

## Features

### 🔔 Notifications
- **Enable/Disable Notifications**: Master toggle for all app notifications
- **Step Tracking**: Control step counting notifications
- **Workout Reminders**: Manage workout schedule reminders

### ⚙️ Features
- **Dark Mode**: Toggle between light and dark themes
- **Data Sync**: Control automatic data synchronization
- **Location Services**: Manage location-based features
- **Sound Effects**: Enable/disable audio feedback
- **Vibration**: Control haptic feedback

### 🎯 Goals & Reminders
- **Workout Goals**: Configure daily workout targets
- **Break Reminders**: Set break reminder intervals
- **Achievement Alerts**: Manage achievement notifications

### 👤 Account & Legal
- **Account Settings**: Change password, update profile, delete account
- **Privacy Policy**: Access privacy information
- **Terms of Service**: View app terms
- **About App**: App information and version

### 📊 Data Management
- **Export Data**: Download user data
- **Delete Data**: Remove all app data
- **Contact Support**: Get help from support team
- **App Version**: Display current version

## Technical Implementation

### Files Created
- `SettingsFragment.java` - Main settings logic and UI handling
- `fragment_settings.xml` - Settings screen layout
- `SettingsManager.java` - Centralized settings management utility
- Various drawable resources for icons and backgrounds

### Key Components

#### SettingsFragment
- Handles all user interactions
- Manages switch states and preferences
- Shows confirmation dialogs for destructive actions
- Integrates with SettingsManager for data persistence

#### SettingsManager
- Singleton pattern for centralized settings access
- Provides type-safe methods for all settings
- Handles SharedPreferences internally
- Includes utility methods for data export/import

#### Layout Design
- Organized into logical sections with clear headers
- Uses Material Design principles
- Responsive design with proper spacing
- Ripple effects for interactive elements

## Usage

### Accessing Settings
1. Navigate to the bottom navigation
2. Tap the "Settings" tab (gear icon)
3. Scroll through different sections
4. Toggle switches or tap items for more options

### Settings Persistence
- All settings are automatically saved when changed
- Settings persist across app restarts
- Settings are stored locally using SharedPreferences

### Future Enhancements
- Settings backup and restore
- Cloud synchronization of preferences
- Advanced notification scheduling
- Custom theme creation
- Data export in multiple formats

## Integration

The settings screen integrates with:
- **MainActivity**: Added to bottom navigation
- **Existing fragments**: Can access settings through SettingsManager
- **App-wide**: Settings affect behavior across the entire application

## Dependencies

- AndroidX Fragment
- Material Design components
- Android SharedPreferences
- Custom drawable resources
