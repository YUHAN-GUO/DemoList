package com.base.gyh.baselib.utils.imgeloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Created by GUO_YH on 2019/9/15 18:06
 */
public class MyGlideUtils {
    private static MyGlideUtils mInstance;
    private MyGlideUtils(){}
    public static MyGlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (MyGlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new MyGlideUtils();
                }
            }
        }
        return mInstance;
    }

    public void loadToView(Context mContext,String url,ImageView img){
    }


    //默认加载
    public static    void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).transition(DrawableTransitionOptions.withCrossFade()).into(mImageView);
    }
    //默认加载
    public   void loadImageView(Context mContext, int res, ImageView mImageView) {
        Glide.with(mContext).load(res).transition(DrawableTransitionOptions.withCrossFade()).into(mImageView);
    }
    //加载指定大小
    public   void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext).load(path).override(width, height).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public   void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public   void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置跳过内存缓存
    public   void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    //设置下载优先级
    public   void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public   void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public   void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public   void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public   void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).centerCrop().into(mImageView);
    }

    //设置动态GIF加载方式
    public   void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).asGif().load(path).into(mImageView);
    }

    //设置静态GIF加载方式
    public   void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).asBitmap().load(path).into(mImageView);
    }


    //清理磁盘缓存
    public   void guideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public   void guideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}
