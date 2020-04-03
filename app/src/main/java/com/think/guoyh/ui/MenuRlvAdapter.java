package com.think.guoyh.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;
import com.think.guoyh.data.bean.MenuBean;

import java.util.List;

public class MenuRlvAdapter extends BaseRlvAdapter<MenuBean> {
    public MenuRlvAdapter(@Nullable List<MenuBean> data) {
        super(R.layout.item_home_rlv, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        helper.setText(R.id.item_home_tv,item.getTitle());
    }
}
