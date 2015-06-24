package com.petertemplin.scrummaster.models;

import android.content.Context;

import com.petertemplin.scrummaster.data.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 2015-06-21.
 */
public class Sprint extends AbstractTaskList{

    private int id;

    private String name;

    private String description;

    private String startDate;

    private String endDate;

    private String duration;

    private Boolean started;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public String getDurationFormatted() {
        if (duration != null) {
            String[] nums = duration.split(" ");
            return nums[0] + " months " + nums[1] + " days " + nums[2] + " hours";
        }
        return "No time limit";
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean isStarted() {
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

    public void save(Context parent) {
        DataUtils.getInstance(parent).saveSprint(this);
    }

    public String getTimeRemaining() {
        if (duration != null && startDate != null) {
            //String[] nums = duration.split(" ");
            //int monthsTotal = Integer.parseInt(nums[0]);
            //int daysTotal = Integer.parseInt(nums[1]);
            //int hoursTotal = Integer.parseInt(nums[2]);
        }
        return "Not started";
    }
}
