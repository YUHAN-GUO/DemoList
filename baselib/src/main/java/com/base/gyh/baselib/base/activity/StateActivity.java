package com.base.gyh.baselib.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.annotation.StateType;
import com.base.gyh.baselib.broadcast.NetBroadcastReceiver;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetEmptyView;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetErrorView;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetLoadingView;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.jaeger.library.StatusBarUtil;

import static com.base.gyh.baselib.annotation.StateType.EMPTY;
import static com.base.gyh.baselib.annotation.StateType.ERROR;
import static com.base.gyh.baselib.annotation.StateType.LOADING;
import static com.base.gyh.baselib.annotation.StateType.NORMAL;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class StateActivity extends SupportActivity implements NetBroadcastReceiver.NetChangeListener {

    //网络错误重新加载的接口
    public ReloadAndEmptyListener reloadAndEmptyListener;
    private LinearLayout mRootBaseView;//根布局
    private NetStateLayout netStateLayout;//状态布局
    private MyToolbar myToolbar;//Toolbar
    private boolean isStatusbar = true;

    public void setStatusbar(boolean statusbar) {
        isStatusbar = statusbar;
    }

    /**
     * 必须重写setContentView注意不能够添加这行代码 目的将当前界面的布局添加到BaseActivity中去
     * super.setContentView(R.layout.activity_base);
     */
    @Override
    public void setContentView(int layoutResID) {
        if (isStatusbar){
            StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.color_blue));
            StatusBarUtilTextColor.setStatusBarMode(this, true);
            View view = getLayoutInflater().inflate(layoutResID, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            if (null != mRootBaseView) {
                mRootBaseView.addView(view, lp);
            }
        }else{
            super.setContentView(layoutResID);
        }

    }

    private void initListener() {
        netStateLayout.setOnEmptyAndErrorRetryClickListener(new NetStateLayout.OnEmptyAndErrorRetryClickListener() {
            @Override
            public void onEmptyRetryClicked() {
                reloadAndEmptyListener.emptyClickListener();
            }

            @Override
            public void onErrorRetryClicked() {
                reloadAndEmptyListener.reloadClickListener();
            }
        });
    }

    //这里控件初始化没用ButterKnife 会报错  原因暂时没找到
    public void initBaseView() {
        mRootBaseView = (LinearLayout) findViewById(R.id.activity_base_root);
        netStateLayout = findViewById(R.id.netStateLayout);
        myToolbar = findViewById(R.id.activity_base_toolBar);
        netStateLayout.setNetLoadingView(new SimpleNetLoadingView());
        netStateLayout.setNetErrorView(new SimpleNetErrorView());
        netStateLayout.setNetEmptyView(new SimpleNetEmptyView());
    }

    public void initTitleBar(boolean titleBarIsShow){
        initTitleBar(false,false,"");
    }
                             /**
                              * @param titleBarIsShow titleBar是否显示
                              * @param tvLeftIsShow   返回键是否显示
                              * @param title          中间的文字
                              */
    public void initTitleBar(boolean titleBarIsShow, boolean tvLeftIsShow,
                             String title) {
        if (titleBarIsShow == true) {
            if (!tvLeftIsShow) {
                myToolbar.hideBack(true);
            } else {
                myToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                myToolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
                    @Override
                    public void onClick(int type) {
                        if (type == MyToolbar.BACK) {
                            finish();
                        }
                    }
                });
            }
            myToolbar.setTitleText(title);
        } else {
            myToolbar.setVisibility(View.GONE);
        }
    }
    /**
     * 更改布局状态
     */
    protected void showEmpty(){
        changePageState(EMPTY);
    }
    protected void showError(){
        changePageState(ERROR);
    }
    protected void showNormal(){
        changePageState(NORMAL);
    }

    /**
     * 切换页面的布局
     *
     * @param stateType 页面状态 NORMAL  EMPTY  ERROR LOADING
     */
    public void changePageState(@StateType int stateType) {
        if (netStateLayout.getVisibility() == View.GONE) {
            netStateLayout.setVisibility(View.VISIBLE);
        }
        if (stateType == NORMAL) {
            mRootBaseView.setVisibility(View.VISIBLE);
        } else {
            mRootBaseView.setVisibility(View.GONE);

        }
        switch (stateType) {
            case NORMAL:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_HIDE);
                break;
            case ERROR:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_ERROR);
                break;
            case EMPTY:
                Logger.dd("CONTENT_STATE_EMPTY");
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_EMPTY);
                break;
            case LOADING:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_LOADING);
                break;
        }


    }

    public void setReloadInterface(ReloadAndEmptyListener reloadAndEmptyListener) {
        this.reloadAndEmptyListener = reloadAndEmptyListener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意：这里的setContentView必须有super才可以，需要走父类方法
        super.setContentView(R.layout.activity_base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initBaseView();
        initTitleBar(false);
        initListener();
    }



    public interface ReloadAndEmptyListener {
        void reloadClickListener();

        void emptyClickListener();
    }
}
