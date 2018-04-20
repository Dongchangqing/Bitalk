package com.duozhuan.bitalk.event;

public class UserInfoEvent {
    public String name;
    public String token;

    public UserInfoEvent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
