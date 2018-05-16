package com.duozhuan.bitalk.views.browser;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.util.UIUtils;


/**
 * 给 webview 默认的设置
 */

public class DefaultWebViewSetting {


    public static void init(AppCompatActivity context, WebView webView,boolean isContent) {
        init(context,webView,false,isContent,false);
    }


    public static void init(AppCompatActivity context, WebView webView, boolean isViewProt, boolean isContent,boolean isChrome) {
        WebSettings settings = webView.getSettings();
//        // 存储(storage)
//        // 启用HTML5 DOM storage API，默认值 false
        settings.setDomStorageEnabled(true);

//        // 是否支持viewport属性，默认值 false
//        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        settings.setUseWideViewPort(isViewProt);
//
//        // 是否使用overview mode加载页面，默认值 false
//        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        settings.setLoadWithOverviewMode(true);
//      设置缓存模式

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//      是否支持Javascript，默认值false
        settings.setJavaScriptEnabled(true);
//
        settings.setLoadsImagesAutomatically(true); // 是否自动加载图片
        settings.setUserAgentString(settings.getUserAgentString() + " android mobile bitalkApp "+ Constants.APP_VERSION);
        Log.i("useagent:",settings.getUserAgentString());
        settings.setBlockNetworkImage(false);
        webView.addJavascriptInterface(new JSInterface(context), "NativeAPI");//webview和js相关调用
        if (isContent){ // 是否是详情页，详情页用另外一个 client
            webView.setWebViewClient(new ContentWebViewClient(context));
        }else {
            webView.setWebViewClient(new DefaultWebViewClient(context));
        }
        if (isChrome) {
            webView.setWebChromeClient(new ContentChromeClient());
        }else{
            webView.setWebChromeClient(new DefaultChromeClient());

        }
    }

}
