package com.s23010285.desk.ui.workout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
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
import com.s23010285.desk.R;
import com.s23010285.desk.model.WorkoutSchedule;
import java.util.List;

/**
 * Workout session activity for active workout tracking
 * Displays live steps, distance, workout progress, and Google Maps
 */
public class WorkoutSessionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView workoutSessionTitle, tvSteps, tvDistance, tvTime;
    private Button btnPause, btnStop, btnFinish;

    private String workoutId;
    private String workoutName;
    private int workoutDuration;
    private boolean isPaused = false;

    private WorkoutSchedule selectedSchedule;

    // Google Maps and Location
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private LatLng currentLocation;
    private int stepCount = 0;
    private double totalDistance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity_workout_session);

        // Get workout data from intent
        workoutId = getIntent().getStringExtra("workout_id");
        workoutName = getIntent().getStringExtra("workout_name");
        workoutDuration = getIntent().getIntExtra("workout_duration", 5);

        // Resolve full schedule by id
        resolveSelectedSchedule();

        setupViews();
        setupUI();
        setupClickListeners();
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
        }
    }

    private void setupViews() {
        workoutSessionTitle = findViewById(R.id.workoutSessionTitle);
        tvSteps = findViewById(R.id.tvSteps);
        tvDistance = findViewById(R.id.tvDistance);
        tvTime = findViewById(R.id.tvTime);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        btnFinish = findViewById(R.id.btnFinish);
    }

    private void setupUI() {
        // Title
        if (workoutName != null && !workoutName.isEmpty()) {
            workoutSessionTitle.setText(workoutName);
        }

        // Initialize stats
        tvSteps.setText("0");
        tvDistance.setText("0.0m");
        tvTime.setText("00:00");

        // Render exercises if available
        renderExercises();

        // Show Google Map only for Lite Cardio (Cardio category)
        View cardMap = findViewById(R.id.cardMap);
        boolean showMap = selectedSchedule != null &&
                ("Cardio".equalsIgnoreCase(selectedSchedule.getCategory()) ||
                 "lite_cardio".equalsIgnoreCase(selectedSchedule.getId()));
        cardMap.setVisibility(showMap ? View.VISIBLE : View.GONE);

        if (showMap) {
            setupGoogleMaps();
        }
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
        for (WorkoutSchedule.WorkoutExercise ex : selectedSchedule.getExercises()) {
            TextView item = new TextView(this);
            item.setText("• " + ex.getName() + "  (" + ex.getDurationSeconds() + "s)\n" + ex.getDescription());
            item.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            item.setTextSize(14f);
            item.setLineSpacing(4f, 1f);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = (int) (getResources().getDisplayMetrics().density * 8);
            item.setLayoutParams(lp);
            container.addView(item);
        }
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
        // TODO: Pause workout tracking
    }

    private void resumeWorkout() {
        isPaused = false;
        btnPause.setText("Pause");
        // TODO: Resume workout tracking
    }

    private void startWorkoutSession() {
        // TODO: Initialize workout session, sensors, timers
        simulateWorkoutActivity();
    }

    private void simulateWorkoutActivity() {
        new Thread(() -> {
            while (!isPaused) {
                try {
                    Thread.sleep(2000); // Update every 2 seconds
                    runOnUiThread(() -> {
                        stepCount += 5; // Simulate 5 steps every 2 seconds
                        tvSteps.setText(String.valueOf(stepCount));
                        totalDistance = stepCount * 0.7; // meters
                        tvDistance.setText(String.format("%.1fm", totalDistance));
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Clean up workout session resources
    }
}
