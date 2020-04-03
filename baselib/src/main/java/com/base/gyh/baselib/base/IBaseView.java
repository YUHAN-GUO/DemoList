package com.base.gyh.baselib.base;

import android.content.Context;

/*
 * created by taofu on 2018/11/28
 **/
public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);
    Context getContextObject();
}
