package com.s23010285.desk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.s23010285.desk.model.User;
import com.s23010285.desk.model.WorkoutSession;
import com.s23010285.desk.model.ActivityRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps manage the app's database
 * It creates tables, stores data, and retrieves information about users, workouts, and activities
 * The database is like a digital filing cabinet that keeps all the app's information organized
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    // These constants define the database name and version
    // DATABASE_NAME is what we call our database file
    private static final String DATABASE_NAME = "DeskBreakDB";
    // DATABASE_VERSION helps us know when to update the database structure
    private static final int DATABASE_VERSION = 1;
    
    // User table - this table stores information about all the app's users
    // TABLE_USERS is the name of the table that holds user data
    private static final String TABLE_USERS = "users";
    // These constants define the names of each column in the users table
    // COLUMN_USER_ID is a unique number that identifies each user
    private static final String COLUMN_USER_ID = "id";
    // COLUMN_USER_NAME stores the user's full name
    private static final String COLUMN_USER_NAME = "name";
    // COLUMN_USER_EMAIL stores the user's email address
    private static final String COLUMN_USER_EMAIL = "email";
    // COLUMN_USER_PASSWORD stores the user's password (encrypted)
    private static final String COLUMN_USER_PASSWORD = "password";
    // COLUMN_USER_CREATED_AT stores when the user's account was created
    private static final String COLUMN_USER_CREATED_AT = "created_at";
    
    // Workout sessions table - this table stores information about each workout the user does
    // TABLE_WORKOUT_SESSIONS is the name of the table that holds workout data
    private static final String TABLE_WORKOUT_SESSIONS = "workout_sessions";
    // These constants define the names of each column in the workout sessions table
    // COLUMN_SESSION_ID is a unique number that identifies each workout session
    private static final String COLUMN_SESSION_ID = "id";
    // COLUMN_SESSION_USER_ID links the workout to the user who did it
    private static final String COLUMN_SESSION_USER_ID = "user_id";
    // COLUMN_SESSION_TYPE stores what kind of workout it was (cardio, strength, etc.)
    private static final String COLUMN_SESSION_TYPE = "type";
    // COLUMN_SESSION_DURATION stores how long the workout lasted in minutes
    private static final String COLUMN_SESSION_DURATION = "duration";
    // COLUMN_SESSION_STEPS stores how many steps the user took during the workout
    private static final String COLUMN_SESSION_STEPS = "steps";
    // COLUMN_SESSION_DISTANCE stores how far the user moved during the workout
    private static final String COLUMN_SESSION_DISTANCE = "distance";
    // COLUMN_SESSION_START_TIME stores when the workout began
    private static final String COLUMN_SESSION_START_TIME = "start_time";
    // COLUMN_SESSION_END_TIME stores when the workout ended
    private static final String COLUMN_SESSION_END_TIME = "end_time";
    
    // Activity records table - this table stores daily summaries of user activity
    // TABLE_ACTIVITY_RECORDS is the name of the table that holds daily activity data
    private static final String TABLE_ACTIVITY_RECORDS = "activity_records";
    // These constants define the names of each column in the activity records table
    // COLUMN_RECORD_ID is a unique number that identifies each daily record
    private static final String COLUMN_RECORD_ID = "id";
    // COLUMN_RECORD_USER_ID links the daily record to the user it belongs to
    private static final String COLUMN_RECORD_USER_ID = "user_id";
    // COLUMN_RECORD_DATE stores which day this record is for (YYYY-MM-DD format)
    private static final String COLUMN_RECORD_DATE = "date";
    // COLUMN_RECORD_STEPS stores the total steps for that day
    private static final String COLUMN_RECORD_STEPS = "steps";
    // COLUMN_RECORD_ACTIVE_MINUTES stores how many minutes the user was active that day
    private static final String COLUMN_RECORD_ACTIVE_MINUTES = "active_minutes";
    // COLUMN_RECORD_DISTANCE stores how far the user moved that day
    private static final String COLUMN_RECORD_DISTANCE = "distance";
    
    /**
     * Constructor for the DatabaseHelper
     * This method is called when we want to create a new database or connect to an existing one
     * @param context The app's context, which helps us access system resources
     */
    public DatabaseHelper(Context context) {
        // Call the parent class constructor to set up the database
        // This creates a new database file or opens an existing one
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /**
     * This method is called when the database is first created
     * It creates all the tables we need to store our app's data
     * @param db The database object that we can use to create tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table - this stores information about all app users
        // This SQL command creates a table with columns for user ID, name, email, password, and creation date
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT NOT NULL,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL,"
                + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        
        // Create workout sessions table - this stores information about each workout
        // This SQL command creates a table with columns for session details, timing, and performance metrics
        String CREATE_WORKOUT_SESSIONS_TABLE = "CREATE TABLE " + TABLE_WORKOUT_SESSIONS + "("
                + COLUMN_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SESSION_USER_ID + " INTEGER,"
                + COLUMN_SESSION_TYPE + " TEXT NOT NULL,"
                + COLUMN_SESSION_DURATION + " INTEGER,"
                + COLUMN_SESSION_STEPS + " INTEGER,"
                + COLUMN_SESSION_DISTANCE + " REAL,"
                + COLUMN_SESSION_START_TIME + " DATETIME,"
                + COLUMN_SESSION_END_TIME + " DATETIME,"
                + "FOREIGN KEY(" + COLUMN_SESSION_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        
        // Create activity records table - this stores daily summaries of user activity
        // This SQL command creates a table with columns for daily step counts, workout counts, and other metrics
        String CREATE_ACTIVITY_RECORDS_TABLE = "CREATE TABLE " + TABLE_ACTIVITY_RECORDS + "("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RECORD_USER_ID + " INTEGER,"
                + COLUMN_RECORD_DATE + " DATE NOT NULL,"
                + COLUMN_RECORD_STEPS + " INTEGER DEFAULT 0,"
                + COLUMN_RECORD_ACTIVE_MINUTES + " INTEGER DEFAULT 0,"
                + COLUMN_RECORD_DISTANCE + " REAL DEFAULT 0.0,"
                + "FOREIGN KEY(" + COLUMN_RECORD_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        
        // Execute the SQL commands to create all our tables
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_WORKOUT_SESSIONS_TABLE);
        db.execSQL(CREATE_ACTIVITY_RECORDS_TABLE);
    }
    
    /**
     * This method is called when the database version needs to be updated
     * It handles upgrading the database structure when we make changes to the app
     * @param db The database object
     * @param oldVersion The previous version number
     * @param newVersion The new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, we'll just delete the old tables and recreate them
        // In a real app, you might want to migrate data instead
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_RECORDS);
        // Create the new tables with the updated structure
        onCreate(db);
    }
    
    // User operations
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }
    
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        }
        
        cursor.close();
        db.close();
        return user;
    }
    
    public User getUserById(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        }
        
        cursor.close();
        db.close();
        return user;
    }

    public boolean checkUserCredentials(String email, String password) {
        User user = getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    
    // Workout session operations
    public long addWorkoutSession(WorkoutSession session) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SESSION_USER_ID, session.getUserId());
        values.put(COLUMN_SESSION_TYPE, session.getType());
        values.put(COLUMN_SESSION_DURATION, session.getDuration());
        values.put(COLUMN_SESSION_STEPS, session.getSteps());
        values.put(COLUMN_SESSION_DISTANCE, session.getDistance());
        values.put(COLUMN_SESSION_START_TIME, session.getStartTime());
        values.put(COLUMN_SESSION_END_TIME, session.getEndTime());
        
        long id = db.insert(TABLE_WORKOUT_SESSIONS, null, values);
        db.close();
        return id;
    }
    
    public List<WorkoutSession> getWorkoutSessionsByUserId(long userId) {
        List<WorkoutSession> sessions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String[] columns = {COLUMN_SESSION_ID, COLUMN_SESSION_USER_ID, COLUMN_SESSION_TYPE, 
                           COLUMN_SESSION_DURATION, COLUMN_SESSION_STEPS, COLUMN_SESSION_DISTANCE,
                           COLUMN_SESSION_START_TIME, COLUMN_SESSION_END_TIME};
        String selection = COLUMN_SESSION_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        String orderBy = COLUMN_SESSION_START_TIME + " DESC";
        
        Cursor cursor = db.query(TABLE_WORKOUT_SESSIONS, columns, selection, selectionArgs, null, null, orderBy);
        
        while (cursor.moveToNext()) {
            WorkoutSession session = new WorkoutSession();
            session.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_SESSION_ID)));
            session.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_SESSION_USER_ID)));
            session.setType(cursor.getString(cursor.getColumnIndex(COLUMN_SESSION_TYPE)));
            session.setDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_SESSION_DURATION)));
            session.setSteps(cursor.getInt(cursor.getColumnIndex(COLUMN_SESSION_STEPS)));
            session.setDistance(cursor.getDouble(cursor.getColumnIndex(COLUMN_SESSION_DISTANCE)));
            session.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_SESSION_START_TIME)));
            session.setEndTime(cursor.getLong(cursor.getColumnIndex(COLUMN_SESSION_END_TIME)));
            sessions.add(session);
        }
        
        cursor.close();
        db.close();
        return sessions;
    }
    
    // Activity record operations
    public long addActivityRecord(ActivityRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECORD_USER_ID, record.getUserId());
        values.put(COLUMN_RECORD_DATE, record.getDate());
        values.put(COLUMN_RECORD_STEPS, record.getSteps());
        values.put(COLUMN_RECORD_ACTIVE_MINUTES, record.getActiveMinutes());
        values.put(COLUMN_RECORD_DISTANCE, record.getDistance());
        
        long id = db.insert(TABLE_ACTIVITY_RECORDS, null, values);
        db.close();
        return id;
    }
    
    public ActivityRecord getActivityRecordByDate(long userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ActivityRecord record = null;
        
        String[] columns = {COLUMN_RECORD_ID, COLUMN_RECORD_USER_ID, COLUMN_RECORD_DATE,
                           COLUMN_RECORD_STEPS, COLUMN_RECORD_ACTIVE_MINUTES, COLUMN_RECORD_DISTANCE};
        String selection = COLUMN_RECORD_USER_ID + " = ? AND " + COLUMN_RECORD_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(userId), date};
        
        Cursor cursor = db.query(TABLE_ACTIVITY_RECORDS, columns, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            record = new ActivityRecord();
            record.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_RECORD_ID)));
            record.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_RECORD_USER_ID)));
            record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_DATE)));
            record.setSteps(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_STEPS)));
            record.setActiveMinutes(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_ACTIVE_MINUTES)));
            record.setDistance(cursor.getDouble(cursor.getColumnIndex(COLUMN_RECORD_DISTANCE)));
        }
        
        cursor.close();
        db.close();
        return record;
    }
    
    public void updateActivityRecord(ActivityRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECORD_STEPS, record.getSteps());
        values.put(COLUMN_RECORD_ACTIVE_MINUTES, record.getActiveMinutes());
        values.put(COLUMN_RECORD_DISTANCE, record.getDistance());
        
        String whereClause = COLUMN_RECORD_ID + " = ?";
        String[] whereArgs = {String.valueOf(record.getId())};
        
        db.update(TABLE_ACTIVITY_RECORDS, values, whereClause, whereArgs);
        db.close();
    }
    
    /**
     * Check if a user exists with the given email
     */
    public boolean doesUserExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        
        cursor.close();
        db.close();
        return exists;
    }
    
    /**
     * Update user's password
     */
    public boolean updateUserPassword(long userId, String newPassword) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_PASSWORD, newPassword);
            
            String whereClause = COLUMN_USER_ID + " = ?";
            String[] whereArgs = {String.valueOf(userId)};
            
            int rowsAffected = db.update(TABLE_USERS, values, whereClause, whereArgs);
            db.close();
            
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
