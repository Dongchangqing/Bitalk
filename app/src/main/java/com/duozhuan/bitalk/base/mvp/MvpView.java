package com.duozhuan.bitalk.base.mvp;

public interface MvpView {
    // 显示loading
    void showLoading(String msg);

    // 隐藏loading
    void hideLoading();

    // 显示错误页面
    void showError(String errorMsg);

    // 隐藏错误页面
    void hideError();

    // 是否销毁了
    boolean isDestroyed();
}