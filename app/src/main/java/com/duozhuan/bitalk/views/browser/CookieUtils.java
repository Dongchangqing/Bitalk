package com.duozhuan.bitalk.views.browser;

import android.webkit.CookieManager;




public class CookieUtils {
    public static void setCookie(String cookie) {
        // 设置 cookie 必须设置 domain 和 path ，只有当前网页的 domain 和 path 匹配时，才会携带 cookie
        cookie = cookie + ";path=/;domain=" + ".duozhuan.cn";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie("http://steem.duozhuan.cn", cookie);
    }

    public static void clearCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public static String getCookie(String url){
        String cookie = CookieManager.getInstance().getCookie(url);
        return cookie;
    }

}
