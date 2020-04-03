package com.think.guoyh.base;

import android.support.v4.content.ContextCompat;

import com.base.gyh.baselib.base.activity.StateActivity;
import com.base.gyh.baselib.base.activity.SupportActivity;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.jaeger.library.StatusBarUtil;
import com.think.guoyh.R;

/**
 *  与1 的区别：没有状态栏需要自己隔出距离 用于 顶部为ImageView 的Activity
 */
public abstract class MyBaseActivity extends StateActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }
    public void setStatusBar() {
        Logger.d("%s+++++++++%s","guoyh2","---------"+isShowStatus());

        if (!isShowStatus()){
            Logger.d("%s+++++++++%s","guoyh2","---------");
            StatusBarUtilTextColor.setStatusBarMode(this, false);
            StatusBarUtil.setTranslucentForImageView(this, 0,null);
        }else{
            StatusBarUtilTextColor.setStatusBarMode(this, true);
            StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.color_blue));
        }
    }

    public abstract boolean isShowStatus();

}
