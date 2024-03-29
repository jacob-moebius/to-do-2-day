package edu.miracosta.cs134.jmoebius.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Task 1: Make constants for all database values
    // database name, version, table name, field names, primary key
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String TABLE_NAME = "Tasks";
    public static final int VERSION = 1;

    public static final String KEY_FIELD_ID = "_id";
    public static final String FIELD_DESCRIPTION = "_description";
    public static final String FIELD_IS_DONE = "is_done";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";

        Log.i(DATABASE_NAME, createSQL);
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 1) Drop the old table, recreate the new
        if (newVersion > oldVersion) {
            String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
            Log.i(DATABASE_NAME, dropSQL);
            db.execSQL(dropSQL);
            onCreate(db);
        }
    }

    /**
     * Adds a new task to the database.
     *
     * @param task      The new task to be added.
     * @return id       The TABLE id.
     */
    public long addTask(Task task)
    {
        // Decide whether we are reading from or writing to the database.
        // For adding tasks, we are writing to the database.
        SQLiteDatabase db = getWritableDatabase();

        // When we add to the database, use a data structure called ContentValues (key, value) pairs
        ContentValues values = new ContentValues();

        // Set up key/value pairs
        values.put(FIELD_DESCRIPTION, task.getDescription());
        values.put(FIELD_IS_DONE, task.isDone() ? 1 : 0);

        // insert() returns a long (the id of the TABLE)
        long id = db.insert(TABLE_NAME, null, values);

        // After we're done, close the connection to the database
        db.close();
        return id;
    }

    /**
    * Goes into database and returns list of tasks.
    * @return allTasks      List of Tasks
    */
    public List<Task> getAllTasks() {

        List<Task> allTasks = new ArrayList<>();
        // Get the tasks from the database.
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to retrieve all records and store them in a data structure called a
        // Cursor.
        Cursor cursor =
                db.query(
                    TABLE_NAME,
                    new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                    null,
                    null,
                    null,
                    null,
                    null);

        // Loop through each of the cursor results, one at a time
        // 1) Instantiate a new Task object.
        // 2) Add the new Task to the List.
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String description = cursor.getString(1);
                boolean isDone = cursor.getInt(2) == 1;
                allTasks.add(new Task(id, description, isDone));
            } while (cursor.moveToNext());
        }

        // Close the cursor.
        cursor.close();

        // Close the connection to the database.
        db.close();
        return allTasks;
    }

    /**
     * Completely wipes the records without removing the columns.
     */
    public void clearAllTasks() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
