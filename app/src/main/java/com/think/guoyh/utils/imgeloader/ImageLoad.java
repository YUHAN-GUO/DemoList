package com.think.guoyh.utils.imgeloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.think.guoyh.R;


/**
 * Created by GUO_YH on 2019/9/19 10:36
 */
public class ImageLoad {
    public static void load(Context mContext, int res, ImageView img){
        Glide.with(mContext).load(res).transition(DrawableTransitionOptions.withCrossFade()).into(img);
    }
    public static void load(Context mContext, String path, ImageView img){
        loadImg(mContext, path, img);
//        MyGlideUtils.getInstance().loadImageViewLoding(mContext,path,img, R.drawable.icon_loading,R.drawable.icon_error);
    }
    public static void loadImg(Context mContext, String path, ImageView img){
        MyGlideUtils.getInstance().loadImageView(mContext,path,img);
    }

    public static void loadHeard(Context context, String url, ImageView img) {
        loadImg(context, url, img);

//        MyGlideUtils.getInstance().loadImageViewLoding(context,url,img, R.drawable.icon_heard,R.drawable.icon_heard);

    }
}
