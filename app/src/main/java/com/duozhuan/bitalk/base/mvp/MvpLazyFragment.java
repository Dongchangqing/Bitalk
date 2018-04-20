package com.duozhuan.bitalk.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duozhuan.bitalk.base.base.LazyFragment;
import com.duozhuan.bitalk.util.CommonUtils;

public abstract class MvpLazyFragment<T extends MvpPresenter> extends LazyFragment implements MvpView {
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        // 必须在 presenter 初始化之后才能调用父类的懒加载
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    protected abstract T initInject();


    @Override
    public void showLoading(String msg) {
        CommonUtils.showToastShort(msg);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        CommonUtils.showToastShort(errorMsg);
    }

    @Override
    public void hideError() {

    }


}