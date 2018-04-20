package com.duozhuan.bitalk.event;

public class UserExitEvent {
    private String userName;
    private boolean isClearUserInfo;

    public UserExitEvent() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isClearUserInfo() {
        return isClearUserInfo;
    }

    public void setClearUserInfo(boolean clearUserInfo) {
        isClearUserInfo = clearUserInfo;
    }
}
