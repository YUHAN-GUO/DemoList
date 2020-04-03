package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

/**
 * Created by GUOYH on 2019/6/15.
 */

public interface INetLoadingView {
    View getView(Context context);

    void hide();

    void show();
}
