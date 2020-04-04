package com.think.guoyh.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.base.gyh.baselib.adapter.vpager.MyFragmentVPAdapter;
import com.base.gyh.baselib.widgets.ScrollViewPager;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;
import com.think.guoyh.data.Const;
import com.think.guoyh.ui.demo.function.button.ButtonFragment;
import com.think.guoyh.ui.demo.function.coord.CoordFragment;
import com.think.guoyh.ui.demo.function.encry.EncryFragment;
import com.think.guoyh.ui.demo.function.index.IndexFragment;
import com.think.guoyh.ui.demo.function.loadinganim.LoadAnimFragment;
import com.think.guoyh.ui.demo.function.mmkv.MMKVFragment;
import com.think.guoyh.ui.demo.function.move.MoveFragment;
import com.think.guoyh.ui.demo.function.process.ProcessFragment;
import com.think.guoyh.ui.demo.function.rilisign.RiliSignFragment;
import com.think.guoyh.ui.demo.function.rlvscroll.RlvScrollFragment;
import com.think.guoyh.ui.demo.function.save.SaveFragment;
import com.think.guoyh.ui.demo.function.secondtaobao.SecondTaobaoFragment;
import com.think.guoyh.ui.demo.function.sendnotifaction.SendNotifactionFragment;
import com.think.guoyh.ui.demo.function.shadow.ShadowFragment;
import com.think.guoyh.ui.demo.function.textread.TextReadFragment;
import com.think.guoyh.ui.demo.function.tiktok.TiktokFragment;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends MyBaseActivity {
    public static String DEMO_KEY = "DEMO_KEY";
    private ScrollViewPager demoVp;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
        getData();
        setViewPager();
        showPager();
    }

    //
    public static void toStart(FragmentActivity activity, int position) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(DEMO_KEY, position);
        activity.startActivity(intent);
    }

    private void setViewPager() {
        List<Fragment> fragments = new ArrayList<>();

        ButtonFragment buttonFragment = new ButtonFragment();//0
        fragments.add(buttonFragment);

        MoveFragment moveFragment = new MoveFragment();//1
        fragments.add(moveFragment);

        CoordFragment coorFragment = new CoordFragment();//2
        fragments.add(coorFragment);

        IndexFragment indexFragment = new IndexFragment();//3
        fragments.add(indexFragment);

//        AlireFragment alireFragment = new AlireFragment();//4
//        fragments.add(alireFragment);

        ProcessFragment processFragment = new ProcessFragment();//5
        fragments.add(processFragment);

        SaveFragment saveFragment = new SaveFragment();//6
        fragments.add(saveFragment);

        SendNotifactionFragment sendNotifactionFragment = new SendNotifactionFragment();//7
        fragments.add(sendNotifactionFragment);

        RiliSignFragment riliSignFragment = new RiliSignFragment();//8
        fragments.add(riliSignFragment);

        ShadowFragment shadowFragment = new ShadowFragment();//9
        fragments.add(shadowFragment);

        MMKVFragment mmkvFragment = new MMKVFragment();//10
        fragments.add(mmkvFragment);

        TextReadFragment textReadFragment = new TextReadFragment();//11
        fragments.add(textReadFragment);

        EncryFragment encryFragment = new EncryFragment();//12
        fragments.add(encryFragment);

        TiktokFragment tiktokFragment = new TiktokFragment();//13
        fragments.add(tiktokFragment);

        SecondTaobaoFragment secondTaobaoFragment = new SecondTaobaoFragment();//14
        fragments.add(secondTaobaoFragment);

        RlvScrollFragment rlvScrollFragment = new RlvScrollFragment();//15
        fragments.add(rlvScrollFragment);


        LoadAnimFragment loadAnimFragment = new LoadAnimFragment();//16
        fragments.add(loadAnimFragment);

        MyFragmentVPAdapter vpAdapter = new MyFragmentVPAdapter(getSupportFragmentManager(), fragments);
        demoVp.setAdapter(vpAdapter);
    }

    private void showPager() {
        //这个 pos 与 DemoVp 可能不对应
        switch (pos) {
            case Const.GuoDemo.ButtonDemo:
                showPosition(0);
                break;
            case Const.GuoDemo.MoveLayout:
                showPosition(1);
                break;
            case Const.GuoDemo.CoordDemo:
                showPosition(2);
                break;
            case Const.GuoDemo.IndexDemo:
                showPosition(3);
                break;
            case Const.GuoDemo.ProcessDemo:
                showPosition(4);
                break;
            case Const.GuoDemo.SaveImgDemo:
                showPosition(5);
                break;
            case Const.GuoDemo.SendNotiDemo:
                showPosition(6);
                break;
            case Const.GuoDemo.RiliSignDemo:
                showPosition(7);
                break;
            case Const.GuoDemo.ShadowDemo:
                showPosition(8);
                break;
            case Const.GuoDemo.MMKVDemo:
                showPosition(9);
                break;
            case Const.GuoDemo.TextReadDemo:
                showPosition(10);
                break;
            case Const.GuoDemo.EncryDemo:
                showPosition(11);
                break;
            case Const.GuoDemo.TikTokDemo:
                showPosition(12);
                break;
            case Const.GuoDemo.SecondTaoDemo:
                showPosition(13);
                break;
            case Const.GuoDemo.RlvScrollDemo:
                showPosition(14);
                break;
            case Const.GuoDemo.LoadAnimDemo:
                showPosition(15);
                break;
        }
    }

    private void showPosition(int position) {
        demoVp.setCurrentItem(position);

    }


    @Override
    public boolean isShowStatus() {
        return true;
    }

    private void getData() {
        pos = getIntent().getIntExtra(DEMO_KEY, -1);
    }

    private void initView() {
        demoVp = (ScrollViewPager) findViewById(R.id.DemoViewPager);
    }


}
