package com.petertemplin.scrummaster.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Me on 2015-06-21.
 */
public class DateUtils {

    public static final String EMPTY_DATE = "00000000000000";

    public static String currentDateToString() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timestamp;
    }

}
