package com.duozhuan.bitalk.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/1/28.
 */

public class LoginBean {

    //用户名
    @SerializedName("username")
    private String username;

    //用户ID
    @SerializedName("id")
    private String id;

    //仪器型号
    @SerializedName("realname")
    private String realname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
