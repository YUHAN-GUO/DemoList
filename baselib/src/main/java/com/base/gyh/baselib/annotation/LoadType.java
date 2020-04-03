package com.base.gyh.baselib.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by GUOYH on 2019/5/28.
 */

@IntDef({LoadType.FRIST,LoadType.REFRESH,LoadType.LOADMORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadType {
    int FRIST = 0x10;
    int REFRESH = 0x20;
    int LOADMORE = 0x40;
}