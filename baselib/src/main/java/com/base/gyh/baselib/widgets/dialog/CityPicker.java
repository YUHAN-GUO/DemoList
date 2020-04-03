package com.base.gyh.baselib.widgets.dialog;

import android.support.annotation.IntDef;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.base.gyh.baselib.data.bean.city.City;
import com.base.gyh.baselib.data.bean.city.HotCity;
import com.base.gyh.baselib.data.bean.city.LocatedCity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by GUOYH on 2019/4/30.
 */
public class CityPicker {



    private static final String TAG = "CityPicker";

    private static CityPicker mInstance;

    private CityPicker(){}

    public static CityPicker getInstance(){
        if (mInstance == null){
            synchronized (CityPicker.class){
                if (mInstance == null){
                    mInstance = new CityPicker();
                }
            }
        }
        return mInstance;
    }

    private FragmentManager mFragmentManager;
    private Fragment mTargetFragment;

    private boolean enableAnim;
    private int mAnimStyle;
    private LocatedCity mLocation;
    private List<HotCity> mHotCities;
    private OnPickListener mOnPickListener;

    public CityPicker setFragmentManager(FragmentManager fm) {
        this.mFragmentManager = fm;
        return this;
    }

    public CityPicker setTargetFragment(Fragment targetFragment) {
        this.mTargetFragment = targetFragment;
        return this;
    }

    /**
     * 设置动画效果
     * @param animStyle
     * @return
     */
    public CityPicker setAnimationStyle(@StyleRes int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置当前已经定位的城市
     * @param location
     * @return
     */
    public CityPicker setLocatedCity(LocatedCity location) {
        this.mLocation = location;
        return this;
    }

    public CityPicker setHotCities(List<HotCity> data){
        this.mHotCities = data;
        return this;
    }

    /**
     * 启用动画效果，默认为false
     * @param enable
     * @return
     */
    public CityPicker enableAnimation(boolean enable){
        this.enableAnim = enable;
        return this;
    }

    /**
     * 设置选择结果的监听器
     * @param listener
     * @return
     */
    public CityPicker setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
        return this;
    }

    public void show(){
        if (mFragmentManager == null){
            throw new UnsupportedOperationException("CityPicker：method setFragmentManager() must be called.");
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        final Fragment prev = mFragmentManager.findFragmentByTag(TAG);
        if (prev != null){
            ft.remove(prev).commit();
            ft = mFragmentManager.beginTransaction();
        }
        ft.addToBackStack(null);
        final CityPickerDialogFragment cityPickerFragment =
                CityPickerDialogFragment.newInstance(enableAnim);
        cityPickerFragment.setLocatedCity(mLocation);
        cityPickerFragment.setHotCities(mHotCities);
        cityPickerFragment.setAnimationStyle(mAnimStyle);
        cityPickerFragment.setOnPickListener(mOnPickListener);
        if (mTargetFragment != null){
            cityPickerFragment.setTargetFragment(mTargetFragment, 0);
        }
        cityPickerFragment.show(ft, TAG);
    }

    /**
     * 定位完成
     * @param location
     * @param state
     */
    public void locateComplete(LocatedCity location, @LocateState.State int state){
        CityPickerDialogFragment fragment = (CityPickerDialogFragment) mFragmentManager.findFragmentByTag(TAG);
        if (fragment != null){
            fragment.locationChanged(location, state);
        }
    }
    public interface OnPickListener {
        void onPick(int position, City data);
        void onLocate();
    }
    public interface InnerListener {
        void dismiss(int position, City data);
        void locate();
    }

    public static class LocateState {
        public static final int LOCATING    = 123;
        public static final int SUCCESS     = 132;
        public static final int FAILURE     = 321;

        @IntDef({SUCCESS, FAILURE})
        @Retention(RetentionPolicy.SOURCE)
        @interface State{}
    }
}
