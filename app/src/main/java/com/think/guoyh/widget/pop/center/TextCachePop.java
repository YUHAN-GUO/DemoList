package com.think.guoyh.widget.pop.center;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;
import com.think.guoyh.R;

public class TextCachePop extends CenterPopupView implements View.OnClickListener {
    private CachePopOnClick cachePopOnClick;
    public TextCachePop(@NonNull Context context,CachePopOnClick cachePopOnClick) {
        super(context);
        this.cachePopOnClick = cachePopOnClick;
    }

    public interface CachePopOnClick{
       void  onClick(View view);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_textcache_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ViewHolder vh = new ViewHolder(this);
        vh.popCahceBt1.setOnClickListener(this);
        vh.popCahceBt2.setOnClickListener(this);
        vh.popCahceBt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        cachePopOnClick.onClick(view);

    }

    private static class ViewHolder {
        public View rootView;
        public TextView popCahceBt1;
        public TextView popCahceBt2;
        public TextView popCahceBt3;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.popCahceBt1 = (TextView) rootView.findViewById(R.id.pop_cahce_bt1);
            this.popCahceBt2 = (TextView) rootView.findViewById(R.id.pop_cahce_bt2);
            this.popCahceBt3 = (TextView) rootView.findViewById(R.id.pop_cahce_bt3);
        }

    }
}
