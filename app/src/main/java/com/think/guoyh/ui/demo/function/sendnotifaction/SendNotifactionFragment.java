package com.think.guoyh.ui.demo.function.sendnotifaction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNotifactionFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton sendNoti;

    public SendNotifactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_notifaction, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        sendNoti = (ShapeButton) view.findViewById(R.id.sendNoti);

        sendNoti.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendNoti:
                NotificationUtils.getInsance(getContext()).sendNotification("测试Title","ContentContent",null,true);
                break;
        }
    }
}
