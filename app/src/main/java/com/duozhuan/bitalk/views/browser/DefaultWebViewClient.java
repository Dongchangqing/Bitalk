package com.duozhuan.bitalk.views.browser;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.duozhuan.bitalk.app.Constants;
import com.hwangjr.rxbus.RxBus;


import java.util.regex.Matcher;
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
        Log.i("dcqing",url);
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
        RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_START, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        loadJS(view);
        RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_FINISH, url);
    }

    // 将要加载资源(url)
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
//        deelUrlOverride(view,url);
//        Matcher matcher = WEB_URL.matcher(url);
//        if (!matcher.find()){
//            RxBus.get().post(Constants.EVENT_WEBVIEW_PAGE_LOADRESOURCE, url);
//        }
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

    // 拦截 URL 之后的处理
    private boolean deelUrlOverride(WebView view, String url) {

        /*if (ServiceUtils.deelForum(mActivity, url)) { // 满足帖子链接，跳转帖子详情页
            return true;
        } else if (url.contains(Constant.LICAIJIE_SHARE)) { // 理财节点击跳出原生分享
            try {
                // 去掉之后的 &
                String imageUrl = url.split("&")[1];
                UmengShareUtils.shareLicaijie(mActivity, imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }*/
        return true;
    }


    /**
     * 暂时未用
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset(WebView webView) {

        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 加载需要的所有JS
     */
    private void loadJS(WebView webView) {

        // 图片查看的 JS
        String jsImagePicture = "javascript:" + "function img_listen(){var Img=document.getElementsByClassName('content')[0].getElementsByTagName('img');for(var i=0;i<Img.length;i++){Img[i].onclick=function(i){var c=i;return function(){var imgarr=new Array;var data=new Array;for(var j=0;j<Img.length;j++){if(Img[j].src.indexOf(\"http\")>=0){imgarr[j]=Img[j].src}else if(Img[j].src.indexOf(\".com\")>=0){imgarr[j]=\"http:\"+Img[j].src}else{imgarr[j]=Img[j].src}}data[0]=c;data[1]=imgarr.length;var imgarrstr=JSON.stringify(imgarr);data[2]=imgarrstr;var json=JSON.stringify(data);console.log(json);window.NativeAPI.openImage(json);return false}}(i)}}img_listen();";
        webView.loadUrl(jsImagePicture);

        // 获取标题
        String jsGetTitle = "javascript:" +"function getTitle(){var field = document.getElementsByName('content-title')[0];  return field.content;};window.NativeAPI.setTitle(getTitle());";
        webView.loadUrl(jsGetTitle);

    }
}
