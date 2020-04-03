package com.think.guoyh.ui.mine;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.base.gyh.baselib.base.fragment.StateFragment;
import com.base.gyh.baselib.utils.MyLibUtils;
import com.base.gyh.baselib.utils.PermissUtils;
import com.base.gyh.baselib.widgets.view.RoundImageView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.think.guoyh.R;
import com.base.gyh.baselib.utils.BitmapUtil;
import com.think.guoyh.utils.HeardSelecterUtil;
import com.think.guoyh.utils.SDCardUtils;
import com.think.guoyh.widget.pop.center.SetHeardPop;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends StateFragment {
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;
    private ViewHolder holder;
    private BasePopupView setHeardPop;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(View view) {
        holder = new ViewHolder(view);
    }

    @Override
    protected void initListener() {
        holder.mine_heard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelecterPop();
            }
        });
    }

    private void showSelecterPop() {
        if (setHeardPop==null){
            setHeardPop = new XPopup.Builder(getContext())
                    .asCustom(new SetHeardPop(getContext(), new SetHeardPop.OnSetButtonListener() {
                        @Override
                        public void carm() {
                            //调用相机拍照的方法
                            cameraAndReadPermiss();

                        }

                        @Override
                        public void read() {
                            checkReadPermiss();
                        }
                    }));
        }
        setHeardPop.toggle();
    }

    //去拍照
    private void toCamera() {
        startActivityForResult(HeardSelecterUtil.toCamera(getActivity()), CODE_CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (SDCardUtils.hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), HeardSelecterUtil.IMAGE_FILE_NAME);
                    cropRawPhoto(HeardSelecterUtil.getImageContentUri(tempFile,getActivity()));
                } else {
                    Toast.makeText(getActivity(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }

                break;

            case CODE_RESULT_REQUEST:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(HeardSelecterUtil.mUriPath));
                    setImageToHeadView(intent, bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
                break;

            default:
                break;
        }
    }

    //去选择图片
    private void toPicter() {
        startActivityForResult(HeardSelecterUtil.toPicter(), CODE_GALLERY_REQUEST);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        startActivityForResult(HeardSelecterUtil.cropRawPhoto(uri), CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent, Bitmap b) {
        try {
            if (intent != null) {
//                Bitmap bitmap = imageZoom(b);//看个人需求，可以不压缩
                holder.mine_heard.setImageBitmap(b);
                File picFile = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
//                toggleLoading();
                // 把bitmap放置到文 件中
                // format 格式
                // quality 质量
                try {
                    b.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(picFile));
//                    Toast.makeText(getContext(), "头像上传中...", Toast.LENGTH_SHORT).show();
                    String s = BitmapUtil.compressImage(picFile.getAbsolutePath());
//                    presenterIml.setHeardImg(s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cameraAndReadPermiss() {
        PermissUtils.cameraAndReadPermiss(new PermissUtils.PermissCallBack() {
            @Override
            public void onSuccess() {
                toCamera();

            }

            @Override
            public void onFail() {
                Toast.makeText(getContext(), "权限获取失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkReadPermiss() {
        PermissUtils.readWritePermiss(new PermissUtils.PermissCallBack() {
            @Override
            public void onSuccess() {
                toPicter();
                Toast.makeText(getContext(), "读写权限获取成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                Toast.makeText(getContext(), "读写权限获取失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onEmptyRetryClicked() {

    }

    @Override
    public void onErrorRetryClicked() {

    }


    public class ViewHolder {
        public View rootView;
        public RoundImageView mine_heard;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mine_heard = (RoundImageView) rootView.findViewById(R.id.mine_heard);
        }

    }
}
