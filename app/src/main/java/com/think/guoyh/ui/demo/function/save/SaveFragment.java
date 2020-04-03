package com.think.guoyh.ui.demo.function.save;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.gyh.baselib.utils.ImgUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.utils.ImageLoad;
import com.think.guoyh.widget.ShapeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton saveBt;
    private ShapeButton saveBt2;
    private ShapeButton saveBt3;
    private ImageView saveImg;
    private String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549382334&di=332b0aa1ec4ccd293f176164d998e5ab&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D121ef3421a38534398c28f62fb7ada0b%2Ffaf2b2119313b07eedb4502606d7912397dd8c96.jpg";
    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        saveBt = (ShapeButton) view.findViewById(R.id.saveBt);
        saveBt2 = (ShapeButton) view.findViewById(R.id.saveBt2);
        saveBt3 = (ShapeButton) view.findViewById(R.id.saveBt3);
        saveImg = (ImageView) view.findViewById(R.id.saveImg);

        saveBt.setOnClickListener(this);
        saveBt2.setOnClickListener(this);
        saveBt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBt:
                ImageLoad.load(getContext(),"http://dmimg.5054399.com/allimg/pkm/pk/22.jpg",saveImg);
                ImageUtils.saveImage(getContext(), "http://dmimg.5054399.com/allimg/pkm/pk/22.jpg");
                break;

            case R.id.saveBt2:
                savBitMap(false);
                break;

            case R.id.saveBt3:
                savBitMap(true);
                break;
        }
    }

    private void savBitMap(boolean is) {
        Handler myHandler = new Handler(Looper.getMainLooper());
        Glide.with(getContext()).asBitmap().load(imgUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        saveImg.setImageBitmap(resource);
                        ImageUtils.saveImage(getContext(),resource ,is);
                    }
                });
            }
        });

    }
}
