package com.think.guoyh.ui.demo.function.rlvscroll;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RlvScrollFragment extends MyBaseFragment implements View.OnClickListener {

    private GridLayoutManager mManager;
    private RecyclerView mScrollRlv;

    public RlvScrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rlv_scroll, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    protected void loadData() {

    }

    protected void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("position" + i);
        }
        mManager = new GridLayoutManager(getActivity(),2);
        mScrollRlv.setLayoutManager(mManager);
        mScrollRlv.setAdapter(new MyAdapter(list));
    }

    private void initView(View view) {
        mScrollRlv = (RecyclerView) view.findViewById(R.id.rv_rvhuadong);
        TextView tv_rvhuadong_GuanXing_1 = (TextView) view.findViewById(R.id.tv_rvhuadong_GuanXing_1);
        TextView tv_rvhuadong_GuanXing_2 = (TextView) view.findViewById(R.id.tv_rvhuadong_GuanXing_2);
        TextView tv_rvhuadong_GuanXing_3 = (TextView) view.findViewById(R.id.tv_rvhuadong_GuanXing_3);
        TextView tv_rvhuadong_ShanXian_1 = (TextView) view.findViewById(R.id.tv_rvhuadong_ShanXian_1);
        TextView tv_rvhuadong_ShanXian_2 = (TextView) view.findViewById(R.id.tv_rvhuadong_ShanXian_2);
        TextView tv_rvhuadong_ShanXian_3 = (TextView) view.findViewById(R.id.tv_rvhuadong_ShanXian_3);
        tv_rvhuadong_GuanXing_1.setOnClickListener(this);
        tv_rvhuadong_GuanXing_2.setOnClickListener(this);
        tv_rvhuadong_GuanXing_3.setOnClickListener(this);
        tv_rvhuadong_ShanXian_1.setOnClickListener(this);
        tv_rvhuadong_ShanXian_2.setOnClickListener(this);
        tv_rvhuadong_ShanXian_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //
            case R.id.tv_rvhuadong_GuanXing_1:
                int position1 = (int) (Math.random() * 100);
                Toast.makeText(getActivity(), "滑到：" + position1, Toast.LENGTH_SHORT).show();
                LinearSmoothScroller s1 = new TopSmoothScroller(getActivity());
                s1.setTargetPosition(position1);
                mManager.startSmoothScroll(s1);
                break;
            case R.id.tv_rvhuadong_GuanXing_2:
                LinearSmoothScroller s2 = new TopSmoothScroller(getActivity());
                s2.setTargetPosition(20);
                mManager.startSmoothScroll(s2);
                break;
            case R.id.tv_rvhuadong_GuanXing_3:
                LinearSmoothScroller s3 = new TopSmoothScroller(getActivity());
                s3.setTargetPosition(99);
                mManager.startSmoothScroll(s3);
                break;
            case R.id.tv_rvhuadong_ShanXian_1:
                int position2 = (int) (Math.random() * 100);
                Toast.makeText(getActivity(), "闪到：" + position2, Toast.LENGTH_SHORT).show();
                mManager.scrollToPositionWithOffset(position2, 0);
                break;
            case R.id.tv_rvhuadong_ShanXian_2:
                mManager.scrollToPositionWithOffset(20, 0);
                break;
            case R.id.tv_rvhuadong_ShanXian_3:
                mManager.scrollToPositionWithOffset(99, 0);
                break;
        }
    }


    private class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
        private final List<String> mList;

        public MyAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.setOrientation(LinearLayout.VERTICAL);

            AppCompatTextView tv = new AppCompatTextView(getActivity());
            tv.setTextSize(30);
            tv.setBackgroundColor(0xffeeeeee);
            ll.addView(tv);

            RecyclerView rv = new RecyclerView(getActivity());
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setNestedScrollingEnabled(true);
            rv.setAdapter(new ItemAdapter(new ArrayList<String>()));
            ll.addView(rv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return new BaseViewHolder(ll);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            ViewGroup vg = (ViewGroup) holder.itemView;
            TextView tv = (TextView) vg.getChildAt(0);
            tv.setText(mList.get(position));

            RecyclerView rv = (RecyclerView) vg.getChildAt(1);
            ItemAdapter adapter = (ItemAdapter) rv.getAdapter();
            adapter.mList.clear();
            for (int i = 0; i < 6; i++) {
                adapter.mList.add("item" + i);
            }
            adapter.notifyDataSetChanged();//在bind时确定好数据
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
        private final List<String> mList;

        public ItemAdapter(List<String> list) {
            mList = list;
        }


        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            AppCompatTextView tv = new AppCompatTextView(getActivity());
            tv.setTextSize(30);
            tv.setBackgroundColor(0xffeeeeee);
            tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BaseViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText(mList.get(position));
            if (position >= mList.size() - 2) {
                tv.getLayoutParams().height = 600;
            } else {
                tv.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            tv.setLayoutParams(tv.getLayoutParams());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }
    }
}
