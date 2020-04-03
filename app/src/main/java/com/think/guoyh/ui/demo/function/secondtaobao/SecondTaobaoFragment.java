package com.think.guoyh.ui.demo.function.secondtaobao;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondTaobaoFragment extends MyBaseFragment {


    private ImageView mSecondFloor;
    private RecyclerView mSecondFloorRlv;
    private FrameLayout mSecondFloorContent;
    private ClassicsHeader mClassics;
    private TwoLevelHeader mHeader;
    private FrameLayout mContentPanel;
    private ScrollView mScrollView;
    private SmartRefreshLayout mRefreshLayout;
    private Toolbar mToolbar;

    public SecondTaobaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_taobao, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mHeader.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getContext(), "淘宝二楼", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        mSecondFloor = (ImageView) view.findViewById(R.id.second_floor);
        mSecondFloorRlv = (RecyclerView) view.findViewById(R.id.second_floor_Rlv);
        mSecondFloorContent = (FrameLayout) view.findViewById(R.id.second_floor_content);
        mClassics = (ClassicsHeader) view.findViewById(R.id.classics);
        mHeader = (TwoLevelHeader) view.findViewById(R.id.header);
        mContentPanel = (FrameLayout) view.findViewById(R.id.contentPanel);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }
}
