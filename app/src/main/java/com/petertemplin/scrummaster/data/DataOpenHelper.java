package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scrum_master.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + DataUtils.TABLE_TASK + "("
            + DataUtils.COLUMN_ID + " integer primary key, "
            + DataUtils.COLUMN_NAME + " text not null, "
            + DataUtils.COLUMN_DESCRIPTION + " text, "
            + DataUtils.COLUMN_PRIORITY + " integer "
            + DataUtils.COLUMN_ESTIMATED_TIME + " text, "
            + DataUtils.COLUMN_POINTS + " integer, "
            + DataUtils.COLUMN_PROGRESS + " text, "
            + DataUtils.COLUMN_START_DATE + " integer, "
            + DataUtils.COLUMN_END_DATE + " integer);";

    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_ESTIMATED_TIME = "estimatedTime";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_START_DATE = "startDate";
    public static final String COLUMN_END_DATE = "endDate";
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
        db.execSQL("DROP TABLE IF EXISTS " + DataUtils.TABLE_TASK);
        onCreate(db);
    }

}