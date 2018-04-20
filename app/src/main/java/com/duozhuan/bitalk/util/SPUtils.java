package com.duozhuan.bitalk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.duozhuan.bitalk.MyApplication;
import com.duozhuan.bitalk.app.Constants;


public class SPUtils {
    private static SharedPreferences sharedPreferences;

    private static final String PREFERENCE_FILE_NAME = "mybitalksp";
    static {
        sharedPreferences = MyApplication.getContext()
                .getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultString) {
        return sharedPreferences.getString(key, defaultString);
    }

    public static void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public static boolean isLogin(){
        String accenttoken=getString(Constants.LOGIN_ACCENTTOKEN,"");
        if (TextUtils.isEmpty(accenttoken)){
            return false;
        }
        return true;
    }
}
