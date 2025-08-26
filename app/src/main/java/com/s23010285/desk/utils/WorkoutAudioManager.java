package com.s23010285.desk.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import java.util.HashMap;
import java.util.Map;

/**
 * Advanced Audio Manager for DeskBreak App
 * Handles background music, workout sounds, and audio feedback
 * This class is like a personal DJ that provides music and sound effects during workouts
 */
public class WorkoutAudioManager {
    
    // This tag is used for logging messages to help with debugging
    private static final String TAG = "AudioManager";
    
    // Audio categories - these define different types of audio content
    // Each category has its own volume and playback settings
    public enum AudioCategory {
        WORKOUT_MUSIC,       // Background music for workouts
        MEDITATION_AMBIENT,  // Soothing sounds for meditation
        MOTIVATION_SPEECH,   // Inspirational speech audio
        WORKOUT_SOUNDS,      // Sound effects for exercises
        UI_FEEDBACK          // Audio feedback for app interactions
    }
    
    // Background music tracks - these store URLs to different music files
    // Each workout type can have its own background music
    private static final Map<String, String> backgroundMusic = new HashMap<>();
    // Workout sound effects - these store URLs to different sound effect files
    // Each exercise can have its own sound effect
    private static final Map<String, String> workoutSounds = new HashMap<>();
    
    // Static initialization block - this runs once when the class is first loaded
    // It sets up all the background music and sound effect URLs
    static {
        // Background music for different workout types
        // Each workout category gets its own music track
        backgroundMusic.put("cardio", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        backgroundMusic.put("strength", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        backgroundMusic.put("meditation", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        backgroundMusic.put("stretching", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        
        // Workout sound effects
        // Each exercise event gets its own sound effect
        workoutSounds.put("exercise_start", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        workoutSounds.put("exercise_complete", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        workoutSounds.put("workout_complete", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
        workoutSounds.put("timer_beep", "https://www.soundjay.com/misc/sounds/bell-ringing-05.wav");
    }
    
    // These variables help manage audio playback
    // context helps us access the app's resources and audio system
    private Context context;
    // backgroundPlayer plays background music during workouts
    private ExoPlayer backgroundPlayer;
    // soundPool plays short sound effects
    private SoundPool soundPool;
    // soundIds stores the IDs of loaded sound effects
    private Map<String, Integer> soundIds;
    // isMuted tracks whether audio is currently muted
    private boolean isMuted = false;
    // volume controls how loud the audio is (0.0 = silent, 1.0 = full volume)
    private float volume = 0.7f;
    
    // Audio state variables - these track what's currently playing
    // currentCategory tells us what type of audio is currently active
    private AudioCategory currentCategory = AudioCategory.WORKOUT_MUSIC;
    // isBackgroundMusicPlaying tracks whether background music is currently playing
    private boolean isBackgroundMusicPlaying = false;
    
    /**
     * Constructor for the WorkoutAudioManager
     * This method sets up the audio system and prepares it for use
     * @param context The app's context, which helps us access system resources
     */
    public WorkoutAudioManager(Context context) {
        this.context = context;
        // Set up all the audio components we need
        initializeAudio();
    }
    
    /**
     * Initialize audio components
     * This method sets up the music player and sound effect system
     */
    private void initializeAudio() {
        // Initialize ExoPlayer for background music
        // ExoPlayer is a modern music player that can handle various audio formats
        backgroundPlayer = new ExoPlayer.Builder(context).build();
        // Set up a listener to handle when music finishes playing
        backgroundPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    // Loop background music - when it ends, start it again
                    backgroundPlayer.seekTo(0);
                    backgroundPlayer.play();
                }
            }
        });
        
        // Initialize SoundPool for sound effects
        // SoundPool is good for playing short sound effects quickly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // For newer Android versions, use modern audio attributes
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)           // Treat audio as game audio
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)  // Treat as sound effects
                .build();
            
            // Create SoundPool with modern settings
            soundPool = new SoundPool.Builder()
                .setMaxStreams(4)                              // Allow up to 4 sounds at once
                .setAudioAttributes(audioAttributes)           // Use the audio attributes we defined
                .build();
        } else {
            // For older Android versions, use legacy SoundPool
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }
        
        // Load sound effects
        loadSoundEffects();
    }
    
