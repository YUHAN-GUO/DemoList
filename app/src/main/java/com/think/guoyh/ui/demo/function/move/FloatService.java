package com.think.guoyh.ui.demo.function.move;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;

public class FloatService extends Service {
    private WindowManager mWindowManager;
    private AVCallFloatView mFloatingView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //设置WindowManger布局参数以及相关属性
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        //初始化位置
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 10;
        params.y = 100;
        //获取WindowManager对象
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

//        MoveLayout moveLayout = (MoveLayout) LayoutInflater.from(getApplication()).inflate(R.layout.float_layout,null,false);
//        moveLayout.setLayoutParams(params);
        mFloatingView = new AVCallFloatView(FloatService.this);
        mFloatingView.setParams(params);
        mFloatingView.setIsShowing(true);
        mFloatingView.setOnClickListener(new AVCallFloatView.OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(FloatService.this, "你点击了我", Toast.LENGTH_SHORT).show();
            }
        });
        mWindowManager.addView(mFloatingView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.dd("Destory");
        if (mFloatingView!=null){
            mWindowManager.removeView(mFloatingView);
        }
    }
}
