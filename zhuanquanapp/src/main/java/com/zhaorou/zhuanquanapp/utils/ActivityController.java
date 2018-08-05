package com.zhaorou.zhuanquanapp.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    private static List<Activity> mActivityList = new ArrayList<>();
    private static Activity mCurrentActivity;

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    public static void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void finishAll() {
        for (Activity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
