package com.duozhuan.bitalk.views.browser;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.duozhuan.bitalk.app.Constants;
import com.hwangjr.rxbus.RxBus;

import java.util.regex.Pattern;


public class DefaultWebViewClient extends WebViewClient {


    private final AppCompatActivity mActivity;

    public DefaultWebViewClient(AppCompatActivity activity) {
        mActivity = activity;
    }
    private static Pattern WEB_URL;
    static {
        //进行匹配，以过滤加载资源的请求,剩下ajax请求
        WEB_URL = Pattern.compile("\\.(png|css|js|jpg|ico|gif)");
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //Log.i("onLoadSource",request.getUrl().toString());
        return super.shouldOverrideUrlLoading(view, request);
    }


        @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("dddd-url",url);
        if (url.contains("access_token")){
            RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_LOADRESOURCE, url);
        }
        else if(url.contains("steemconnect.com/oauth2/authorize")){
            RxBus.get().post(Constants.EVENT_WEBVIEW_NEW_PAGE,Constants.LOGINURL);
        }else if(url.contains("signup.steemit.com")){
            RxBus.get().post(Constants.EVENT_WEBVIEW_NEW_PAGE,Constants.REGISTERURL);
        }

        return super.shouldOverrideUrlLoading(view, url);
    }

    // 此方法添加于API21，调用于非UI线程
// 拦截资源请求并返回数据，返回null时WebView将继续加载资源
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //不要删除
        Log.i("dddd-start",CookieUtils.getCookie(url)+"");
        RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_START, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //不要删除
        Log.i("dddd-finish",CookieUtils.getCookie(url)+"");
        //loadJS(view);
        RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_FINISH, url);
    }

    // 此方法添加于API23
// 加载资源时出错，通常意味着连接不到服务器
// 由于所有资源加载错误都会调用此方法，所以此方法应尽量逻辑简单
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_ERROR, "");
    }

    // 此方法添加于API23
// 在加载资源(iframe,image,js,css,ajax...)时收到了 HTTP 错误(状态码>=400)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    // 加载资源时发生了一个SSL错误，应用必需响应(继续请求或取消请求)
// 处理决策可能被缓存用于后续的请求，默认行为是取消请求
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }





}
