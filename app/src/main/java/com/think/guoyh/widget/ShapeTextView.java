package com.think.guoyh.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.think.guoyh.R;

public class ShapeTextView extends AppCompatTextView {
    private int startColor;
    private int centerColor;
    private int endColor;
    private int gravity;
    private int radius_size;
    private int shapeTvOrientation;


    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        startColor = ta.getColor(R.styleable.ShapeTextView_start_color, -1);
        centerColor = ta.getColor(R.styleable.ShapeTextView_center_color, -1);
        endColor = ta.getColor(R.styleable.ShapeTextView_end_color, -1);
        shapeTvOrientation = ta.getInteger(R.styleable.ShapeTextView_shapeTv_Orientation, 3);
        radius_size = (int) ta.getDimension(R.styleable.ShapeTextView_radius_sizeTv, dip2px(0));
        gravity = ta.getInt(R.styleable.ShapeTextView_android_gravity, Gravity.CENTER);
        ta.recycle();
        setGravity(gravity);
        init();
    }

    private void init() {
        Drawable drawable = getSolidRectDrawable(radius_size, shapeTvOrientation, startColor, centerColor, endColor);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(dip2px(left), dip2px(top), dip2px(right), dip2px(bottom));
    }

    /**
     * 得到实心的drawable, 一般作为选中，点中的效果
     *
     * @param cornerRadius 圆角半径
     * @return 得到实心效果
     */
    public static GradientDrawable getSolidRectDrawable(int cornerRadius, int or, int startColor, int centerColor, int endColor) {
            int colors[] = null;
        if (startColor==-1){
            startColor = Color.parseColor("#00000000");
            centerColor = Color.parseColor("#00000000");
            colors = new int[]{startColor,centerColor};
        }else {
            if (centerColor==-1){
                centerColor = startColor;
                if (endColor==-1){
                    colors = new int[]{startColor,centerColor};
                }else{
                    colors = new int[]{startColor,centerColor,endColor};
                }
            }else{
                if (endColor==-1){
                    colors = new int[]{startColor,centerColor};
                }else{
                    colors = new int[]{startColor,centerColor,endColor};
                }
            }

        }



        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setColor(Color.RED);
        // 设置矩形的圆角半径
        gradientDrawable.setCornerRadius(cornerRadius);
        // 设置绘画图片色值
        gradientDrawable.setColors(colors);
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        switch (or) {
            case 0:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 1:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 2:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 3:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
        }
        gradientDrawable.setOrientation(orientation);
        // 绘画的是矩形
//        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        return gradientDrawable;
    }

    private int dip2px(float dpValue) {
        final float scale = getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
