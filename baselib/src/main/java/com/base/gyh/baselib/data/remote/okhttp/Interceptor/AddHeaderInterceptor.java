package com.base.gyh.baselib.data.remote.okhttp.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by Zy on 2019/1/14
 * 添加拦截器用来添加请求头
 */
public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        return chain.proceed(builder.build());
    }
}
