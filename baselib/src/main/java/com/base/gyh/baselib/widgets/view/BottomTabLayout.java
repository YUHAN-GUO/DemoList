package com.base.gyh.baselib.widgets.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.base.gyh.baselib.widgets.behavior.BottomNavigationTabBehavior;



/*
 * created by taofu on 2018/12/4
 **/

public class BottomTabLayout extends ConstraintLayout implements CoordinatorLayout.AttachedBehavior {


    private OnCheckedChangeListener mListener;


    public BottomTabLayout(Context context) {
        super(context);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        RadioButton radioButton;

        for(int i = 0; i < getChildCount(); i++){
            final View v = getChildAt(i);
            if(v instanceof RadioButton){
                radioButton = (RadioButton) v;
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 因为我们设置的是 选中状态的监听，因此 每当我们点击一个 radioButton 时 这个方法会调用
                        // 两次，第一次是我们点击的那个radiobutton 变为选中，另一次是上次选中的那个有原来的选中变为未选中
                        if(isChecked){
                            unCheckOther(buttonView);
                            if(mListener != null){
                                mListener.onCheckedChanged(BottomTabLayout.this, (RadioButton) v);

                            }
                        }

                    }
                });
            }


        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void check(int radioButtonId){

        View view = findViewById(radioButtonId);
        if(view != null){
            ((RadioButton)view).setChecked(true);
            unCheckOther(view);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        mListener = listener;
    }

    /**
     *  取消其他 radiobutton 的选中状态
     * @param v
     */
    private void unCheckOther(View v ){
        RadioButton r;
        View view;
        for(int i = 0; i < getChildCount(); i++){
            view =  getChildAt(i);
            if(view instanceof RadioButton){
                r = (RadioButton) view;
                if(v != r){
                    r.setChecked(false);
                }
            }
        }
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new BottomNavigationTabBehavior();
    }

    public interface  OnCheckedChangeListener{
        void onCheckedChanged(BottomTabLayout group, RadioButton checkedView);
    }


}