    /**
     * Load sound effects into SoundPool
     */
    private void loadSoundEffects() {
        soundIds = new HashMap<>();
        
        // Load workout sound effects
        for (Map.Entry<String, String> entry : workoutSounds.entrySet()) {
            // For now, we'll use placeholder sounds
            // In production, these would be local audio files
            soundIds.put(entry.getKey(), 1); // Placeholder
        }
    }
    
    /**
     * Play background music for workout category
     */
    public void playBackgroundMusic(String workoutCategory) {
        if (isMuted || backgroundPlayer == null) return;
        
        try {
            String musicUrl = backgroundMusic.get(workoutCategory.toLowerCase());
            if (musicUrl != null) {
                MediaItem mediaItem = MediaItem.fromUri(musicUrl);
                backgroundPlayer.setMediaItem(mediaItem);
                backgroundPlayer.prepare();
                backgroundPlayer.setVolume(volume);
                backgroundPlayer.play();
                isBackgroundMusicPlaying = true;
                
                Log.d(TAG, "Playing background music for: " + workoutCategory);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error playing background music: " + e.getMessage());
        }
    }
    
    /**
     * Stop background music
     */
    public void stopBackgroundMusic() {
        if (backgroundPlayer != null && isBackgroundMusicPlaying) {
            backgroundPlayer.stop();
            isBackgroundMusicPlaying = false;
            Log.d(TAG, "Background music stopped");
        }
    }
    
    /**
     * Pause background music
     */
    public void pauseBackgroundMusic() {
        if (backgroundPlayer != null && isBackgroundMusicPlaying) {
            backgroundPlayer.pause();
            Log.d(TAG, "Background music paused");
        }
    }
    
    /**
     * Resume background music
     */
    public void resumeBackgroundMusic() {
        if (backgroundPlayer != null && isBackgroundMusicPlaying) {
            backgroundPlayer.play();
            Log.d(TAG, "Background music resumed");
        }
    }
    
    /**
     * Play workout sound effect
     */
    public void playWorkoutSound(String soundName) {
        if (isMuted || soundPool == null) return;
        
        Integer soundId = soundIds.get(soundName);
        if (soundId != null) {
            soundPool.play(soundId, volume, volume, 1, 0, 1.0f);
            Log.d(TAG, "Playing workout sound: " + soundName);
        }
    }
    
    /**
     * Play exercise start sound
     */
    public void playExerciseStartSound() {
        playWorkoutSound("exercise_start");
    }
    
    /**
     * Play exercise complete sound
     */
    public void playExerciseCompleteSound() {
        playWorkoutSound("exercise_complete");
    }
    
    /**
     * Play workout complete sound
     */
    public void playWorkoutCompleteSound() {
        playWorkoutSound("workout_complete");
    }
    
    /**
     * Play timer beep sound
     */
    public void playTimerBeepSound() {
        playWorkoutSound("timer_beep");
    }
    
    /**
     * Set volume level (0.0f to 1.0f)
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        
        if (backgroundPlayer != null) {
            backgroundPlayer.setVolume(this.volume);
        }
        
        Log.d(TAG, "Volume set to: " + this.volume);
    }
    
    /**
     * Mute/unmute audio
     */
    public void setMuted(boolean muted) {
        this.isMuted = muted;
        
        if (muted) {
            if (backgroundPlayer != null) {
                backgroundPlayer.setVolume(0.0f);
            }
            Log.d(TAG, "Audio muted");
        } else {
            if (backgroundPlayer != null) {
                backgroundPlayer.setVolume(volume);
            }
            Log.d(TAG, "Audio unmuted");
        }
    }
    
    /**
     * Check if audio is muted
     */
    public boolean isMuted() {
        return isMuted;
    }
    
    /**
     * Get current volume level
     */
    public float getVolume() {
        return volume;
    }
    
    /**
     * Check if background music is playing
     */
    public boolean isBackgroundMusicPlaying() {
        return isBackgroundMusicPlaying;
    }
    
    /**
     * Get ExoPlayer for UI integration
     */
    public ExoPlayer getBackgroundPlayer() {
        return backgroundPlayer;
    }
    
    /**
     * Clean up audio resources
     */
    public void release() {
        if (backgroundPlayer != null) {
            backgroundPlayer.release();
            backgroundPlayer = null;
        }
        
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        
        Log.d(TAG, "AudioManager released");
    }
}
