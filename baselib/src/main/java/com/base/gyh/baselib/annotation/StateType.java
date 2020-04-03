package com.base.gyh.baselib.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by GUO_YH on 2019/8/16 19:15
 */
@IntDef(value = {StateType.EMPTY, StateType.ERROR, StateType.LOADING, StateType.NORMAL})
@Retention(RetentionPolicy.SOURCE)
public  @interface StateType {
    int NORMAL = 0x100;//数据内容显示正常
    int EMPTY = 0x200;//数据内容显示正常
    int ERROR = 0x400;//数据加载失败
    int LOADING = 0x800;//数据加载中
}
