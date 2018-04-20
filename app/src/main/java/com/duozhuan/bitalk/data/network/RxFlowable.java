package com.duozhuan.bitalk.data.network;

import org.reactivestreams.Publisher;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.data.network.exception.ApiException;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class RxFlowable {

    public static <T> FlowableTransformer<ResultBean<T>, T> handleResult() {
        return new FlowableTransformer<ResultBean<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<ResultBean<T>> upstream) {
                return upstream.flatMap(new Function<ResultBean<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(ResultBean<T> tResultBean) throws Exception {
                        if (tResultBean.getStatus() == Constants.STATUS_SUCCESS) {
                            return createData(tResultBean.getData());
                        } else {
                            return Flowable.error(new ApiException(tResultBean));
                        }
                    }
                });
            }
        };
    }

    private static <T> Flowable<T> createData(final T data) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static boolean isNotDispose(Disposable observer) {
        return observer != null && !observer.isDisposed();
    }

    public static void disposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void disposable(DisposableObserver observer) {
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }

}
