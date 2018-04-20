package com.duozhuan.bitalk.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;



public abstract class LazyFragment extends BaseFragment {
    // 是否加载完成
    private boolean mIsInited;
    // view 是否已经准备好
    private boolean mIsPrepared;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepared = true;
        lazyLoad();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    public void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !mIsInited) {
            // 开始加载数据
            loadData();
            // 这里直接将 初始化完成置为 true 是有问题的
            mIsInited = true;
        }
    }

    protected abstract void loadData();

}
