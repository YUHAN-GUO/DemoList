package com.think.guoyh.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;

public class ShapeButton extends AppCompatButton {
    private int normal_color;
    private int pressed_color;
    private int enabled_color;
    private int gravity;
    private int radius_size;
    private boolean isClick = false;


    public ShapeButton(Context context) {
        this(context, null);
    }

    public ShapeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeButton);
        normal_color = ta.getColor(R.styleable.ShapeButton_normal_color, Color.parseColor("#ffffff"));
        pressed_color = ta.getColor(R.styleable.ShapeButton_pressed_color, Color.parseColor("#999999"));
        enabled_color = ta.getColor(R.styleable.ShapeButton_enabled_color, Color.GRAY);
        radius_size = (int) ta.getDimension(R.styleable.ShapeButton_radius_sizeBt, dip2px(0));
        gravity = ta.getInt(R.styleable.ShapeButton_android_gravity, Gravity.CENTER);
        ta.recycle();
        setGravity(gravity);
        init();
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        super.setTextColor(color);
    }

    public void setClick( ) {
        isClick = !isClick;
        init();
    }

    public boolean isClick() {
        return isClick;
    }

    private void init() {
        Integer selecterColor = 0;
        Integer unSelecterColor = 0;
        if (isClick){
            selecterColor =  normal_color;
            unSelecterColor = pressed_color;
        }else{
            selecterColor =  pressed_color;
            unSelecterColor = normal_color;
        }
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = new RippleDrawable(
                    getStateListColor(selecterColor),
                    getSolidRectDrawable(radius_size, unSelecterColor),
                    getSolidRectDrawable(radius_size, unSelecterColor)
            );
        } else {
            drawable = getStateListDrawable(getSolidRectDrawable(radius_size, selecterColor), getSolidRectDrawable(radius_size, unSelecterColor));
        }
        setBackgroundDrawable(drawable);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(dip2px(left), dip2px(top), dip2px(right), dip2px(bottom));
    }

    /**
     * 得到实心的drawable, 一般作为选中，点中的效果
     *
     * @param cornerRadius 圆角半径
     * @param solidColor   实心颜色
     * @return 得到实心效果
     */
    public static GradientDrawable getSolidRectDrawable(int cornerRadius, int solidColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setColor(Color.RED);
        // 设置矩形的圆角半径
        gradientDrawable.setCornerRadius(cornerRadius);
        // 设置绘画图片色值
        gradientDrawable.setColor(solidColor);
        // 绘画的是矩形
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        return gradientDrawable;
    }

    /**
     * 背景选择器
     *
     * @param pressedDrawable 按下状态的Drawable
     * @param normalDrawable  正常状态的Drawable
     * @return 状态选择器
     */
    public StateListDrawable getStateListDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置不能用的状态
        //默认其他状态背景
        GradientDrawable gray = getSolidRectDrawable(radius_size, enabled_color);
        stateListDrawable.addState(new int[]{}, gray);
        return stateListDrawable;
    }

    public ColorStateList getStateListColor(Integer color) {
        int[] colors = new int[] { color, 0xffffff00, 0xff0000ff, 0xffff0000 ,color};
        int[][] states = new int[5][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_activated };
        ColorStateList colorList = new ColorStateList(states, colors);
        return ColorStateList.valueOf(color);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}