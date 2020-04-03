package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

import com.base.gyh.baselib.R;



/**
 * Created by GUOYH on 2019/6/15.
 */
public class SimpleNetErrorView implements INetErrorView {
    private OnErrorRetryClickListener mRetryClickListener;
    private View mView;


    @Override
    public void setErrorRetryClickListener(OnErrorRetryClickListener retryClickListener) {
        this.mRetryClickListener = retryClickListener;
        if (mView != null) {
            mView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null) {
                        mRetryClickListener.onErrorRetryClicked();
                    }
                }
            });
        }
    }

    @Override
    public View getView(Context context) {
        if (mView == null) {
            mView = View.inflate(context, R.layout.state_load_error, null);
        }
        return mView;
    }

    @Override
    public void hide() {
        if (mView != null) {
            mView.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
        }
    }
}
