package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.petertemplin.scrummaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    // task table
    public static final String TABLE_TASK = "Task";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_ESTIMATED_TIME = "estimatedTime";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_START_DATE = "startDate";
    public static final String COLUMN_END_DATE = "endDate";

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
        String ADD_TASK = "insert into " + TABLE_TASK + " values (null, 'test', " +
                "null, null,null,null,null,null);";
        database.execSQL(ADD_TASK);
    }

    public List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();

        // Get a cursor on the header table "categories"
        final String getTasksQuery = "select * from " + TABLE_TASK + ";";
        Cursor cursor = database.rawQuery(getTasksQuery, null);
        cursor.moveToFirst();
        // Iterate through the header table building a list of categories
        while (!cursor.isAfterLast()) {
            Integer taskId;
            try {
                taskId = cursor.getInt(0);
            } catch (Exception e) {
                taskId = 0;
            }
            String taskName = cursor.getString(1);
            Task task = new Task(taskId, taskName);
            tasks.add(task);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return tasks;
    }

    public Task findTaskById(int id) {

        // Get a cursor on the header table "categories"
        final String getTaskQuery = "select * from " + TABLE_TASK + " where \"id\"=" + id + ";";
        Cursor cursor = database.rawQuery(getTaskQuery, null);
        cursor.moveToFirst();
        String name = cursor.getString(1);
        Task task = new Task(id, name);
        try {
            task.setDescription(cursor.getString(2));
        } catch (Exception e) {}
        try {
            task.setPriority(cursor.getInt(3));
        } catch (Exception e) {}
        try {
            task.setEstimatedTime(cursor.getString(4));
        } catch (Exception e) {}
        try {
            task.setPoints(cursor.getInt(5));
        } catch (Exception e) {}
        try {
            task.setProgress(cursor.getString(6));
        } catch (Exception e) {}
        try {
            task.setStartedDate(cursor.getInt(7));
        } catch (Exception e) {}
        try {
            task.setCompletedDate(cursor.getInt(8));
        } catch (Exception e) {}
        // make sure to close the cursor
        cursor.close();
        return task;
    }

    public void resetDatabase(Context context) {
        context.deleteDatabase(DataOpenHelper.DATABASE_NAME);
        dbHelper.close();
        instance = new DataUtils(context);
    }

}