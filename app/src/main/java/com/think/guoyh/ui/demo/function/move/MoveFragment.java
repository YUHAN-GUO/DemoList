package com.think.guoyh.ui.demo.function.move;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeTextView;
import com.think.guoyh.widget.movelayout.MoveLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoveFragment extends MyBaseFragment {


    private WebView move_webView;
    private MoveLayout moveLayout;
    private ShapeTextView closeFloat;

    public MoveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_move, container, false);
        initView(view);
        initListener();
        initWeb();
        return view;
    }

    private void initWeb() {
        //https://cpu.baidu.com/1021/d5e395c7/detail/72311671586/video?from=list
        WebSettings settings = move_webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheEnabled(true);
        move_webView.loadUrl("https://cpu.baidu.com/1021/d5e395c7/detail/72311671586/video?pvid=Q1ySO7JDqpsFL9NlkO3vDUp-QnmN3CSdJ5&rt=130&rts=549755813888&logid=15839780169461eabc3ab36327e29cce&expinfos=20408_20511_20531_20544_20572_20587_20605_20627_20651_20664_20684_20902_20985_21500_21501_21901_21952_21961_22113_22641_23021_23161_23453_23456_23622_23631_23743_23921_24501_24811_24922_25312_25412_26457_26901_29021_29273_29578_29598_29721_29727_510101_20010_46000_46048_40001_43172_44002_44013_43304_44053_44125_44131_44142_44151_44163_44173_44183_44193_530101_8103801_8102901_8190302&from=list");
    }

    private void initListener() {
        moveLayout.setOnClickListener(new MoveLayout.OnClickListener() {
            @Override
            public void onClick() {
                checkDrawPermiss();
            }
        });
    }
//        需要注册    <activity android:name=".utils.PermissionUtils$PermissionActivity" />

    //检查是否有浮窗权限---      <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    private void checkDrawPermiss() {
        if (PermissionUtils.isGrantedDrawOverlays()) {
            //去开启一个服务 打开悬浮
            startFloatService();
        } else {
            openDraw();
        }
    }

    //打开悬浮权限  这个权限比较特殊 需要手动打开
    private void openDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestDrawOverlays(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    checkDrawPermiss();
                }

                @Override
                public void onDenied() {
                    Toast.makeText(getActivity(), "你拒绝了开启悬浮权限", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //开启浮窗服务
    private void startFloatService() {
        Intent intent = new Intent(getActivity(), FloatService.class);
        getActivity().startService(intent);
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        move_webView = (WebView) view.findViewById(R.id.move_webView);
        moveLayout = (MoveLayout) view.findViewById(R.id.moveLayout);
        closeFloat = (ShapeTextView) view.findViewById(R.id.closeFloat);
        closeFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("%s+++++++++++%s","guoyh","---------");
                Intent intent = new Intent(getActivity(), FloatService.class);
                getActivity().stopService(intent);
            }
        });
    }
}
