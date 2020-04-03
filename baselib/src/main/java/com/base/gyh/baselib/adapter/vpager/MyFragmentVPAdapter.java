package com.base.gyh.baselib.adapter.vpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUO_YH on 2019/6/16 19:53
 */
public class MyFragmentVPAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public MyFragmentVPAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
    }

    public MyFragmentVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
        Fragment fragment = fragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }
}
