package com.think.guoyh.ui.demo.function.save;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telecom.Call;
import android.widget.Toast;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.lxj.xpermission.PermissionConstants;
import com.lxj.xpermission.XPermission;
import com.lxj.xpopup.util.XPopupUtils;
import com.think.guoyh.utils.imgeloader.ImageLoader;
import com.toomee.mengplus.common.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static void checkPermiss(Context context,IBaseHttpResultCallBack<Object> callBack){
        XPermission.create(context, PermissionConstants.STORAGE)
                .callback(new XPermission.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        //save bitmap to album.
                        callBack.onSuccess(null);
                    }
                    @Override
                    public void onDenied() {
                        Toast.makeText(context, "没有保存权限，保存功能无法使用！", Toast.LENGTH_SHORT).show();
                    }
                }).request();
    }


    public static void saveImage(Context context,String path){
        checkPermiss(context,new IBaseHttpResultCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                ImageLoader imageLoader = new ImageLoader();
                XPopupUtils.saveBmpToAlbum(context, imageLoader, path);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public static void saveImage(Context context, Bitmap bitmap,boolean is){
        checkPermiss(context,new IBaseHttpResultCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                saveBmp2Gallery(context,bitmap,is);
//
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private static File mPhotoFile = null;
//    public static void setPhotoFile(File photoFile){
//        mPhotoFile = photoFile;
//    }

    /**
     * 保存图片到图库
     */
    private static void saveImageToGallery(Context context,Bitmap bmp ) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "yingtan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        MediaStore.Images.Media.insertImage(context.getContentResolver(),bmp,fileName,null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
//        setPhotoFile(file);
    }

    /**
     * @param bmp 获取的bitmap数据
     *            is true 保存到相册；
     */
    public static void saveBmp2Gallery(Context context,Bitmap bmp,boolean is) {
        if (is){
            saveToCarmera(context,bmp);
        }else{
            saveImageToGallery(context,bmp);
        }
    }

    private static void saveToCarmera(Context context, Bitmap bmp) {
        Logger.d("%s+++++++++++++++%s","guoyh3","保存到相册");
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, "123" + ".jpg");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        }catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaStore.Images.Media.insertImage(context.getContentResolver(),bmp,fileName,null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
    }
}
