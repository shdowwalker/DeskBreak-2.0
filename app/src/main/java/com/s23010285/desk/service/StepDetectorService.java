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
 */
public class StepDetectorService extends Service implements SensorEventListener {
    
    private static final String TAG = "StepDetectorService";
    private static final float STEP_THRESHOLD = 2.0f; // Lowered threshold for better sensitivity
    private static final int STEP_DELAY_NS = 200000000; // 200ms between steps (faster detection)
    
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor stepCounterSensor;
    private boolean isDetecting = false;
    private boolean useStepCounter = false;
    private int initialStepCounterValue = -1;
    
    // Step detection variables
    private float lastMagnitude = 0;
    private boolean isStepDetected = false;
    private long lastStepTimeNs = 0;
    private int stepCount = 0;
    
    // Smoothing filter variables
    private static final int FILTER_SIZE = 10;
    private float[] magnitudeHistory = new float[FILTER_SIZE];
    private int historyIndex = 0;
    
    // Callback interface for step events
    public interface StepDetectionListener {
        void onStepDetected(int totalSteps);
        void onStepCountReset();
    }
    
    private StepDetectionListener stepListener;
    
    // Binder for local service binding
    public class LocalBinder extends Binder {
        public StepDetectorService getService() {
            return StepDetectorService.this;
        }
    }
    
    private final IBinder binder = new LocalBinder();
    
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                // Try to use built-in step counter first
                stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                
                if (stepCounterSensor != null) {
                    Log.d(TAG, "Using built-in step counter sensor");
                    useStepCounter = true;
                } else if (accelerometer != null) {
                    Log.d(TAG, "Using accelerometer for step detection");
                    useStepCounter = false;
                } else {
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
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    /**
     * Start step detection
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
