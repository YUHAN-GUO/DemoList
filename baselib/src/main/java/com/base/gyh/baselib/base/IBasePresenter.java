package com.base.gyh.baselib.base;

/*
 * created by taofu on 2018/11/28
 **/
public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView();
}
