package com.duozhuan.bitalk.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.duozhuan.bitalk.MyApplication;


public class CommonUtils {

    private static Toast mToast;

    //提供获取上下环境方法
    public static Context getContext() {
        return MyApplication.getContext();
    }


    public static void showToastShort(int resId) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), CommonUtils.getString(resId), Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToastShort(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToastLong(int resId) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), CommonUtils.getString(resId), Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void showToastLong(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        mToast.show();
    }

    //xml--->view
    public static View inflate(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId, null);
    }

    //获取资源文件夹
    public static Resources getResources() {
        return getContext().getResources();
    }

    //获取string操作
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    //获取drawable
    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(getContext(), drawableId);
    }

    //获取color
    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    //获取Dimension
    public static float getDimension(int dimensionId) {
        return getResources().getDimension(dimensionId);
    }

    public static int getInteger(int integerId) {
        return getResources().getInteger(integerId);
    }

    //获取stringArray数组
    public static String[] getStringArray(int stringArrayId) {
        return getResources().getStringArray(stringArrayId);
    }

    //获取屏幕的宽
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    //获取屏幕的高
    public static int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    //获取导航栏的高度
    public static int getNavigationBarHeight() {
        int resourceId;
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    //获取状态栏高度
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //手机的像素密度跟文档中的最接近值
    //dip--->px
    public static int dp2px(int dip) {
        //获取dip和px的比例关系
        float d = getResources().getDisplayMetrics().density;
        // (int)(80.4+0.5)   (int)(80.6+0.5)
        return (int) (dip * d + 0.5);
    }

    //px---->dip
    public static int px2dp(int px) {
        float d = getResources().getDisplayMetrics().density;
        return (int) (px / d + 0.5);
    }

    public static int sp2px(Context context, float spValue) {
        float d = getResources().getDisplayMetrics().density;
        return (int) (spValue * d + 0.5f);
    }
}
