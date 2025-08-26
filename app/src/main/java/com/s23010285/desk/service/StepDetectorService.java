package com.s23010285.desk.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 * Service for detecting steps using accelerometer sensor
 * Implements step detection algorithm for real-time step counting
 * This service runs in the background to count the user's steps even when the app is not open
 */
public class StepDetectorService extends Service implements SensorEventListener {
    
    // This tag is used for logging messages to help with debugging
    private static final String TAG = "StepDetectorService";
    // This value determines how much movement is needed to count as a step
    // Lower values make it more sensitive to small movements
    private static final float STEP_THRESHOLD = 2.0f; // Lowered threshold for better sensitivity
    // This value sets the minimum time between steps to avoid counting the same movement multiple times
    // 200ms means steps must be at least 0.2 seconds apart
    private static final int STEP_DELAY_NS = 200000000; // 200ms between steps (faster detection)
    
    // These variables help manage the device's sensors
    // sensorManager helps us access the device's built-in sensors
    private SensorManager sensorManager;
    // accelerometer measures movement and acceleration in three directions (x, y, z)
    private Sensor accelerometer;
    // stepCounterSensor is a built-in sensor that some devices have for counting steps
    private Sensor stepCounterSensor;
    // isDetecting tracks whether we're currently monitoring for steps
    private boolean isDetecting = false;
    // useStepCounter tells us whether to use the built-in step counter or calculate steps ourselves
    private boolean useStepCounter = false;
    // initialStepCounterValue stores the starting value of the built-in step counter
    private int initialStepCounterValue = -1;
    
    // Step detection variables - these help us determine when a step has occurred
    // lastMagnitude stores the previous movement intensity to compare with current movement
    private float lastMagnitude = 0;
    // isStepDetected tracks whether we've already counted a step for the current movement
    private boolean isStepDetected = false;
    // lastStepTimeNs stores when the last step was detected (in nanoseconds)
    private long lastStepTimeNs = 0;
    // stepCount keeps track of the total number of steps detected
    private int stepCount = 0;
    
    // Smoothing filter variables - these help reduce false step detections
    // FILTER_SIZE determines how many previous movement measurements we keep
    private static final int FILTER_SIZE = 10;
    // magnitudeHistory stores the last 10 movement measurements
    private float[] magnitudeHistory = new float[FILTER_SIZE];
    // historyIndex keeps track of where in the array to store the next measurement
    private int historyIndex = 0;
    
    // Callback interface for step events
    // This interface lets other parts of the app know when steps are detected
    public interface StepDetectionListener {
        /**
         * This method is called whenever a new step is detected
         * @param totalSteps The total number of steps detected so far
         */
        void onStepDetected(int totalSteps);
        
        /**
         * This method is called when the step count is reset
         */
        void onStepCountReset();
    }
    
    // This variable holds the listener that will receive step detection events
    private StepDetectionListener stepListener;
    
    // Binder for local service binding
    // This class lets other parts of the app connect to this service
    public class LocalBinder extends Binder {
        /**
         * This method returns the service instance so other parts of the app can use it
         * @return The StepDetectorService instance
         */
        public StepDetectorService getService() {
            return StepDetectorService.this;
        }
    }
    
    // This binder object lets other parts of the app connect to this service
    private final IBinder binder = new LocalBinder();
    
    /**
     * This method is called when the service is first created
     * It sets up the sensors and prepares them for step detection
     */
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // Get access to the device's sensor system
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                // Try to use built-in step counter first (more accurate)
                stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                // Also get the accelerometer sensor as a backup
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                
                if (stepCounterSensor != null) {
                    // If the device has a built-in step counter, use it
                    Log.d(TAG, "Using built-in step counter sensor");
                    useStepCounter = true;
                } else if (accelerometer != null) {
                    // If no built-in step counter, use the accelerometer to calculate steps
                    Log.d(TAG, "Using accelerometer for step detection");
                    useStepCounter = false;
                } else {
                    // If no suitable sensors are available, log an error
                    Log.e(TAG, "No suitable sensors available for step detection");
                }
                
