# 📱 DeskBreak Android App - Project Notes

## 🎯 **App Overview**
DeskBreak is a comprehensive fitness and wellness app designed for office workers and desk-bound professionals. It helps users stay active during work hours through quick workouts, step tracking, meditation sessions, and progress monitoring.

---

## 🏗️ **Project Structure**

### 📁 **Main App Components**
- **Authentication System** - User login, signup, and password recovery
- **Workout Management** - Exercise library and session tracking
- **Progress Tracking** - Steps, workouts, and achievements
- **User Profile** - Personal settings and statistics
- **Settings & Preferences** - App customization and notifications

---

## 🔐 **Authentication & User Management**

### **WelcomeActivity.java** 🚪
- **Purpose**: Entry point of the app with welcome screen
- **What it does**: Shows a friendly welcome message with logo and illustrations
- **How it works**: 
  - Displays welcome screen with "Next" and "Skip" buttons
  - "Next" button takes users to signup
  - "Skip" button takes users directly to login
  - Creates a smooth onboarding experience

### **LoginActivity.java** 🔑
- **Purpose**: Handles user login and authentication
- **What it does**: 
  - Provides email and password input fields
  - Validates user credentials against database
  - Manages user sessions and preferences
  - Redirects to main app after successful login
- **How it works**:
  - Checks if user is already logged in
  - Validates email format and password requirements
  - Connects to database to verify credentials
  - Saves user session in SharedPreferences
  - Shows success/error messages

### **SignUpActivity.java** ✍️
- **Purpose**: New user account creation
- **What it does**:
  - Collects user information (name, email, password)
  - Creates new user accounts in the database
  - Handles password confirmation
  - Automatically logs in new users
- **How it works**:
  - Validates all input fields
  - Checks if email is already registered
  - Creates new User object and saves to database
  - Establishes user session immediately

### **ForgotPasswordActivity.java** 🔒
- **Purpose**: Password recovery for existing users
- **What it does**:
  - Allows users to reset forgotten passwords
  - Generates secure temporary passwords
  - Updates user accounts with new passwords
- **How it works**:
  - Verifies email exists in database
  - Generates random 8-character password
  - Updates user's password in database
  - Shows new password to user

---

## 🏠 **Main App Interface**

### **MainActivity.java** 🎯
- **Purpose**: Main container activity with bottom navigation
- **What it does**: 
  - Houses all main app screens as fragments
  - Provides bottom navigation between sections
  - Manages fragment switching and navigation
- **How it works**:
  - Uses BottomNavigationView for main sections
  - Loads different fragments (Dashboard, Workouts, Progress, Profile)
  - Handles fragment transactions and state management

### **DashboardFragment.java** 📊
- **Purpose**: Main dashboard showing daily stats and quick actions
- **What it does**:
  - Displays daily step count and workout progress
  - Shows active minutes and today's achievements
  - Provides quick access to start workouts or breaks
- **How it works**:
  - Loads user's daily statistics
  - Updates progress indicators in real-time
  - Handles workout and break session initiation

---

## 💪 **Workout System**

### **WorkoutLibraryFragment.java** 📚
- **Purpose**: Displays available workout routines and schedules
- **What it does**:
  - Shows list of workout programs
  - Displays workout details (duration, purpose, schedule)
  - Allows users to select and start workouts
- **How it works**:
  - Uses RecyclerView to display workout cards
  - Loads workout schedules from WorkoutSchedule class
  - Handles workout selection and navigation to workout sessions

### **WorkoutSessionActivity.java** 🏃‍♂️
- **Purpose**: Active workout tracking and exercise guidance
- **What it does**:
  - Guides users through workout exercises
  - Tracks workout progress and timing
  - Provides video tutorials and animations
  - Monitors steps and distance during outdoor workouts
- **How it works**:
  - Loads workout schedule and exercises
  - Uses CountDownTimer for exercise timing
  - Integrates with YouTube for video tutorials
  - Connects to step detection service
  - Handles different workout types (cardio, stretching, meditation)

---

## ⚙️ **Settings & Configuration**

### **SettingsFragment.java** ⚙️
- **Purpose**: App preferences and configuration management
- **What it does**:
  - Manages notification settings
  - Controls step tracking and workout reminders
  - Handles dark mode and theme preferences
  - Provides account and privacy settings
- **How it works**:
  - Uses switches and toggles for boolean settings
  - Integrates with SettingsManager for persistence
  - Updates app behavior based on user preferences
  - Handles data export and account management

---

## 👤 **User Profile & Progress**

