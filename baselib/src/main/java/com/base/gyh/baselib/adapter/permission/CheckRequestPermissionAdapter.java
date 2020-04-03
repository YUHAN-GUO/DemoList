package com.base.gyh.baselib.adapter.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

/**
 * Created by GUOYH on 2019/5/11.
 */
public class CheckRequestPermissionAdapter implements CheckRequestPermissionListener, CheckRequestPermissionsListener {
    @Override
    public void onPermissionOk(Permission permission) {
        Activity activity = SoulPermission.getInstance().getTopActivity();
        Toast.makeText(activity, permission.permissionName+"获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenied(Permission permission) {
        //SoulPermission提供栈顶Activity
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null == activity) {
            return;
        }
        String permissionDesc = permission.getPermissionNameDesc();
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //去设置页
                        SoulPermission.getInstance().goPermissionSettings();
                    }
                }).create().show();
    }

    @Override
    public void onAllPermissionOk(Permission[] allPermissions) {

    }

    @Override
    public void onPermissionDenied(Permission[] refusedPermissions) {
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null == activity) {
            return;
        }
        for (int i = 0; i < refusedPermissions.length; i++) {
            String permissionDesc = refusedPermissions[i].getPermissionNameDesc();
            new AlertDialog.Builder(activity)
                    .setTitle("提示")
                    .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //去设置页
                            SoulPermission.getInstance().goPermissionSettings();
                        }
                    }).create().show();
        }
    }
}
