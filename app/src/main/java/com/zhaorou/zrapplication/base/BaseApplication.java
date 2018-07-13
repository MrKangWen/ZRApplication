package com.zhaorou.zrapplication.base;

import android.app.Application;
import android.icu.util.TaiwanCalendar;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.utils.ApplicationUtils;

import com.zhaorou.zrapplication.network.HttpRequestUtil;

public class BaseApplication extends Application {

    protected static IWXAPI mWxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtils.setApplicationContext(this);
        HttpRequestUtil.init();
        initWXAPI();
    }

    private void initWXAPI() {
        mWxapi = WXAPIFactory.createWXAPI(this, ZRDConstants.AppIds.WX_APP_ID, true);
        mWxapi.registerApp(ZRDConstants.AppIds.WX_APP_ID);
    }


    public static IWXAPI getWXAPI() {
        return mWxapi;
    }
}