### **ProfileFragment.java** 👤
- **Purpose**: User profile management and statistics display
- **What it does**:
  - Shows user profile picture and information
  - Displays workout and step statistics
  - Manages personal goals and achievements
  - Provides profile editing capabilities
- **How it works**:
  - Loads user data from database
  - Generates weekly progress charts
  - Handles profile picture uploads
  - Manages goal setting and tracking

### **ProgressFragment.java** 📈
- **Purpose**: Comprehensive progress tracking and analytics
- **What it does**:
  - Shows daily, weekly, and monthly statistics
  - Displays progress charts and trends
  - Tracks workout history and achievements
  - Provides motivation through progress visualization
- **How it works**:
  - Integrates with ProgressTracker for data
  - Generates dynamic charts and graphs
  - Calculates averages and trends
  - Updates statistics in real-time

---

## 🔧 **Utility & Helper Classes**

### **ProgressTracker.java** 📊
- **Purpose**: Central progress tracking and statistics management
- **What it does**:
  - Tracks daily steps and workout counts
  - Manages user streaks and achievements
  - Calculates progress percentages and goals
  - Stores progress data persistently
- **How it works**:
  - Uses SharedPreferences for data storage
  - Calculates daily, weekly, and monthly statistics
  - Updates streaks and achievement progress
  - Provides data for progress visualization

### **SettingsManager.java** ⚙️
- **Purpose**: App settings and preferences management
- **What it does**:
  - Stores and retrieves user preferences
  - Manages notification and feature toggles
  - Handles goal settings and reminders
  - Provides centralized settings access
- **How it works**:
  - Uses SharedPreferences for persistent storage
  - Implements singleton pattern for global access
  - Provides boolean and integer preference methods
  - Handles default values and settings validation

### **AchievementManager.java** 🏆
- **Purpose**: Gamification and achievement system
- **What it does**:
  - Manages workout and step achievements
  - Tracks progress towards goals
  - Awards badges and points
  - Provides motivation through rewards
- **How it works**:
  - Defines achievement types and levels
  - Tracks user progress towards achievements
  - Calculates points and user levels
  - Manages achievement unlocking and notifications

### **SocialFeaturesManager.java** 🌐
- **Purpose**: Social media integration and sharing
- **What it does**:
  - Shares workout achievements on social platforms
  - Creates shareable workout summaries
  - Integrates with Instagram, Facebook, Twitter
  - Generates motivational content for sharing
- **How it works**:
  - Creates formatted text for different platforms
  - Handles platform-specific sharing requirements
  - Generates workout achievement graphics
  - Manages sharing permissions and intents

### **ThemeManager.java** 🎨
- **Purpose**: App theming and visual customization
- **What it does**:
  - Manages different app themes and color schemes
  - Provides background images for different screens
  - Handles dark mode and light mode switching
  - Customizes UI elements based on theme
- **How it works**:
  - Defines theme types and categories
  - Maps themes to colors and backgrounds
  - Persists theme preferences
  - Applies themes across app screens

### **WorkoutAudioManager.java** 🎵
- **Purpose**: Audio management for workouts and feedback
- **What it does**:
  - Plays background music during workouts
  - Provides audio feedback for exercises
  - Manages meditation and relaxation sounds
  - Controls volume and audio settings
- **How it works**:
  - Uses ExoPlayer for background music
  - Implements SoundPool for sound effects
  - Manages different audio categories
  - Handles audio focus and interruptions

### **ExerciseVideoManager.java** 🎥
- **Purpose**: YouTube video tutorial management
- **What it does**:
  - Provides exercise-specific video tutorials
  - Manages video timestamps for exercises
  - Curates workout video content
  - Ensures relevant video content for each exercise
- **How it works**:
  - Maps exercise names to YouTube video IDs
  - Provides precise timestamps for exercise segments
  - Generates direct video URLs with timestamps
  - Falls back to default videos when needed

### **ExerciseAnimationManager.java** 🎬
- **Purpose**: Exercise animation and GIF management
- **What it does**:
  - Provides animated GIFs for exercises
  - Shows proper exercise form and movement
  - Enhances workout experience with visuals
  - Supports different exercise categories
- **How it works**:
  - Maps exercises to high-quality GIF URLs
  - Uses Glide library for image loading
  - Provides fallback animations
  - Caches images for better performance

---

## 🗄️ **Database & Data Management**

### **DatabaseHelper.java** 💾
- **Purpose**: SQLite database management and operations
- **What it does**:
  - Creates and manages database tables
  - Handles user data storage and retrieval
  - Manages workout sessions and activity records
  - Provides data persistence for the app
- **How it works**:
  - Extends SQLiteOpenHelper for database operations
  - Creates tables for users, workouts, and activities
  - Provides CRUD operations for all data types
  - Handles database versioning and upgrades

