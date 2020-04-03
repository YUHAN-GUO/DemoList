package com.think.guoyh.ui.demo.function.rilisign.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kangbaibai on 2018/8/30.
 */

public class DateUtil {

    public static String formatTime(int time, boolean negative) {
        int hour = time / 3600;
        int minute = (time - hour * 3600) / 60;
        int second = (time - hour * 3600 - minute * 60);

        String template = negative ? "/" + "%02d:%02d:%02d" : "%02d:%02d:%02d";
        if (hour <= 0L) {
            template = negative ? "/" + "%02d:%02d" : "%02d:%02d";
        }
        return String.format(template, hour, minute, second);
    }

    public static String formatTime(long time) {
        time /= 1000;
        long hour = time / 3600;
        long minute = (time - hour * 3600) / 60;
        long second = (time - hour * 3600 - minute * 60);
        if (hour <= 0L) {
            return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }

    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static int getFirstDayOfMonth() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DAY_OF_MONTH, 1);
        int i = a.get(Calendar.DAY_OF_WEEK);
        return i;
    }

    //获取今天是星期几
    public static int getCurrentDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();//日历对象
        if (date != null) {
            calendar.setTime(date);//设置传入日期
        } else {
            calendar.setTime(new Date());//设置当前日期
        }
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        return i;
    }

    //获取前一天的日期
    public static Date getDateForLastDay(Date date, int amount) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        if (date != null) {
            ca.setTime(date); //设置时间为传入时间
        } else {
            ca.setTime(new Date()); //设置时间为当前时间
        }
        ca.add(Calendar.DATE, -amount);
        Date lastDay = ca.getTime(); //结果
        //前一年ca.add(Calendar.YEAR, -1);
        //求前一月ca.add(Calendar.MONTH, -1)，
        //前一天ca.add(Calendar.DATE, -1)
        //lastDay.getDay()周几  lastDay.getDate()日期
        return lastDay;
    }

    //获取后一天的日期
    public static Date getDateForNextDay(Date date, int amount) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        if (date != null) {
            ca.setTime(date); //设置时间为传入时间
        } else {
            ca.setTime(new Date()); //设置时间为当前时间
        }
        ca.add(Calendar.DATE, +amount);
        Date lastDay = ca.getTime(); //结果
        //前一年ca.add(Calendar.YEAR, -1);
        //求前一月ca.add(Calendar.MONTH, -1)，
        //前一天ca.add(Calendar.DATE, -1)
        //lastDay.getDay()周几  lastDay.getDate()日期
        return lastDay;
    }
}
