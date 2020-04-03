package com.think.guoyh.widget.pop.bottom;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.base.gyh.baselib.widgets.view.RoundImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;

import java.util.List;

public class TextSetRlvAdapter extends BaseRlvAdapter<Drawable> {
    public TextSetRlvAdapter(@Nullable List<Drawable> data) {
        super(R.layout.item_img_textsetrlv_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Drawable item) {
        RoundImageView img = helper.getView(R.id.item_imgtextset_img);
        img.setImageDrawable(item);
    }
}