---

## 📱 **Data Models**

### **User.java** 👤
- **Purpose**: User data structure and management
- **What it does**:
  - Stores user information (name, email, password)
  - Manages user goals and preferences
  - Tracks account creation and settings
- **How it works**:
  - Simple POJO class with getters and setters
  - Provides default values for goals
  - Supports user account management

### **Workout.java** 💪
- **Purpose**: Workout data structure and properties
- **What it does**:
  - Defines workout characteristics and details
  - Stores exercise instructions and equipment
  - Manages workout difficulty and target areas
- **How it works**:
  - Uses Room annotations for database integration
  - Provides comprehensive workout information
  - Supports different workout categories

### **WorkoutSchedule.java** 📅
- **Purpose**: Workout program scheduling and organization
- **What it does**:
  - Defines complete workout programs
  - Manages exercise sequences and timing
  - Provides workout categories and purposes
- **How it works**:
  - Contains predefined workout schedules
  - Manages exercise lists and durations
  - Supports different workout types and purposes

### **WorkoutSession.java** 🏃‍♂️
- **Purpose**: Individual workout session tracking
- **What it does**:
  - Records workout start and end times
  - Tracks session duration and progress
  - Stores workout statistics and metrics
- **How it works**:
  - Uses Room annotations for database storage
  - Tracks session status and progress
  - Manages workout timing and completion

### **ActivityRecord.java** 📊
- **Purpose**: Daily activity and progress records
- **What it does**:
  - Stores daily step counts and workout data
  - Tracks distance, calories, and active minutes
  - Records goal achievement status
- **How it works**:
  - Uses Room annotations for database integration
  - Provides daily activity summaries
  - Supports progress tracking and analytics

---

## 🌐 **API & External Services**

### **WeatherService.java** 🌤️
- **Purpose**: Weather API integration interface
- **What it does**:
  - Defines weather API endpoints
  - Handles current weather and forecasts
  - Integrates with external weather services
- **How it works**:
  - Uses Retrofit for API communication
  - Provides methods for weather data retrieval
  - Supports location-based weather queries

### **WeatherResponse.java** 🌡️
- **Purpose**: Weather data structure and parsing
- **What it does**:
  - Defines weather response data structure
  - Handles JSON parsing and serialization
  - Provides weather information access
- **How it works**:
  - Uses Gson annotations for JSON parsing
  - Provides nested classes for weather components
  - Supports temperature, wind, and location data

---

## 🔄 **Background Services**

### **StepDetectorService.java** 👣
- **Purpose**: Step counting and movement detection
- **What it does**:
  - Detects user steps using device sensors
  - Tracks movement and activity levels
  - Provides real-time step counting
- **How it works**:
  - Uses accelerometer and step counter sensors
  - Implements step detection algorithms
  - Provides step count callbacks
  - Manages sensor lifecycle and permissions

---

## 🎯 **Key Features Summary**

### **Core Functionality** ⭐
- **User Authentication**: Secure login and account management
- **Workout Management**: Exercise library and session tracking
- **Progress Tracking**: Steps, workouts, and achievement monitoring
- **Social Features**: Achievement sharing and community engagement
- **Customization**: Themes, audio, and personal preferences

### **Technical Highlights** 🔧
- **Modern Architecture**: Uses Room database and ViewModels
- **Sensor Integration**: Step counting and movement detection
- **Media Integration**: YouTube videos and animated GIFs
- **Social Sharing**: Multi-platform social media integration
- **Responsive UI**: Material Design with custom themes

### **User Experience** 💫
- **Beginner Friendly**: Simple navigation and clear instructions
- **Motivational**: Achievement system and progress tracking
- **Flexible**: Customizable workouts and settings
- **Engaging**: Visual content and social features
- **Accessible**: Works for all fitness levels

---

## 📱 **App Flow**

1. **Welcome Screen** → User introduction and app overview
2. **Authentication** → Login or create new account
3. **Main Dashboard** → Daily stats and quick actions
4. **Workout Selection** → Choose from available routines
5. **Workout Session** → Guided exercise with videos and animations
6. **Progress Tracking** → Monitor achievements and statistics
7. **Profile Management** → Personal settings and goals
8. **Social Sharing** → Share achievements and progress

---

## 🚀 **Getting Started**

The app is designed to be intuitive and user-friendly:
- **First-time users** can create accounts and explore workout options
- **Returning users** can track progress and continue their fitness journey
- **All users** benefit from the comprehensive tracking and motivation systems

This app helps desk workers stay active and healthy during their workday through quick, effective workouts and comprehensive progress monitoring! 🎉
