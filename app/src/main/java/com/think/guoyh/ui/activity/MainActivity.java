package com.think.guoyh.ui.activity;


import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.TabMenu;
import com.think.guoyh.R;
import com.think.guoyh.base.MainStateActivity;
import com.think.guoyh.ui.demo.function.loadinganim.LoadAnimFragment;
import com.think.guoyh.ui.demo.view.fish.FishFragment;
import com.think.guoyh.ui.mine.MineFragment;

import java.util.List;

public class MainActivity extends MainStateActivity {
    @Override
    protected void initFTs(List<FT> fts) {
        super.initFTs(fts);
        fts.add(new FT(new FishFragment(), "首页", R.mipmap.svg_tab_bar_find, R.mipmap.svg_tab_bar_find_hl));
        fts.add(new FT(new LoadAnimFragment(), "加载动画", R.mipmap.svg_tab_bar_find, R.mipmap.svg_tab_bar_find_hl));
        fts.add(new FT(new MineFragment(), "我的", R.mipmap.svg_tab_bar_find, R.mipmap.svg_tab_bar_find_hl));
        SetmSelceterListener(new isSelceterListener() {
            @Override
            public void isSelceter(int pos) {
                Logger.dd(pos);
            }
        });
    }

    @Override
    protected int initMpos() {
        return 0;
    }

    @Override
    protected void setSelecterTvColor(TabMenu tvMenu) {

    }
}
