package com.duozhuan.bitalk.data.network;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;



public class RxPresenter{

    protected CompositeDisposable mCompositeDisposable;

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


}
