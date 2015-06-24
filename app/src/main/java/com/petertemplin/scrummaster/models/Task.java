package com.petertemplin.scrummaster.models;

import android.content.Context;

import com.petertemplin.scrummaster.data.DataUtils;

/**
 * Created by Me on 2015-06-20.
 */
public class Task {

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

    public static int getIdFromTitle(String title) {
        title = title.substring(0, title.indexOf("-"));
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
        return id + "-" + name;
    }

    public void save(Context context) {
        DataUtils.getInstance(context).saveTask(this);
    }
}
