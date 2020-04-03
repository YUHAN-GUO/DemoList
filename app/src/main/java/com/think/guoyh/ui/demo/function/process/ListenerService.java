package com.think.guoyh.ui.demo.function.process;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.ui.demo.function.move.ProcessUtils;


public class ListenerService extends Service {
    public static final String TASK_PACKNAME = "taskPackName";
    public static final String TASK_COM = "taskCom";
        private  boolean flag = true;//服务Destroy结束线程
        private String taskPackName = "com.ttxmzq.pandamakemoney.asjz";
        //在这个三方应用里待了多少次 1次 0.5秒
        private int timeFlag = 0;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.dd("onStartCommand");
        String taskPackName = intent.getStringExtra(TASK_PACKNAME);
        if (!TextUtils.isEmpty(taskPackName)){
            this.taskPackName = taskPackName;
        }
        return super.onStartCommand(intent, flags, startId);

    }

    Handler my = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Logger.d("%s++++++++++++++%s","guoyh","任务完成 ");
            Toast.makeText(ListenerService.this, "任务完成", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
        public void onCreate() {
            super.onCreate();
            //监视任务栈最顶端应用
//            am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (flag) {
                        synchronized (ListenerService.class) {
                            String processName = ProcessUtils.getForegroundProcessName();
                            Logger.d("%s++++++++++++++%s","guoyh","processName "+ processName+"------taskPackName"+taskPackName+"------------"+timeFlag);
                            if (processName.equals(taskPackName)){
                                timeFlag++;
                                if (timeFlag>=10){
                                    timeFlag = 0;
                                    flag = false;
                                    my.sendEmptyMessage(1);
                                }
                            }else{
                                timeFlag = 0;
                            }


                            SystemClock.sleep(500);
                        }
                    }
                }
            }.start();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            flag = false;
            timeFlag = 0;

        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
}
