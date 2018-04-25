package com.duozhuan.bitalk.event;

import android.net.Uri;
import android.webkit.ValueCallback;

public class SelectImg6Event {
    public ValueCallback<Uri[]> uploadMsg;
    int type=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ValueCallback<Uri[]> getUploadMsg() {
        return uploadMsg;
    }

    public void setUploadMsg(ValueCallback<Uri[]> uploadMsg) {
        this.uploadMsg = uploadMsg;
    }
}
