package com.think.guoyh.ui.suan.yiwei;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeButton;
import com.think.guoyh.widget.ShapeTextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class YiweiFragment extends MyBaseFragment implements View.OnClickListener {


    private EditText yiweiS1;
    private EditText yiweiS2;
    private ShapeButton yiweiBt;
    private ShapeTextView yiweiJie;

    public YiweiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yiwei, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        yiweiS1 = (EditText) view.findViewById(R.id.yiwei_s1);
        yiweiS2 = (EditText) view.findViewById(R.id.yiwei_s2);
        yiweiBt = (ShapeButton) view.findViewById(R.id.yiwei_Bt);
        yiweiJie = (ShapeTextView) view.findViewById(R.id.yiwei_jie);

        yiweiBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yiwei_Bt:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String s1 = yiweiS1.getText().toString().trim();
        if (TextUtils.isEmpty(s1)) {
            Toast.makeText(getContext(), "请输入字符串1", Toast.LENGTH_SHORT).show();
            return;
        }

        String s2 = yiweiS2.getText().toString().trim();
        if (TextUtils.isEmpty(s2)) {
            Toast.makeText(getContext(), "请输入字符串2", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        boolean b = example(s1, s2);
        if (b){
            yiweiJie.setText("true");
        }else{
            yiweiJie.setText("false");
        }

    }

    private boolean example(String s1, String s2) {
        char[] chars2 = s2.toCharArray();
        char[] chars1 = s1.toCharArray();
        TreeMap<Character,Integer> trMap = new TreeMap<>();
        if (chars2.length == chars1.length) {
            for (int i = 0; i < chars1.length; i++) {
                Integer value=trMap.get(chars1[i]);
                int count=0;//用于记录次数
                if(value!=null) {
                    count=value;
                }
                count++;
                trMap.put(chars1[i], count);
            }
            for (int i = 0; i < chars2.length; i++) {
                Integer value=trMap.get(chars2[i]);
                int count=0;//用于记录次数
                if(value!=null) {
                    count=value;
                }
                //有的话 -1 没有为 0
                count--;
                trMap.put(chars2[i], count);
            }
            Set<Character> keyset=trMap.keySet();
            Iterator<Character> iterator = keyset.iterator();
            while (iterator.hasNext()){
                Character next = iterator.next();
                Integer integer = trMap.get(next);
                //如果有大于 0 说明有不相同的字符
                if (integer>0){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }


    }
}
