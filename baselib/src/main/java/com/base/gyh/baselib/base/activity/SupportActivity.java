package com.base.gyh.baselib.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.base.gyh.baselib.broadcast.NetBroadcastReceiver;
import com.base.gyh.baselib.utils.ActivityUtil;
import com.base.gyh.baselib.utils.ButtonUtils;
import com.base.gyh.baselib.utils.NetworkUtil;
import com.base.gyh.baselib.utils.RlvMangerUtil;
import com.base.gyh.baselib.widgets.dialog.pop.LoadingPop;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * Created by GUO_YH on 2018/11/3 16:37
 * //所有 的Activity 的基类
 */
public abstract class SupportActivity extends RxAppCompatActivity implements NetBroadcastReceiver.NetChangeListener {

    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    private FragmentManager mFragmentManager;//添加fragment 事务管理器
    private BasePopupView loadingPop;


    /**
     * 必须重写setContentView注意不能够添加这行代码 目的将当前界面的布局添加到BaseActivity中去
     * super.setContentView(R.layout.activity_base);
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    // 封装公共的添加Fragment的方法
    public void addFragment(Class<? extends Fragment> zClass, int layoutId) {
        addFragment(zClass, layoutId, null);
    }

    public void addFragment(Class<? extends Fragment> zClass, int layoutId, Bundle bundle) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tag = zClass.getName();

        // 从 fragmentManager中查找这个fragment是否存在，
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        if (fragment != null) { // 如果存在就不用重新创建
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            if (fragment.isAdded()) { // 如果 fragment 已经被添加
                if (fragment.isHidden()) { // 如果fragment 已经被添加，并且处于隐藏状态，那么显示
                    transaction.show(fragment); // 显示 fragment
                    hideOtherPage(transaction, fragment); // 隐藏其他fragment
                }
            } else { // 如果 fragment存在，但是没有被添加到activity，那么执行下面添加，（这种一般发生在activity 横竖屏切换）
                transaction.add(layoutId, fragment);
                hideOtherPage(transaction, fragment);
            }
        } else {
            // 如果没有从fragmentManager 中通过tag 找到fragment,那么创建一个新的fragment 实例
            try {
                fragment = zClass.newInstance();
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (fragment != null) {
                transaction.add(layoutId, fragment, tag);
                hideOtherPage(transaction, fragment);
            }
        }

        transaction.commit();
    }

    // 隐藏除了将要显示的fragment 以外的其他所有fragment
    private void hideOtherPage(FragmentTransaction transaction, Fragment willShowFragment) {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != willShowFragment) {
                transaction.hide(fragment);
            }
        }

    }
    public LinearLayoutManager getLinearLayoutManger(){
        return RlvMangerUtil.getInstance().getLinearLayoutManager(this);
    }
    public LinearLayoutManager getHorLinearLayoutManager(){
        return RlvMangerUtil.getInstance().getHorLinearLayoutManager(this);
    }
    public GridLayoutManager getGridLayoutManager(int spanCount){
        return RlvMangerUtil.getInstance().getGridLayoutManager(this,spanCount);
    }
    public void startActivity(Class<? extends Activity> zClass) {
        startActivity(new Intent(this, zClass));
    }

    public void startActivity(Class<? extends Activity> zClass, int type, String key) {
        Intent intent = new Intent(this, zClass);
        intent.putExtra(key, type);
        startActivity(intent);
    }
    protected void startActivity(Class<? extends Activity> zClass, boolean type, String key) {
        Intent intent = new Intent(this, zClass);
        intent.putExtra(key, type);
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> zClass, Bundle bundle) {
        startActivity(new Intent(this, zClass).putExtras(bundle));
    }

    protected void startActivityForResult(Class<? extends Activity> zClass, int coode) {
        startActivityForResult(new Intent(this, zClass), coode);
    }

    protected void startActivityForResult(Class<? extends Activity> zClass, Bundle bundle, int coode) {
        startActivityForResult(new Intent(this, zClass).putExtras(bundle), coode);
    }

    public interface CanDataCallBack{
        void onCanData();
        void onNoNet();
    }
    public void canGetData(CanDataCallBack callBack){
        if (NetworkUtil.isNetworkConnected(this)){
            callBack.onCanData();
        }else{
            Toast.makeText(this, "没有网络，请重试", Toast.LENGTH_SHORT).show();
            callBack.onNoNet();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
        if (netWorkState) {
//            Toast.makeText(this, "有网toast在BaseActivity", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "没网toast在BaseActivity", Toast.LENGTH_SHORT).show();

        }
    }

    public void finishAll() {
        ActivityUtil.getInstance().exitSystem();
    }


    /**
     * 判断是否有网
     *
     * @return
     */
    public boolean getNetIs() {
        return NetworkUtil.isNetworkConnected(this);
    }

    /**
     * 加载的popuWindow
     */
    public void toggleLoading() {
        if (loadingPop == null) {
            loadingPop = new XPopup.Builder(SupportActivity.this)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(SupportActivity.this));
        }
        loadingPop.toggle();
    }
    public void toggleLoading(boolean is) {
        if (loadingPop == null) {
            loadingPop = new XPopup.Builder(SupportActivity.this)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(SupportActivity.this));
        }
        if (is){
            loadingPop.show();
        }else{
            loadingPop.dismiss();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意：这里的setContentView必须有super才可以，需要走父类方法
//        super.setContentView(R.layout.activity_base);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView()
//                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);
        // 初始化netEvent
        netEvent = this;
        // 执行初始化方法
        NetBroadcastReceiver.registerReceiver(this);//网络变化广播注册
        ActivityUtil.getInstance().addActivity(this);
        mFragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();K
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        NetBroadcastReceiver.unregisterReceiver(this);//网络变化
        super.onDestroy();
    }

    /**
     * 防暴力点击
     * @return
     */
    protected boolean onBtClick(){
        return ButtonUtils.onClick();
    }

    public Boolean isSuccess(int code){
        if (code == 200){
            return true;
        }else{
            return false;
        }
    }

}
