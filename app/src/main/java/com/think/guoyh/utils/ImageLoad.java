package com.think.guoyh.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Created by GUO_YH on 2019/9/19 10:36
 */
public class ImageLoad {
    public static void load(Context mContext, int res, ImageView img){
        Glide.with(mContext).load(res).transition(DrawableTransitionOptions.withCrossFade()).into(img);
    }
    public static void load(Context mContext, String path, ImageView img){
        Glide.with(mContext).load(path).transition(DrawableTransitionOptions.withCrossFade()).into(img);
    }
}
