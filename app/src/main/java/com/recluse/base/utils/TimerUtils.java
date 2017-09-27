package com.recluse.base.utils;

public class TimerUtils {

    public static String getDurationString(long msec) {
        int sec = (int) (msec / 1000);
        if (sec <= 0) {
            sec = 0;
        }
        StringBuilder builder = new StringBuilder();
        int hour = sec / 3600;
        int minute = (sec / 60) % 60;
        int second = sec % 60;
        if (hour > 0) {
            builder.append(hour).append(":");
        }
        if (minute < 10) {
            builder.append("0");
        }
        builder.append(minute).append(":");
        if (second < 10) {
            builder.append("0");
        }
        builder.append(second);
        return builder.toString();
    }

}
