package com.duozhuan.bitalk.views.browser;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;



public class ContentWebViewClient extends DefaultWebViewClient {

//    private final AppCompatActivity mActivity;

    public ContentWebViewClient(AppCompatActivity activity) {
        super(activity);
//        mActivity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        super.shouldOverrideUrlLoading(view, request);
        WebActivity.actionWeb(view.getContext(), request.getUrl().toString(),"");
        return true;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        super.shouldOverrideUrlLoading(view, url);
        WebActivity.actionWeb(view.getContext(), url,"");
        return true;
    }
}
