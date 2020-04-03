package com.think.guoyh.ui.demo.function.rilisign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kangbaibai on 2018/12/13.
 */

@SuppressLint("AppCompatCustomView")
public class SquareTextView extends TextView {
    public SquareTextView(Context context) {
        super(context);
    }

    public SquareTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
