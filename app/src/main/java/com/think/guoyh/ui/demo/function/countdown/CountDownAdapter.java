package com.think.guoyh.ui.demo.function.countdown;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class CountDownAdapter extends BaseQuickAdapter<CountDownAdapter.CountDownBean, CountDownAdapter.ViewHolder> {

    private List<ViewHolder> mHolderList = new ArrayList<>();


    public CountDownAdapter(@Nullable List<CountDownBean> data) {
        super(R.layout.item_count_down, data);
    }

    @Override
    protected void convert(ViewHolder helper, CountDownBean item) {
        int nowPositon = helper.getLayoutPosition();
        helper.setDataPosition(nowPositon);
        TextView timeView = helper.getView(R.id.item_count_down_time);
        //先判断集合中是否包含当前的ViewHolder
        if (!mHolderList.contains(helper)) {
            mHolderList.add(helper);//添加
        }
        timeView.setText("还有"+formatDate(item.getTimes()));
        helper.setText(R.id.item_count_down_title,item.getTitle());
    }

    /**
     * 格式化时间
     *
     * @param l
     * @return
     */
    public String formatDate(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");//初始化Formatter的转换格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00:00"));
        String format = formatter.format(l);
        return format;
    }


    /**
     * 只刷新item中的计时器数据
     */
    public void notifyTime() {
        for (int i = 0; i < mHolderList.size(); i++) {
            mHolderList.get(i).itemCountDownTime.setText("还有" + formatDate(mData.get(mHolderList.get(i).position).getTimes()));
        }
    }

    public static class ViewHolder extends BaseViewHolder {
        public View rootView;
        public TextView itemCountDownTitle;
        public TextView itemCountDownTime;
        private int position;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.itemCountDownTitle = (TextView) rootView.findViewById(R.id.item_count_down_title);
            this.itemCountDownTime = (TextView) rootView.findViewById(R.id.item_count_down_time);
        }

        /**
         * 绑定position
         *
         * @param position
         */
        public void setDataPosition(int position) {
            this.position = position;
        }

    }


    static class CountDownBean {
        String title;
        long times;

        public CountDownBean(String title, long times) {
            this.title = title;
            this.times = times;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }


}

