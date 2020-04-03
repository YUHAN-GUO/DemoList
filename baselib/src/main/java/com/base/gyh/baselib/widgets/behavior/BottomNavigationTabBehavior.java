package com.base.gyh.baselib.widgets.behavior;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.base.gyh.baselib.widgets.view.BottomTabLayout;


/*
 * created by taofu on 2018/12/14
 **/
public class BottomNavigationTabBehavior extends CoordinatorLayout.Behavior<BottomTabLayout> {

    private ObjectAnimator outAnimator, inAnimator;

    /**
     *  在滑动开始时，由父控件 CoordinatorLayout 调用该方法，表示开始嵌套滑动
     * @param coordinatorLayout 父控件
     * @param child  底部tab layout
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return  true  表示参与嵌套滑动，后续的事件都会传给该behavior ,否则 返回 false，后续就不会收到任何滑动事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomTabLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
         return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomTabLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        // 向上滑动
        if(dy > 0){
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight());
                outAnimator.setDuration(200);
            }
            if (!outAnimator.isRunning() && child.getTranslationY() <= 0) {
                outAnimator.start();
            }
        }else if (dy < 0) {// 下滑显示
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0);
                inAnimator.setDuration(200);
            }
            if (!inAnimator.isRunning() && child.getTranslationY() >= child.getHeight()) {
                inAnimator.start();
            }
        }
    }
}


