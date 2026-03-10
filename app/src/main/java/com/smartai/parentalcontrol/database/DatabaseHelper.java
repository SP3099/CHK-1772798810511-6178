package com.smartai.parentalcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "ParentalControl.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE + " TEXT,"
                + COLUMN_CREATED_AT + " INTEGER"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG, "Database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Register new user
    public boolean registerUser(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_ROLE, role);
            values.put(COLUMN_CREATED_AT, System.currentTimeMillis());

            long result = db.insert(TABLE_USERS, null, values);
            
            if (result == -1) {
                Log.e(TAG, "Failed to register user");
                return false;
            } else {
                Log.d(TAG, "User registered successfully with ID: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error registering user", e);
            return false;
        } finally {
            db.close();
        }
    }

    // Check if email already exists
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        
        try {
            cursor = db.query(TABLE_USERS,
                    new String[]{COLUMN_EMAIL},
                    COLUMN_EMAIL + "=?",
                    new String[]{email},
                    null, null, null);
            
            boolean exists = cursor.getCount() > 0;
            Log.d(TAG, "Email " + email + " exists: " + exists);
            return exists;
        } catch (Exception e) {
            Log.e(TAG, "Error checking email", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // Login user
    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        
        try {
            cursor = db.query(TABLE_USERS,
                    new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_ROLE},
                    COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                    new String[]{email, password},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
                
                Log.d(TAG, "Login successful for: " + email);
                return new User(id, name, userEmail, role);
            } else {
                Log.d(TAG, "Login failed for: " + email);
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error during login", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // User class
    public static class User {
        private int id;
        private String name;
        private String email;
        private String role;

        public User(int id, String name, String email, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }
    }
}
