package com.zhaorou.zrapplication.base;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.utils.ApplicationUtils;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {

    protected static IWXAPI mWxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtils.setApplicationContext(this);
        HttpRequestUtil.init();
        initWXAPI();
        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        Stetho.initializeWithDefaults(this);
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);



    }

    private void initWXAPI() {
        mWxapi = WXAPIFactory.createWXAPI(this, ZRDConstants.AppIds.WX_APP_ID, true);
        mWxapi.registerApp(ZRDConstants.AppIds.WX_APP_ID);
    }


    public static IWXAPI getWXAPI() {
        return mWxapi;
    }
}
