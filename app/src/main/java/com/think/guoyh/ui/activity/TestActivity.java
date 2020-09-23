package com.think.guoyh.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseActivity;

public class TestActivity extends MyBaseActivity {

    private TextView testTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        String key = getIntent().getStringExtra("key");
        testTv.setText(key);
    }

    @Override
    public boolean isShowStatus() {
        return false;
    }

    private void initView() {
        testTv = (TextView) findViewById(R.id.testTv);
    }
}