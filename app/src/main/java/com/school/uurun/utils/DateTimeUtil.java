package com.school.uurun.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 时间日期工具类
 */
public class DateTimeUtil {

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateTimeWithText() {
        return new SimpleDateFormat("yyyy-MM-hh hh:mm:ss").format(new Date());
    }
}
