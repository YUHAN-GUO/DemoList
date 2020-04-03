package com.think.guoyh.ui.demo.function.process;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.ui.demo.function.move.PermissionUtils;
import com.think.guoyh.ui.demo.function.move.ProcessUtils;
import com.think.guoyh.widget.ShapeButton;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton processBt1;
    private ShapeButton processBt2;
    private ShapeButton processBt3;
    private ShapeButton closeService;

    public ProcessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_process, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        processBt1 = (ShapeButton) view.findViewById(R.id.process_bt1);
        processBt2 = (ShapeButton) view.findViewById(R.id.process_bt2);
        processBt3 = (ShapeButton) view.findViewById(R.id.process_bt3);
        closeService = (ShapeButton) view.findViewById(R.id.closeService);

        processBt1.setOnClickListener(this);
        processBt2.setOnClickListener(this);
        processBt3.setOnClickListener(this);
        closeService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.process_bt1:
                checkUsagePermiss();
                break;
            case R.id.process_bt2:
                getProcessInfo();
                break;
            case R.id.process_bt3:
                startListenerService(true);
                break;
                case R.id.closeService:
                startListenerService(false);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startListenerService(false);
    }

    private void startListenerService(boolean b) {
        Logger.d("%s+++++++++++++%s", "guoyh", "开启监听");
        Intent intent = new Intent(getActivity(), ListenerService.class);
        intent.putExtra(ListenerService.TASK_PACKNAME, "com.tencent.qqlive");
        if (b) {
            getActivity().startService(intent);
        } else {
            getActivity().stopService(intent);
        }
    }


    private void getProcessInfo() {
        Set<String> allBackgroundProcesses = ProcessUtils.getAllBackgroundProcesses();
        Logger.d("%s+++++%s","guoyh","全部后台进程");
        for (String allBackgroundProcess : allBackgroundProcesses) {
            Logger.d("%s+++++%s","guoyh",allBackgroundProcess);
        }
        Logger.d("%s+++++%s","guoyh","currentProcessName");
        String currentProcessName = ProcessUtils.getCurrentProcessName();
        Logger.d("%s++++++++%s","guoyhCurr",currentProcessName);

        Logger.d("%s+++++%s","guoyh","foregroundProcessName");
        String foregroundProcessName = ProcessUtils.getForegroundProcessName();
        Logger.d("guoyhFor+++++++%s",foregroundProcessName);
        boolean mainProcess = ProcessUtils.isMainProcess();
        Logger.d("guoyh+++++++%s","是不是当前进程"+mainProcess);
    }

    //检查权限    <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions" />
    private void checkUsagePermiss() {
        if (PermissionUtils.isGrantedUsageStats()) {
            Toast.makeText(getContext(), "权限申请成功", Toast.LENGTH_SHORT).show();
        } else {
            openUsage();
        }
    }

    private void openUsage() {
        PermissionUtils.requestUsageStats(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                checkUsagePermiss();

            }

            @Override
            public void onDenied() {
                Toast.makeText(getActivity(), "您拒绝开启访问情况 权限", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
