package com.s23010285.desk.ui.workout;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.s23010285.desk.R;
import com.s23010285.desk.model.WorkoutSchedule;
import com.s23010285.desk.utils.ProgressTracker;
import com.s23010285.desk.utils.ExerciseVideoManager;
import com.s23010285.desk.utils.WorkoutAudioManager;
import com.s23010285.desk.utils.AchievementManager;
import com.s23010285.desk.utils.SocialFeaturesManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.s23010285.desk.service.StepDetectorService;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import java.util.List;

/**
 * Workout session activity for active workout tracking
 * Displays different UI based on workout type with interactive exercise flow
 * This is the main screen where users actually do their workouts
 */
public class WorkoutSessionActivity extends AppCompatActivity implements OnMapReadyCallback, StepDetectorService.StepDetectionListener {

    // These variables hold references to the main workout session UI elements
    // workoutSessionTitle shows the name of the current workout
    private TextView workoutSessionTitle, tvSteps, tvDistance;
    // These buttons let users control their workout session
    private Button btnPause, btnStop, btnFinish;

    // Exercise navigation views - these help users move through different exercises
    // exerciseNumber shows which exercise the user is currently doing (e.g., "Exercise 1 of 5")
    private TextView exerciseNumber, exerciseName, exerciseDescription, exerciseTimerText;
    // exerciseProgress shows a visual progress bar for the current exercise
    private LinearProgressIndicator exerciseProgress;
    // These buttons let users go to the previous or next exercise
    private Button btnPrevious, btnNext;
    
    // YouTube video tutorial views - these show exercise videos to help users
    // youTubePlayerView displays the video player
    private YouTubePlayerView youTubePlayerView;
    // youTubePlayer controls the video playback
    private YouTubePlayer youTubePlayer;
    // videoTutorialLabel shows text explaining the video
    private TextView videoTutorialLabel;

    // Meditation-specific views - these are used when the workout is a meditation session
    // cardMeditation contains all meditation-related UI elements
    private View cardMeditation, breathingCircle, meditationIcon;
    // These text views show meditation instructions and progress
    private TextView breathingGuide, meditationProgress;

    // Workout type tracking - these variables store information about the current workout
    // workoutId uniquely identifies the workout
    private String workoutId;
    // workoutName is the display name of the workout
    private String workoutName;
    // workoutCategory tells us what type of workout this is (cardio, strength, meditation, etc.)
    private String workoutCategory;
    // workoutDuration is how long the workout should take in minutes
    private int workoutDuration;
    // isPaused tracks whether the workout is currently paused
    private boolean isPaused = false;
    // isMeditationSession tells us if this is a meditation workout
    private boolean isMeditationSession = false;

    // These variables help manage the workout flow and exercises
    // selectedSchedule contains all the details about the chosen workout
    private WorkoutSchedule selectedSchedule;
    // exercises is the list of individual exercises in this workout
    private List<WorkoutSchedule.WorkoutExercise> exercises;
    // currentExerciseIndex tracks which exercise the user is currently doing
    private int currentExerciseIndex = 0;
    // exerciseTimer counts down the time for each exercise
    private CountDownTimer exerciseTimer;
    // totalWorkoutTime tracks the total time the workout should take
    private int totalWorkoutTime = 0;
    // completedWorkoutTime tracks how much time has been completed
    private int completedWorkoutTime = 0;

    // Google Maps and Location - these help track outdoor workouts
    // mMap is the Google Maps object that shows the user's location
    private GoogleMap mMap;
    // fusedLocationClient helps get the user's current location
    private FusedLocationProviderClient fusedLocationClient;
    // This constant is used when asking for location permission
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    // currentLocation stores the user's current GPS coordinates
    private LatLng currentLocation;
    // stepCount tracks how many steps the user has taken during this workout
    private int stepCount = 0;
    // totalDistance tracks how far the user has moved during this workout
    private double totalDistance = 0.0;

