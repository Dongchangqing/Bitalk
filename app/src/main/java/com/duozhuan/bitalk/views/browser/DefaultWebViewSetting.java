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
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//
//        // 是否支持Javascript，默认值false
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


    public static void loadContent(WebView webView, String content) {
        content = "<!doctype html>\n" +
                "<html lang=\"en\"><head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1\">\n" +
                "\t<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "\t<meta name=\"x5-orientation\" content=\"portrait\">\n" +
                "\t<meta name=\"screen-orientation\" content=\"portrait\">\n" +
                "\t<title></title>\n" +
                "\t<style>@charset \"utf-8\";html{line-height:1.15;-webkit-text-size-adjust:none;-webkit-user-select:none;-webkit-touch-callout:none;font-family:Helvetica;-webkit-font-smoothing:antialiased;-webkit-tap-highlight-color:transparent}body{margin:0;font-size:12px;color:;background:#fff}article,aside,footer,header,nav,section{display:block}h1{font-size:2em;margin:.67em 0}a{background-color:transparent;-webkit-text-decoration-skip:objects;text-decoration:none;-webkit-tap-highlight-color:rgba(255,255,255,0);color:#333;cursor:pointer}b,strong{font-weight:inherit}em{font-style:normal}b,strong{font-weight:bolder}img{border-style:none;vertical-align:top}button,input,optgroup,select,textarea{font-family:Helvetica;font-size:100%;line-height:1.15;margin:0}button,input{overflow:visible}button,select{text-transform:none}[type=reset],[type=submit],button,html [type=button]{-webkit-appearance:button}textarea{overflow:auto}[type=checkbox],[type=radio]{box-sizing:border-box;padding:0}[type=number]::-webkit-inner-spin-button,[type=number]::-webkit-outer-spin-button{height:auto}[type=search]{-webkit-appearance:textfield;outline-offset:-2px}[type=search]::-webkit-search-cancel-button,[type=search]::-webkit-search-decoration{-webkit-appearance:none}::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}[hidden]{display:none}a,button,input,select,textarea{background:0 0;-webkit-tap-highlight-color:rgba(255,0,0,0);outline:0;-webkit-appearance:none}li{list-style:none}*,::after,::before{margin:0;padding:0;box-sizing:border-box;-webkit-box-sizing:border-box}.content a {color: #4587fa !important; text-decoration:none !important;}.content {background: #fff;padding: 0 .32rem; max-width: 7.5rem;min-width: 3.2rem; width: 100%; margin: 0 auto; font-size: .32rem; background-color: #fff;word-break: break-all;text-align: justify;max-height: 999999px;line-height: .5rem !important;padding-top: .24rem; color: #333; } .content * { font-size: .32rem !important;line-height: .5rem !important;word-break: break-all !important;text-align: justify !important;background-color: #fff !important;color: #333 !important; }.content p { margin-bottom: .3rem !important; }.content img { display: block !important; width: auto !important; height: auto !important; max-width: 100% !important;  margin: .4rem auto !important;} .content table{width: 100% !important;}</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"content\">" +
                content +
                "</div>" +
                "\t<script>var clientWidth = 0;if (document.documentElement.clientWidth > 750) {clientWidth = 750;} else { clientWidth = " + UIUtils.getWebWidth() + "; }document.documentElement.style.fontSize = clientWidth / 7.5 + 'px';</script>\n" +
                "</body>\n" +
                "</html>";
        // 图片必须是全路径
        webView.loadDataWithBaseURL("", content,
                "text/html",
                "utf-8", null);
    }

}
