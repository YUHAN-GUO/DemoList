package com.think.guoyh.widget.page;


import android.support.annotation.ColorRes;

import com.think.guoyh.R;


/**
 * Created by newbiechen on 2018/2/5.
 * 作用：页面的展示风格。
 */

public enum PageStyle {
    //font ----- bg
    BG_0(R.color.color_white, R.color.color_blue),
    BG_1(R.color.color_white, R.color.Blue),
//    BG_2(R.color.nb_read_font_3, R.color.nb_read_bg_3),
    BG_3(R.color.color_white, R.color.colorPrimary),
    BG_4(R.color.nb_read_font_5, R.color.color_white),
    NIGHT(R.color.nb_read_font_night, R.color.nb_read_bg_night),;

    private int fontColor;
    private int bgColor;

    PageStyle(@ColorRes int fontColor, @ColorRes int bgColor) {
        this.fontColor = fontColor;
        this.bgColor = bgColor;
    }

    public int getFontColor() {
        return fontColor;
    }

    public int getBgColor() {
        return bgColor;
    }
}
