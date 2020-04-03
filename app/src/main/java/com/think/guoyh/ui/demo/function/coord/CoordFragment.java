package com.think.guoyh.ui.demo.function.coord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.data.bean.MenuBean;
import com.think.guoyh.ui.MenuRlvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoordFragment extends MyBaseFragment {


    private MyToolbar toolBar;
    private RecyclerView coorRlv;

    public CoordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coord, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        toolBar = (MyToolbar) view.findViewById(R.id.toolbar);
        toolBar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
            @Override
            public void onClick(int type) {
                if (type == MyToolbar.BACK){
                    getActivity().finish();
                }
            }
        });
        coorRlv = (RecyclerView) view.findViewById(R.id.coorRlv);
        List<MenuBean> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(new MenuBean("123",i));
        }

        MenuRlvAdapter adapter = new MenuRlvAdapter(datas);
        coorRlv.setAdapter(adapter);
        coorRlv.setLayoutManager(getLinearLayoutManger());


    }
}
