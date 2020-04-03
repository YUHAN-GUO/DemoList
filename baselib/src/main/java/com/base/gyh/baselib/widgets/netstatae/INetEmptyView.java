package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by GUOYH on 2019/6/15.
 */
public interface INetEmptyView {
    void setEmptyRetryClickListener(OnEmptyRetryClickListener retryClickListener);

    View getView(Context context);

    void hide();

    void show();

    interface OnEmptyRetryClickListener {
        void onEmptyRetryClicked();
    }
    void setImage(Drawable drawable);
}
