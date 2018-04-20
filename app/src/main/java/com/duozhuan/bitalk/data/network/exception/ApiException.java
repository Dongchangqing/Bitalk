package com.duozhuan.bitalk.data.network.exception;


import com.duozhuan.bitalk.data.network.ResultBean;



public class ApiException extends Exception {
    private ResultBean bean;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(ResultBean bean) {
        super(bean.getMsg());
        this.bean = bean;
    }

    public ResultBean getBean() {
        return bean;
    }

    public void setBean(ResultBean bean) {
        this.bean = bean;
    }

}
