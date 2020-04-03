package com.think.guoyh.widget.pop.center;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.base.gyh.baselib.utils.EmptyUtils;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

import com.think.guoyh.R;

/**
 * Created by GUOYH on 2019/5/11.
 */
public class SelecterPop extends CenterPopupView {
    private OnSetButtonListener setButtonListener;
    private String title = "";
    private String subTitle = "";
    public SelecterPop(@NonNull Context context,String title,String subTitle, OnSetButtonListener onSetButtonListener) {
        super(context);
        this.title = title;
        this.subTitle = subTitle;
    }

    public SelecterPop(@NonNull Context context,String title, OnSetButtonListener onSetButtonListener) {
        this(context,title,"",onSetButtonListener);
    }

    public SelecterPop(@NonNull Context context, OnSetButtonListener onSetButtonListener) {
        this(context,"",onSetButtonListener);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_selecter_layout;
    }

    public void setSetButtonListener(OnSetButtonListener setButtonListener) {
        this.setButtonListener = setButtonListener;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ViewHolder holder = new ViewHolder(this);
        holder.popSelecterCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonListener.onCancle();
                dismiss();
            }
        });
        holder.popSelecterSure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonListener.onSure();
                dismiss();
            }
        });
        if (!EmptyUtils.isEmpty(title)){
            holder.popSelecterTitle.setText(title);
        }
        if (!EmptyUtils.isEmpty(subTitle)){
            holder.popSelecterSubtitle.setText(subTitle);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public BasePopupView show() {
        return super.show();
    }

    public interface OnSetButtonListener {
        void onCancle();

        void onSure();
    }

    private class ViewHolder {
        public View rootView;
        public TextView popSelecterCancle;
        public TextView popSelecterSure;
        public TextView popSelecterSubtitle;
        public TextView popSelecterTitle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.popSelecterCancle = (TextView) rootView.findViewById(R.id.pop_selecter_cancle);
            this.popSelecterSure = (TextView) rootView.findViewById(R.id.pop_selecter_sure);
            this.popSelecterSubtitle = (TextView) rootView.findViewById(R.id.pop_selecter_subtitle);
            this.popSelecterTitle = (TextView) rootView.findViewById(R.id.pop_selecter_title);
        }

    }
}
