package com.petertemplin.scrummaster.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 2015-06-21.
 */
public class Sprint extends AbstractTaskList{

    private int id;

    private String name;

    private String description;

    private int startDate;

    private int endDate;

    private String duration;

    private boolean started;

    private int projectId;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getProject() {
        return projectId;
    }

    public void setProject(int project) {
        this.projectId = project;
    }
}
