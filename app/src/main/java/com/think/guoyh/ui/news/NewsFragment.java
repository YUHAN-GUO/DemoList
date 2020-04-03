package com.think.guoyh.ui.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.fragment.StateFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.think.guoyh.R;
import com.think.guoyh.data.Const;
import com.think.guoyh.data.bean.MenuBean;
import com.think.guoyh.ui.MenuRlvAdapter;
import com.think.guoyh.ui.demo.DemoActivity;
import com.think.guoyh.ui.suan.SuanActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends StateFragment {


    private RecyclerView newsRlv;
    private RecyclerView rlv;
    private MenuRlvAdapter menuRlvAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    protected void loadData() {
        List<MenuBean> data = new ArrayList<>();
        data.add(new MenuBean("是否为异位词", Const.SuanDemo.YIWEIDEMO));

        menuRlvAdapter.setNewData(data);
    }

    @Override
    protected void initView(View view) {
        rlv = view.findViewById(R.id.newsRlv);
        menuRlvAdapter = new MenuRlvAdapter(null);
        rlv.setAdapter(menuRlvAdapter);
        rlv.setLayoutManager(getLinearLayoutManger());
    }

    @Override
    protected void initListener() {
        menuRlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuBean item = (MenuBean) adapter.getItem(position);
                SuanActivity.toStart(getActivity(), item.getPosition());
            }
        });
    }

    @Override
    public void onEmptyRetryClicked() {

    }

    @Override
    public void onErrorRetryClicked() {

    }
}
