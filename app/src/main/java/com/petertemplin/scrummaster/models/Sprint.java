package com.petertemplin.scrummaster.models;

import android.content.Context;

import com.petertemplin.scrummaster.data.DataUtils;
import com.petertemplin.scrummaster.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Me on 2015-06-21.
 */
public class Sprint extends AbstractTaskList{

    // defaults
    public static int DEFAULT_ID = 0;
    public static String DEFAULT_NAME = "New Sprint";
    public static String DEFAULT_DESC = "No description";
    public static String DEFAULT_START = DateUtils.EMPTY_DATE;
    public static String DEFAULT_END = DateUtils.EMPTY_DATE;
    public static String DEFAULT_DURATION = "No time limit";

    private int id = 0;

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
            String durationS = "";
            if (!nums[0].equals("0")) {
                durationS += nums[0] + " weeks ";
            }
            if (!nums[1].equals("0")) {
                durationS += nums[1] + " days ";
            }
            if (!nums[2].equals("0")) {
                durationS += nums[2] + " hours ";
            }
            if (!durationS.isEmpty()) {
                return durationS;
            } else {
                return DEFAULT_DURATION;
            }
        }
        return DEFAULT_DURATION;
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

    public void start() {
        started = true;
        startDate = DateUtils.currentDateToString();
    }

    public void end() {
        endDate = DateUtils.currentDateToString();
    }

    public void save(Context parent) {
        DataUtils.getInstance(parent).saveSprint(this);
    }

    public String getTimeRemaining() {
        if (duration != null && !duration.equals("0 0 0") && !duration.equals(DEFAULT_DURATION) &&
                startDate != null && !startDate.equals(DateUtils.EMPTY_DATE)) {
            long millisPassed = DateUtils.calculateTimeInPast(startDate);
            long millisDuration = DateUtils.durationToMillis(duration);
            long millisRemaining = millisDuration - millisPassed;
            if (millisRemaining < 0) {
                millisRemaining = 0;
            }
            return DateUtils.formatMillisAsTime(millisRemaining) + " remaining";
        }
        return "";
    }
}
