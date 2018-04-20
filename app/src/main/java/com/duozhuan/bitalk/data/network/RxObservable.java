package com.duozhuan.bitalk.data.network;

import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.data.network.exception.ApiException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



public class RxObservable {

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<ResultBean<T>, T> handleResult() {
        return new ObservableTransformer<ResultBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResultBean<T>> baseModelObservable) {
                return baseModelObservable.flatMap(new Function<ResultBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(ResultBean<T> tBaseModel) throws Exception {
                        if (tBaseModel.getStatus() == Constants.STATUS_SUCCESS) {
                            return createData(tBaseModel.getData());
                        } else {
                            return Observable.error(new ApiException(tBaseModel));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

}
