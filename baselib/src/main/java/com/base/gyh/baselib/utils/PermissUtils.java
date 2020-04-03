package com.base.gyh.baselib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.base.gyh.baselib.adapter.permission.CheckRequestPermissionAdapter;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;

import javax.security.auth.callback.Callback;

/**
 * Created by GUOYH on 2019/5/24.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class PermissUtils {
    /**
     * 存储权限
     *  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void readWritePermiss(PermissCallBack callBack) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        callBack.onSuccess();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        super.onPermissionDenied(refusedPermissions);
                        callBack.onFail();
                    }
                });
    }

    /**
     * 位置权限
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
     */
    public static void locationPermiss(final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION),
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        Toast.makeText(activity, "位置权限获取成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 日历权限
     * <uses-permission android:name="android.permission.WRITE_CALENDAR"/>
     *     <uses-permission android:name="android.permission.READ_CALENDAR"/>
     */
    public static void calendarPermiss(final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR),
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        Toast.makeText(activity, "日历权限获取成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 相机权限
     *     <uses-permission android:name="android.permission.CAMERA"/>
     */
    public static void cameraPermiss(PermissCallBack callBack) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.CAMERA,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        callBack.onSuccess();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        super.onPermissionDenied(permission);
                        callBack.onFail();
                    }
                });
    } /**
     * 相机 和读写 权限
     *     <uses-permission android:name="android.permission.CAMERA"/>
     */
    public static void cameraAndReadPermiss(PermissCallBack callBack) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA),
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        callBack.onSuccess();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        callBack.onFail();
                    }
                });
    }
    /**
     * 信息权限
     *     <uses-permission android:name="android.permission.SEND_SMS"/>
     */
    public static void smsPermiss(final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.SEND_SMS,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Toast.makeText(activity, "信息权限获取成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    /**
     * 传感器权限
     *     <uses-permission android:name="android.permission.BODY_SENSORS"/>
     */
    public static void bodySensorsPermiss (final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.BODY_SENSORS,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Toast.makeText(activity, "传感器权限获取成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    /**
     * 麦克风权限
     *     <uses-permission android:name="android.permission.RECORD_AUDIO"/>
     */
    public static void recordAudioPermiss(final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.RECORD_AUDIO,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Toast.makeText(activity, "麦克风权限获取成功", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    /**
     * 拨打指定电话
     *     <uses-permission android:name="android.permission.CALL_PHONE"/>
     */
    public static void makeCall(final Activity activity, final int requestCode, final String phoneNumber) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.CALL_PHONE,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + phoneNumber);
                        intent.setData(data);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                });
    }

    /**
     * 选择联系人
     *     <uses-permission android:name="android.permission.READ_CONTACTS"/>
     */
    public static void chooseContact(final Activity activity, final int requestCode) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_CONTACTS,
                new CheckRequestPermissionAdapter() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), requestCode);
                    }
                });
    }

    /**
     * 与选择联系人搭配使用
     * 使用：
     * //返回码的判断： chooseContact(activity,requestCode)
     * <p>
     * 重写 onActivityResult
     * if (requestCode==requestCode){
     * Uri uri = data.getData();
     * String[] contacts=PermissUtils.getPhoneContacts(this,uri);
     * contacts[0] //联系人姓名
     * contacts[1]// 联系人号码
     *
     * @param activity
     * @param uri
     * @return
     */
    public static String[] getPhoneContacts(Activity activity, Uri uri) {
        String[] contact = new String[2]; //得到ContentResolver对象
        ContentResolver cr = activity.getContentResolver(); //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst(); //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex); //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    private PermissCallBack callBack;
    public interface PermissCallBack{
        void onSuccess();
        void onFail();
    }
}
