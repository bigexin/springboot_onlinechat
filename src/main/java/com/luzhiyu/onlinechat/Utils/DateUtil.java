package com.luzhiyu.onlinechat.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(currentTime);
    }
}
