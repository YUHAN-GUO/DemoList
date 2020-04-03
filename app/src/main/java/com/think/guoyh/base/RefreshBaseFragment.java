package com.think.guoyh.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class RefreshBaseFragment extends MyBaseFragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser&&isViewInitiated) {
            toRefreshData();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toRefreshData();
    }

    public abstract void toRefreshData() ;
}
