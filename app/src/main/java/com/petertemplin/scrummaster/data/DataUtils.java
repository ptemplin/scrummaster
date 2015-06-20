package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataUtils {

    // Database fields
    private SQLiteDatabase database;
    private DataOpenHelper dbHelper;

    public static DataUtils instance;

    protected DataUtils(Context context) {
        dbHelper = new DataOpenHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            dbHelper.close();
        }
    }

    public static DataUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DataUtils(context);
        }
        return instance;
    }

    /*
     * Closes the open database open helper
     */
    public void close() {
        dbHelper.close();
    }

    /*
     * adds a new task to the task table
     */
    public void addTask() {
        // for debugging
        String ADD_TASK = "insert into " + DataOpenHelper.TABLE_TASK + " values (null, 'test');";
        database.execSQL(ADD_TASK);
    }

}