package com.petertemplin.scrummaster.models;

/**
 * Created by Me on 2015-06-20.
 */
public class Task {

    private int id = 0;

    private String name;

    private String description;

    private int priority;

    private String estimatedTime;

    private int points;

    private String progress;

    private int startedDate;

    private int completedDate;

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
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

    public int getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(int startedDate) {
        this.startedDate = startedDate;
    }

    public int getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(int completedDate) {
        this.completedDate = completedDate;
    }

    public String toString() {
        if (id == 0) {
            return name;
        }
        return id + "-" + name;
    }
}
