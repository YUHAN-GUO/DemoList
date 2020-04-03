package com.think.guoyh.base;

import com.base.gyh.baselib.base.fragment.BaseFragment;
import com.think.guoyh.utils.handleback.HandleBackInterface;
import com.think.guoyh.utils.handleback.HandleBackUtil;

public abstract class MyBaseFragment extends BaseFragment implements HandleBackInterface {

    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }
}
