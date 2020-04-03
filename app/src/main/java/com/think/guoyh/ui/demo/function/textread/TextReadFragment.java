package com.think.guoyh.ui.demo.function.textread;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.utils.GsonUtil;
import com.base.gyh.baselib.utils.RlvMangerUtil;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.data.bean.text.TextDetailsBean;
import com.think.guoyh.data.bean.text.TextListBean;
import com.think.guoyh.data.module.TextModule;
import com.think.guoyh.data.module.db.CollBookBean;
import com.think.guoyh.data.module.db.dbManage.BookRepository;
import com.think.guoyh.ui.demo.function.textread.adapter.TextListAdapter;
import com.think.guoyh.utils.MyViewUtil;
import com.think.guoyh.widget.ShapeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextReadFragment extends MyBaseFragment {
    private RecyclerView textListRlv;
    private FrameLayout textListLinear;
    private TextListAdapter adapter;
    private boolean isCollect;

    public TextReadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_read, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {
        TextModule.getModule().getListText(this, new IBaseHttpResultCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                TextListBean listBean = GsonUtil.Json2Result(TextListBean.class, data);
                if (listBean.getCode() == 1) {
                    initSuccess(listBean.getData());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void initSuccess(TextListBean.DataBean data) {
        adapter = new TextListAdapter(data.getList());
        textListRlv.setAdapter(adapter);
        textListRlv.setLayoutManager(RlvMangerUtil.getInstance().getGridLayoutManager(getContext(), 2));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextListBean.DataBean.ListBean item = (TextListBean.DataBean.ListBean) adapter.getItem(position);
//                TextReadActivity.toReadText(getActivity(),item.getBook_id()+"");
                getDetails(item.getBook_id() + "");
            }
        });
    }

    private void getDetails(String bookId) {
        TextModule.getModule().getBookDetails(bookId, TextReadFragment.this, new IBaseHttpResultCallBack<TextDetailsBean>() {
            @Override
            public void onSuccess(TextDetailsBean data) {
                if (data.getCode() == 1) {
                    detailsSuccess(data);
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.dd(e.getMessage());
            }
        });
    }

    private void detailsSuccess(TextDetailsBean data) {
        View footerView = MyViewUtil.getInstance().getView(R.layout.footer_text_layout);
        ViewHolder holder = new ViewHolder(footerView);
        textListLinear.removeAllViews();
        textListLinear.addView(footerView);
        holder.footerTitle.setText(data.getData().getBook().getDescription());
        CollBookBean collBook = BookRepository.getInstance().getCollBook(data.getData().getCollBookBean().getId().toString());
        if (collBook!=null){
            holder.footerRead.setText("继续阅读");
            isCollect = true;
        }else {
            holder.footerRead.setText("开始阅读");
            isCollect = false;
        }
        holder.footerRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollBookBean collBookBean = data.getData().getCollBookBean();
                TextReadActivity.toReadText(getActivity(),collBookBean,isCollect);
//                Toast.makeText(getContext(), "去阅读", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        textListLinear = (FrameLayout) view.findViewById(R.id.textListLinear);
        textListRlv = (RecyclerView) view.findViewById(R.id.textListRlv);
    }

    private static class ViewHolder {
        public View rootView;
        public TextView footerTitle;
        public ShapeButton footerRead;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.footerTitle = (TextView) rootView.findViewById(R.id.footer_title);
            this.footerRead = (ShapeButton) rootView.findViewById(R.id.footer_read);
        }

    }
}
