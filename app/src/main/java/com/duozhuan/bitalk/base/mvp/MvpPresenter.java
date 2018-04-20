package com.duozhuan.bitalk.base.mvp;

public interface MvpPresenter<T extends MvpView> {
    void attachView(T view);
    void detachView();
}