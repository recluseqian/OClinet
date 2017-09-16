package com.recluse.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by recluse on 17-9-15.
 */

public class DateUtils {

    public static String getPublishTime(long publishTime, String pattern) {
        Date date = new Date(publishTime);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
