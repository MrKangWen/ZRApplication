package com.zhaorou.zrapplication.base;

import android.app.Application;

import com.zhaorou.zrapplication.utils.ApplicationUtils;

import com.zhaorou.zrapplication.network.HttpRequestUtil;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtils.setApplicationContext(this);
        HttpRequestUtil.init();
    }
}
