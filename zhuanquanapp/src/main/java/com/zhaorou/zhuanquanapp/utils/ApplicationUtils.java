package com.zhaorou.zhuanquanapp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用相关信息工具类
 * <p/>
 * Created by lykevin on 2016/9/1.
 */
public class ApplicationUtils {

    private static Context mContext;

    /**
     * 设置ApplicationContext
     *
     * @param context
     */
    public static void setApplicationContext(Context context) {
        mContext = context;
    }

    /**
     * 获取ApplicationContext
     *
     * @return mContext
     */
    public static Context getApplicationContext() {
        return mContext;
    }

    /**
     * 获取应用包名
     *
     * @return packageName
     */
    public static String getPackageName() {
        return mContext.getPackageName();
    }

    /**
     * 获取App versionName
     *
     * @return
     */
    public static String getVersionName() {
        PackageInfo packageInfo = getPackageInfo();
        return packageInfo == null ? "" : packageInfo.versionName;
    }

    /**
     * 获取App versionCode
     */
    public static int getVersionCode() {
        PackageInfo packageInfo = getPackageInfo();
        return packageInfo == null ? 0 : packageInfo.versionCode;
    }

    /**
     * 获取applicationInfo
     *
     * @return
     */
    private static ApplicationInfo getApplicationInfo() {
        PackageManager pm = getPackageManager();
        String packageName = getPackageName();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = pm.getApplicationInfo(packageName, 0);
            return applicationInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取packageInfo
     *
     * @return
     */
    private static PackageInfo getPackageInfo() {
        PackageManager pm = getPackageManager();
        String packageName = getPackageName();
        try {
            return pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取packageManager
     *
     * @return
     */
    private static PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }

    private ApplicationUtils() {
    }
}
