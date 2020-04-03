package com.base.gyh.baselib.data.remote.okhttp.okcallback;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.google.gson.internal.$Gson$Types;
import com.just.agentweb.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by leo
 * on 2019/7/22.
 * 不用于封装，带泛型解析
 */
public abstract class ResultMyCall {

    //请求网络之前，一般展示loading
    public void onBefore() {
        Logger.i("guoyh+++++封装有点起色了啊++++++%s", "没有重写这个方法我会打印哦...");
    }

    //请求网络结束，消失loading
    public void onAfter() {

    }

    //监听上传图片的进度(目前支持图片上传,其他重写这个方法无效)
    public void inProgress(float progress) {

    }


    //错误信息
    public void onError(Throwable e) {
        Logger.i("封装网络请求", "请求失败 ===>>+%s " + e.getMessage());
//        ToastUtils.showToast(errorMessage);
    }

    public void onSuccess(Object response) {
        LogUtils.i("封装网络请求", "请求成功 ===>> ");
    }


    public Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return null;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Type getType() {
        return getSuperclassTypeParameter(getClass());
    }


}
