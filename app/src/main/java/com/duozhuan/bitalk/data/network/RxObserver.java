package com.duozhuan.bitalk.data.network;

import java.net.SocketTimeoutException;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.data.network.exception.ApiException;
import com.duozhuan.bitalk.data.network.okhttputils.NetworkUtil;
import com.duozhuan.bitalk.base.mvp.MvpView;
import com.duozhuan.bitalk.util.CommonUtils;
import io.reactivex.observers.DisposableObserver;


public abstract class RxObserver<T> extends DisposableObserver<T> {

    private MvpView mView;
    private String mMsg;
    private boolean isShowDialog;

    public RxObserver(MvpView view, String msg, boolean showDialog) {
        this.mView = view;
        this.mMsg = msg;
        this.isShowDialog = showDialog;
    }

    public RxObserver(MvpView view){
        this(view, CommonUtils.getString(R.string.api_loading), true);
    }

    public RxObserver(MvpView view, boolean showDialog){
        this(view, CommonUtils.getString(R.string.api_loading), showDialog);
    }

    @Override
    public void onStart() {
        if(mView == null || mView.isDestroyed()) return;
        if(isShowDialog) mView.showLoading(mMsg);
    }

    @Override
    public void onNext(T bean) {
        if(mView == null || mView.isDestroyed()) return;
        onSuccess(bean);
    }

    @Override
    public void onError(Throwable e) {
        if(mView == null || mView.isDestroyed()) return;
        ResultBean bean;
        if(!NetworkUtil.isConnected()){
            bean = new ResultBean(Constants.STATUS_DISCONNECT, CommonUtils.getString(R.string.api_net_disable));
        }else if(e instanceof ApiException){
            bean = ((ApiException) e).getBean();
        }else if(e instanceof SocketTimeoutException){
            bean = new ResultBean(Constants.STATUS_TIMEOUT, CommonUtils.getString(R.string.api_net_timeout));
        }else {
            bean = new ResultBean(Constants.STATUS_ERROR, CommonUtils.getString(R.string.api_net_error));
        }
        mView.showError(bean.getMsg());
        onFailed(bean);
    }

    /**
     * 成功回调方法
     */
    public abstract void onSuccess(T bean);

    /**
     * 失败回调方法
     */
    public void onFailed(ResultBean bean) {
        mView.showError(bean.getMsg());
    }

    @Override
    public void onComplete() {
        RxFlowable.disposable(this);
    }

}
