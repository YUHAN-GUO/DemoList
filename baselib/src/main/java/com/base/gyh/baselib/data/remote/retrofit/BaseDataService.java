
package com.base.gyh.baselib.data.remote.retrofit;

import com.base.gyh.baselib.BuildConfig;
import com.base.gyh.baselib.base.BaseApplication;
import com.base.gyh.baselib.data.remote.retrofit.cookie.CookieManager;
import com.base.gyh.baselib.utils.text.SecurityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * created by taofu on 2018/11/27
 **/
public abstract class BaseDataService {

    private static final long DEFAULT_TIMEOUT = 20000;

    private static volatile Object mRetrofitService;
    private static volatile Object mRetrofitService2;


    public static Object getService(Class zclas) {

        if (mRetrofitService == null) {
            synchronized (BaseDataService.class) {
                if (mRetrofitService == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                    if (BuildConfig.isDebug) {
                        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging).
                                    cookieJar(new CookieManager(BaseApplication.getContext()))
                            .addInterceptor(new HeaderInterceptor())
//                            .addInterceptor(new SaveCookieInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BuildConfig.ApiUrl)
                            .build();

                    mRetrofitService = retrofit.create(zclas);
                }
            }
        }
        return mRetrofitService;
    }

    public static Object getService(Class zclas,String baseUrl) {
        if (mRetrofitService2 == null) {
            synchronized (BaseDataService.class) {
                if (mRetrofitService2 == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    if (BuildConfig.isDebug) {
                        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging).
                                    cookieJar(new CookieManager(BaseApplication.getContext()))
                            .addInterceptor(new HeaderInterceptor())
//                            .addInterceptor(new SaveCookieInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(baseUrl)
                            .build();

                    mRetrofitService2 = retrofit.create(zclas);
                }
            }
        }
        return mRetrofitService2;
    }

    public static class  HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            String authKey = "Android";
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            Request.Builder builder = new Request.Builder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("UID", "16568");
            builder.addHeader("AUTHKEY", authKey);
            builder.addHeader("TIMESTAMP", timeStamp);
            builder.addHeader("SIGN", SecurityUtils.getInstance().MD5Decode(authKey + timeStamp).toUpperCase());
            return chain.proceed(builder.build());
        }
    }

}
/**
 * 使用：创建一个接口 WanAndroidService 是app下的 可以参考 package com.base.gyh.baselib.data.remote.retrofit;
 * 注意不是lib下的需要自己在app下创建
 * public class DataService {
 * <p>
 * public static WanAndroidService getService(){
 * return  (WanAndroidService) BaseDataService.getService(WanAndroidService.class);
 * }
 * }
 */
