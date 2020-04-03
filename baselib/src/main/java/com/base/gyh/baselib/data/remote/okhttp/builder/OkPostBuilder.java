package com.base.gyh.baselib.data.remote.okhttp.builder;


import android.os.Handler;

import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leo
 * on 2019/7/23.
 * post builder
 */
public class OkPostBuilder {
    /**
     * 下面是解析参数，包括成功后 解析type
     */
    private int type = 0;
    private MediaType mediaType;
    private static final int TYPE_PARAMS = 1;
    private static final int TYPE_JSON = 2;
    private String url;
    /**
     * okHttpUtils里单例里唯一
     */
    private OkHttpClient okHttpClient;
    private Handler mDelivery;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String json;
    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 每次请求网络生成的请求request
     */
    private Request okHttpRequest;

    public OkPostBuilder() {
        this.okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        this.mDelivery = OkHttpUtils.getInstance().getmDelivery();
    }


    public OkPostBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }
    public OkPostBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    //判断参数方式只能是一个
    protected void validParams() {
        int count = 0;
        if (params != null) {
            type = TYPE_PARAMS;
            count++;
        }

        if (json != null) {
            type = TYPE_JSON;
            count++;
        }

        if (count <= 0 || count > 1) {
            throw new IllegalArgumentException("the params must has one and only one .");
        }
    }
    public OkPostBuilder build() {
        Request.Builder mBuilder = new Request.Builder();
        validParams();
        mBuilder.url(url);
        if (headers != null) {
            mBuilder.headers(appendHeaders(headers));
        }
        RequestBody requestBody = null;
        switch (type) {
            case TYPE_PARAMS:
                FormBody.Builder formBody = new FormBody.Builder();

                addParams(formBody, params);
                requestBody = formBody.build();
                break;
            case TYPE_JSON:
                Logger.d("%s+++++++++++%s", "guoyh  json ==> " + json);

                requestBody = RequestBody.create(mediaType != null ? mediaType : MEDIA_TYPE_JSON, json);
                break;
        }
        //这里的.post是区分get请求的关键步骤
        mBuilder.post(requestBody);

        okHttpRequest = mBuilder.build();
//        //遍历Map集合
//        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
//
//        if (params != null) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                Logger.d("%s+++++++++++++%s", "guoyuhan", entry.getKey() + "-----------" + entry.getValue());
//                form.add(entry.getKey(), entry.getValue());
//            }
//        }
//        RequestBody body = form.build();
//        okHttpRequest = new Request.Builder().url(url).post(body).build();//采用post提交数据
        return this;
    }

    public OkPostBuilder url(String url) {
        this.url = url;
        return this;
    }
    public OkPostBuilder json(String json) {
        this.json = json;
        return this;
    }


    //拼接头部参数
    public Headers appendHeaders(Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return null;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        return headerBuilder.build();
    }

    //键值对拼接的参数
    private void addParams(FormBody.Builder builder, Map<String, String> params) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be null .");
        }

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
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
        if (okHttpRequest != null) {
            Logger.d("%s++++++++++%s", "guoyuhan", "----------");
            okHttpClient.newCall(okHttpRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mDelivery.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resultMyCall.onError(e);
                        }
                    }, 50);
                    Logger.d("%s+++++++++++++%s", "guoyuhan", e.getMessage());
                    resultMyCall.onAfter();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response != null) {
                        String result = response.body().string();
                        Logger.d("%s+++++++++++++%s", "guoyuhan", result);
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                resultMyCall.onSuccess(result);
                            }
                        });
                        mDelivery.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultMyCall.onAfter();
                            }
                        }, 50);

                    }
                }
            });
        }
    }
}
