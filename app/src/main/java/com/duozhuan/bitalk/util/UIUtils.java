package com.duozhuan.bitalk.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.duozhuan.bitalk.MyApplication;

public class UIUtils {

    //提供获取上下环境方法
    public static Context getContext() {
        return MyApplication.getContext();
    }

    //Handler
    public static Handler getHandler() {
        return MyApplication.getHandler();
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
    public static int dip2px(int dip) {
        //获取dip和px的比例关系
        float d = getResources().getDisplayMetrics().density;
        // (int)(80.4+0.5)   (int)(80.6+0.5)
        return (int) (dip * d + 0.5);
    }

    //px---->dip
    public static int px2dip(int px) {
        float d = getResources().getDisplayMetrics().density;
        return (int) (px / d + 0.5);
    }

    /**
     * 隐藏当前activity键盘
     *
     * @param view     （传递非EditView 对象 用来关闭对象）
     * @param activity 当前activity的实例对象
     */
    static public void hiddenKeyBord(View view, Activity activity) {

        if (activity == null || activity.getSystemService(Context.INPUT_METHOD_SERVICE) == null)
            return;
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    public static float getWebWidth() {
        float width = getScreenWidth() / getResources().getDisplayMetrics().density;
        return width;
    }

}
