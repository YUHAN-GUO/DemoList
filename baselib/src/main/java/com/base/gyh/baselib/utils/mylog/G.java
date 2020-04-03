package com.base.gyh.baselib.utils.mylog;

/**
 * Created by GUOYH on 2019/4/29.
 */
public class G {
    private static String TAG = "guoyh";
    public  static void d(String args){
        Logger.d("%s++++++++%s",TAG,args);
    }
    public static void v(String args){
        Logger.v("%s++++++++%s",TAG,args);
    }
    public static void e(String args){
        Logger.e("%s++++++++%s",TAG,args);
    }
}
