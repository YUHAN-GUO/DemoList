package com.base.gyh.baselib.base;

import android.app.Application;
import android.content.Context;

import com.base.gyh.baselib.utils.Utils;
import com.jeremyliao.liveeventbus.LiveEventBus;


/**
 * Created by GUOYH on 2019/5/5.
 */
public class BaseApplication extends Application {

    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //
        Utils.init(this);
        //LiveEvent配置
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
    }
}
