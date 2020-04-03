package com.think.guoyh.ui.demo.function.textread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;
import com.think.guoyh.data.bean.text.TextArticleBean;
import com.think.guoyh.data.module.TextModule;
import com.think.guoyh.data.module.db.BookChapterBean;
import com.think.guoyh.data.module.db.ChapterInfoBean;
import com.think.guoyh.data.module.db.CollBookBean;
import com.think.guoyh.data.module.db.dbManage.BookRepository;
import com.think.guoyh.ui.demo.function.textread.adapter.TextReadRlvAdapter;
import com.think.guoyh.utils.SystemBarUtils;
import com.think.guoyh.utils.text.BrightnessUtils;
import com.think.guoyh.widget.page.PageLoader;
import com.think.guoyh.widget.page.PageView;
import com.think.guoyh.widget.page.ReadSettingManager;
import com.think.guoyh.widget.page.TxtChapter;
import com.think.guoyh.widget.pop.bottom.TextSetPop;
import com.think.guoyh.widget.pop.center.TextCachePop;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.LAYER_TYPE_SOFTWARE;

public class TextReadActivity extends MyBaseActivity implements View.OnClickListener {
    String tag = "tag123";
    private static String COLL_BOOK = "COLL_BOOK";
    private static String COLLECT_BOOK = "COLLECT_BOOK";
    private boolean isFullScreen = false;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private boolean isCollected;
    private String mBookId;
    private RecyclerView read_bookRlv;
    private ConstraintLayout read_bookLayout;
    private DrawerLayout read_bookDraw;
    private TextView read_bookMulu;
    private TextView read_bookLiang;
    private TextView read_bookHuan;
    private TextView read_bookSet;
    private LinearLayout read_bookBottom;
    private LinearLayout read_bookLightLayout;
    private ConstraintLayout read_bookTop;
    private ImageView read_bookBack;
    private ImageView read_bookMenu_light_img;
    private TextView read_bookTitle;
    private PageView read_bookPage;
    private SeekBar read_bookMenu_light_SeekBar;
    private CollBookBean mCollBook;
    private PageLoader mPageLoader;
    //当前章节
    private int currentChapter;
    private TextReadRlvAdapter muluAdapter;
    private ReadSettingManager readSettingManager;
    private boolean isNightMode;

