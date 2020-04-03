package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.view.View;

import com.base.gyh.baselib.R;



/**
 * Created by GUOYH on 2019/6/15.
 */
public class SimpleNetLoadingView implements INetLoadingView {
    private View mView;

    @Override
    public View getView(Context context) {
        if (mView == null) {
            mView = View.inflate(context, R.layout.state_load_ing, null);
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
