package com.recluse.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getPublishTime(long publishTime, String pattern) {
        Date date = new Date(publishTime);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
