package com.duozhuan.bitalk.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 网络请求 最外层的Bean
 * <p>
 * Created by 徐极凯 on 2018/1/3.
 */

public class ResultBean<T> {
    @SerializedName("state")
    private int status;//状态码
    @SerializedName("data")
    private T data;//返回成功数据
    @SerializedName("result")
    private String msg;//错误提示信息

    public ResultBean() {
    }

    public ResultBean(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
