package com.think.guoyh.ui.demo.function.countdown;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountDowmFragment extends MyBaseFragment {


    private RecyclerView countDownRlv;
    private MyThread myThread;
    private CountDownAdapter countDownAdapter;

    public CountDowmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_count_dowm, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toGetData();

    }

    @Override
    protected void loadData() {
    }

    private void toGetData() {
        Logger.dd("==========");
        List<CountDownAdapter.CountDownBean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CountDownAdapter.CountDownBean countDownBean = new CountDownAdapter.CountDownBean("第" + i, 700000 - i * 2500);
            data.add(countDownBean);
        }
        Logger.dd("==========");

        countDownAdapter = new CountDownAdapter(data);
        countDownRlv.setAdapter(countDownAdapter);
        countDownRlv.setLayoutManager(getGridLayoutManager(2));
        myThread = new MyThread(data);
        new Thread(myThread).start();
    }

    private void initView(View view) {
        countDownRlv = (RecyclerView) view.findViewById(R.id.countDownRlv);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //优化刷新adapter的方法
                    countDownAdapter.notifyTime();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    //列表倒计时
    class MyThread implements Runnable {
        private List<CountDownAdapter.CountDownBean> mModelList;
        public boolean endThread;

        public MyThread(List<CountDownAdapter.CountDownBean> modelList) {
            mModelList = modelList;
        }

        @Override
        public void run() {
            try {
                while (!endThread) {
                    Thread.sleep(1000);
                    //当前时间 单位毫秒
                    for (int i = 0; i < mModelList.size(); i++) {
                        long time = mModelList.get(i).getTimes() - 1000;
                        mModelList.get(i).setTimes(time);
                        Message message = new Message();
                        message.what = 1;
                        //发送信息给handler
                        mHandler.sendMessage(message);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
