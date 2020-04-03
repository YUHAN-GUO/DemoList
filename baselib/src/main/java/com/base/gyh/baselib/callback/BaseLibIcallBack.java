package com.base.gyh.baselib.callback;

/**
 * Created by GUOYH on 2019/5/23.
 */
public interface BaseLibIcallBack<T> {
    void onSuccess( T t);
    void onFail(String msg);
}
