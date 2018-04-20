package com.duozhuan.bitalk.data.network;

import com.duozhuan.bitalk.R;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.data.network.exception.ApiException;
import com.duozhuan.bitalk.data.network.okhttputils.NetworkUtil;
import com.duozhuan.bitalk.util.CommonUtils;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.DisposableSubscriber;

public abstract class RxSimpleSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T bean) {
        onSuccess(bean);
    }

    @Override
    public void onError(Throwable e) {
        if (!NetworkUtil.isConnected()) {
            onFailed(new ResultBean(Constants.STATUS_DISCONNECT, CommonUtils.getString(R.string.api_net_disable)));
        } else if (e instanceof ApiException) {
            ResultBean bean = ((ApiException) e).getBean();
            onFailed(bean);
        } else if (e instanceof SocketTimeoutException) {
            onFailed(new ResultBean(Constants.STATUS_TIMEOUT, CommonUtils.getString(R.string.api_net_timeout)));
        } else {
            onFailed(new ResultBean(Constants.STATUS_ERROR, CommonUtils.getString(R.string.api_net_error)));
        }
        e.printStackTrace();
    }

    /**
     * 成功回调方法
     */
    public abstract void onSuccess(T bean);

    /**
     * 失败回调方法
     */
    public void onFailed(ResultBean bean) {
        CommonUtils.showToastShort(bean.getMsg());
    }

    @Override
    public void onComplete() {
        RxFlowable.disposable(this);
    }

}