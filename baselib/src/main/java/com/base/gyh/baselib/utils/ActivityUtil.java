package com.base.gyh.baselib.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2018/10/29.
 */
public class ActivityUtil {
    private List<Activity> activityList = new ArrayList<>();
    private static ActivityUtil instance;

    // 单例模式中获取唯一的ExitApplication实例
    public static synchronized ActivityUtil getInstance() {
        if (null == instance) {
            instance = new ActivityUtil();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    // 移除Activity
    public void removeActivity(Activity activity) {
        if (activityList != null) {
            activityList.remove(activity);

        }
    }

    // 遍历所有Activity并finish
    public void exitSystem() {
        for (Activity activity : activityList) {
            if (activity != null){
                activity.finish();
            }
        }
        // 退出进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
