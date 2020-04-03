package com.think.guoyh.widget.pop.bottom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;

import com.think.guoyh.R;

/**
 * Created by GUOYH on 2019/6/19.
 */
public class ShareQQWexPop extends BottomPopupView {
    public ShareQQWexPop(@NonNull Context context,OnSharePopClickListener onSharePopClickListener) {
        super(context);
                this.onSharePopClickListener = onSharePopClickListener;

    }
//    private ShareParams shareParams;
//    public ShareQQWexPop(@NonNull Context context, ShareParams shareParams, OnSharePopClickListener onSharePopClickListener) {
//        super(context);
//        this.shareParams = shareParams;
//        this.onSharePopClickListener = onSharePopClickListener;
//    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_share_qqwxin;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ViewHolder holder = new ViewHolder(this);
        holder.popShareWeixin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                JShareInterface.share(Wechat.Name, shareParams, mPlatActionListener);
                onSharePopClickListener.weixin();
            }
        });
        holder.popShareQq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                JShareInterface.share(QQ.Name, shareParams, mPlatActionListener);
                onSharePopClickListener.qq();
            }
        });
        holder.popShareCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private OnSharePopClickListener onSharePopClickListener;

    public void setOnSharePopClickListener(OnSharePopClickListener onSharePopClickListener) {
        this.onSharePopClickListener = onSharePopClickListener;
    }

    public interface OnSharePopClickListener{
        void weixin();
        void qq();
    }



    public static class ViewHolder {
        public View rootView;
        public TextView popShareWeixin;
        public TextView popShareQq;
        public TextView popShareCancle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.popShareWeixin = (TextView) rootView.findViewById(R.id.pop_share_weixin);
            this.popShareQq = (TextView) rootView.findViewById(R.id.pop_share_qq);
            this.popShareCancle = (TextView) rootView.findViewById(R.id.pop_share_cancle);
        }

    }
}
