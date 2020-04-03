package com.think.guoyh.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.base.gyh.baselib.base.BaseApplication;
import com.base.gyh.baselib.utils.CrashHandler;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mmkv.MMKV;
import com.think.guoyh.BuildConfig;
import com.think.guoyh.utils.MyUtils;
import com.think.guoyh.utils.text.SpUtil;

import org.litepal.LitePal;

/**
 * Created by GUO_YH on 2019/9/18 09:53
 */
public class MyApp extends BaseApplication {

    //        //设置全局默认配置（优先级最低，会被其他设置覆盖）
//        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
//            @Override
//            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
//                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
//                layout.setReboundDuration(1000);
//                layout.setReboundInterpolator(new DropBounceInterpolator());
//                layout.setFooterHeight(100);
//                layout.setDisableContentWhenLoading(false);
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
//            }
//        });
//
//        //全局设置默认的 Header
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
//                layout.setEnableHeaderTranslationContent(false);
//                return new MaterialHeader(context);
//                //.setColorSchemeResources(R.color.blue,R.color.black,R.color.bar_grey)
//            }
//        });
//    }
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setDrawableSize(20);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        MMKV.initialize(this);
        LitePal.initialize(this);
        SpUtil.init(getContext());

        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(com.dueeeke.videoplayer.BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ThunderMediaPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());
//        TooMeeManager.init(this, "", "", "", new TooMeeImageLoader() {
//            @Override
//            public void loadImage(String path, ImageView imageView) {
//                 Logger.d("%s++++++++++++%s","guoyh",path);
//            }
//
//            @Override
//            public void loadImage(int imageDrawable, ImageView imageView, int errorView) {
//
//            }
//        });
        Logger.d("guoyh2我试试看吧当前是什么+++++++++++++++%s", MyUtils.getInstance().getChannel() + "=========");
        if (!BuildConfig.DEBUG) {
//        if (true) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }
}
