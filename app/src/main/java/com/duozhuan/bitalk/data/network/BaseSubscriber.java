package com.duozhuan.bitalk.data.network;

import android.app.Dialog;
import android.content.Context;

import java.lang.ref.WeakReference;

import com.duozhuan.bitalk.data.network.exception.ApiExceptionHandler;
import io.reactivex.subscribers.DisposableSubscriber;



public abstract class BaseSubscriber<T> extends DisposableSubscriber<ResultBean<T>> {
    private WeakReference<Context> mContext;
    private Dialog mDialog;

    public BaseSubscriber(Context context, Dialog dialog) {
        this.mContext = new WeakReference<>(context);
        this.mDialog = dialog;
    }

    protected void onStart() {
        super.onStart();

        if (mDialog!=null)
            mDialog.show();

    }

    @Override
    public void onComplete() {
        if (mDialog!=null && mDialog.isShowing())
            mDialog.dismiss();
        dispose();
    }

    @Override
    public void onNext(ResultBean<T> t) {
        if (mDialog!=null && mDialog.isShowing())
            mDialog.dismiss();

//        if (t.code==0) {
//            onRequestSuccess(t);
//        }else {
//            if (!ApiExceptionHandler.handlerApiException(mContext.get(), new ApiException(t.code, t.message))){
//                onResponseError(t);
//            }
//        }
    }

    @Override
    public void onError(Throwable t) {
        if (mDialog!=null && mDialog.isShowing())
            mDialog.dismiss();

        ApiExceptionHandler.handlerApiException(mContext.get(), t);
        dispose();
    }

    public abstract void onRequestSuccess(ResultBean<T> t);
    public abstract void onResponseError(ResultBean<T> t);
}
