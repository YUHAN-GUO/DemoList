package com.base.gyh.baselib.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

/**
 * Created by GUO_YH on 2019/9/20 09:42
 */
public class RlvMangerUtil {
    private RlvMangerUtil(){}
    private static RlvMangerUtil newInstance;
    public static RlvMangerUtil getInstance(){
        if (newInstance == null){
            synchronized (RlvMangerUtil.class){
                if (newInstance==null){
                    return new RlvMangerUtil();
                }
            }
        }
        return newInstance;
    }

    public LinearLayoutManager getLinearLayoutManager(Context context){
        return  new LinearLayoutManager(context);
    }
    public LinearLayoutManager getHorLinearLayoutManager(Context context){
        LinearLayoutManager linearLayoutManager = getLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }
    public GridLayoutManager getGridLayoutManager(Context context,int spanCount){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        return  gridLayoutManager;
    }

    public FlexboxLayoutManager getFlexboxLayoutManager(Context context){
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
        return flexboxLayoutManager;
    }

}
