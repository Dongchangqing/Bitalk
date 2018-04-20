package com.duozhuan.bitalk.views.browser;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.duozhuan.bitalk.app.Constants;
import com.hwangjr.rxbus.RxBus;




public class DefaultChromeClient extends WebChromeClient {

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        RxBus.get().post(Constants.EVENT_WEBVIEW_PROGRESS_REFRESH, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        RxBus.get().post(Constants.EVENT_SET_WEB_TITLE, title);
    }
}
