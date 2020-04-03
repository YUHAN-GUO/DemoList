package com.think.guoyh.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.base.gyh.baselib.utils.Utils;

/**
 * Created by GUO_YH on 2019/9/12 21:15
 */
public class MyViewUtil {
    private MyViewUtil(){}
    private static MyViewUtil mInstance;
    public static MyViewUtil getInstance(){
        if (mInstance==null){
            synchronized (MyViewUtil.class){
                if (mInstance==null){
                    mInstance = new MyViewUtil();
                }
            }
        }
        return mInstance;
    }
    public View getView(Context context, int layout){
        return  LayoutInflater.from(context).inflate(layout,null);
    }
    public View getView(int layout){
        return getView(Utils.getApp(),layout);
    }

    public int dp2px(float dp) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
