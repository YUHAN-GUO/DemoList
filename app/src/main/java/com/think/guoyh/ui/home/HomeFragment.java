package com.think.guoyh.ui.home;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.gyh.baselib.base.fragment.StateFragment;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.view.tab.SegmentTabLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.think.guoyh.R;
import com.think.guoyh.data.Const;
import com.think.guoyh.data.bean.MenuBean;
import com.think.guoyh.ui.MenuRlvAdapter;
import com.think.guoyh.ui.demo.DemoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends StateFragment {

    private RecyclerView rlv;
    private SegmentTabLayout tab;
    private MenuRlvAdapter rlvAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {

    }

    private void initAdapter() {
        if (rlvAdapter == null) {
            Logger.d("%s++++++++++++%s","guoyh",getGongData().size());
            rlvAdapter = new MenuRlvAdapter(null);
            rlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MenuBean item = (MenuBean) adapter.getItem(position);
                    DemoActivity.toStart(getActivity(), item.getPosition());
                }
            });
            rlv.setAdapter(rlvAdapter);
            rlv.setLayoutManager(getLinearLayoutManger());
        }
    }


    @Override
    protected void initView(View view) {
        rlv = view.findViewById(R.id.rlv);
        tab = view.findViewById(R.id.home_tab);
        List<String> titles = new ArrayList<>();
        titles.add("~~功能123456");
        titles.add("—三方—");
        tab.setTabData(titles);
        setGongRlv();
    }

    @Override
    protected void initListener() {
        tab.setOnTabSelectListener(new SegmentTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabSelectOnClick(position);
                }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void tabSelectOnClick(int position) {

        switch (position) {
            case 0:
                setGongRlv();
                break;
            case 1:
                setSansdkRlv();
                break;

        }
    }

    private void setGongRlv() {
        initAdapter();
        rlvAdapter.setNewData(getGongData());
    }
    private void setSansdkRlv() {
        initAdapter();
        rlvAdapter.setNewData(getSansdkData());
    }

    private List<MenuBean> getGongData() {
        List<MenuBean> data = new ArrayList<>();
        data.add(new MenuBean("自定义Button和水波纹效果", Const.GuoDemo.ButtonDemo));
        data.add(new MenuBean("悬浮按钮+可以移动的布局", Const.GuoDemo.MoveLayout));
        data.add(new MenuBean("CoordLayout示例", Const.GuoDemo.CoordDemo));
        data.add(new MenuBean("IndexDemo-Z排列", Const.GuoDemo.IndexDemo));
        data.add(new MenuBean("获取当前运行进程", Const.GuoDemo.ProcessDemo));
        data.add(new MenuBean("保存图片到相册", Const.GuoDemo.SaveImgDemo));
        data.add(new MenuBean("发送通知", Const.GuoDemo.SendNotiDemo));
        data.add(new MenuBean("日历签到", Const.GuoDemo.RiliSignDemo));
        data.add(new MenuBean("发光示例", Const.GuoDemo.ShadowDemo));
        data.add(new MenuBean("MMKV示例", Const.GuoDemo.MMKVDemo));
        data.add(new MenuBean("加密", Const.GuoDemo.EncryDemo));
        data.add(new MenuBean("小说阅读", Const.GuoDemo.TextReadDemo));
        data.add(new MenuBean("仿抖音", Const.GuoDemo.TikTokDemo));
        data.add(new MenuBean("淘宝二楼", Const.GuoDemo.SecondTaoDemo));
        data.add(new MenuBean("rlv滑动指定位置", Const.GuoDemo.RlvScrollDemo));
        data.add(new MenuBean("列表倒计时", Const.GuoDemo.CountDownDemo));
        data.add(new MenuBean("长按图标显示快捷方式", Const.GuoDemo.ShortCutDemo));
        return data;
    }

    private List<MenuBean> getSansdkData() {
        List<MenuBean> data = new ArrayList<>();

        return data;
    }


    @Override
    public void onEmptyRetryClicked() {

    }

    @Override
    public void onErrorRetryClicked() {

    }
}
