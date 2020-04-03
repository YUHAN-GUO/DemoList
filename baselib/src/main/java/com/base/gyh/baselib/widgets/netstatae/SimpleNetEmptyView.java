package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.base.gyh.baselib.R;


/**
 * Created by GUOYH on 2019/6/15.
 */
public class SimpleNetEmptyView implements INetEmptyView {
    private OnEmptyRetryClickListener mRetryClickListener;
    private View mView;
    private ImageView image;
    private Context context;

    @Override
    public void setEmptyRetryClickListener(OnEmptyRetryClickListener retryClickListener) {
        this.mRetryClickListener = retryClickListener;
        if (mView != null) {
            if (image == null) {
                image = mView.findViewById(R.id.empty_reload);
            }
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null) {
                        mRetryClickListener.onEmptyRetryClicked();
                    }
                }
            });
        }
    }

    @Override
    public View getView(Context context) {
        this.context = context;
        if (mView == null) {
            mView = View.inflate(context, R.layout.state_load_empty, null);
        }
        image = mView.findViewById(R.id.empty_reload);
        return mView;
    }

    @Override
    public void hide() {
        mView.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setImage(Drawable drawable) {
        if (mView == null) {
            mView = View.inflate(context, R.layout.state_load_empty, null);
        }
        if (image == null) {
            image = mView.findViewById(R.id.empty_reload);
        }
        image.setImageDrawable(drawable);
    }
}