                Log.d(TAG, "StepDetectorService created successfully");
            } else {
                Log.e(TAG, "SensorManager service not available");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error creating StepDetectorService: " + e.getMessage(), e);
        }
    }
    
    /**
     * This method is called when another part of the app wants to connect to this service
     * It returns the binder object that lets them communicate with the service
     * @param intent The intent that started the service
     * @return The binder object for communication
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    /**
     * Start step detection
     * This method begins monitoring the sensors for step movements
     */
    public void startStepDetection() {
        try {
            if (sensorManager != null && !isDetecting) {
                boolean registered = false;
                
                if (useStepCounter && stepCounterSensor != null) {
                    // Use built-in step counter
                    registered = sensorManager.registerListener(
                        this, 
                        stepCounterSensor, 
                        SensorManager.SENSOR_DELAY_NORMAL
                    );
                    Log.d(TAG, "Attempting to register step counter sensor");
                } else if (accelerometer != null) {
                    // Use accelerometer-based detection
                    registered = sensorManager.registerListener(
                        this, 
                        accelerometer, 
                        SensorManager.SENSOR_DELAY_GAME
                    );
                    Log.d(TAG, "Attempting to register accelerometer sensor");
                }
                
                if (registered) {
                    isDetecting = true;
                    resetStepCount();
                    Log.d(TAG, "Step detection started successfully using " + 
                              (useStepCounter ? "step counter" : "accelerometer"));
                } else {
                    Log.e(TAG, "Failed to register sensor listener");
                }
            } else {
                if (sensorManager == null) {
                    Log.e(TAG, "Cannot start step detection: SensorManager is null");
                } else if (isDetecting) {
                    Log.w(TAG, "Step detection already running");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error starting step detection: " + e.getMessage(), e);
        }
    }
    
    /**
     * Stop step detection
     */
    public void stopStepDetection() {
        try {
            if (isDetecting && sensorManager != null) {
                sensorManager.unregisterListener(this);
                isDetecting = false;
                Log.d(TAG, "Step detection stopped");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error stopping step detection: " + e.getMessage(), e);
            isDetecting = false; // Reset flag even if unregister fails
        }
    }
    
    /**
     * Reset step count to zero
     */
    public void resetStepCount() {
        stepCount = 0;
        lastMagnitude = 0;
        isStepDetected = false;
        lastStepTimeNs = 0;
        historyIndex = 0;
        initialStepCounterValue = -1; // Reset step counter baseline
        
        // Clear magnitude history
        for (int i = 0; i < FILTER_SIZE; i++) {
            magnitudeHistory[i] = 0;
        }
        
        if (stepListener != null) {
            stepListener.onStepCountReset();
        }
        Log.d(TAG, "Step count reset (including step counter baseline)");
    }
    
    /**
     * Get current step count
     */
    public int getStepCount() {
        return stepCount;
    }
    
    /**
     * Set step detection listener
     */
    public void setStepDetectionListener(StepDetectionListener listener) {
        this.stepListener = listener;
    }
    
    /**
     * Check if step detection is currently active
     */
    public boolean isDetecting() {
        return isDetecting;
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // Use built-in step counter
            int currentStepCount = (int) event.values[0];
            
            if (initialStepCounterValue == -1) {
                // First reading, store as baseline
                initialStepCounterValue = currentStepCount;
                Log.d(TAG, "Step counter baseline set to: " + initialStepCounterValue);
            } else {
                // Calculate steps since we started
                stepCount = currentStepCount - initialStepCounterValue;
                Log.d(TAG, "Step counter - Current: " + currentStepCount + 
                          ", Baseline: " + initialStepCounterValue + ", Steps: " + stepCount);
                
                // Notify listener
                if (stepListener != null) {
                    stepListener.onStepDetected(stepCount);
                }
            }
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Calculate magnitude of acceleration vector
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);
            
            // Log sensor data for debugging (remove gravity baseline ~9.8)
            float adjustedMagnitude = Math.abs(magnitude - 9.8f);
            
            // Apply smoothing filter
            float smoothedMagnitude = applySmoothingFilter(adjustedMagnitude);
            
            // Log every 50th reading to avoid spam
            if (stepCount % 50 == 0 || stepCount < 5) {
                Log.d(TAG, "Accelerometer - Raw: " + magnitude + ", Adjusted: " + adjustedMagnitude + 
                          ", Smoothed: " + smoothedMagnitude + ", Steps: " + stepCount);
            }
            
            // Detect steps using improved peak detection algorithm
            detectStep(smoothedMagnitude, event.timestamp);
        }
    }
    
    /**
     * Apply smoothing filter to reduce noise
     */
    private float applySmoothingFilter(float magnitude) {
        magnitudeHistory[historyIndex] = magnitude;
        historyIndex = (historyIndex + 1) % FILTER_SIZE;
        
        // Calculate moving average
        float sum = 0;
        for (float value : magnitudeHistory) {
            sum += value;
        }
        
        return sum / FILTER_SIZE;
    }
    
    /**
     * Detect steps using improved peak detection algorithm
     */
    private void detectStep(float magnitude, long timestamp) {
        // Ensure minimum time between steps
        long timeSinceLastStep = timestamp - lastStepTimeNs;
        
        // Check for significant change in magnitude (potential step)
        float magnitudeDifference = Math.abs(magnitude - lastMagnitude);
        
        // Improved step detection logic
        if (timeSinceLastStep > STEP_DELAY_NS) {
            // Look for peaks in acceleration that indicate steps
            if (magnitude > STEP_THRESHOLD && magnitude > lastMagnitude) {
                // This looks like a step peak
                stepCount++;
                lastStepTimeNs = timestamp;
                
                Log.d(TAG, "Step detected! Count: " + stepCount + 
                          ", Magnitude: " + magnitude + ", Diff: " + magnitudeDifference);
                
                // Notify listener
                if (stepListener != null) {
                    stepListener.onStepDetected(stepCount);
                }
            }
        }
        
        lastMagnitude = magnitude;
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "Sensor accuracy changed: " + accuracy);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopStepDetection();
        Log.d(TAG, "StepDetectorService destroyed");
    }
}
