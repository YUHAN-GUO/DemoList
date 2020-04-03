package com.base.gyh.baselib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.base.gyh.baselib.base.activity.StateActivity;
import com.base.gyh.baselib.utils.NetworkUtil;


/**
 * Created by GUOYH on 2019/4/29.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private static class InstanceHolder {
        private static final NetBroadcastReceiver INSTANCE = new NetBroadcastReceiver();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean netWorkState = NetworkUtil.isNetworkConnected(context);
            // 接口回调传过去状态的类型
            if (StateActivity.netEvent != null) {
                StateActivity.netEvent.onNetChange(netWorkState);
            }
        }
    }
    /**
     * 注册网络监听
     */
    public static void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    /**
     * 取消网络监听
     */
    public static void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }
    // 网络状态变化接口
    public interface NetChangeListener {
        void onNetChange(boolean netWorkState);
    }
}
