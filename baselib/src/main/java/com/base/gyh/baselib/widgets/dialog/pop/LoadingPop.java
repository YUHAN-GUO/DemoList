package com.base.gyh.baselib.widgets.dialog.pop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.gyh.baselib.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * Created by GUO_YH on 2019/8/16 12:07
 */
public class LoadingPop extends CenterPopupView {
    private static BasePopupView loadingPop;
    private String title = "加载中...";

    public LoadingPop(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPop(@NonNull Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.loading_pop;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ViewHolder holder = new ViewHolder(this);
        if (!TextUtils.isEmpty(title)) {
            holder.loadingPopMsg.setText(title);
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public static void showLoad(Context context) {
        showLoad(context, null);
    }

    public static void showLoad(Context context, String title) {
        if (loadingPop == null) {
            // 按返回键是否关闭弹窗，默认为true 点击外部是否关闭弹窗，默认为true
            loadingPop = new XPopup.Builder(context)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(context, title));
        }
        loadingPop.show();
    }

    public static void hideLoad(Context context) {
        if (loadingPop == null) {
            // 按返回键是否关闭弹窗，默认为true 点击外部是否关闭弹窗，默认为true
            loadingPop = new XPopup.Builder(context)
                    .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                    .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                    .asCustom(new LoadingPop(context));
        }
        loadingPop.dismiss();
    }

    private static class ViewHolder {
        public View rootView;
        public TextView loadingPopMsg;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.loadingPopMsg = (TextView) rootView.findViewById(R.id.loading_pop_msg);
        }

    }
}