    // Meditation session variables - these help manage meditation-specific features
    // meditationHandler helps schedule meditation-related actions
    private Handler meditationHandler;
    // meditationProgressValue tracks how far along the meditation session is
    private int meditationProgressValue = 0;
    // isBreathingIn tracks whether the user should be breathing in or out
    private boolean isBreathingIn = true;
    // breathingAnimator creates smooth animations for breathing guidance
    private ObjectAnimator breathingAnimator;
    
    // Progress tracking - these help monitor the user's workout performance
    private ProgressTracker progressTracker;
    
    // Premium features
    private WorkoutAudioManager audioManager;
    private AchievementManager achievementManager;
    private SocialFeaturesManager socialFeaturesManager;
    
    // Step detection service
    private StepDetectorService stepDetectorService;
    private boolean isStepServiceBound = false;
    private int sessionStartSteps = 0;
    
    // Service connection for step detector
    private ServiceConnection stepServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Log.d("WorkoutSession", "StepDetectorService connected");
                StepDetectorService.LocalBinder binder = (StepDetectorService.LocalBinder) service;
                stepDetectorService = binder.getService();
                stepDetectorService.setStepDetectionListener(WorkoutSessionActivity.this);
                isStepServiceBound = true;
                
                // Start step detection for cardio workouts
                if ("Cardio".equalsIgnoreCase(workoutCategory) || 
                    "lite_cardio".equalsIgnoreCase(workoutId)) {
                    Log.d("WorkoutSession", "Starting step counting for cardio workout");
                    startStepCounting();
                } else {
                    Log.d("WorkoutSession", "Not a cardio workout, skipping step detection. Category: " + 
                                           workoutCategory + ", ID: " + workoutId);
                }
            } catch (Exception e) {
                Log.e("WorkoutSession", "Error in onServiceConnected: " + e.getMessage(), e);
            }
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("WorkoutSession", "StepDetectorService disconnected");
            stepDetectorService = null;
            isStepServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity_workout_session);

        // Get workout data from intent
        workoutId = getIntent().getStringExtra("workout_id");
        workoutName = getIntent().getStringExtra("workout_name");
        workoutDuration = getIntent().getIntExtra("workout_duration", 5);

        // Initialize progress tracker
        progressTracker = new ProgressTracker(this);
        
        // Initialize premium features
        audioManager = new WorkoutAudioManager(this);
        achievementManager = new AchievementManager(this);
        socialFeaturesManager = new SocialFeaturesManager(this);

        // Resolve full schedule by id
        resolveSelectedSchedule();

        setupViews();
        setupUI();
        setupClickListeners();
        
        // Bind step detector service only for cardio workouts
        if ("Cardio".equalsIgnoreCase(workoutCategory) || 
            "lite_cardio".equalsIgnoreCase(workoutId)) {
            bindStepDetectorService();
        }
        
        startWorkoutSession();
    }

    private void resolveSelectedSchedule() {
        List<WorkoutSchedule> all = WorkoutSchedule.getAllWorkoutSchedules();
        if (all != null) {
            for (WorkoutSchedule s : all) {
                if (s.getId().equals(workoutId)) {
                    selectedSchedule = s;
                    break;
                }
            }
        }
        if (selectedSchedule != null) {
            // prefer canonical values from the schedule
            workoutName = selectedSchedule.getName();
            workoutDuration = selectedSchedule.getDurationMinutes();
            workoutCategory = selectedSchedule.getCategory();
            exercises = selectedSchedule.getExercises();
            
            // Check if this is a mental recharge session
            isMeditationSession = "Mental Recharge".equalsIgnoreCase(workoutCategory) ||
                                 workoutName.toLowerCase().contains("meditation") ||
                                 workoutName.toLowerCase().contains("mental") ||
                                 workoutName.toLowerCase().contains("breathing");
        }
    }

    private void setupViews() {
        workoutSessionTitle = findViewById(R.id.workoutSessionTitle);
        tvSteps = findViewById(R.id.tvSteps);
        tvDistance = findViewById(R.id.tvDistance);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        btnFinish = findViewById(R.id.btnFinish);

        // Exercise navigation views
        exerciseNumber = findViewById(R.id.exerciseNumber);
        exerciseName = findViewById(R.id.exerciseName);
        exerciseDescription = findViewById(R.id.exerciseDescription);
        exerciseTimerText = findViewById(R.id.exerciseTimer);
        exerciseProgress = findViewById(R.id.exerciseProgress);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        videoTutorialLabel = findViewById(R.id.videoTutorialLabel);
        
        // Setup YouTube player
        setupYouTubePlayer();

        // Meditation views
        cardMeditation = findViewById(R.id.cardMeditation);
        breathingCircle = findViewById(R.id.breathingCircle);
        meditationIcon = findViewById(R.id.meditationIcon);
        breathingGuide = findViewById(R.id.breathingGuide);
        meditationProgress = findViewById(R.id.meditationProgress);
    }

    private void setupUI() {
        // Title
        if (workoutName != null && !workoutName.isEmpty()) {
            workoutSessionTitle.setText(workoutName);
        }

        // Initialize stats with real data
        updateStepCounter();

        // Show/hide UI elements based on workout type
        if (isMeditationSession) {
            setupMeditationSession();
        } else if ("Cardio".equalsIgnoreCase(workoutCategory) || 
                   "lite_cardio".equalsIgnoreCase(workoutId)) {
            setupCardioSession();
        } else {
            setupRegularWorkoutSession();
        }

        // Setup exercise navigation
        setupExerciseNavigation();

        // Render exercises if available
        renderExercises();
    }

    private void setupExerciseNavigation() {
        if (exercises == null || exercises.isEmpty()) {
            // Hide exercise navigation if no exercises
            findViewById(R.id.cardCurrentExercise).setVisibility(View.GONE);
            findViewById(R.id.exerciseNavigation).setVisibility(View.GONE);
            return;
        }

        // Show first exercise
        showCurrentExercise();
        
        // Setup navigation buttons
        btnPrevious.setOnClickListener(v -> previousExercise());
        btnNext.setOnClickListener(v -> nextExercise());
        
        // Initially disable previous button
        btnPrevious.setEnabled(false);
        updateNavigationButtons();
    }

    private void showCurrentExercise() {
        if (exercises == null || currentExerciseIndex >= exercises.size()) {
            return;
        }

        WorkoutSchedule.WorkoutExercise exercise = exercises.get(currentExerciseIndex);
        
        // Update exercise info
        exerciseNumber.setText("Exercise " + (currentExerciseIndex + 1) + " of " + exercises.size());
        exerciseName.setText(exercise.getName());
        exerciseDescription.setText(exercise.getDescription());
        
        // Load exercise animation
        // Exercise video will be loaded when YouTube player is ready
        
        // Start exercise timer
        startExerciseTimer(exercise.getDurationSeconds());
        
        // Update progress
        updateExerciseProgress();
    }

    /**
     * Setup YouTube player for exercise tutorials
     */
    private void setupYouTubePlayer() {
        if (youTubePlayerView != null) {
            getLifecycle().addObserver(youTubePlayerView);
            
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer player) {
                    youTubePlayer = player;
                    // Load initial video
                    loadExerciseVideo();
                }
            });
        }
    }
    
    /**
     * Load YouTube video for current exercise with timestamp
     */
    private void loadExerciseVideo() {
        if (youTubePlayer == null) return;
        
        String exerciseName = null;
        if (exercises != null && !exercises.isEmpty() && currentExerciseIndex < exercises.size()) {
            exerciseName = exercises.get(currentExerciseIndex).getName();
        }
        
        ExerciseVideoManager.VideoData videoData;
        if (exerciseName != null) {
            videoData = ExerciseVideoManager.getVideoData(exerciseName);
        } else {
            videoData = ExerciseVideoManager.getVideoDataForCategory(workoutCategory);
        }
        
        // Load video with timestamp
        youTubePlayer.loadVideo(videoData.videoId, videoData.startTimeSeconds);
        
        // Update label with video title
        if (videoTutorialLabel != null) {
            videoTutorialLabel.setText(videoData.title);
        }
        
        Log.d("WorkoutSession", "Loading video: " + videoData.title + 
              " (ID: " + videoData.videoId + ", Time: " + videoData.startTimeSeconds + "s)");
    }

    private void updateExerciseProgress() {
        // Update the exercise list to highlight current exercise
        renderExercises();
    }

    private void startExerciseTimer(int durationSeconds) {
        if (exerciseTimer != null) {
            exerciseTimer.cancel();
        }

        // Play exercise start sound
        audioManager.playExerciseStartSound();

        exerciseTimer = new CountDownTimer(durationSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isPaused) {
                    int secondsLeft = (int) (millisUntilFinished / 1000);
                    exerciseTimerText.setText(String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60));
                    
                    // Update progress bar
                    int progress = (int) (((durationSeconds - secondsLeft) * 100) / durationSeconds);
                    exerciseProgress.setProgress(progress);
                    
                    // Play timer beep for last 3 seconds
                    if (secondsLeft <= 3 && secondsLeft > 0) {
                        audioManager.playTimerBeepSound();
                    }
                }
            }

            @Override
            public void onFinish() {
                exerciseTimerText.setText("00:00");
                exerciseProgress.setProgress(100);
                
                // Play exercise complete sound
                audioManager.playExerciseCompleteSound();
                
                // Auto-advance to next exercise after a short delay
                new Handler().postDelayed(() -> {
                    if (currentExerciseIndex < exercises.size() - 1) {
                        nextExercise();
                    }
                }, 2000);
            }
        }.start();
    }

    private void previousExercise() {
        if (currentExerciseIndex > 0) {
            currentExerciseIndex--;
            showCurrentExercise();
            updateNavigationButtons();
            loadExerciseVideo(); // Load new video for this exercise
        }
    }

    private void nextExercise() {
        if (currentExerciseIndex < exercises.size() - 1) {
            currentExerciseIndex++;
            showCurrentExercise();
            updateNavigationButtons();
            loadExerciseVideo(); // Load new video for this exercise
        } else {
            // Last exercise completed
            completeWorkout();
        }
    }

    private void updateNavigationButtons() {
        btnPrevious.setEnabled(currentExerciseIndex > 0);
        
        if (currentExerciseIndex == exercises.size() - 1) {
            btnNext.setText("Finish Workout");
        } else {
            btnNext.setText("Next Exercise");
        }
    }

    private void completeWorkout() {
        // Record workout completion in progress tracker
        if (selectedSchedule != null) {
            progressTracker.completeWorkout(workoutName, workoutDuration);
        }
        
        // Play workout complete sound
        audioManager.playWorkoutCompleteSound();
        
        // Update achievements
        achievementManager.updateProgress(AchievementManager.AchievementType.EXERCISE_COUNT, 1);
        achievementManager.updateProgress(AchievementManager.AchievementType.TIME_GOAL, workoutDuration);
        
        // Show completion message
        Toast.makeText(this, "Workout completed! Great job!", Toast.LENGTH_LONG).show();
        
        // Update UI to show completion
        exerciseName.setText("Workout Complete! 🎉");
        exerciseDescription.setText("You've successfully completed all exercises. Well done!");
        exerciseTimerText.setText("00:00");
        exerciseProgress.setProgress(100);
        
        // Hide navigation buttons
        btnPrevious.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        
        // Show finish button prominently
        btnFinish.setVisibility(View.VISIBLE);
        btnFinish.setText("Finish & Save");
        
        // Stop background music
        audioManager.stopBackgroundMusic();
    }
    
    private void updateStepCounter() {
        if (tvSteps != null) {
            int todaySteps = progressTracker.getTodaySteps();
            tvSteps.setText(String.valueOf(todaySteps));
            
            // Update step goal achievements
            achievementManager.updateProgress(AchievementManager.AchievementType.STEP_GOAL, todaySteps);
        }
        if (tvDistance != null) {
            int todaySteps = progressTracker.getTodaySteps();
            double distance = todaySteps * 0.7; // meters per step approximation
            tvDistance.setText(String.format("%.1fm", distance));
        }
    }

    private void setupMeditationSession() {
        // Hide cardio-specific elements
        findViewById(R.id.cardSteps).setVisibility(View.GONE);
        findViewById(R.id.cardDistance).setVisibility(View.GONE);
        findViewById(R.id.cardMap).setVisibility(View.GONE);
        findViewById(R.id.cardCurrentExercise).setVisibility(View.GONE);
        findViewById(R.id.exerciseNavigation).setVisibility(View.GONE);

        // Show meditation elements
        cardMeditation.setVisibility(View.VISIBLE);

        // Start breathing animation
        startBreathingAnimation();
        
        // Start meditation progress
        startMeditationProgress();
        
        // Load meditation video tutorial
        if (youTubePlayer != null) {
            ExerciseVideoManager.VideoData videoData = 
                ExerciseVideoManager.getVideoDataForCategory("meditation");
            youTubePlayer.loadVideo(videoData.videoId, videoData.startTimeSeconds);
            if (videoTutorialLabel != null) {
                videoTutorialLabel.setText(videoData.title);
            }
        }
        
        // Start background music for meditation
        audioManager.playBackgroundMusic("meditation");
    }

    private void setupCardioSession() {
        // Show cardio-specific elements
        findViewById(R.id.cardSteps).setVisibility(View.VISIBLE);
        findViewById(R.id.cardDistance).setVisibility(View.VISIBLE);
        findViewById(R.id.cardMap).setVisibility(View.VISIBLE);

        // Hide meditation elements
        cardMeditation.setVisibility(View.GONE);

        // Initialize step tracking with real data
        updateStepCounter();
        
        // Initialize display values
        if (tvSteps != null) {
            tvSteps.setText("0");
        }
        if (tvDistance != null) {
            tvDistance.setText("0.0m");
        }

        // Setup Google Maps
        setupGoogleMaps();
        
        // Load cardio video tutorial
        if (youTubePlayer != null) {
            ExerciseVideoManager.VideoData videoData = 
                ExerciseVideoManager.getVideoDataForCategory("cardio");
            youTubePlayer.loadVideo(videoData.videoId, videoData.startTimeSeconds);
            if (videoTutorialLabel != null) {
                videoTutorialLabel.setText(videoData.title);
            }
        }
        
        // Start background music for cardio
        audioManager.playBackgroundMusic("cardio");
    }

    private void setupRegularWorkoutSession() {
        // Hide cardio-specific elements
        findViewById(R.id.cardSteps).setVisibility(View.GONE);
        findViewById(R.id.cardDistance).setVisibility(View.GONE);
        findViewById(R.id.cardMap).setVisibility(View.GONE);

        // Hide meditation elements
        cardMeditation.setVisibility(View.GONE);
        
        // Load default animation for regular workout
        // Exercise video will be loaded when YouTube player is ready
        
        // Start background music for strength training
        audioManager.playBackgroundMusic("strength");
    }

    private void startBreathingAnimation() {
        // Create breathing animation
        breathingAnimator = ObjectAnimator.ofFloat(breathingCircle, "scaleX", 1.0f, 1.5f, 1.0f);
        breathingAnimator.setDuration(4000); // 4 seconds for complete breath cycle
        breathingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        breathingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        
        breathingAnimator.addUpdateListener(animation -> {
            float scale = (Float) animation.getAnimatedValue();
            breathingCircle.setScaleX(scale);
            breathingCircle.setScaleY(scale);
            
            // Update breathing guide text
            if (scale > 1.25f) {
                breathingGuide.setText("Breathe in...");
            } else if (scale < 1.25f) {
                breathingGuide.setText("Breathe out...");
            }
        });
        
        breathingAnimator.start();
    }

    private void startMeditationProgress() {
        meditationHandler = new Handler(Looper.getMainLooper());
        
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused && meditationProgressValue < 100) {
                    meditationProgressValue += 1;
                    meditationProgress.setText("Session Progress: " + meditationProgressValue + "%");
                    
                    // Continue progress
                    meditationHandler.postDelayed(this, (workoutDuration * 60 * 1000) / 100);
                }
            }
        };
        
        meditationHandler.post(progressRunnable);
    }

    private void setupClickListeners() {
        // Pause/Resume button
        btnPause.setOnClickListener(v -> {
            if (isPaused) {
                resumeWorkout();
            } else {
                pauseWorkout();
            }
        });

        // Stop button
        btnStop.setOnClickListener(v -> {
            // TODO: Implement stop functionality
            finish();
        });

        // Finish button
        btnFinish.setOnClickListener(v -> {
            // TODO: Implement finish functionality
            finish();
        });
    }

    private void setupGoogleMaps() {
        // Initialize Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check location permission
        if (checkLocationPermission()) {
            enableMyLocation();
        } else {
            requestLocationPermission();
        }

        // Fallback default location
        LatLng defaultLocation = new LatLng(37.7749, -122.4194); // San Francisco
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void enableMyLocation() {
        if (checkLocationPermission() && mMap != null) {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory
                                    .newLatLngZoom(currentLocation, 15));
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission required for workout tracking",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pauseWorkout() {
        isPaused = true;
        btnPause.setText("Resume");
        
        if (isMeditationSession) {
            // Pause breathing animation
            if (breathingAnimator != null) {
                breathingAnimator.pause();
            }
        }
        
        // Pause exercise timer
        if (exerciseTimer != null) {
            exerciseTimer.cancel();
        }
        
        // Pause YouTube video
        if (youTubePlayer != null) {
            youTubePlayer.pause();
        }
        
        // Pause background music
        audioManager.pauseBackgroundMusic();
        
        // Pause step detection for cardio workouts
        if (("Cardio".equalsIgnoreCase(workoutCategory) || 
             "lite_cardio".equalsIgnoreCase(workoutId)) && stepDetectorService != null) {
            stopStepCounting();
        }
    }

    private void resumeWorkout() {
        isPaused = false;
        btnPause.setText("Pause");
        
        if (isMeditationSession) {
            // Resume breathing animation
            if (breathingAnimator != null) {
                breathingAnimator.resume();
            }
        }
        
        // Resume exercise timer
        if (exercises != null && currentExerciseIndex < exercises.size()) {
            WorkoutSchedule.WorkoutExercise exercise = exercises.get(currentExerciseIndex);
            startExerciseTimer(exercise.getDurationSeconds());
        }
        
        // Resume YouTube video
        if (youTubePlayer != null) {
            youTubePlayer.play();
        }
        
        // Resume background music
        audioManager.resumeBackgroundMusic();
        
        // Resume step detection for cardio workouts
        if (("Cardio".equalsIgnoreCase(workoutCategory) || 
             "lite_cardio".equalsIgnoreCase(workoutId)) && stepDetectorService != null) {
            startStepCounting();
        }
    }

    private void startWorkoutSession() {
        if (isMeditationSession) {
            // Start meditation session
            startMeditationProgress();
        } else if ("Cardio".equalsIgnoreCase(workoutCategory) || 
                   "lite_cardio".equalsIgnoreCase(workoutId)) {
            // Start cardio session with real step tracking
            // Step counting will be started when service connects
        }
    }

    private void bindStepDetectorService() {
        try {
            Intent intent = new Intent(this, StepDetectorService.class);
            boolean bound = bindService(intent, stepServiceConnection, Context.BIND_AUTO_CREATE);
            if (!bound) {
                Log.e("WorkoutSession", "Failed to bind StepDetectorService");
            }
        } catch (Exception e) {
            Log.e("WorkoutSession", "Error binding StepDetectorService: " + e.getMessage(), e);
        }
    }
    
    private void startStepCounting() {
        try {
            if (stepDetectorService != null && stepDetectorService.isDetecting()) {
                // Already detecting, just reset for this session
                sessionStartSteps = stepDetectorService.getStepCount();
                Log.d("WorkoutSession", "Using existing step detection, session start: " + sessionStartSteps);
            } else if (stepDetectorService != null) {
                // Start fresh step detection
                stepDetectorService.resetStepCount();
                stepDetectorService.startStepDetection();
                sessionStartSteps = 0;
                Log.d("WorkoutSession", "Started new step detection");
            } else {
                Log.w("WorkoutSession", "StepDetectorService not available, cannot start step counting");
                return;
            }
            
            // Initialize display
            updateStepCounter();
        } catch (Exception e) {
            Log.e("WorkoutSession", "Error starting step counting: " + e.getMessage(), e);
        }
    }
    
    private void stopStepCounting() {
        try {
            if (stepDetectorService != null) {
                stepDetectorService.stopStepDetection();
                Log.d("WorkoutSession", "Step detection stopped");
            }
        } catch (Exception e) {
            Log.e("WorkoutSession", "Error stopping step counting: " + e.getMessage(), e);
        }
    }
    
    // Step detection listener methods
    @Override
    public void onStepDetected(int totalSteps) {
        try {
            runOnUiThread(() -> {
                try {
                    // Calculate steps for this session
                    int sessionSteps = totalSteps - sessionStartSteps;
                    
                    // Update progress tracker with real steps
                    if (sessionSteps > 0) {
                        progressTracker.addSteps(1); // Add one step at a time as detected
                    }
                    
                    // Update UI
                    updateStepCounter();
                    
                    // Show step count in UI
                    if (tvSteps != null) {
                        tvSteps.setText(String.valueOf(sessionSteps));
                    }
                    
                    // Update distance based on real steps
                    if (tvDistance != null) {
                        double distance = sessionSteps * 0.7; // meters per step approximation
                        tvDistance.setText(String.format("%.1fm", distance));
                    }
                    
                    Log.d("WorkoutSession", "Step detected - Total: " + totalSteps + 
                                           ", Session start: " + sessionStartSteps + 
                                           ", Session steps: " + sessionSteps);
                } catch (Exception e) {
                    Log.e("WorkoutSession", "Error updating UI for step detection: " + e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            Log.e("WorkoutSession", "Error in onStepDetected: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void onStepCountReset() {
        runOnUiThread(() -> {
            sessionStartSteps = 0;
            updateStepCounter();
        });
    }

    private void renderExercises() {
        LinearLayout container = findViewById(R.id.exercisesContainer);
        if (container == null) return;
        if (selectedSchedule == null || selectedSchedule.getExercises() == null) {
            container.setVisibility(View.GONE);
            return;
        }
        container.setVisibility(View.VISIBLE);
        // Remove any existing children after header
        if (container.getChildCount() > 1) {
            container.removeViews(1, container.getChildCount() - 1);
        }
        for (int i = 0; i < selectedSchedule.getExercises().size(); i++) {
            WorkoutSchedule.WorkoutExercise ex = selectedSchedule.getExercises().get(i);
            TextView item = new TextView(this);
            
            // Highlight current exercise
            String exerciseText = "• " + ex.getName() + "  (" + ex.getDurationSeconds() + "s)\n" + ex.getDescription();
            item.setText(exerciseText);
            
            if (i == currentExerciseIndex) {
                item.setTextColor(ContextCompat.getColor(this, R.color.accent_green));
                item.setTypeface(null, android.graphics.Typeface.BOLD);
            } else {
                item.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            }
            
            item.setTextSize(14f);
            item.setLineSpacing(4f, 1f);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = (int) (getResources().getDisplayMetrics().density * 8);
            item.setLayoutParams(lp);
            container.addView(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Clean up step detector service
        try {
            if (isStepServiceBound) {
                stopStepCounting();
                unbindService(stepServiceConnection);
                isStepServiceBound = false;
                Log.d("WorkoutSession", "StepDetectorService unbound successfully");
            }
        } catch (Exception e) {
            Log.e("WorkoutSession", "Error unbinding StepDetectorService: " + e.getMessage(), e);
        }
        
        // Clean up meditation resources
        if (meditationHandler != null) {
            meditationHandler.removeCallbacksAndMessages(null);
        }
        if (breathingAnimator != null) {
            breathingAnimator.cancel();
        }
        if (exerciseTimer != null) {
            exerciseTimer.cancel();
        }
        
        // Clean up audio manager
        if (audioManager != null) {
            audioManager.release();
        }
    }
}
