package com.base.gyh.baselib.data.remote.okhttp;

import android.content.Context;
import android.util.Pair;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.NetWorkListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.OnDownloadListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.dialog.pop.LoadingPop;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2019/7/31.
 */
public class ModelSuperImpl extends ModelBase {
    private static final String getUrl = "https://wanandroid.com/wxarticle/chapters/json";
    private static final String postUrl = "https://www.wanandroid.com/project/tree/json";
    public static final String QQ_APK = "https://imtt.dd.qq.com/16891/apk/06AB1F5B0A51BEFD859B2B0D6B9ED9D9.apk?fsname=com.tencent.mobileqq_8.1.0_1232.apk&csr=1bbd";
    public static final String UPLOAD_PIC = "https://uat-service.juranguanjia.com/api/file/upload";
    private static final ModelSuperImpl ourInstance = new ModelSuperImpl();
    private Context finalContext;
    public static ModelSuperImpl netWork() {
        return ourInstance;
    }


    //模拟搜索方法,外面参数过多，如果在多个页面都用到这个网络请求，把重复的写在里面
    public<T> void gankGet(ParamsBuilder paramsBuilder, Class<T> zClass, IBaseHttpResultCallBack<T> callBack) {
//        paramsBuilder.url(postUrl);
        sendOkHttpGet(paramsBuilder,getResultMyCall(paramsBuilder,callBack,zClass));
    }
    public void gankGet(ParamsBuilder paramsBuilder, IBaseHttpResultCallBack<String> callBack) {
        gankGet(paramsBuilder,null,callBack);
    }
    public<T> void gankPost(ParamsBuilder paramsBuilder, Class<T> zClass, IBaseHttpResultCallBack<T> callBack) {
//        paramsBuilder.url(postUrl);
        sendOkHttpPost(paramsBuilder,getResultMyCall(paramsBuilder,callBack,zClass));
    }
    public void gankPost(ParamsBuilder paramsBuilder, IBaseHttpResultCallBack<String> callBack) {
        gankPost(paramsBuilder,null,callBack);
    }



    public void downApk(ParamsBuilder paramsBuilder, OnDownloadListener onDownloadListener) {
//        paramsBuilder.url(QQ_APK);
        sendOkHttpDownload(paramsBuilder, onDownloadListener);
    }

    //不同file 不同key
    public void uploadPic(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, Pair<String, File>... files) {
        paramsBuilder.url(UPLOAD_PIC);
//                .type(new TypeToken<ResponModel<String>>() {
//                }.getType())

        sendOkHttpUpload(paramsBuilder, netWorkListener, files);
    }

    //同一key 不同file
    public void uploadPic(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, String key, ArrayList<File> files) {
        paramsBuilder.url(UPLOAD_PIC);
//                .type(new TypeToken<ResponModel<String>>() {
//                }.getType())

        sendOkHttpUpload(paramsBuilder, netWorkListener, key, files);
    }

    private<T> ResultMyCall getResultMyCall(ParamsBuilder paramsBuilder, IBaseHttpResultCallBack<T> callBack, Class<T> zClass){
        Context context = null;
        if (paramsBuilder.getActivity()!=null){
            context = paramsBuilder.getActivity();
        }else {
            if (paramsBuilder.getFragment()!=null){
                context = paramsBuilder.getFragment().getActivity();
            }
        }
        if (context == null){
            throw  new NullPointerException("context  不能为空");
        }
        finalContext = context;
        return new ResultMyCall() {
            @Override
            public void onBefore() {
                super.onBefore();

                Logger.d("%s+++++++++++%s","guoyuhan","onBefore");
                LoadingPop.showLoad(finalContext);
            }

            @Override

            public void onAfter() {
                super.onAfter();
                Logger.d("%s+++++++++++%s","guoyuhan","onAfter");
                LoadingPop.hideLoad(finalContext);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callBack.onError(e);
                Logger.d("%s+++++++++++++%s","guoyuhan",e.getMessage());
            }

            @Override
            public void onSuccess(Object response) {
                super.onSuccess(response);
               if (zClass==null){
                   callBack.onSuccess((T) response);
               }else{
                   Gson gson = new Gson();
                   T bean = gson.fromJson((String) response, zClass);
                   callBack.onSuccess(bean);
               }
//               Logger.d("%s+++++++++++%s","guoyuhan",response);
            }
        };
    }

}
