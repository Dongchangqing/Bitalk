package com.duozhuan.bitalk.event;

import android.net.Uri;
import android.webkit.ValueCallback;

public class SelectImgEvent {
    public ValueCallback<Uri> uploadMsg;
    int type=0;


    public ValueCallback<Uri> getUploadMsg() {
        return uploadMsg;
    }
    public void setUploadMsg(ValueCallback<Uri> uploadMsg) {
        this.uploadMsg = uploadMsg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
