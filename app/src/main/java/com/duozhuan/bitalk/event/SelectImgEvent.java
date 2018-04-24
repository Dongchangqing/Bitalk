package com.duozhuan.bitalk.event;

import android.net.Uri;
import android.webkit.ValueCallback;

public class SelectImgEvent {
    public ValueCallback<Uri> uploadMsg;

    public ValueCallback<Uri> getUploadMsg() {
        return uploadMsg;
    }

    public void setUploadMsg(ValueCallback<Uri> uploadMsg) {
        this.uploadMsg = uploadMsg;
    }
}
