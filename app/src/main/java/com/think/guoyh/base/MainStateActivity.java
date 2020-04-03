package com.think.guoyh.base;

import android.os.Bundle;

import com.base.gyh.baselib.base.activity.ViewPagerFragmentStateActivity;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by GUO_YH on 2019/9/3 16:37
 */
public abstract class MainStateActivity extends ViewPagerFragmentStateActivity {

    @Override
    protected void initFTs(List<FT> fts) {
        StatusBarUtilTextColor.setStatusBarMode(this, false);
        StatusBarUtil.setTranslucentForImageView(this, 0,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
