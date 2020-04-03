package com.think.guoyh.widget.pop.bottom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.base.gyh.baselib.utils.RlvMangerUtil;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.think.guoyh.R;
import com.think.guoyh.widget.page.PageLoader;
import com.think.guoyh.widget.page.PageMode;
import com.think.guoyh.widget.page.PageStyle;
import com.think.guoyh.widget.page.ReadSettingManager;

import java.util.ArrayList;
import java.util.List;

public class TextSetPop extends BottomPopupView  implements View.OnClickListener {

    private ViewHolder holder;
    private SetOnClick setOnClick;
    private TextSetRlvAdapter popSetAdapter;
    private PageLoader mPageLoader;
    private  ReadSettingManager  readSettingManager;
    public TextSetPop(@NonNull Context context, PageLoader mPageLoader,SetOnClick setOnClick) {
        super(context);
        readSettingManager = ReadSettingManager.getInstance();
        this.mPageLoader = mPageLoader;
        this.setOnClick = setOnClick;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_textset_layout;
    }

    public interface SetOnClick{
        void onClick(View view);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Context context = getContext();
        holder = new ViewHolder(this);
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(ContextCompat.getDrawable(context,R.color.color_blue));
        drawables.add(ContextCompat.getDrawable(context,R.color.Blue));
        drawables.add(ContextCompat.getDrawable(context,R.color.colorPrimary));
        drawables.add(ContextCompat.getDrawable(context,R.color.color_white));
        popSetAdapter = new TextSetRlvAdapter(drawables);
        holder.popTextsetColorRlv.setLayoutManager(RlvMangerUtil.getInstance().getHorLinearLayoutManager(context));
        holder.popTextsetColorRlv.setAdapter(popSetAdapter);
        intiListener();
    }

    private void intiListener() {
        holder.popTextsetJian.setOnClickListener(this);
        holder.popTextsetFan.setOnClickListener(this);
        holder.popTextsetXiao.setOnClickListener(this);
        holder.popTextsetDa.setOnClickListener(this);
        holder.popTextsetFangzhen.setOnClickListener(this);
        holder.popTextsetFugai.setOnClickListener(this);
        holder.popTextsetGun.setOnClickListener(this);
        holder.popTextsetWu.setOnClickListener(this);
        popSetAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPageLoader.setPageStyle(PageStyle.values()[position]);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_textset_jian:
                setFontType(true);
                break;
            case R.id.pop_textset_fan:
                setFontType(false);
                break;
            case R.id.pop_textset_xiao:
                setFontSize(false);
                break;
            case R.id.pop_textset_da:
                setFontSize(true);
                break;
            case R.id.pop_textset_fangzhen:
                setPageAnim(1);
                break;
            case R.id.pop_textset_fugai:
                setPageAnim(2);
                break;
            case R.id.pop_textset_gun:
                setPageAnim(3);

                break;
            case R.id.pop_textset_wu:
                setPageAnim(0);
                break;
            default:
                break;
        }
        if (view.getId() != R.id.pop_textset_xiao && view.getId() != R.id.pop_textset_da) {
            dismiss();
        }
    }
    private void setPageAnim(int i) {
        PageMode pageMode = PageMode.SIMULATION;
        switch (i) {
            case 0:
                pageMode = PageMode.NONE;
                break;
            case 1:
                pageMode = PageMode.SIMULATION;
                break;
            case 2:
                pageMode = PageMode.COVER;
                break;
            case 3:
                pageMode = PageMode.SCROLL;
                break;
        }
        mPageLoader.setPageMode(pageMode);
    }


    /**
     * @param b true 字体简体  false  字体繁体
     */
    private void setFontType(boolean b) {
        int fontType = readSettingManager.getConvertType();
        Logger.dd(fontType + "-----" + b);
        if (b) {
            if (fontType == 0) {
                return;
            }
            readSettingManager.setConvertType(0);
        } else {
            if (fontType == 1) {
                return;
            }
            readSettingManager.setConvertType(1);
        }
        Logger.dd("-------------");
        mPageLoader.setTextSize(readSettingManager.getTextSize());

    }

    /**
     * @param b true 字体加  false  字体减
     */
    private void setFontSize(boolean b) {
        int fontSize = readSettingManager.getTextSize();
        if (b) {
            fontSize = fontSize + 1;
        } else {
            fontSize = fontSize - 1;
            if (fontSize < 0) {
                return;
            }
        }
        mPageLoader.setTextSize(fontSize);
    }


    private static class ViewHolder {
        public View rootView;
        public TextView popTextsetJian;
        public TextView popTextsetFan;
        public TextView popTextsetXiao;
        public TextView popTextsetDa;
        public TextView popTextsetFangzhen;
        public TextView popTextsetFugai;
        public TextView popTextsetGun;
        public TextView popTextsetWu;
        public RecyclerView popTextsetColorRlv;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.popTextsetJian = (TextView) rootView.findViewById(R.id.pop_textset_jian);
            this.popTextsetFan = (TextView) rootView.findViewById(R.id.pop_textset_fan);
            this.popTextsetXiao = (TextView) rootView.findViewById(R.id.pop_textset_xiao);
            this.popTextsetDa = (TextView) rootView.findViewById(R.id.pop_textset_da);
            this.popTextsetFangzhen = (TextView) rootView.findViewById(R.id.pop_textset_fangzhen);
            this.popTextsetFugai = (TextView) rootView.findViewById(R.id.pop_textset_fugai);
            this.popTextsetGun = (TextView) rootView.findViewById(R.id.pop_textset_gun);
            this.popTextsetWu = (TextView) rootView.findViewById(R.id.pop_textset_wu);

            this.popTextsetColorRlv = (RecyclerView) rootView.findViewById(R.id.pop_textset_colorRlv);
        }


    }
}
