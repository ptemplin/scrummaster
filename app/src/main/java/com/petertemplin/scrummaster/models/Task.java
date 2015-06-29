package com.petertemplin.scrummaster.models;

import android.content.Context;

import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.util.DateUtils;

/**
 * Created by Me on 2015-06-20.
 */
public class Task {

    // defaults
    public static int DEFAULT_ID = 0;
    public static String DEFAULT_NAME = "Task";
    public static String DEFAULT_DESCRIPTION = "No description";
    public static int DEFAULT_PRIORITY = 0;
    public static int MAX_PRIORITY = 5;
    public static int MIN_PRIORITY = 0;
    public static String DEFAULT_ESTIMATE = "None";
    public static int DEFAULT_POINTS = 0;
    public static int MAX_POINTS = 21;
    public static int POINTS_INTERVALS = 7;
    public static String DEFAULT_PROGRESS = "Back Burner";
    public static String DEFAULT_START = DateUtils.EMPTY_DATE;
    public static String DEFAULT_END = DateUtils.EMPTY_DATE;

    private int id = 0;

    private String name;

    private String description;

    private int priority = 0;

    private String estimatedTime;

    private int points = 0;

    private String progress;

    private String startedDate;

    private String completedDate;

    private int sprintId = 0;

    private int projectId = 0;

    private int backlogId = 0;

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getBacklogId() {
        return backlogId;
    }

    public void setBacklogId(int backlogId) {
        this.backlogId = backlogId;
    }

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public void start() {
        startedDate = DateUtils.currentDateToString();
    }

    public void end() {
        completedDate = DateUtils.currentDateToString();
    }

    public static int getIdFromTitle(String title) {
        title = title.substring(0, title.indexOf(" "));
        int taskId = 0;
        try {
            taskId = Integer.parseInt(title);
        } catch (Exception e) {
            taskId = 1;
        }
        return taskId;
    }

    public String toString() {
        if (id == 0) {
            return name;
        }
        return id + " - " + name;
    }

    public void save(Context context) {
        DataUtils.getInstance(context).saveTask(this);
    }
}
