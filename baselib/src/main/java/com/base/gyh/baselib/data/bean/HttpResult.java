package com.base.gyh.baselib.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class HttpResult<T> {
    /**
     * SerializedName  做的一个转换，将返回的json 中的字段 名称做了一个转换，
     */
//    @SerializedName("data")//括号内是 json 所对应的字段名称，
    private T data;//转换后的名字

//    @SerializedName("errorCode")
    private int errorCode;

//    @SerializedName("errorMsg")
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
