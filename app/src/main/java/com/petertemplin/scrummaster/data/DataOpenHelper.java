package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DataOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scrum_master.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String TASK_TABLE_CREATE = "create table if not exists "
            + DataUtils.TABLE_TASK + "("
            + DataUtils.COLUMN_ID + " integer primary key, "
            + DataUtils.COLUMN_NAME + " text not null, "
            + DataUtils.COLUMN_DESCRIPTION + " text, "
            + DataUtils.COLUMN_PRIORITY + " integer, "
            + DataUtils.COLUMN_ESTIMATED_TIME + " text, "
            + DataUtils.COLUMN_POINTS + " integer, "
            + DataUtils.COLUMN_PROGRESS + " text, "
            + DataUtils.COLUMN_START_DATE + " text, "
            + DataUtils.COLUMN_END_DATE + " text, "
            + DataUtils.COLUMN_SPRINT + " integer, "
            + DataUtils.COLUMN_PROJECT + " integer, "
            + DataUtils.COLUMN_BACKLOG + " integer);";

    private static final String SPRINT_TABLE_CREATE = "create table if not exists "
            + DataUtils.TABLE_SPRINT + "("
            + DataUtils.COLUMN_SPRINT_ID + " integer primary key, "
            + DataUtils.COLUMN_SPRINT_NAME + " text not null, "
            + DataUtils.COLUMN_SPRINT_DESCRIPTION + " text, "
            + DataUtils.COLUMN_SPRINT_START_DATE + " text, "
            + DataUtils.COLUMN_SPRINT_END_DATE + " text, "
            + DataUtils.COLUMN_SPRINT_DURATION + " text, "
            + DataUtils.COLUMN_SPRINT_STARTED + " text, "
            + DataUtils.COLUMN_SPRINT_PROJECT + " integer);";

    private static final String BACKLOG_TABLE_CREATE = "create table if not exists "
            + DataUtils.TABLE_BACKLOG + "("
            + DataUtils.COLUMN_BACKLOG_ID + " integer primary key, "
            + DataUtils.COLUMN_BACKLOG_NAME + " text not null, "
            + DataUtils.COLUMN_BACKLOG_DESCRIPTION + " text, "
            + DataUtils.COLUMN_BACKLOG_PROJECT + " integer);";

    private static final String PROJECT_TABLE_CREATE = "create table if not exists "
            + DataUtils.TABLE_PROJECT + "("
            + DataUtils.COLUMN_PROJECT_ID + " integer primary key, "
            + DataUtils.COLUMN_PROJECT_NAME + " text not null, "
            + DataUtils.COLUMN_PROJECT_DESCRIPTION + " text, "
            + DataUtils.COLUMN_PROJECT_START_DATE + " text);";

    private static final String[] createStmts = {TASK_TABLE_CREATE,
                                                 SPRINT_TABLE_CREATE,
                                                 BACKLOG_TABLE_CREATE,
                                                 PROJECT_TABLE_CREATE};

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
        for (int i = 0; i < createStmts.length; i++) {
            database.execSQL(createStmts[i]);
        }
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