package com.think.guoyh.ui.suan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.base.gyh.baselib.adapter.vpager.MyFragmentVPAdapter;
import com.base.gyh.baselib.widgets.ScrollViewPager;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;
import com.think.guoyh.data.Const;
import com.think.guoyh.ui.demo.DemoActivity;
import com.think.guoyh.ui.suan.yiwei.YiweiFragment;

import java.util.ArrayList;
import java.util.List;

public class SuanActivity extends MyBaseActivity {
    public static String SUAN_KEY = "SUAN_KEY";
    private ScrollViewPager suanVp;

    public static void toStart(FragmentActivity activity, int position) {
        Intent intent = new Intent(activity, SuanActivity.class);
        intent.putExtra(SUAN_KEY, position);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suan);
        initView();
        setViewP();
        getData();
    }

    private void getData() {
       int pos = getIntent().getIntExtra(SUAN_KEY, -1);
       showPage(pos);
    }

    private void showPage(int pos){
        switch (pos) {
            case Const.SuanDemo.YIWEIDEMO:
                showPosition(0);
                break;
        }

    }

    private void showPosition(int i) {
        suanVp.setCurrentItem(i);
    }


    @Override
    public boolean isShowStatus() {
        return true;
    }

    private void initView() {
        suanVp = (ScrollViewPager) findViewById(R.id.suanVp);
    }
    private void setViewP() {
        List<Fragment> fragments = new ArrayList<>();

        YiweiFragment yiweiFragment = new YiweiFragment();
        fragments.add(yiweiFragment);

        MyFragmentVPAdapter vpAdapter = new MyFragmentVPAdapter(getSupportFragmentManager(), fragments);
        suanVp.setAdapter(vpAdapter);
    }
}
