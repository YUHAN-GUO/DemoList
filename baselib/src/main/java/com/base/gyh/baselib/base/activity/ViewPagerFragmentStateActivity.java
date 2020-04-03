package com.base.gyh.baselib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.adapter.vpager.MyFragmentVPAdapter;
import com.base.gyh.baselib.widgets.ScrollViewPager;
import com.base.gyh.baselib.widgets.TabMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzm on 2018/04/18. email: 2209011667@qq.com
 */


public abstract class ViewPagerFragmentStateActivity extends SupportActivity {

    protected List<Fragment> mFragmentList = new ArrayList<>();
    protected String[] mStr = new String[6];
    protected int[] mIcon1 = new int[6];
    protected int[] mIcon2 = new int[6];
    protected TabMenu mTabMenu;
    protected ScrollViewPager mViewPager;

    public void setViewPagerPosition(int position){
        if (mViewPager!=null&&mFragmentList.size()>position){
            mViewPager.setCurrentItem(position);
        }
    }

    protected List<FT> mFTs = new ArrayList<>();

    protected class FT {
        Fragment fragment;
        String str;
        int icon1;
        int icon2;

        public FT(Fragment fragment, String str, int icon1, int icon2) {
            this.fragment = fragment;
            this.str = str;
            this.icon1 = icon1;
            this.icon2 = icon2;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_fragment);
        mViewPager = (ScrollViewPager) findViewById(R.id.viewPager);
        mTabMenu = (TabMenu) findViewById(R.id.tabMenu);
        setSelecterTvColor(mTabMenu);
        mTabMenu.setmSelecterListener(new TabMenu.onSelecterListener() {
            @Override
            public void onSelecter(int pos) {
                if (mSelceterListener!=null){
                    mSelceterListener.isSelceter(pos);
                }
            }
        });
        mViewPager.setScanScroll(false);
        initData();
    }

    protected void addFT(Fragment fragment, String str, int icon1, int icon2) {
        mFTs.add(new FT(fragment, str, icon1, icon2));
    }

    protected abstract void initFTs(List<FT> fts);

    protected void initData() {
        initFTs(mFTs);
        if (mFTs == null || mFTs.size() == 0) {
            return;
        }
        int i = 0;
        for (FT ft : mFTs) {
            mFragmentList.add(ft.fragment);
            mStr[i] = ft.str;
            mIcon1[i] = ft.icon1;
            mIcon2[i] = ft.icon2;
            if (++i >= 6) break;
        }


        MyFragmentVPAdapter mAdapter = new MyFragmentVPAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);

        mTabMenu.setItemText(mStr)
                .setItemIcon(mIcon1, mIcon2)
                .setupWithViewPager(mViewPager);
        int which = getWhich();
        if (which < mStr.length)
            mTabMenu.change(which);

        int moPos = initMpos();
        mTabMenu.change(moPos);
        mTabMenu.setMoPage(moPos);
    }


    protected int getWhich() {
        return 0;
    }

    private isSelceterListener mSelceterListener;

    public void SetmSelceterListener(isSelceterListener mSelceterListener) {
        this.mSelceterListener = mSelceterListener;
    }

    public interface  isSelceterListener{
        void isSelceter(int pos);
    }
    protected abstract int initMpos();
    protected abstract void setSelecterTvColor(TabMenu tvMenu);

}
