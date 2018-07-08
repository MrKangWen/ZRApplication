package com.zhaorou.zrapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by lykevin on 2018/3/13.
 */

public class ScreenInfoHelper {
    private ScreenInfoHelper() {
    }

    /**
     * 获取屏幕widthPixels
     *
     * @param context
     * @return widthPixels
     */
    public static int getScreenWidthPixels(Context context) {
        return getDeviceMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕heightPixels
     *
     * @param context
     * @return heightPixels
     */
    public static int getScreenHeightPixels(Context context) {
        return getDeviceMetrics(context).heightPixels;
    }

    /**
     * 获取屏幕density
     *
     * @param context
     * @return density
     */
    public static float getDeviceDensity(Context context) {
        return getDeviceMetrics(context).density;
    }

    /**
     * 获取屏幕相关参数
     *
     * @param context
     * @return metrics
     */
    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }
}
