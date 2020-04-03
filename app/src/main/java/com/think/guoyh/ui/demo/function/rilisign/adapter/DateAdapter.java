package com.think.guoyh.ui.demo.function.rilisign.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;
import com.think.guoyh.ui.demo.function.rilisign.model.SignDateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbaibai on 2018/8/27.
 */

public class DateAdapter extends BaseRlvAdapter<SignDateModel> {
    private Context mContext;
    private List<SignDateModel> mModels = new ArrayList<>();

    public DateAdapter(@Nullable List<SignDateModel> data) {
        super(R.layout.item_sign_date, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignDateModel model) {
        TextView datItemTv = helper.getView(R.id.tv_date_item);
        switch (model.getStatus()) {
            case SignDateModel.STATUS_CURRENT_DAY:
                datItemTv.setTextColor(datItemTv.getResources().getColor(R.color.color1));
                break;
            case SignDateModel.STATUS_HAVE_SIGN:
                datItemTv.setTextColor(datItemTv.getResources().getColor(R.color.color9));
                datItemTv.setBackgroundDrawable(datItemTv.getResources().getDrawable(R.drawable.shape_sign_date_red_bg));
                break;
            case SignDateModel.STATUS_SAT_OR_SUN:
                datItemTv.setTextColor(datItemTv.getResources().getColor(R.color.btime_transparent_40percent));
                break;
            case SignDateModel.STATUS_NO_SIGN:
            default:
                datItemTv.setTextColor(datItemTv.getResources().getColor(R.color.btime_transparent_80percent));
                break;
        }
        String content = model.getContent();
        if (content.equals("00")){
            hide(datItemTv);
        }else{
            datItemTv.setText(model.getContent());
        }
    }
}
