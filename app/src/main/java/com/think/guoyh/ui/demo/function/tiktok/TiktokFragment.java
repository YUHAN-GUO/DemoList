package com.think.guoyh.ui.demo.function.tiktok;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.utils.GsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.ui.demo.function.tiktok.adapter.TiktokFragmentAdapter;
import com.think.guoyh.utils.ResourceUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TiktokFragment extends MyBaseFragment {


    private View view;
    private RecyclerView tiktok_rlv;
    private TiktokFragmentAdapter tiktokAdapter;

    public TiktokFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tiktok, container, false);
        initView(view);
        initAdapter();
        setListener();
        return view;
    }

    private void setListener() {
        tiktokAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            TiktokActivity.start(getActivity(),position);
            }
        });
    }

    private void initAdapter() {
        if (tiktokAdapter == null) {
            tiktokAdapter = new TiktokFragmentAdapter(null);
            tiktok_rlv.setAdapter(tiktokAdapter);
            tiktok_rlv.setLayoutManager(getGridLayoutManager(2));
        }
    }

    @Override
    protected void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = ResourceUtils.readAssets2String("tiktok_data.json");
                List<TiktokBean> tiktokList = GsonUtil.Json2Array(TiktokBean.class, s);
                tiktok_rlv.post(new Runnable() {
                    @Override
                    public void run() {
                        tiktokAdapter.addData(tiktokList);
//                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void initView(View view) {
        tiktok_rlv = (RecyclerView) view.findViewById(R.id.tiktok_rlv);
    }
}
