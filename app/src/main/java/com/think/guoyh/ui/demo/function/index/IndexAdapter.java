package com.think.guoyh.ui.demo.function.index;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.think.guoyh.R;
import com.think.guoyh.data.bean.IndexBean;

import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;

public class IndexAdapter extends IndexableAdapter<IndexBean> {
    private LayoutInflater mInflater;

    public IndexAdapter(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_title, parent, false);
        return new TitleVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_content, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        TitleVH titleVH = (TitleVH) holder;
        titleVH.itemIndexTilteTv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, IndexBean entity) {
        ContentVH contentVH = (ContentVH) holder;
        contentVH.itemIndexContentTv.setText(entity.getCity());
    }

    private static class TitleVH extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView itemIndexTilteTv;

        public TitleVH(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.itemIndexTilteTv = (TextView) rootView.findViewById(R.id.item_index_tilteTv);
        }

    }

    private static class ContentVH extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView itemIndexContentTv;

        public ContentVH(@NonNull View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.itemIndexContentTv = (TextView) rootView.findViewById(R.id.item_index_contentTv);
        }
    }

    public static class IndexHeardAdapter extends IndexableHeaderAdapter {

        public IndexHeardAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_heard, parent, false);
            HeardVH holder = new HeardVH(view);
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            HeardVH hol =  (HeardVH)holder;
            hol.itemIndexHeardTv.setText("自由发挥");
        }

        private static class HeardVH extends RecyclerView .ViewHolder{
            public View rootView;
            public TextView itemIndexHeardTv;

            public HeardVH(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.itemIndexHeardTv = (TextView) rootView.findViewById(R.id.item_index_heardTv);
            }

        }
    }
}
