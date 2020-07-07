package com.mythmayor.basicproject.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 * Activity管理器工具类
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
