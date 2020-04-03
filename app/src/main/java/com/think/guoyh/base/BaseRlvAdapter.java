package com.think.guoyh.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.utils.ImageLoad;

import java.util.List;


public abstract class BaseRlvAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseRlvAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public void loadImage(String url, ImageView img){

        ImageLoad.load(mContext,url,img);
    }
    public void loadImage(int url, ImageView img){
        ImageLoad.load(mContext,url,img);
    }
    protected void show(View view){
        view.setVisibility(View.VISIBLE);
    }
    protected void hide(View view){
        view.setVisibility(View.GONE);
    }
}
