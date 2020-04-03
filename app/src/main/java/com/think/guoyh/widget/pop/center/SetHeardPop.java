package com.think.guoyh.widget.pop.center;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

import com.think.guoyh.R;

/**
 * Created by GUOYH on 2019/5/11.
 */
public class SetHeardPop extends CenterPopupView {
    public SetHeardPop(@NonNull Context context, OnSetButtonListener onSetButtonListener) {
        super(context);
        this.setButtonListener = onSetButtonListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_set_heard;
    }

    private OnSetButtonListener setButtonListener;

    public void setSetButtonListener(OnSetButtonListener setButtonListener) {
        this.setButtonListener = setButtonListener;
    }

    public interface OnSetButtonListener{
        void carm();
        void read();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView carm = findViewById(R.id.pop_set_msg_heard_carm);
        carm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonListener.carm();
                dismiss();
            }
        });
        TextView read = findViewById(R.id.pop_set_msg_heard_read);
        read.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonListener.read();
                dismiss();
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public BasePopupView show() {
        return super.show();
    }
}
