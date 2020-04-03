package com.base.gyh.baselib.data.remote.okhttp;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.base.gyh.baselib.data.remote.okhttp.Interceptor.NetCacheInterceptor;
import com.base.gyh.baselib.data.remote.okhttp.Interceptor.OfflineCacheInterceptor;
import com.base.gyh.baselib.data.remote.okhttp.builder.OkDownloadBuilder;
import com.base.gyh.baselib.data.remote.okhttp.builder.OkGetBuilder;
import com.base.gyh.baselib.data.remote.okhttp.builder.OkPostBuilder;
import com.base.gyh.baselib.data.remote.okhttp.builder.OkUploadBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

public class OkHttpUtils {

    //OkHttp 请求
    private final OkHttpClient okHttpClient;
    //单例做一些初始化
    private static volatile OkHttpUtils mOkHttpUtils;
    //用于线程的切换
    private Handler mDelivery;
    private ArrayList<String> onesTag;

    private OkHttpUtils() {
        onesTag = new ArrayList<>();
        mDelivery = new Handler(Looper.getMainLooper());
        //设置缓存文件路径，和文件大小
        okHttpClient = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), 50 * 1024 * 1024))
                .hostnameVerifier(new HostnameVerifier() {//证书信任
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                //这里是网上对cookie的封装 github : https://github.com/franmontiel/PersistentCookieJar
                //如果你的项目没有遇到cookie管理或者你想通过网络拦截自己存储，那么可以删除persistentcookiejar包
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext())))
                .addInterceptor(OfflineCacheInterceptor.getInstance())
                .addNetworkInterceptor(NetCacheInterceptor.getInstance())
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (mOkHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttpUtils == null) {
                    mOkHttpUtils = new OkHttpUtils();
                }
            }
        }
        return mOkHttpUtils;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Handler getmDelivery() {
        return mDelivery;
    }
    public ArrayList<String> getOnesTag() {
        return onesTag;
    }
    public OkGetBuilder get() {
        return new OkGetBuilder();
    }
    public OkPostBuilder post(){
        return new OkPostBuilder();
    }
    public OkDownloadBuilder download(){
        return new OkDownloadBuilder();
    }
    public static OkUploadBuilder upload(){
        return  new OkUploadBuilder();
    }


    //tag取消网络请求
    public void cancleOkhttpTag(String tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            //请求列表里的，取消网络请求
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            //正在请求网络的，取消网络请求
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
