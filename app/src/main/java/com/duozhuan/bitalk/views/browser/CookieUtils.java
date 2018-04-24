package com.duozhuan.bitalk.views.browser;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.duozhuan.bitalk.MainActivity;
import com.duozhuan.bitalk.MyApplication;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.util.SPUtils;


public class CookieUtils {
    public static void setCookie(String cookie) {
        // 设置 cookie 必须设置 domain 和 path ，只有当前网页的 domain 和 path 匹配时，才会携带 cookie
        CookieSyncManager.createInstance(MyApplication.getContext());
        cookie = cookie + ";path=/;domain=" + ".duozhuan.cn";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie("http://steem.duozhuan.cn", cookie);
        CookieSyncManager.getInstance().sync();
    }

    public static void clearCookie() {
        setCookie("");
        setCookies("");
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
    }

    public static void setCookies(String cookie) {
        // 设置 cookie 必须设置 domain 和 path ，只有当前网页的 domain 和 path 匹配时，才会携带 cookie
        CookieSyncManager.createInstance(MyApplication.getContext());
        cookie = cookie + ";path=/;domain=" + ".steemconnect.com";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie("https://steemconnect.com", cookie);
        CookieSyncManager.getInstance().sync();
    }

    public static String getCookie(String url){
        String cookie = CookieManager.getInstance().getCookie(url);
        if (TextUtils.isEmpty(cookie)){
            setCookie(SPUtils.getString(Constants.LOGIN_ACCENTTOKEN));
        }
        if (cookie!=null&&cookie.contains("__cfduid")){
            setCookies("");
            setCookie("");
        }
        return cookie;
    }

}
