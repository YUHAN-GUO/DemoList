package com.think.guoyh.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.gyh.baselib.widgets.view.CircleProgressTv;

import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;

public class SplashActivity extends MyBaseActivity {

    private boolean isTo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        CircleProgressTv jump = (CircleProgressTv) findViewById(R.id.jump);
        jump.setTimeMillis(5000);
        jump.setCountdownProgressListener(5, new CircleProgressTv.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if (progress <= 70) {
                    jump.setClickable(true);
                } else {
                    jump.setClickable(false);
                }
                if (progress <= 1){
                    startToMain();
                }
            }
        });
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToMain();
            }
        });
        jump.start();
    }

    private void startToMain() {
        if(isTo){
            isTo = false;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean isShowStatus() {
        return false;
    }
}
