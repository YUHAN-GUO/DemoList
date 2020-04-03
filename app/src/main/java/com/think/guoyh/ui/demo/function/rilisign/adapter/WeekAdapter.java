package com.think.guoyh.ui.demo.function.rilisign.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * Created by kangbaibai on 2018/8/29.
 */

public class WeekAdapter extends BaseRlvAdapter<String> {
    public WeekAdapter() {
        super(R.layout.item_sign_week, Arrays.asList(new String[]{"日", "一", "二", "三", "四", "五", "六"}));
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView itemData = helper.getView(R.id.tv_date_item);
        itemData.setText(item);
        int position = helper.getLayoutPosition();
        if (position == 0 || position == 6) {
            itemData.setTextColor(itemData.getResources().getColor(R.color.bar_grey_90));
        }
    }

}
