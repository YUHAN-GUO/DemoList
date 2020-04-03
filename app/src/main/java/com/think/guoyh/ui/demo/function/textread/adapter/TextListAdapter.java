package com.think.guoyh.ui.demo.function.textread.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;
import com.think.guoyh.data.bean.text.TextListBean;

import java.util.List;

public class TextListAdapter extends BaseRlvAdapter<TextListBean.DataBean.ListBean> {
    public TextListAdapter(@Nullable List<TextListBean.DataBean.ListBean> data) {
        super(R.layout.text_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextListBean.DataBean.ListBean item) {
        loadImage(item.getBook_cover(),helper.getView(R.id.item_textList_img));
        helper.setText(R.id.item_textList_title,item.getBook_title());

    }
}
