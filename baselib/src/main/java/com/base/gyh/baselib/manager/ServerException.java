package com.base.gyh.baselib.manager;

/**
 * Created by GUO_YH on 2018/11/30 14:53
 */
public class ServerException extends Exception {
    private int mCode;
    public ServerException(int code, String msg){
        super(msg);
        mCode = code;
    }
    public ServerException(int code, String msg, Throwable throwable){
        super(msg,throwable);
        mCode = code;
    }
    public int getmCode(){
        return mCode;
    }
}
