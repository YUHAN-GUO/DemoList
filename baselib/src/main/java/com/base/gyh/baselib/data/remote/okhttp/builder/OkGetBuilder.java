package com.base.gyh.baselib.data.remote.okhttp.builder;

import android.os.Handler;
import android.text.TextUtils;

import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leo
 * on 2019/7/22.
 * get builder
 */
public class OkGetBuilder {
    /**
     * 下面是解析参数，包括成功后 解析type
     */
    private String url;
    /**
     * okHttpUtils里单例里唯一
     */
    private OkHttpClient okHttpClient;
    private Handler mDelivery;
    private Map<String, String> headers;
    private Map<String, String> params;

    private boolean onlyOneNet;
    private String tag;

    /**
     * 每次请求网络生成的请求request
     */
    private Request okHttpRequest;

    public OkGetBuilder() {
        this.okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        this.mDelivery = OkHttpUtils.getInstance().getmDelivery();
    }

    public OkGetBuilder build() {
        //头部的builder
//        okHttpRequest = new Request.Builder().url(appendParams(url, params)).tag(tag).headers(appendHeaders(headers)).build();
        Request.Builder mBuilder = new Request.Builder();
        if (params != null) {
            mBuilder.url(appendParams(url, params));
        } else {
            Logger.d("%s++++++++++++%s", "guoyh 请求接口 ==>> " + url);
            mBuilder.url(url);
        }

        if (!TextUtils.isEmpty(tag)) {
            mBuilder.tag(tag);
        }
        if (headers != null) {
            mBuilder.headers(appendHeaders(headers));
        }
        okHttpRequest = mBuilder.build();
//        okHttpRequest = new Request.Builder()
//                .url(url)
//                .build();
        return this;
    }

    public OkGetBuilder url(String url) {
        this.url = url;
        return this;
    }


    public void enqueue(final ResultMyCall resultMyCall) {
        if (resultMyCall == null) {
            return;
        }
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                resultMyCall.onBefore();
            }
        });
        okHttpClient.newCall(okHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDelivery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resultMyCall.onError(e);
                        resultMyCall.onAfter();

                    }
                }, 50);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
//                Logger.d("%s+++++++++++++%s", "guoyh", result);
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        resultMyCall.onSuccess(result);
                        resultMyCall.onAfter();
                    }
                });
            }
        });
    }
    public OkGetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }
    public OkGetBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    //get 参数拼在url后面
    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (url.indexOf("?") == -1) {
            sb.append(url + "?");
        } else {
            sb.append(url + "&");
        }

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        Logger.d("%s++++++++++%s", "guoyh", sb.toString());
        return sb.toString();
    }

    private Headers appendHeaders(Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return null;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        return headerBuilder.build();
    }
}
