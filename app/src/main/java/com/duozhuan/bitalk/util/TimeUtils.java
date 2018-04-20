package com.duozhuan.bitalk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class TimeUtils {

    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    // 如果转换出错，返回当前时间
    public static Date parseDate(String time) {
        try {
            return mDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String formatDate(Date date) {
        return mDateFormat.format(date);
    }

    public static String formatDate(long time) {
        return formatDate(new Date(time));
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(date);
    }

}
