package com.think.guoyh.widget.movelayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.base.gyh.baselib.utils.mylog.Logger;


/**
 * 可移动布局
 */
public class MoveLayout extends LinearLayout {
    private int previousX;//初始X轴坐标
    private int previousY;//初始Y轴坐标
    private int leftDistance;
    private int rightDistance;

    private DisplayMetrics displayMetrics;
    private int windowWidth;//屏幕宽度

    private long actionDownTime;//手指按下的时间
    private long actionUpTime;//手指抬起的时间

    private OnClickListener onClickListener;

    public MoveLayout(Context context) {
        this(context, null);
    }

    public MoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public interface OnClickListener {
        void onClick();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();//实时触碰的X轴坐标
        int currentY = (int) event.getY();//实时触碰的Y轴坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = currentX;//记录初始X轴坐标
                previousY = currentY;//记录初始Y轴坐标
                leftDistance = getLeft();
                rightDistance = getRight();
                actionDownTime = System.currentTimeMillis();//记录手指按下的时间
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = currentX - previousX;//计算X轴方向偏移
                int offsetY = currentY - previousY;//计算Y轴方向偏移
                int top = getTop() + offsetY;
                Logger.dd(getTop());
                Logger.dd(previousY);
                if (top < 10) {
                    //限制可拖动的最高位置，同理可限制最下、最左和最右
                    top = 10;
                }
                layout(getLeft() + offsetX, top, getRight() + offsetX, top + getHeight());//根据坐标重新放置当前View的位置
                break;
            case MotionEvent.ACTION_UP:
                displayMetrics=getResources().getDisplayMetrics();
                windowWidth=displayMetrics.widthPixels;//获取屏幕宽度
                int finalLeftDistance=getLeft();
                if (finalLeftDistance>(windowWidth-getWidth())/2){//以屏幕中间为界限，平滑回到水平方向的右边
                    //ValueAnimator数值发生器，产生有一定规律的数值，从而控制动画实现
                    ValueAnimator valueAnimator=ValueAnimator.ofInt(getLeft(),windowWidth-10-getWidth());
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int smoothX = (int)animation.getAnimatedValue();
                            layout(smoothX,getTop(),smoothX+getWidth(),getBottom());
                        }
                    });
                    valueAnimator.start();
                }else if (finalLeftDistance<(windowWidth-getWidth())/2){//以屏幕中间为界限，平滑回到水平方向的左边
                    ValueAnimator valueAnimator=ValueAnimator.ofInt(getLeft(),10);
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int smoothX = (int)animation.getAnimatedValue();
                            layout(smoothX,getTop(),smoothX+getWidth(),getBottom());
                        }
                    });
                    valueAnimator.start();
                }
                actionUpTime = System.currentTimeMillis();//记录手指抬起的时间
                long intervalTime = actionUpTime - actionDownTime;
                if (intervalTime < 200){ //判断按下抬起的时间间隔，点击回调解决TouchEvent和点击事件冲突的问题
                    if (onClickListener!=null){
                        onClickListener.onClick();
                    }
                }
                break;
        }
        return true;
    }
}
