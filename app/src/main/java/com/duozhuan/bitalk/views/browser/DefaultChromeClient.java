package com.duozhuan.bitalk.views.browser;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.event.SelectImg6Event;
import com.duozhuan.bitalk.event.SelectImgEvent;
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


    //扩展浏览器上传文件
    //3.0++版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        SelectImgEvent event=new SelectImgEvent();
        event.setUploadMsg(uploadMsg);
        RxBus.get().post(Constants.EVENT_SELECT_IMAGE5,event);
    }

    //3.0--版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        SelectImgEvent event=new SelectImgEvent();
        event.setUploadMsg(uploadMsg);
        RxBus.get().post(Constants.EVENT_SELECT_IMAGE5,event);

    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        SelectImgEvent event=new SelectImgEvent();
        event.setUploadMsg(uploadMsg);
        RxBus.get().post(Constants.EVENT_SELECT_IMAGE5,event);
    }

    // For Android > 5.0
    public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
        SelectImg6Event event=new SelectImg6Event();
        event.setUploadMsg(uploadMsg);
        RxBus.get().post(Constants.EVENT_SELECT_IMAGE6,event);
        return true;
    }


}
