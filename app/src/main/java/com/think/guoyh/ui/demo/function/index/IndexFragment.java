package com.think.guoyh.ui.demo.function.index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.data.bean.IndexBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends MyBaseFragment {


    private IndexableLayout indexLayout;

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        indexLayout = (IndexableLayout) view.findViewById(R.id.indexLayout);
        indexLayout.setIndexBarVisibility(false);
        indexLayout.setLayoutManager(getLinearLayoutManger());

        IndexAdapter adapter = new IndexAdapter(LayoutInflater.from(getContext()));
        indexLayout.setAdapter(adapter);
        indexLayout.setOverlayStyle_Center();
        adapter.setDatas(initDatas());
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexLayout.setCompareMode(IndexableLayout.MODE_FAST);

        List<String> bannerList = new ArrayList<>();
        bannerList.add("123456");
        IndexAdapter.IndexHeardAdapter heardAdapter = new IndexAdapter.IndexHeardAdapter("↑", "你好", bannerList);
        indexLayout.addHeaderAdapter(heardAdapter);


        adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<IndexBean>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, IndexBean entity) {
                Logger.d("%s+++++++++++++%s","guoyh",entity.getCity());
                Toast.makeText(getContext(), entity.getCity(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                Toast.makeText(getContext(), indexTitle, Toast.LENGTH_SHORT).show();

                Logger.d("%s+++++++++++++%s","guoyh",indexTitle);

            }
        });
    }
    private List<IndexBean> initDatas() {
        List<IndexBean> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            IndexBean contactEntity = new IndexBean(contactStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }
}
