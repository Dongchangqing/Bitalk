package com.duozhuan.bitalk.event;

public class JumpUrlEvent {
    public String url;
    public boolean isOpenNew;

    public JumpUrlEvent() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getOpenNew() {
        return isOpenNew;
    }

    public void setOpenNew(boolean openNew) {
        isOpenNew = openNew;
    }
}
