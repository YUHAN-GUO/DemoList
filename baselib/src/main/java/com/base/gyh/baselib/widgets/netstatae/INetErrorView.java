package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

/**
 * Created by GUOYH on 2019/6/15.
 */

public interface INetErrorView {

    void setErrorRetryClickListener(OnErrorRetryClickListener retryClickListener);

    View getView(Context context);

    void hide();

    void show();

    interface OnErrorRetryClickListener {
        void onErrorRetryClicked();
    }
}
