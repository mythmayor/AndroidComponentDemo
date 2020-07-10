package com.mythmayor.basicproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mythmayor on 2020/6/30.
 * 日期工具类
 */
public class DateUtil {

    //获取年月日时分秒
    public static String getAll(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取年月日时分
    public static String getAll2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取年月日
    public static String getYMD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取月日
    public static String getMD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取年月日
    public static String getDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取时间-时分秒
    public static String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取时间-时分
    public static String getHourMinute(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取时间-时
    public static String getHour(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取  分秒
    public static String getMinuteSeconds(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取时间-分
    public static String getMinute(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        String date = sdf.format(new Date(time));
        return date;
    }

    //获取时间-秒
    public static String getSecond(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        String date = sdf.format(new Date(time));
        return date;
    }

    //日期转换为毫秒值
    public static long date2Millseconds(String date) {
        long millseconds = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            millseconds = df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millseconds;
    }

    //时间转换为毫秒值
    public static long time2Millseconds(String date) {
        long millseconds = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            millseconds = df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millseconds;
    }

    //时间转换为毫秒值
    public static long time2Millseconds2(String date) {
        long millseconds = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            millseconds = df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millseconds;
    }

    /**
     * 根据日期查询星期几
     *
     * @param date 需要查询的日期，格式为2020-06-30
     * @return
     */
    public static String getWeekFromDate(String date) {
        String week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "日";
        }
        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "一";
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "二";
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "三";
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "四";
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "五";
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            week += "六";
        }
        return week;
    }
}
