package com.think.guoyh.widget.rip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.think.guoyh.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author genius158
 */
public class RippleLayout extends ViewGroup implements View.OnLayoutChangeListener {
  public static final int DEFAULT_COLOR = Color.parseColor("#24000000");

  private View child;
  private Drawable rippleDrawable;
  private int rippleColor;
  private int rippleMaskId;
  private int rippleStyle;

  public static final int BOUNDED = 0;
  public static final int BORDERLESS = 1;

  @IntDef({ BOUNDED, BORDERLESS }) @Retention(RetentionPolicy.SOURCE)
  public @interface RippleStyle {
  }

  public RippleLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RippleLayout);
    rippleColor = ta.getColor(R.styleable.RippleLayout_rippleColor, DEFAULT_COLOR);
    rippleMaskId = ta.getResourceId(R.styleable.RippleLayout_rippleMask, -1);
    rippleStyle = ta.getInt(R.styleable.RippleLayout_rippleStyle, 0);
    ta.recycle();
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() > 1) {
      throw new IllegalStateException("can only hold one child");
    }

    child = getChildAt(0);
    if (child == null) {
      throw new IllegalStateException("need a child to dell logic");
    }
  }

  @Override public void setClickable(boolean clickable) {
    super.setClickable(false);
  }

  @Override public void setOnClickListener(final OnClickListener onClickListener) {
    child.setOnClickListener(onClickListener);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    child.addOnLayoutChangeListener(this);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    child.removeOnLayoutChangeListener(this);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
    setMeasuredDimension(MeasureSpec.makeMeasureSpec(child.getMeasuredWidth(), MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
  }

  @Override protected boolean checkLayoutParams(LayoutParams p) {
    return p instanceof MarginLayoutParams;
  }

  @Override protected LayoutParams generateDefaultLayoutParams() {
    return new MarginLayoutParams(-1, -1);
  }

  @Override protected LayoutParams generateLayoutParams(LayoutParams p) {
    return new MarginLayoutParams(p);
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    child.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
  }

  @Override
  public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
      int oldTop, int oldRight, int oldBottom) {
    // make sure child that layout finish
    child.post(new Runnable() {
      @Override public void run() {
        boolean bgChanged = rippleDrawable != child.getBackground()
            || child.getBackground() == null && rippleDrawable == null;

        if (bgChanged) {
          Drawable drawableBG = child.getBackground();
          Drawable drawableMask = rippleMaskId == -1 ? drawableBG
              : ContextCompat.getDrawable(getContext(), rippleMaskId);
          rippleDrawable = getRippleDrawable(drawableBG, rippleStyle, drawableMask, rippleColor);
          ViewCompat.setBackground(child, rippleDrawable);
        }
      }
    });
  }

  public static Drawable getRippleDrawable(Drawable drawable) {
    return getRippleDrawable(drawable, BOUNDED, drawable, DEFAULT_COLOR);
  }

  public static Drawable getRippleDrawable(Drawable drawable, Drawable mask, int color) {
    return getRippleDrawable(drawable, BOUNDED, mask, color);
  }

  public static Drawable getRippleDrawable(Drawable drawable, @RippleStyle int rippleStyle,
      Drawable mask, int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // Build.VERSION_CODES.P = 28
      if (Build.VERSION.SDK_INT >= 28 || rippleStyle == BOUNDED) {
        return new RippleDrawable(ColorStateList.valueOf(color), drawable, mask);
      }
      if (drawable == null && mask == null) {
        return new RippleDrawable(ColorStateList.valueOf(color), null, null);
      }
      return new RippleDrawableWrap(drawable, mask, color);
    }
    if (isDrawableTint(drawable) && isDrawableTint(mask)) {
      return new DrawableWithCoverTint(drawable, mask, color);
    }
    return new DrawableWithCover(drawable, mask, color);
  }

  private static boolean isDrawableTint(Drawable drawable) {
    return drawable == null
        || drawable instanceof StateListDrawable
        || drawable instanceof ShapeDrawable
        || drawable instanceof NinePatchDrawable
        || drawable instanceof BitmapDrawable;
  }
}
