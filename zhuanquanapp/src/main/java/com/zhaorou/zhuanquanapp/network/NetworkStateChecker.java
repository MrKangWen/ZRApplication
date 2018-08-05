package com.zhaorou.zhuanquanapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lykevin on 2017/6/19.
 */
public class NetworkStateChecker {

    public static String checkNetworkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            boolean wifiConnected = activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                return NetworkType.TYPE_WIFI;
            } else if (mobileConnected) {
                return NetworkType.TYPE_MOBILE;
            }
        }
        return NetworkType.TYPE_NULL;
    }
}
