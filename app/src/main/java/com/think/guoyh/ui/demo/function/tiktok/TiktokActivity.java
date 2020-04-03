package com.think.guoyh.ui.demo.function.tiktok;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowInsets;

import com.base.gyh.baselib.utils.GsonUtil;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.dueeeke.videoplayer.player.VideoView;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;
import com.think.guoyh.ui.demo.function.tiktok.adapter.TiktokActivityVpAdapter;
import com.think.guoyh.ui.demo.function.tiktok.cache.PreloadManager;
import com.think.guoyh.ui.demo.function.tiktok.controll.TikTokController;
import com.think.guoyh.utils.ResourceUtils;
import com.think.guoyh.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class TiktokActivity extends MyBaseActivity {
    /**
     * 当前播放位置
     */
    private int mCurPos;
    private List<TiktokBean> mVideoList = new ArrayList<>();
    private PreloadManager mPreloadManager;
    private TikTokController mController;
    private VideoView mVideoView;

    private VerticalViewPager tiktok_vp;
    private static final String KEY_INDEX = "index";
    private TiktokActivityVpAdapter mTiktokAdapter;

    public static void start(Context context, int index) {
        Intent i = new Intent(context, TiktokActivity.class);
        i.putExtra(KEY_INDEX, index);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiktok);
        initView();
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(this);
        addData();
        Intent extras = getIntent();
        int index = extras.getIntExtra(KEY_INDEX, 0);
        tiktok_vp.setCurrentItem(index);
        tiktok_vp.post(new Runnable() {
            @Override
            public void run() {
                startPlay(index);
            }
        });
    }

    private void startPlay(int position) {
        int count = tiktok_vp.getChildCount();
        for (int i = 0; i < count; i ++) {
            View itemView = tiktok_vp.getChildAt(i);
            TiktokActivityVpAdapter.ViewHolder viewHolder = (TiktokActivityVpAdapter.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                Utils.removeViewFormParent(mVideoView);
                TiktokBean tiktokBean = mVideoList.get(position);
                String playUrl = mPreloadManager.getPlayUrl(tiktokBean.getVideoDownloadUrl());
                Logger.dd("startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mController.addControlComponent(viewHolder.mTikTokView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;
                break;
            }
        }
    }

    public void addData() {
        String s = ResourceUtils.readAssets2String("tiktok_data.json");
        List<TiktokBean> tiktokList = GsonUtil.Json2Array(TiktokBean.class, s);
        mVideoList.addAll(tiktokList);
        mTiktokAdapter.notifyDataSetChanged();
    }

    private void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);
        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        mController = new TikTokController(this);
        mVideoView.setVideoController(mController);
    }

    private void initViewPager() {
        tiktok_vp.setOffscreenPageLimit(4);
        mTiktokAdapter = new TiktokActivityVpAdapter(mVideoList);
        tiktok_vp.setAdapter(mTiktokAdapter);
        tiktok_vp.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tiktok_vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) return;
                startPlay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = tiktok_vp.getCurrentItem();
                }

                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });
    }

    private void initView() {
        tiktok_vp = (VerticalViewPager) findViewById(R.id.tiktok_vp);
    }

    /**
     * 把状态栏设成透明
     */
    protected void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreloadManager.removeAllPreloadTask();
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
//        ProxyVideoCacheManager.clearAllCache(this);
    }
    @Override
    public boolean isShowStatus() {
        return false;
    }
}
