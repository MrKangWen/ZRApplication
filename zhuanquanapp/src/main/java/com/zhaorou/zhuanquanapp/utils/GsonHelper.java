package com.zhaorou.zhuanquanapp.utils;

import com.google.gson.Gson;

/**
 * Created by lykevin on 2017/7/14.
 */

public class GsonHelper {
    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        T t = gson.fromJson(json, classOfT);
        return t;
    }

    private GsonHelper() {

    }
}
