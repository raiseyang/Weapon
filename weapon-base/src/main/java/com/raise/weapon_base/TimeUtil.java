package com.raise.weapon_base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raise.yang on 19/08/22.
 */
public class TimeUtil {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 获取当前格式化的时间
     */
    public static String getLogTime() {
        return "_" + formatTime(System.currentTimeMillis()) + "_";
    }

    /**
     * 获取时间戳，单位：秒
     */
    public static long getSecondTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 格式化时间
     *
     * @return yyyy-MM-dd'T'HH:mm:ss
     */
    public static String formatTime(long timeInMills) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timeInMills));
    }

    public static String formatTime(long timeInMills, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(timeInMills));
    }

    public static String convertToHsm(long timeInMills) {
        long timeInSec = timeInMills / 1000;
        int hour = (int) (timeInSec / 3600);
        int minute = (int) (timeInSec % 3600 / 60);
        int second = (int) (timeInSec % 60);
        return hour + "h" + minute + "m" + second + "s";
    }

}
