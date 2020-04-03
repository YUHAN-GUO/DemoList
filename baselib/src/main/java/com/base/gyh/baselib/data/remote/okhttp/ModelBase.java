package com.base.gyh.baselib.data.remote.okhttp;


import android.text.TextUtils;
import android.util.Pair;

import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.NetWorkListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.OnDownloadListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultCall;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2019/8/1.
 * 统一的操作方式都在这，解析，loading
 */
public class ModelBase {
    //okhttp get请求
    public void sendOkHttpGet(final ParamsBuilder paramsBuilder, final ResultMyCall callBack) {
        Logger.d("%s++++++++++%s", "guoyh", "---------------------");
        String url = paramsBuilder.getUrl();
        if (url == null) {
            throw new NullPointerException("URl  不能为空");
        }
        OkHttpUtils.getInstance().get().url(url).headers(paramsBuilder.getHeads()).build().enqueue(callBack);
    }

    public void sendOkHttpPost(final ParamsBuilder paramsBuilder, final ResultMyCall callBack) {
        String url = paramsBuilder.getUrl();
        if (url == null) {
            throw new NullPointerException("URl  不能为空");
        }
        OkHttpUtils.getInstance().post().url(url).params(paramsBuilder.getParams()).headers(paramsBuilder.getHeads()).json(paramsBuilder.getJson()).build().enqueue(callBack);
    }

    public void sendOkHttpDownload(ParamsBuilder paramsBuilder, OnDownloadListener onDownloadListener) {
        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = onDownloadListener.getClass().toString();
        }
        OkHttpUtils.getInstance().download().url(paramsBuilder.getUrl())
                .tag(tag)
                .path(paramsBuilder.getPath())
                .fileName(paramsBuilder.getFileName())
                .resume(paramsBuilder.isResume())
                .build()
                .enqueue(onDownloadListener);
    }

    //同一key，多个file
    public void sendOkHttpUpload(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, String key, ArrayList<File> files) {
        Pair<String, File>[] arr = new Pair[files.size()];
        for (int i = 0; i < files.size(); i++) {
            arr[i] = new Pair<>(key, files.get(i));
        }
        sendOkHttpUpload(paramsBuilder, netWorkListener, arr);
    }


    //okhttp 上传文件;不同file不同key
    public void sendOkHttpUpload(final ParamsBuilder paramsBuilder, final NetWorkListener netWorkListener, Pair<String, File>... files) {

        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = netWorkListener.getClass().toString();
        }
        OkHttpUtils.upload()
                .url(paramsBuilder.getUrl())
                .tag(tag)
                .files(files)
                .params(paramsBuilder.getParams())
                //默认同时多次请求一个接口 只请求一次
                .onlyOneNet(paramsBuilder.isOnlyOneNet())
                //默认不重连
                .tryAgainCount(paramsBuilder.getTryAgainCount())
                .build()
                .enqueue(new ResultCall() {
                    @Override
                    public void onBefore() {
                        Logger.d("%s+++++++++++++++%s", "guoyh", "upLoadOnBefore");
                    }

                    @Override
                    public void onAfter() {
                        Logger.d("%s+++++++++++++++%s", "guoyh", "upLoadOnAfter");

                    }

                    @Override
                    public void onError(String message) {
//                        if (paramsBuilder.isOverrideError()) {
//                            NetFailBean errorBean = new NetFailBean(message);
//                            netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
//                        } else {
//                            // 不重写那么只弹提示
//                            ToastUtils.showToast(message);
//                        }
                        Logger.d("%s+++++++++++++++%s", "guoyh", "upLoadError" + message);

                    }

                    @Override
                    public void inProgress(float progress) {
                        int persent = (int) (progress * 100);
                        Logger.d("%s+++++++++++++++%s", "guoyh", "upLoadInProgress" + persent);

                    }

                    @Override
                    public void onSuccess(final String response) {
                        Logger.d("%s+++++++++++%s", "guoyh", response);
                    }
                });
    }

}
