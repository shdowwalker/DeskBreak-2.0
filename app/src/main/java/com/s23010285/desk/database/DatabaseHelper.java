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

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "DeskBreakDB";
    private static final int DATABASE_VERSION = 1;
    
    // User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_CREATED_AT = "created_at";
    
    // Workout sessions table
    private static final String TABLE_WORKOUT_SESSIONS = "workout_sessions";
    private static final String COLUMN_SESSION_ID = "id";
    private static final String COLUMN_SESSION_USER_ID = "user_id";
    private static final String COLUMN_SESSION_TYPE = "type";
    private static final String COLUMN_SESSION_DURATION = "duration";
    private static final String COLUMN_SESSION_STEPS = "steps";
    private static final String COLUMN_SESSION_DISTANCE = "distance";
    private static final String COLUMN_SESSION_START_TIME = "start_time";
    private static final String COLUMN_SESSION_END_TIME = "end_time";
    
    // Activity records table
    private static final String TABLE_ACTIVITY_RECORDS = "activity_records";
    private static final String COLUMN_RECORD_ID = "id";
    private static final String COLUMN_RECORD_USER_ID = "user_id";
    private static final String COLUMN_RECORD_DATE = "date";
    private static final String COLUMN_RECORD_STEPS = "steps";
    private static final String COLUMN_RECORD_ACTIVE_MINUTES = "active_minutes";
    private static final String COLUMN_RECORD_DISTANCE = "distance";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT NOT NULL,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL,"
                + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
                + COLUMN_USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        
        // Create workout sessions table
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
        
        // Create activity records table
        String CREATE_ACTIVITY_RECORDS_TABLE = "CREATE TABLE " + TABLE_ACTIVITY_RECORDS + "("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RECORD_USER_ID + " INTEGER,"
                + COLUMN_RECORD_DATE + " DATE NOT NULL,"
                + COLUMN_RECORD_STEPS + " INTEGER DEFAULT 0,"
                + COLUMN_RECORD_ACTIVE_MINUTES + " INTEGER DEFAULT 0,"
                + COLUMN_RECORD_DISTANCE + " REAL DEFAULT 0.0,"
                + "FOREIGN KEY(" + COLUMN_RECORD_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_WORKOUT_SESSIONS_TABLE);
        db.execSQL(CREATE_ACTIVITY_RECORDS_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_RECORDS);
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
}
