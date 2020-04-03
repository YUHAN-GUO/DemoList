package com.base.gyh.baselib.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.annotation.StateType;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetEmptyView;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetErrorView;
import com.base.gyh.baselib.widgets.netstatae.SimpleNetLoadingView;
import com.base.gyh.baselib.widgets.view.MyToolbar;

import static com.base.gyh.baselib.annotation.StateType.EMPTY;
import static com.base.gyh.baselib.annotation.StateType.ERROR;
import static com.base.gyh.baselib.annotation.StateType.LOADING;
import static com.base.gyh.baselib.annotation.StateType.NORMAL;


/*
 * created by taofu on 2018/11/28
 **/
public abstract class StateFragment extends BaseFragment implements NetStateLayout.OnEmptyAndErrorRetryClickListener {
    public ReloadAndEmptyListener reloadAndEmptyListener;
    private View view;
    private LinearLayout mRootBaseView;//根布局
    private NetStateLayout netStateLayout;//状态布局
    private MyToolbar myToolbar;//Toolbar


    /**
     * 获取到子布局
     *
     * @param view view
     */
    private void initBaseView(View view) {
        mRootBaseView = view.findViewById(R.id.activity_base_root);
        netStateLayout = view.findViewById(R.id.netStateLayout);
        myToolbar = view.findViewById(R.id.activity_base_toolBar);
        myToolbar.setVisibility(View.GONE);
        netStateLayout.setNetLoadingView(new SimpleNetLoadingView());
        netStateLayout.setNetErrorView(new SimpleNetErrorView());
        netStateLayout.setNetEmptyView(new SimpleNetEmptyView());
        netStateLayout.setOnEmptyAndErrorRetryClickListener(this);

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
                myToolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
                    @Override
                    public void onClick(int type) {
                        if (type == MyToolbar.BACK) {
//                            finish();
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
    protected void showEmpty() {
        hideNormal();
        changePageState(EMPTY);
    }
    //
    protected void showEmpty2(){
        showNormal();
        changePageState(EMPTY);
    }

    protected void showError() {
        hideNormal();
        changePageState(ERROR);
    }
    protected void showError2(){
        showNormal();
        changePageState(ERROR);
    }

    protected void showNormal() {
        mRootBaseView.setVisibility(View.VISIBLE);
        changePageState(NORMAL);
    }

    protected void showLoad() {
        hideNormal();
        changePageState(LOADING);
    }
    protected void showLoad2() {
        showNormal();
        changePageState(LOADING);
    }

    protected void hideNormal() {
        mRootBaseView.setVisibility(View.GONE);
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
//        if (stateType == NORMAL) {
//            mRootBaseView.setVisibility(View.VISIBLE);
//        } else {
//            mRootBaseView.setVisibility(View.GONE);
//
//        }
        switch (stateType) {
            case NORMAL:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_HIDE);
                break;
            case ERROR:
                netStateLayout.setContentState(NetStateLayout.CONTENT_STATE_ERROR);
                break;
            case EMPTY:
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

    public View initContentView(int layoutId) {
        View view = LayoutInflater.from(mRootBaseView.getContext()).inflate(layoutId, mRootBaseView, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (null != mRootBaseView) {
            mRootBaseView.addView(view, lp);
        }
        return view;
    }


    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    protected abstract void initListener();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_base, container, false);
            view.setClickable(true);
        }
        //找到State布局
        initBaseView(view);
        //将布局加到State布局中
        View view = initContentView(getLayoutRes());
        //提供View 相关操作的方法
        initView(view);
        //取消ToolBar 展示  可以在子类中打开
        initTitleBar(false, false, "");
        //初始化 Error 和Empty的 布局点击监听
        //提供监听方法
        initListener();
        return this.view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    public interface ReloadAndEmptyListener {
        void reloadClickListener();

        void emptyClickListener();
    }
}
