package com.petertemplin.scrummaster.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Me on 2015-06-21.
 */
public class DateUtils {

    public static final String EMPTY_DATE = "00000000000000";
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int DAYS_IN_WEEK = 7;

    public static String currentDateToString() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timestamp;
    }

    public static String formatDate(String date) {
        int year = getYearFromDate(date);
        int month = getMonthFromDate(date);
        int day = getDayFromDate(date);
        int hour = getHourFromDate(date);
        int minute = getMinuteFromDate(date);
        return year + "-" + month + "-" + day + " " + timeTo12Hour(hour, minute);
    }

    public static String timeTo12Hour(int hour, int minute) {
        if (hour > 12) {
            return (hour - 12) + ":" + minute + " pm";
        } else if (hour == 0) {
            return "12:" + minute + " am";
        } else {
            return hour + ":" + minute + " am";
        }
    }

    public static int getYearFromDate(String date) {
        String year = date.substring(0,4);
        try {
            return Integer.parseInt(year);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getMonthFromDate(String date) {
        String month = date.substring(4,6);
        try {
            return Integer.parseInt(month);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getDayFromDate(String date) {
        String day = date.substring(6,8);
        try {
            return Integer.parseInt(day);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getHourFromDate(String date) {
        String hour = date.substring(8,10);
        try {
            return Integer.parseInt(hour);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getMinuteFromDate(String date) {
        String minute = date.substring(10,12);
        try {
            return Integer.parseInt(minute);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getDifferenceInDates(String oldDate, String newDate) {
        int newYear = getYearFromDate(newDate);
        int newMonth = getMonthFromDate(newDate);
        int newDay = getDayFromDate(newDate);
        int newHour = getHourFromDate(newDate);
        int newMinute = getMinuteFromDate(newDate);
        int oldYear = getYearFromDate(oldDate);
        int oldMonth = getMonthFromDate(oldDate);
        int oldDay = getDayFromDate(oldDate);
        int oldHour = getHourFromDate(oldDate);
        int oldMinute = getMinuteFromDate(oldDate);

        GregorianCalendar oldCalendar =
                new GregorianCalendar(oldYear,oldMonth,oldDay,oldHour,oldMinute);
        GregorianCalendar newCalendar =
                new GregorianCalendar(newYear,newMonth,newDay,newHour,newMinute);
        long oldTime = oldCalendar.getTimeInMillis();
        long newTime = newCalendar.getTimeInMillis();
        return newTime - oldTime;
    }

    public static long calculateTimeInPast(String pastDate) {
        return getDifferenceInDates(pastDate, currentDateToString());
    }

    public static long durationToMillis(String duration) {
        String[] nums = duration.split(" ");
        int weeks = Integer.parseInt(nums[0]);
        int days = Integer.parseInt(nums[1]);
        int hours = Integer.parseInt(nums[2]);
        return 60000*MINUTES_IN_HOUR*(hours + HOURS_IN_DAY*(days + DAYS_IN_WEEK*weeks));
    }

    public static String formatMillisAsTime(long milliseconds) {
        long minutes = milliseconds/60000;
        long weeks = minutes/(MINUTES_IN_HOUR*HOURS_IN_DAY*DAYS_IN_WEEK);
        minutes = minutes % (MINUTES_IN_HOUR*HOURS_IN_DAY*DAYS_IN_WEEK);
        long days = minutes/(MINUTES_IN_HOUR*HOURS_IN_DAY);
        minutes = minutes % (MINUTES_IN_HOUR*HOURS_IN_DAY);
        long hours = minutes / MINUTES_IN_HOUR;
        minutes = minutes % MINUTES_IN_HOUR;
        if (weeks != 0) {
            return weeks + " weeks " + days + " days";
        } else if (days != 0) {
            return days + " days " + hours + " hours";
        } else if (hours != 0) {
            return hours + " hours " + minutes + " minutes";
        } else if (minutes != 0) {
            return minutes + " minutes";
        } else {
            return "No time";
        }
    }

    public static String formatAsDate(int year, int month, int day, int hour, int minute) {
        String monthString = Integer.toString(month);
        if (month/10==0) {
            monthString = 0 + monthString;
        }
        String dayString = Integer.toString(day);
        if (day/10==0) {
            dayString = 0 + dayString;
        }
        String hourString = Integer.toString(hour);
        if (hour/10==0) {
            hourString = 0 + hourString;
        }
        String minuteString = Integer.toString(minute);
        if (minute/10==0) {
            minuteString = 0 + minuteString;
        }
        return year + monthString + dayString + hourString + minuteString + "00";
    }

}
