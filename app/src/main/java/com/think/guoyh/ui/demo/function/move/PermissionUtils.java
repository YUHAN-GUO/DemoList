package com.think.guoyh.ui.demo.function.move;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.base.gyh.baselib.utils.Utils;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class PermissionUtils {
    private static final List<String> PERMISSIONS = getPermissions();

    private static PermissionUtils sInstance;

    private OnRationaleListener mOnRationaleListener;
    private SimpleCallback mSimpleCallback;
    private FullCallback mFullCallback;
    private ThemeCallback mThemeCallback;
    private Set<String> mPermissions;
    private List<String> mPermissionsRequest;
    private List<String> mPermissionsGranted;
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;

    //系统设置权限
    private static SimpleCallback sSimpleCallback4WriteSettings;
    //浮窗权限
    private static SimpleCallback sSimpleCallback4DrawOverlays;
    //访问使用情况权限
    private static SimpleCallback sSimpleCallback4UsageState;
    //开启安装应用程序权限
    private static SimpleCallback sSimpleCallback4Insall;

    /**
     * Return the permissions used in application.
     *
     * @return the permissions used in application
     */
    public static List<String> getPermissions() {
        return getPermissions(Utils.getApp().getPackageName());
    }

    /**
     * Return the permissions used in application.
     *
     * @param packageName The name of the package.
     * @return the permissions used in application
     */
    public static List<String> getPermissions(final String packageName) {
        PackageManager pm = Utils.getApp().getPackageManager();
        try {
            String[] permissions = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (permissions == null) return Collections.emptyList();
            return Arrays.asList(permissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Return whether <em>you</em> have been granted the permissions.
     *
     * @param permissions The permissions.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isGranted(final String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(final String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(Utils.getApp(), permission);
    }

    /**
     * Return whether the app can modify system settings.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(Utils.getApp());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestWriteSettings(final SimpleCallback callback) {
        if (isGrantedWriteSettings()) {
            if (callback != null) callback.onGranted();
            return;
        }
        sSimpleCallback4WriteSettings = callback;
        PermissionActivity.start(Utils.getApp(), PermissionActivity.TYPE_WRITE_SETTINGS);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private static void startWriteSettingsActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Return whether the app can draw on top of other apps.
     * 是否开启浮窗权限
     *
     * @return {@code true}: yes<br>{@code false}: no
     */

    public static boolean isGrantedDrawOverlays() {
        boolean b = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            b = Settings.canDrawOverlays(Utils.getApp());
        } else {
            b = true;
        }
        return b;
    }


    /**
     * 是否开启 访问使用情况权限
     * PACKAGE_USAGE_STATS
     */
    public static boolean isGrantedUsageStats() {
        boolean granted = false;
        AppOpsManager appOps = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            appOps = (AppOpsManager) Utils.getApp()
                    .getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            mode = appOps.checkOpNoThrow("android:get_usage_stats",
                    Process.myUid(), Utils.getApp().getPackageName());
            granted = mode == AppOpsManager.MODE_ALLOWED;
        } else {
            granted = true;
        }

        return granted;
    }

    /**
     * 开启 访问情况权限
     *
     * @param callback
     */
    public static void requestUsageStats(final SimpleCallback callback) {
        if (isGrantedUsageStats()) {
            if (callback != null) callback.onGranted();
            return;
        }
        sSimpleCallback4UsageState = callback;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionActivity.start(Utils.getApp(), PermissionActivity.TYPE_USAGE_STATS);
        }
    }

    /**
     * 是否开启安装应用权限
     *
     * @return
     */
    public static boolean isGrantedCanInstall() {
        boolean b = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            b = Utils.getApp().getPackageManager().canRequestPackageInstalls();
        }
        Logger.d("%s++++++++%s", "guoyh", b);
        return b;

    }


    /**
     * 开启安装应用程序权限
     */
    public static void requestInsallApp(final SimpleCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isGrantedCanInstall()) {
                if (callback != null) callback.onGranted();
                return;
            }
        }

        sSimpleCallback4Insall = callback;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionActivity.start(Utils.getApp(), PermissionActivity.TYPE_INSTALL);
        }

    }

    /**
     * 开启浮窗权限
     *
     * @param callback
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestDrawOverlays(final SimpleCallback callback) {
        if (isGrantedDrawOverlays()) {
            if (callback != null) callback.onGranted();
            return;
        }
        sSimpleCallback4DrawOverlays = callback;
        PermissionActivity.start(Utils.getApp(), PermissionActivity.TYPE_DRAW_OVERLAYS);
    }

    /**
     * 前往 浮窗权限 开启界面
     *
     * @param activity
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void startOverlayPermissionActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 前往 访问使用权限 开启界面
     *
     * @param activity
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void startUsageStatusPermissionActivity(final Activity activity, final int requestCode) {
        Logger.d("%s++++++++++++%s", "guoyh", "startUsageStatusPermissionActivity");
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }


    public static void startCanInstallPermissActivity(final Activity activity, final int requestCode) {
        Logger.d("%s++++++++%s","guoyh","startCanInstallPermissActivity");
        Uri packageUri = Uri.parse("package:" + Utils.getApp().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
        activity.startActivityForResult(intent, requestCode);

    }

    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) return;
        Utils.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * Set the permissions.
     *
     * @param permissions The permissions.
     * @return the single {@link PermissionUtils} instance
     */
    public static PermissionUtils permission(final String... permissions) {
        return new PermissionUtils(permissions);
    }

    private static boolean isIntentAvailable(final Intent intent) {
        return Utils.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    private PermissionUtils(final String... permissions) {
        mPermissions = new LinkedHashSet<>();
        for (String permission : permissions) {
            for (String aPermission : PermissionConstants.getPermissions(permission)) {
                if (PERMISSIONS.contains(aPermission)) {
                    mPermissions.add(aPermission);
                }
            }
        }
        sInstance = this;
    }


    /**
     * Set rationale listener.
     *
     * @param listener The rationale listener.
     * @return the single {@link PermissionUtils} instance
     */
    public PermissionUtils rationale(final OnRationaleListener listener) {
        mOnRationaleListener = listener;
        return this;
    }

    /**
     * Set the simple call back.
     *
     * @param callback the simple call back
     * @return the single {@link PermissionUtils} instance
     */
    public PermissionUtils callback(final SimpleCallback callback) {
        mSimpleCallback = callback;
        return this;
    }

    /**
     * Set the full call back.
     *
     * @param callback the full call back
     * @return the single {@link PermissionUtils} instance
     */
    public PermissionUtils callback(final FullCallback callback) {
        mFullCallback = callback;
        return this;
    }

    /**
     * Set the theme callback.
     *
     * @param callback The theme callback.
     * @return the single {@link PermissionUtils} instance
     */
    public PermissionUtils theme(final ThemeCallback callback) {
        mThemeCallback = callback;
        return this;
    }

    /**
     * Start request.
     */
    public void request() {
        mPermissionsGranted = new ArrayList<>();
        mPermissionsRequest = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionsGranted.addAll(mPermissions);
            requestCallback();
        } else {
            for (String permission : mPermissions) {
                if (isGranted(permission)) {
                    mPermissionsGranted.add(permission);
                } else {
                    mPermissionsRequest.add(permission);
                }
            }
            if (mPermissionsRequest.isEmpty()) {
                requestCallback();
            } else {
                startPermissionActivity();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startPermissionActivity() {
        mPermissionsDenied = new ArrayList<>();
        mPermissionsDeniedForever = new ArrayList<>();
        PermissionActivity.start(Utils.getApp(), PermissionActivity.TYPE_RUNTIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean rationale(final Activity activity) {
        boolean isRationale = false;
        if (mOnRationaleListener != null) {
            for (String permission : mPermissionsRequest) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    getPermissionsStatus(activity);
                    mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest() {
                        @Override
                        public void again(boolean again) {
                            activity.finish();
                            if (again) {
                                startPermissionActivity();
                            } else {
                                requestCallback();
                            }
                        }
                    });
                    isRationale = true;
                    break;
                }
            }
            mOnRationaleListener = null;
        }
        return isRationale;
    }

    private void getPermissionsStatus(final Activity activity) {
        for (String permission : mPermissionsRequest) {
            if (isGranted(permission)) {
                mPermissionsGranted.add(permission);
            } else {
                mPermissionsDenied.add(permission);
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    mPermissionsDeniedForever.add(permission);
                }
            }
        }
    }

    private void requestCallback() {
        if (mSimpleCallback != null) {
            if (mPermissionsRequest.size() == 0
                    || mPermissions.size() == mPermissionsGranted.size()) {
                mSimpleCallback.onGranted();
            } else {
                if (!mPermissionsDenied.isEmpty()) {
                    mSimpleCallback.onDenied();
                }
            }
            mSimpleCallback = null;
        }
        if (mFullCallback != null) {
            if (mPermissionsRequest.size() == 0
                    || mPermissions.size() == mPermissionsGranted.size()) {
                mFullCallback.onGranted(mPermissionsGranted);
            } else {
                if (!mPermissionsDenied.isEmpty()) {
                    mFullCallback.onDenied(mPermissionsDeniedForever, mPermissionsDenied);
                }
            }
            mFullCallback = null;
        }
        mOnRationaleListener = null;
        mThemeCallback = null;
    }

    private void onRequestPermissionsResult(final Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class PermissionActivity extends Activity {

        private static final String TYPE = "TYPE";
        public static final int TYPE_RUNTIME = 0x01;
        public static final int TYPE_WRITE_SETTINGS = 0x02;
        public static final int TYPE_DRAW_OVERLAYS = 0x03;
        public static final int TYPE_USAGE_STATS = 0x04;
        public static final int TYPE_INSTALL = 0x05;

        public static void start(final Context context, int type) {
            Intent starter = new Intent(context, PermissionActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.putExtra(TYPE, type);
            context.startActivity(starter);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
            int byteExtra = getIntent().getIntExtra(TYPE, TYPE_RUNTIME);
            if (byteExtra == TYPE_RUNTIME) {
                if (sInstance == null) {
                    super.onCreate(savedInstanceState);
                    Log.e("PermissionUtils", "request permissions failed");
                    finish();
                    return;
                }
                if (sInstance.mThemeCallback != null) {
                    sInstance.mThemeCallback.onActivityCreate(this);
                }
                super.onCreate(savedInstanceState);

                if (sInstance.rationale(this)) {
                    return;
                }
                if (sInstance.mPermissionsRequest != null) {
                    int size = sInstance.mPermissionsRequest.size();
                    if (size <= 0) {
                        finish();
                        return;
                    }
                    requestPermissions(sInstance.mPermissionsRequest.toArray(new String[size]), 1);
                }
            } else if (byteExtra == TYPE_WRITE_SETTINGS) {
                super.onCreate(savedInstanceState);
                startWriteSettingsActivity(this, TYPE_WRITE_SETTINGS);
            } else if (byteExtra == TYPE_DRAW_OVERLAYS) {
                super.onCreate(savedInstanceState);
                startOverlayPermissionActivity(this, TYPE_DRAW_OVERLAYS);
            } else if (byteExtra == TYPE_USAGE_STATS) {
                super.onCreate(savedInstanceState);
                startUsageStatusPermissionActivity(this, TYPE_USAGE_STATS);
            } else if (byteExtra == TYPE_INSTALL) {
                super.onCreate(savedInstanceState);
                startCanInstallPermissActivity(this, TYPE_INSTALL);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (sInstance != null && sInstance.mPermissionsRequest != null) {
                sInstance.onRequestPermissionsResult(this);
            }
            finish();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            finish();
            return true;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == TYPE_WRITE_SETTINGS) {
                if (sSimpleCallback4WriteSettings == null) return;
                if (isGrantedWriteSettings()) {
                    sSimpleCallback4WriteSettings.onGranted();
                } else {
                    sSimpleCallback4WriteSettings.onDenied();
                }
                sSimpleCallback4WriteSettings = null;
            } else if (requestCode == TYPE_DRAW_OVERLAYS) {
                if (sSimpleCallback4DrawOverlays == null) return;
                Utils.runOnUiThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isGrantedDrawOverlays()) {
                            sSimpleCallback4DrawOverlays.onGranted();
                        } else {
                            sSimpleCallback4DrawOverlays.onDenied();
                        }
                        sSimpleCallback4DrawOverlays = null;
                    }
                }, 100);
            } else if (requestCode == TYPE_USAGE_STATS) {
                if (sSimpleCallback4UsageState == null) return;
                if (isGrantedUsageStats()) {
                    sSimpleCallback4UsageState.onGranted();
                } else {
                    sSimpleCallback4UsageState.onDenied();
                }
                sSimpleCallback4UsageState = null;
            } else if (requestCode == TYPE_INSTALL) {
                Logger.d("%s++++++++++++%s","guoyh","回调");
                if (sSimpleCallback4Insall == null) return;
                if (isGrantedCanInstall()) {
                    sSimpleCallback4Insall.onGranted();
                } else {
                    sSimpleCallback4Insall.onDenied();
                }
                sSimpleCallback4Insall = null;
            }

            finish();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnRationaleListener {

        void rationale(ShouldRequest shouldRequest);

        interface ShouldRequest {
            void again(boolean again);
        }
    }

    public interface SimpleCallback {
        void onGranted();

        void onDenied();
    }

    public interface FullCallback {
        void onGranted(List<String> permissionsGranted);

        void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }
}
