package com.think.guoyh.ui.demo.function.loadinganim;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadAnimFragment extends MyBaseFragment {


    public LoadAnimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_load_anim, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View view){
    }

    @Override
    protected void loadData() {

    }
}
