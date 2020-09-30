package com.think.guoyh.ui.demo.view.fish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.think.guoyh.R;


public class FishFragment extends Fragment {


    private ImageView imgFinish;
    private ImageView imgFinish2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fish, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imgFinish = (ImageView) view.findViewById(R.id.imgFinish);
        imgFinish2 = (ImageView) view.findViewById(R.id.imgFinish2);
        imgFinish.setImageDrawable(new FishDrawable2());
        imgFinish2.setImageDrawable(new FinishDrawable());
    }
}