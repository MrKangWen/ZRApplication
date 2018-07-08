package com.zhaorou.zrapplication.network;

/**
 * Created by lykevin on 2017/6/20.
 */

public class NetworkType {
    public static final String TYPE_WIFI = "type_wifi";
    public static final String TYPE_MOBILE = "type_mobile";
    public static final String TYPE_NULL = "type_null";
    private String netType;

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }
}
