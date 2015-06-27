package com.petertemplin.scrummaster.util;

/**
 * Created by Me on 2015-06-27.
 */
public class StringUtils {

    public static boolean isEmpty(String s) {

        if (s == null) {
            return true;
        } else if (s.isEmpty()) {
            return true;
        }

        String temp = s;
        temp = temp.replace(" ","");
        temp = temp.replace("\n","");
        temp = temp.replace("\t","");
        if (temp.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String validateInt(String s, String fallback) {
        if (s == null || StringUtils.isEmpty(s)) {
            return fallback;
        } else {
            try {
                if (s.equals("0")) {
                    return s;
                }
                while (s.charAt(0) == '0' && s.length() >= 2) {
                    s = s.substring(1);
                }
                Integer.parseInt(s);
                return s;
            } catch (NumberFormatException e) {
                return fallback;
            }
        }
    }

}
