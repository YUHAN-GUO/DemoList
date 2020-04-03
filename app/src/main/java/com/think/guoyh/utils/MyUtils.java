package com.think.guoyh.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.base.gyh.baselib.utils.OpenFileUtils;
import com.base.gyh.baselib.utils.Utils;

import java.io.File;

/**
 * 只适用 该项目
 */
public class MyUtils {
    private Context context;
    private MyUtils() {
        context = Utils.getApp();
    }

    private volatile static MyUtils mInstance;

    public static MyUtils getInstance() {
        if (mInstance == null) {
            mInstance = new MyUtils();
        }
        return mInstance;
    }

    public boolean isImage(String url) {
        if (url == null) {
            return false;
        }
        if (isGif(url)) {
            return true;
        }
        if (isJpg(url)) {
            return true;
        }
        if (isPng(url)) {
            return true;
        }
        return false;
    }

    public boolean isGif(String file) {
        if (file.toLowerCase().endsWith(".gif")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isJpg(String file) {
        if (file.toLowerCase().endsWith(".jpg")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPng(String file) {
        if (file.toLowerCase().endsWith(".png")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public int packageCode() {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
    public String getChannel(){
        //测试多渠道打包
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String app_version = appInfo.metaData.getString("UMENG_CHANNEL");
        return app_version;
    }
    /**
     * 获取当前本地apk的版本
     *
     * @return
     */
    public  int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 获取版本号名称
     *
     * @return
     */
    public  String getVerName() {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
    public String getPhoneInfo() {
        PackageManager pm = Utils.getApp().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(Utils.getApp().getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
//        //App版本
//        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append("_");
        sb.append(pi.versionCode + ",");

        //Android版本号
//        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT + ",");

        //手机制造商
//        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + ",");

        //手机型号
//        sb.append("Model: ");
        sb.append(Build.MODEL);

//        //CPU架构
//        sb.append("CPU: ");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
//        } else {
//            sb.append(Build.CPU_ABI);
//        }
        return sb.toString();
    }
}
