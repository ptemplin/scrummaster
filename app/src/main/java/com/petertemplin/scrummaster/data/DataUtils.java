package com.petertemplin.scrummaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.petertemplin.scrummaster.models.Sprint;
import com.petertemplin.scrummaster.models.Task;
import com.petertemplin.scrummaster.util.DateUtils;

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
    public static final String COLUMN_SPRINT = "sprints";
    public static final String COLUMN_PROJECT = "project";
    public static final String COLUMN_BACKLOG = "backlogs";

    // sprint table
    public static final String TABLE_SPRINT = "Sprint";
    public static final String COLUMN_SPRINT_ID = "id";
    public static final String COLUMN_SPRINT_NAME = "name";
    public static final String COLUMN_SPRINT_DESCRIPTION = "description";
    public static final String COLUMN_SPRINT_START_DATE = "startDate";
    public static final String COLUMN_SPRINT_END_DATE = "endDate";
    public static final String COLUMN_SPRINT_DURATION = "duration";
    public static final String COLUMN_SPRINT_STARTED = "started";
    public static final String COLUMN_SPRINT_PROJECT = "project";

    // backlog table
    public static final String TABLE_BACKLOG = "Backlog";
    public static final String COLUMN_BACKLOG_ID = "id";
    public static final String COLUMN_BACKLOG_NAME = "name";
    public static final String COLUMN_BACKLOG_DESCRIPTION = "description";
    public static final String COLUMN_BACKLOG_PROJECT = "project";

    // project table
    public static final String TABLE_PROJECT = "Project";
    public static final String COLUMN_PROJECT_ID = "id";
    public static final String COLUMN_PROJECT_NAME = "name";
    public static final String COLUMN_PROJECT_DESCRIPTION = "description";
    public static final String COLUMN_PROJECT_START_DATE = "startDate";


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
    public void addTask(Task task) {
        // for debugging
        String description = task.getDescription();
        if (description == null) {
            description = "No description";
        }
        int priority = task.getPriority();
        String ADD_TASK = "insert into " + TABLE_TASK + " values (null, '" + task.getName() + "', " +
                "'" + description + "', " + priority + ", null, null, null, null, null, null, null, null);";
        database.execSQL(ADD_TASK);
    }

    public void addNewSprint(Sprint sprint) {
        // first add the sprint to the database
        // validate all fields
        String name = sprint.getName();
        if (name == null) {
            name = "New Sprint";
        }
        String description = sprint.getDescription();
        if (description == null) {
            description = "No description";
        }
        String startDate = sprint.getStartDate();
        if (startDate == null) {
            startDate = DateUtils.EMPTY_DATE;
        }
        String endDate = sprint.getEndDate();
        if (endDate == null) {
            endDate = DateUtils.EMPTY_DATE;
        }
        String duration = sprint.getDuration();
        if (duration == null) {
            duration = "No time limit";
        }
        Boolean started = sprint.isStarted();
        if (started == null) {
            started = false;
        }
        String ADD_SPRINT = "insert into " + TABLE_SPRINT + " values (null, '"
                + name + "', '"
                + description + "', '"
                + startDate + "', '"
                + endDate + "', '"
                + duration + "', '"
                + started + "', "
                + sprint.getProject() + ");";
        database.execSQL(ADD_SPRINT);

        // if there are no tasks in the sprint, return
        if (sprint.getTasks().isEmpty()) {
            return;
        }

        // get the sprint's id
        String GET_SPRINT_ID = "select \"id\" from " + TABLE_SPRINT +
                " where \"name\"='" + name + "' order by \"id\" desc;";
        Cursor cursor = database.rawQuery(GET_SPRINT_ID, null);
        cursor.moveToFirst();
        int sprintId = cursor.getInt(0);

        // add the tasks to the sprint
        addTasksToSprint(sprint.getTasks(), sprintId);
    }

    public void addTasksToSprint(List<Task> tasks, int sprintId) {
        String updateTasksQuery = "update " + TABLE_TASK + " SET " +
                COLUMN_SPRINT + "=" + sprintId + ", " +
                COLUMN_BACKLOG + "=0 " +
                "where " + COLUMN_ID + " in " + buildIdStringFromList(tasks) + ";";
        database.execSQL(updateTasksQuery);
    }

    public Sprint getLatestSprint() {
        final String getSprintQuery = "select * from " + TABLE_SPRINT + " order by id desc;";
        Cursor cursor = database.rawQuery(getSprintQuery, null);
        Sprint sprint = getSprintFromCursor(cursor);

        final String getTasksQuery = "select * from " + TABLE_TASK + " where " +
                COLUMN_SPRINT + "=" + sprint.getId() + ";";
        cursor = database.rawQuery(getTasksQuery, null);
        sprint.setTasks(getTasksFromCursor(cursor));

        return sprint;
    }

    public List<Task> getTasksFromBacklog() {
        // Get a cursor on the header table "categories"
        final String getTasksQuery = "select * from " + TABLE_TASK + " where " +
                COLUMN_SPRINT + "=0 or " + COLUMN_SPRINT + " is null;";
        Cursor cursor = database.rawQuery(getTasksQuery, null);
        return getTasksFromCursor(cursor);
    }

    public Task findTaskById(int id) {

        // Get a cursor on the header table "categories"
        final String getTaskQuery = "select * from " + TABLE_TASK + " where \"id\"=" + id + ";";
        Cursor cursor = database.rawQuery(getTaskQuery, null);
        return getTasksFromCursor(cursor).get(0);
    }

    public void resetDatabase(Context context) {
        context.deleteDatabase(DataOpenHelper.DATABASE_NAME);
        dbHelper.close();
        instance = new DataUtils(context);
    }

    public String buildIdStringFromList(List<Task> tasks) {
        String ids = "(";
        for(int i = 0; i < tasks.size(); i++) {
            if (i != 0) {
                ids += ", ";
            }
            ids += tasks.get(i).getId();
        }
        return ids + ")";
    }

    public List<Task> getTasksFromCursor(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();

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
            try {
                task.setDescription(cursor.getString(2));
            } catch (Exception e) {
            }
            try {
                task.setPriority(cursor.getInt(3));
            } catch (Exception e) {
            }
            try {
                task.setEstimatedTime(cursor.getString(4));
            } catch (Exception e) {
            }
            try {
                task.setPoints(cursor.getInt(5));
            } catch (Exception e) {
            }
            try {
                task.setProgress(cursor.getString(6));
            } catch (Exception e) {
            }
            try {
                task.setStartedDate(cursor.getString(7));
            } catch (Exception e) {
            }
            try {
                task.setCompletedDate(cursor.getString(8));
            } catch (Exception e) {
            }
            try {
                task.setSprintId(cursor.getInt(9));
            } catch (Exception e) {
            }
            try {
                task.setProjectId(cursor.getInt(10));
            } catch (Exception e) {
            }
            try {
                task.setBacklogId(cursor.getInt(11));
            } catch (Exception e) {
            }
            tasks.add(task);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();

        return tasks;
    }

    public Sprint getSprintFromCursor(Cursor cursor) {
        Sprint sprint = new Sprint();

        try {
            cursor.moveToFirst();
            sprint.setId(cursor.getInt(0));
            sprint.setName(cursor.getString(1));
        } catch (Exception e) {}
        try {
            sprint.setDescription(cursor.getString(2));
        }catch (Exception e) {}
        try {
            sprint.setStartDate(cursor.getString(3));
        } catch (Exception e) {}
        try {
            sprint.setEndDate(cursor.getString(4));
        } catch (Exception e) {}
        try {
            sprint.setDuration(cursor.getString(5));
        } catch (Exception e) {}
        try {
            sprint.setStarted(cursor.getString(6).equals("true"));
        } catch (Exception e) {}
        try {
            sprint.setProject(cursor.getInt(7));
        } catch (Exception e) {}

        cursor.close();
        return sprint;
    }

    public void saveTask(Task task) {
        // validate all fields
        String name = task.getName();
        if (name == null) {
            name = "New Task";
        }
        String description = task.getDescription();
        if (description == null) {
            description = "No description";
        }
        String estimatedTime = task.getEstimatedTime();
        if (estimatedTime == null) {
            estimatedTime = "No estimate given";
        }
        String progress = task.getProgress();
        if (progress == null) {
            progress = "Back Burner";
        }
        String startDate = task.getStartedDate();
        if (startDate == null) {
            startDate = DateUtils.EMPTY_DATE;
        }
        String endDate = task.getCompletedDate();
        if (endDate == null) {
            endDate = DateUtils.EMPTY_DATE;
        }

        String UPDATE_TASK = "update " + TABLE_TASK + " set "
                + COLUMN_NAME + "='" + name + "', "
                + COLUMN_DESCRIPTION + "='" + description + "', "
                + COLUMN_PRIORITY + "=" + task.getPriority() + ", "
                + COLUMN_ESTIMATED_TIME + "='" + estimatedTime + "', "
                + COLUMN_POINTS + "=" + task.getPoints() + ", "
                + COLUMN_PROGRESS + "='" + progress + "', "
                + COLUMN_START_DATE + "='" + startDate + "', "
                + COLUMN_END_DATE + "='" + endDate + "', "
                + COLUMN_SPRINT + "=" + task.getSprintId() + ", "
                + COLUMN_PROJECT + "=" + task.getProjectId() + ", "
                + COLUMN_BACKLOG + "=" + task.getBacklogId()
                + " where " + COLUMN_ID + "=" + task.getId() + ";";
        database.execSQL(UPDATE_TASK);
    }

    public void saveSprint(Sprint sprint) {
        // validate all fields
        String name = sprint.getName();
        if (name == null) {
            name = "New Sprint";
        }
        String description = sprint.getDescription();
        if (description == null) {
            description = "No description";
        }
        String startDate = sprint.getStartDate();
        if (startDate == null) {
            startDate = DateUtils.EMPTY_DATE;
        }
        String endDate = sprint.getEndDate();
        if (endDate == null) {
            endDate = DateUtils.EMPTY_DATE;
        }
        String duration = sprint.getDuration();
        if (duration == null) {
            duration = "No time limit";
        }
        Boolean started = sprint.isStarted();
        if (started == null) {
            started = false;
        }

        String UPDATE_SPRINT = "update " + TABLE_SPRINT + " set "
                + COLUMN_SPRINT_NAME + "='" + name + "', "
                + COLUMN_SPRINT_DESCRIPTION + "='" + description + "', "
                + COLUMN_SPRINT_START_DATE + "=" + startDate + ", "
                + COLUMN_SPRINT_END_DATE + "=" + endDate + ", "
                + COLUMN_SPRINT_DURATION + "='" + duration + "', "
                + COLUMN_SPRINT_STARTED + "='" + started + "', "
                + COLUMN_SPRINT_PROJECT + "=" + sprint.getProject()
                + " where " + COLUMN_SPRINT_ID + "=" + sprint.getId() + ";";
        database.execSQL(UPDATE_SPRINT);
    }
}