    public static void toReadText(Activity activity, CollBookBean collBookBean, Boolean isCollected) {
        Intent intent = new Intent(activity, TextReadActivity.class);
        intent.putExtra(COLL_BOOK, collBookBean);
        intent.putExtra(COLLECT_BOOK, isCollected);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_read);
        getIntentData();
        initView();
        initAdapter();
        setListener();
        initPage();
        setPageLoad();
    }

    private void initAdapter() {
        initMuAdapter();
    }

    private void initMuAdapter() {
        if (muluAdapter == null) {
            muluAdapter = new TextReadRlvAdapter(null, mBookId);
            read_bookRlv.setAdapter(muluAdapter);
            read_bookRlv.setLayoutManager(getLinearLayoutManger());
            muluAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    toggleDrawer(false);
                    mPageLoader.skipToChapter(position);
                }
            });
        }
    }


    private void initPage() {
        //注册广播
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            read_bookPage.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        mPageLoader = read_bookPage.getPageLoader(mCollBook);
        initReceiver();
        mPageLoader.refreshChapterList();
        getMulu();
    }

    //电量监听
    private void initReceiver() {
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            } else if (intent.getAction() == Intent.ACTION_TIME_TICK) {
                mPageLoader.updateTime();
            }// 监听分钟的变化
        }

    };

    private void setPageLoad() {
        mPageLoader.setOnPageChangeListener(new PageLoader.OnPageChangeListener() {
            @Override
            public void onChapterChange(int pos) {
                Logger.dd(pos);
                List<TextArticleBean.DataBean.ArticleBean> data = muluAdapter.getData();
//                TxtChapter mCurrentChapter = new TxtChapter();
                muluAdapter.setChapter(pos);
                currentChapter = pos;

//                Logger.d("%s+++++onChapterChange++++%s", tag, pos);
            }

            @Override
            public void requestChapters(List<TxtChapter> requestChapters) {
                Logger.d("%s+++++requestChapters++++%s", tag, mBookId + "--------" + requestChapters.size());
                getNetPage(requestChapters);
            }

            @Override
            public void onCategoryFinish(List<TxtChapter> chapters) {
//                Logger.d("%s+++++onCategoryFinish++++%s", tag, "--------");

            }

            @Override
            public void onPageCountChange(int count) {

            }

            @Override
            public void onPageChange(int pos) {

            }
        });
    }

    //获取内容
    private void getNetPage(List<TxtChapter> requestChapters) {
        TextModule.getModule().getBookArticleDetail(mBookId, requestChapters, TextReadActivity.this, new IBaseHttpResultCallBack<ChapterInfoBean>() {
            @Override
            public void onSuccess(ChapterInfoBean data) {
                if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
                    mPageLoader.openChapter();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    //获取目录
    private void getMulu() {
        TextModule.getModule().getBookArticle(mBookId, TextReadActivity.this, new IBaseHttpResultCallBack<TextArticleBean>() {
            @Override
            public void onSuccess(TextArticleBean data) {
                if (data.getCode() == 1) {
                    List<BookChapterBean> zhuangList = new ArrayList<>();
                    for (int i = 0; i < data.getData().getArticle().size(); i++) {
                        String id = data.getData().getArticle().get(i).getId() + "";
                        String title = data.getData().getArticle().get(i).getTitle();
                        zhuangList.add(new BookChapterBean(id, title));
                    }
                    mPageLoader.getCollBook().setBookChapters(zhuangList);
                    mPageLoader.refreshChapterList();
                    if (mCollBook.isUpdate() && isCollected) {
                        BookRepository.getInstance().saveBookChaptersWithAsync(zhuangList, mCollBook);
                    }
                    initMuluRlv(data);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void getIntentData() {
        mCollBook = (CollBookBean) getIntent().getSerializableExtra(COLL_BOOK);
        boolean isCollected = getIntent().getBooleanExtra(COLLECT_BOOK, false);
        Logger.dd(isCollected);
        mBookId = mCollBook.getId();
        readSettingManager = ReadSettingManager.getInstance();
        isFullScreen = readSettingManager.isFullScreen();
        isNightMode = readSettingManager.isNightMode();

    }

    @Override
    public boolean isShowStatus() {
        return false;
    }


    private void initMuluRlv(TextArticleBean data) {
        initMuAdapter();
        muluAdapter.setNewData(data.getData().getArticle());
    }


    /*******************--------菜单的隐藏和显示--------**********************/
    private boolean hideReadMenu() {
        if (read_bookBottom.getVisibility() == View.VISIBLE) {
            toggleMenu();
            return true;
        } else {
            return false;
        }
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }


    private void initView() {
        read_bookRlv = (RecyclerView) findViewById(R.id.read_bookRlv);
        read_bookLayout = (ConstraintLayout) findViewById(R.id.read_bookLayout);
        read_bookDraw = (DrawerLayout) findViewById(R.id.read_bookDraw);
        //禁止滑动展示DrawerLayout
        read_bookDraw.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        read_bookDraw.setFocusableInTouchMode(false);
        read_bookMulu = (TextView) findViewById(R.id.read_bookMulu);
        read_bookLiang = (TextView) findViewById(R.id.read_bookLiang);
        read_bookHuan = (TextView) findViewById(R.id.read_bookHuan);
        read_bookSet = (TextView) findViewById(R.id.read_bookSet);
        read_bookBottom = (LinearLayout) findViewById(R.id.read_bookBottomMenu);
        read_bookLightLayout = (LinearLayout) findViewById(R.id.read_bookLightLayout);
        read_bookTop = (ConstraintLayout) findViewById(R.id.read_bookTopMenu);
        read_bookBack = (ImageView) findViewById(R.id.read_bookBack);
        read_bookTitle = (TextView) findViewById(R.id.read_bookTitle);
        read_bookPage = (PageView) findViewById(R.id.read_bookPage);
        read_bookMenu_light_SeekBar = (SeekBar) findViewById(R.id.read_bookMenu_light_SeekBar);
        read_bookMenu_light_img = (ImageView) findViewById(R.id.read_bookMenu_light_img);

        read_bookTitle.setText(mCollBook.getTitle());
        read_bookMulu.setOnClickListener(this);
        read_bookLiang.setOnClickListener(this);
        read_bookHuan.setOnClickListener(this);
        read_bookSet.setOnClickListener(this);
        read_bookBack.setOnClickListener(this);
        read_bookMenu_light_img.setOnClickListener(this);

        int brightness = BrightnessUtils.getScreenBrightness(TextReadActivity.this);
        Logger.dd(brightness);
        read_bookMenu_light_SeekBar.setProgress(brightness);


        //需要隐藏的东西
        //亮度布局
        toggleLight(false);
        //隐藏状态栏
        hideSystemBar();
        //两个菜单
        read_bookBottom.setVisibility(View.GONE);
        read_bookTop.setVisibility(View.GONE);
    }

    /*****************************-------------监听------------******************************/
    private void setListener() {
        //阅读页的点击;
        read_bookPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                Logger.d("%s++++++++++%s", "guoyh", "center");
                toggleMenu();

            }

            @Override
            public void prePage() {
                Logger.d("%s++++++++++%s", "guoyh", "prePage");

            }

            @Override
            public void nextPage() {
                Logger.d("%s++++++++++%s", "guoyh", "nextPage");

            }

            @Override
            public void cancel() {
                Logger.d("%s++++++++++%s", "guoyh", "cancel");

            }
        });
        //亮度 seekBar 的拖动
        read_bookMenu_light_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();

                BrightnessUtils.setBrightness(TextReadActivity.this, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                //设置当前 Activity 的亮度
//                BrightnessUtils.setBrightness(TextReadActivity.this, progress);

                //存储亮度的进度条
                readSettingManager.setBrightness(progress);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_bookBack:
                finish();
                break;
            case R.id.read_bookMulu:
                toggleDrawer(true);
                break;
            case R.id.read_bookLiang:
                toggleLight(true);
                break;
            case R.id.read_bookHuan:
                showCachePop();
                break;
            case R.id.read_bookSet:
                showSetPop();
                break;
            case R.id.read_bookMenu_light_img:
                showLight(isNightMode);
                break;
        }
    }

    /**
     * 是否为白天
     *
     * @param b false 夜晚  true 白天
     */
    private void showLight(boolean b) {
        Logger.dd(b + "-----");
        if (b) {
            read_bookLayout.setBackgroundColor(ContextCompat.getColor(TextReadActivity.this, ReadSettingManager.getInstance().getPageStyle().getBgColor()));
            read_bookMenu_light_img.setImageDrawable(ContextCompat.getDrawable(TextReadActivity.this, R.drawable.ic_read_menu_night));
        } else {
            read_bookLayout.setBackgroundColor(ContextCompat.getColor(TextReadActivity.this, R.color.nb_read_bg_night));
            read_bookMenu_light_img.setImageDrawable(ContextCompat.getDrawable(TextReadActivity.this, R.drawable.ic_read_menu_moring));
        }
        isNightMode = !b;
        mPageLoader.setNightMode(!b);

    }


    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu() {
        initMenuAnim();
        if (read_bookBottom.getVisibility() == View.VISIBLE) {
            //关闭
            read_bookTop.startAnimation(mTopOutAnim);
            read_bookBottom.startAnimation(mBottomOutAnim);
            read_bookBottom.setVisibility(View.GONE);
            read_bookTop.setVisibility(View.GONE);
            hideSystemBar();
        } else {
            //关闭亮度
            toggleLight(false);
            read_bookTop.startAnimation(mTopInAnim);
            read_bookBottom.startAnimation(mBottomInAnim);
            read_bookTop.setVisibility(View.VISIBLE);
            read_bookBottom.setVisibility(View.VISIBLE);
            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) {
            return;
        }
        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }


    private void toggleLight(boolean b) {
        if (b) {
            if (read_bookLightLayout.getVisibility() == View.GONE) {
                read_bookLightLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (read_bookLightLayout.getVisibility() == View.VISIBLE) {
                read_bookLightLayout.setVisibility(View.GONE);
            }
        }
    }

    private void toggleDrawer(boolean is) {
        if (is) {
            toggleMenu();
//            read_bookRlv.setSelection(mPageLoader.getChapterPos());
            read_bookDraw.openDrawer(GravityCompat.START);
        } else {
            read_bookDraw.closeDrawer(GravityCompat.START);
        }
    }

    /*******************------------一些弹窗----------***********************/
    private BasePopupView setPop;
    private BasePopupView cachePop;

    private void showSetPop() {
        toggleMenu();
        if (setPop == null) {
            setPop = new XPopup.Builder(TextReadActivity.this)
                    .hasShadowBg(false)
                    .asCustom(new TextSetPop(TextReadActivity.this, mPageLoader, new TextSetPop.SetOnClick() {
                        @Override
                        public void onClick(View view) {
//                            setOnClick(view);
                        }
                    }));
        }
        setPop.toggle();
    }

    private void showCachePop() {
        toggleMenu();
        if (cachePop == null) {
            cachePop = new XPopup.Builder(TextReadActivity.this)
                    .hasShadowBg(false)
                    .asCustom(new TextCachePop(TextReadActivity.this, new TextCachePop.CachePopOnClick() {
                        @Override
                        public void onClick(View view) {
                            cacheOpClick(view);
                        }
                    }));
        }
        cachePop.toggle();
    }

    private void cacheOpClick(View view) {
        switch (view.getId()) {
            case R.id.pop_cahce_bt1:

                break;
            case R.id.pop_cahce_bt2:
                break;
            case R.id.pop_cahce_bt3:
                break;
        }
    }



}

