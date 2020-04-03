package com.base.gyh.baselib.manager;

import android.content.Context;

import com.base.gyh.baselib.R;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class ExceptionManager {
    public static  Throwable buildServerErrorMessage(int code,String msg){
        return new ServerException(code,msg);
    }
    public static String getMsgFromThrowable(Context context, Throwable throwable){
        if (throwable instanceof ServerException){
            return throwable.getMessage();
        }else{
            return context.getString(R.string.text_loading_error);
        }
    }
}
