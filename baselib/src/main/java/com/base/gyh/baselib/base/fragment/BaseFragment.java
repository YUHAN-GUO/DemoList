package com.base.gyh.baselib.base.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.base.gyh.baselib.base.fragment.SupportFragment;
import com.base.gyh.baselib.utils.RlvMangerUtil;

public abstract class BaseFragment extends SupportFragment {

    public LinearLayoutManager getLinearLayoutManger(){
        return RlvMangerUtil.getInstance().getLinearLayoutManager(getActivity());
    }
    public GridLayoutManager getGridLayoutManager(int spanCount){
        return RlvMangerUtil.getInstance().getGridLayoutManager(getActivity(),spanCount);

    }


}
