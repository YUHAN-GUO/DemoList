package com.think.guoyh.ui.demo.function.textread.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.think.guoyh.R;
import com.think.guoyh.base.BaseRlvAdapter;
import com.think.guoyh.data.bean.text.TextArticleBean;
import com.think.guoyh.data.module.db.dbManage.BookManager;

import java.util.List;

public class TextReadRlvAdapter extends BaseRlvAdapter<TextArticleBean.DataBean.ArticleBean> {
    private int selecterPosition = 0;
    private String bookId;
    public TextReadRlvAdapter(@Nullable List<TextArticleBean.DataBean.ArticleBean> data,String bookId) {
        super(R.layout.item_read_rlv, data);
        this.bookId = bookId;
    }

    @Override
    protected void convert(BaseViewHolder helper, TextArticleBean.DataBean.ArticleBean item) {
        if (bookId !=null && BookManager.isChapterCached(bookId,item.getTitle())){
            helper.setText(R.id.item_readTv,"√"+item.getTitle());
        }else{
            helper.setText(R.id.item_readTv,item.getTitle());
        }
        TextView tvTitle = helper.getView(R.id.item_readTv);
        if (helper.getLayoutPosition() == selecterPosition){
            tvTitle.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_blue));
            tvTitle.setTextColor(ContextCompat.getColor(mContext,R.color.color_white));
        }else{
            tvTitle.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_white));
            tvTitle.setTextColor(ContextCompat.getColor(mContext,R.color.color_black));
        }
    }

    public void setChapter(int pos) {
        this.selecterPosition = pos;
        notifyDataSetChanged();
    }
}
