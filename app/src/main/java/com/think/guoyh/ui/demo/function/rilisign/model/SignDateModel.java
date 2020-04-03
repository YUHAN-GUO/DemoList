package com.think.guoyh.ui.demo.function.rilisign.model;

/**
 * Created by kangbaibai on 2018/8/27.
 * 签到日期实体类
 */

public class SignDateModel {
    public static final int STATUS_NO_SIGN = 0; //0 未签到的日期
    public static final int STATUS_HAVE_SIGN = 1; //1 已签到的日期
    public static final int STATUS_SAT_OR_SUN = 2; //2 周六或周日
    public static final int STATUS_CURRENT_DAY = 3; //3 今天日期

    public SignDateModel() {
        this.status = STATUS_NO_SIGN;
    }

    public SignDateModel(int status, String content) {
        this.status = status;
        this.content = content;
    }

    private int status; //签到状态
    private String content; //要展示的文字
    private String date; //签到当天日期
    private int points; //本次签到获得的积分
    private int days; //连续签到的天数
    private int total; //累计签到的天数
    private int usable; //当前可用的积分总数

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsable() {
        return usable;
    }

    public void setUsable(int usable) {
        this.usable = usable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
