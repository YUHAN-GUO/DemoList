package com.think.guoyh.ui.demo.function.mmkv;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.mmkv.MMKV;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MMKVFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton mmkvSetName;
    private ShapeButton mmkvSetOld;
    private ShapeButton mmkvSetSex;
    private ShapeButton mmkvGetName;
    private ShapeButton mmkvGetOld;
    private ShapeButton mmkvGetSex;
    private MMKV kv;

    public MMKVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mmkv, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {
        kv = MMKV.defaultMMKV();

    }

    private void initView(View view) {
        mmkvSetName = (ShapeButton) view.findViewById(R.id.mmkv_setName);
        mmkvSetOld = (ShapeButton) view.findViewById(R.id.mmkv_setOld);
        mmkvSetSex = (ShapeButton) view.findViewById(R.id.mmkv_setSex);
        mmkvGetName = (ShapeButton) view.findViewById(R.id.mmkv_getName);
        mmkvGetOld = (ShapeButton) view.findViewById(R.id.mmkv_getOld);
        mmkvGetSex = (ShapeButton) view.findViewById(R.id.mmkv_getSex);

        mmkvSetName.setOnClickListener(this);
        mmkvSetOld.setOnClickListener(this);
        mmkvSetSex.setOnClickListener(this);
        mmkvGetName.setOnClickListener(this);
        mmkvGetOld.setOnClickListener(this);
        mmkvGetSex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mmkv_setName:
                kv.encode("name", "小明明");
                break;
            case R.id.mmkv_setOld:
                kv.encode("old", 18);

                break;
            case R.id.mmkv_setSex:
                kv.encode("isNan", true);

                break;
            case R.id.mmkv_getName:
                String name = kv.decodeString("name");
                mmkvGetName.setText(name);
                break;
            case R.id.mmkv_getOld:
                int old = kv.decodeInt("old",0);
                mmkvGetOld.setText("岁数"+old);
                break;
            case R.id.mmkv_getSex:
                boolean isNan = kv.decodeBool("isNan");
                mmkvGetSex.setText(isNan+"");
                break;
        }
    }
}
