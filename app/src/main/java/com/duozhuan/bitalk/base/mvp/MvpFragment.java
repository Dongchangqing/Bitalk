package com.duozhuan.bitalk.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duozhuan.bitalk.base.base.BaseFragment;
import com.duozhuan.bitalk.util.CommonUtils;



public abstract class MvpFragment<T extends MvpPresenter> extends BaseFragment implements MvpView {

    protected T mPresenter;
    private boolean destroyed = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        destroyed = false;
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null)
            mPresenter.detachView();
        destroyed = true;
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

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

}
