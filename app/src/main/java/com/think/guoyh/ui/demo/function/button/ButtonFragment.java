package com.think.guoyh.ui.demo.function.button;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton myButton;

    public ButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        myButton = (ShapeButton) view.findViewById(R.id.myButton);

        myButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myButton:
                myButton.setClick();
                Logger.d("%s++++++++++++++++%s","guoyh2",myButton.isClick());
                break;
        }
    }

}
