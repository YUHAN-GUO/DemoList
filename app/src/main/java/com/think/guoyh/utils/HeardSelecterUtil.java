package com.think.guoyh.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.think.guoyh.BuildConfig;

import java.io.File;

/**
 * Created by GUO_YH on 2019/9/18 13:00
 */
public class HeardSelecterUtil {

    /* 头像文件 */
    public static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static final String CROP_IMAGE_FILE_NAME = "ZjwamUserHeard.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    private static final int CODE_SET_USER_MSG = 1001;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;
    private static String savePath = Environment.getExternalStorageDirectory().toString();
    public static Uri mUriPath;

    //去拍照
    public static Intent toCamera(Activity context) {

        Intent intent = null;
        // 判断存储卡是否可以用，可用进行存储
        if (SDCardUtils.hasSdcard()) {
            //设定拍照存放到自己指定的目录,可以先建好
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            Uri pictureUri;
            File pictureFile = new File(savePath, IMAGE_FILE_NAME);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureUri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID+".provider",pictureFile);
//                pictureUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", pictureFile);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureUri = Uri.fromFile(pictureFile);
            }
            if (intent != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        pictureUri);

            }
        }
        return intent;
    }
    /**
     * 裁剪原始的图片
     */
    public static Intent cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        //startActivityForResult(intent, CODE_RESULT_REQUEST); //直接调用此代码在小米手机有异常，换以下代码
        String mLinshi = System.currentTimeMillis() + CROP_IMAGE_FILE_NAME;
        File mFile = new File(savePath, mLinshi);
//        mHeadCachePath = mHeadCacheFile.getAbsolutePath();
        mUriPath = Uri.parse("file://" + mFile.getAbsolutePath());
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }
    //去选择图片
    public static Intent toPicter() {
// 设置文件类型    （在华为手机中不能获取图片，要替换代码）
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intentFromGallery;
    }

    public static Uri getImageContentUri(File imageFile,Activity activity) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return activity.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
