package com.zhaorou.zhuanquanapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lykevin on 2017/7/7.
 */

public class SPreferenceUtil {

    private static final String SP_NAME = ApplicationUtils.getPackageName();

    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (int) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (long) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (float) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (boolean) value);
        } else if (value instanceof Set) {
            edit.putStringSet(key, (Set) value);
        }
        edit.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        boolean b = sp.getBoolean(key, defaultValue);
        return b;
    }

    public static String getString(Context context, String key, String defaultStr) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String str = sp.getString(key, defaultStr);
        return str;
    }

    public static int getInt(Context context, String key, int defaultInt) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        int i = sp.getInt(key, defaultInt);
        return i;
    }

    public static long getLong(Context context, String key, long defaultLong) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        long l = sp.getLong(key, defaultLong);
        return l;
    }

    public static float getFloat(Context context, String key, float defaultFloat) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        float f = sp.getFloat(key, defaultFloat);
        return f;
    }

    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Set<String> hashSet = new HashSet<>();
        Set<String> stringSet = sp.getStringSet(key, hashSet);
        return stringSet;
    }
}
