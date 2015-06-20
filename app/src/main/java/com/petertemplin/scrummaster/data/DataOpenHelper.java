package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataOpenHelper extends SQLiteOpenHelper {

    // Public variables
    public static final String TABLE_TASK = "Task";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    // Private variables
    private static final String DATABASE_NAME = "scrum_master.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_TASK + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null);";

    /* Constructor */
    public DataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /**
     * Executed when the database is created. Creates the header
     * table "categories" in the database, if not already there
     */
    public void onCreate(SQLiteDatabase database) {
        // Execute the create database statement
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    /**
     * Used whenever database is upgraded. Drops all tables
     * and adds them again, but empty
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

}