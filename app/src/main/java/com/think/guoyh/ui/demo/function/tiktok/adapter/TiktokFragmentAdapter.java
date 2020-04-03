package com.think.guoyh.ui.demo.function.tiktok.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;
import com.think.guoyh.ui.demo.function.tiktok.TiktokBean;

import java.util.List;

public class TiktokFragmentAdapter extends BaseRlvAdapter<TiktokBean> {
    public TiktokFragmentAdapter(@Nullable List<TiktokBean> data) {
        super(R.layout.item_tiktok_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TiktokBean item) {
        ImageView thumb = helper.getView(R.id.item_tiktok_thumb);
        loadImage(item.getCoverImgUrl(), thumb);
        helper.setText(R.id.item_tiktok_title, item.getTitle());
    }
}
