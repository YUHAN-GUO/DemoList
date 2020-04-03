package com.base.gyh.baselib.data.remote.retrofit;

import android.annotation.SuppressLint;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.bean.HttpResult;
import com.base.gyh.baselib.manager.ExceptionManager;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class HttpUtils {

    /**
     * @param mObservable
     * @param rxFragment
     * @param callBack
     * @param <T>
     */
    @SuppressLint("CheckResult")
    public static <T> void obserableUtils(Observable<HttpResult<T>> mObservable, RxFragment rxFragment, final IBaseHttpResultCallBack<T> callBack) {
        mObservable.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(HttpResult<T> tHttpResult) throws Exception {
                if (tHttpResult.getErrorCode() == 0) {
                    T bean = tHttpResult.getData();
                    if (bean == null) {
                        tHttpResult.setData((T) "成功");
                        return Observable.just(tHttpResult.getData());
                    }
                    return Observable.just(tHttpResult.getData());
//                if (bean != null){
//                    return Observable.just(tHttpResult.data);
//                }else{
//                    return Observable.error(ExceptionManager.buildServerErrorMessage(tHttpResult.errorCode, tHttpResult.errorMsg));
//                }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(tHttpResult.getErrorCode(), tHttpResult.getErrorMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.<T>bindUntilEvent(FragmentEvent.DETACH))//生命周期绑定 rxlife
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        callBack.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static  <T> void obserableUtils(Observable<HttpResult<T>> mObservable, RxAppCompatActivity rxActivity, IBaseHttpResultCallBack<T> callBack) {
        mObservable.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(HttpResult<T> tHttpResult) throws Exception {
                if (tHttpResult.getErrorCode() == 0) {
                    T bean = tHttpResult.getData();
                    if (bean == null) {
                        tHttpResult.setData((T) "成功");
                        return Observable.just(tHttpResult.getData());
                    }
                    return Observable.just(tHttpResult.getData());
//                if (bean != null){
//                    return Observable.just(tHttpResult.data);
//                }else{
//                    return Observable.error(ExceptionManager.buildServerErrorMessage(tHttpResult.errorCode, tHttpResult.errorMsg));
//                }
                }
                return Observable.error(ExceptionManager.buildServerErrorMessage(tHttpResult.getErrorCode(), tHttpResult.getErrorMsg()));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxActivity.<T>bindUntilEvent(ActivityEvent.DESTROY))//生命周期绑定 rxlife
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        callBack.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
