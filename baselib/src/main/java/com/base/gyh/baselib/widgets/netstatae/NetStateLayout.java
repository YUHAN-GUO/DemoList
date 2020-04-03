package com.base.gyh.baselib.widgets.netstatae;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.utils.mylog.Logger;


/**
 * Created by GUOYH on 2019/6/15.
 */

public class NetStateLayout extends FrameLayout {
    private INetErrorView mNetErrorView;
    private INetLoadingView mNetLoadingView;
    private INetEmptyView mNetEmptyView;
    private String netEmptyClassName;
    private String netErrorClassName;
    private String netLoadingClassName;

    /**
     * show customized network error view
     */
    public static final int CONTENT_STATE_ERROR = 0x1;
    /**
     * show customized network loading view
     */
    public static final int CONTENT_STATE_EMPTY = 0x2;
    /**
     * hide customized view,show your data content
     */
    public static final int CONTENT_STATE_LOADING = 0x3;

    /**
     * show customized network empty view
     */
    public static final int CONTENT_STATE_HIDE = 0x4;

    private int mContentState = CONTENT_STATE_HIDE;

    /**
     * set customized network error view.
     *
     * @param netErrorView
     * @see SimpleNetErrorView  SimpleNetErrorView
     */
    public void setNetErrorView(INetErrorView netErrorView) {
        if (netErrorView == null) {
            return;
        }
        if (mNetErrorView != null) {
            removeView(mNetErrorView.getView(getContext()));
        }
        this.mNetErrorView = netErrorView;
        addView(netErrorView.getView(getContext()));
        mNetErrorView.hide();
    }

    /**
     * 设置空布局
     * @param netEmptyView
     */
    public void setNetEmptyView(INetEmptyView netEmptyView){
        if (netEmptyView==null){
            return;
        }
        if (mNetEmptyView!=null){
            removeView(mNetEmptyView.getView(getContext()));
        }
        this.mNetEmptyView = netEmptyView;
        addView(netEmptyView.getView(getContext()));
        mNetEmptyView.hide();
    }
    public void setNetEmptyImage(Drawable drawable){
        mNetEmptyView.setImage(drawable);
    }
    /**
     * set customized network loading view.
     *
     * @param netLoadingView
     * @see SimpleNetLoadingView SimpleNetLoadingView
     */
    public void setNetLoadingView(INetLoadingView netLoadingView) {
        if (netLoadingView == null) {
            return;
        }
        if (mNetLoadingView != null) {
            removeView(mNetLoadingView.getView(getContext()));
        }
        this.mNetLoadingView = netLoadingView;
        addView(netLoadingView.getView(getContext()));
        mNetLoadingView.hide();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


    public NetStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NetStateLayout);
        netErrorClassName = ta.getString(R.styleable.NetStateLayout_net_error);
        netEmptyClassName = ta.getString(R.styleable.NetStateLayout_net_empty);
        netLoadingClassName = ta.getString(R.styleable.NetStateLayout_net_loading);

        ta.recycle();
    }


    /**
     * get current state
     *
     * @return {@link #CONTENT_STATE_ERROR} ,{@link #CONTENT_STATE_LOADING},{@link #CONTENT_STATE_HIDE}
     */
    public int getContentState() {
        return mContentState;
    }
    private OnEmptyAndErrorRetryClickListener mOnEmptyAndErrorRetryClickListener;

    /**
     * @param contentState {@linkplain #CONTENT_STATE_ERROR} ,{@link #CONTENT_STATE_LOADING},{@link #CONTENT_STATE_HIDE}
     */
    public void setContentState(int contentState) {
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetLoadingView == null) {
            try {
                Class<?> netLoadingClass = Class.forName(netLoadingClassName);
                mNetLoadingView = (INetLoadingView) netLoadingClass.newInstance();
                addView(mNetLoadingView.getView(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mContentState = contentState;
        switch (contentState) {
            case CONTENT_STATE_ERROR:
                if (mNetErrorView != null) {
                    mNetErrorView.show();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.hide();
                }
                if (mNetEmptyView != null) {
                    mNetEmptyView.hide();
                }
                break;
            case CONTENT_STATE_EMPTY:
                if (mNetErrorView != null) {
                    mNetErrorView.hide();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.hide();
                }
                if (mNetEmptyView != null) {
                    mNetEmptyView.show();
                }
                break;
            case CONTENT_STATE_LOADING:
                if (mNetErrorView != null) {
                    mNetErrorView.hide();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.show();
                }
                if (mNetEmptyView != null) {
                    mNetEmptyView.hide();
                }
                break;
            case CONTENT_STATE_HIDE:
                if (mNetErrorView != null) {
                    mNetErrorView.hide();
                }
                if (mNetLoadingView != null) {
                    mNetLoadingView.hide();
                }
                if (mNetEmptyView != null) {
                    mNetEmptyView.hide();
                }
                break;
            default:
                break;
        }
    }

    public void setOnErrorRetryClickListener(INetErrorView.OnErrorRetryClickListener onRetryClickListener) {
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetErrorView != null) {
            mNetErrorView.setErrorRetryClickListener(onRetryClickListener);
        }
    }

    public void setOnEmptyRetryClickListener(INetEmptyView.OnEmptyRetryClickListener onRetryClickListener) {
        if (mNetEmptyView == null) {
            try {
                Class<?> netEmptyClass = Class.forName(netEmptyClassName);
                mNetEmptyView = (INetEmptyView) netEmptyClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetEmptyView != null) {
            mNetEmptyView.setEmptyRetryClickListener(onRetryClickListener);
        }
    }

    public void setOnEmptyAndErrorRetryClickListener(OnEmptyAndErrorRetryClickListener listener) {
        if (mNetErrorView == null) {
            try {
                Class<?> netErrorClass = Class.forName(netErrorClassName);
                mNetErrorView = (INetErrorView) netErrorClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mNetEmptyView == null) {
            try {
                Class<?> netEmptyClass = Class.forName(netEmptyClassName);
                mNetEmptyView = (INetEmptyView) netEmptyClass.newInstance();
                addView(mNetErrorView.getView(getContext()), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mNetEmptyView.setEmptyRetryClickListener(listener);
        mNetErrorView.setErrorRetryClickListener(listener);
    }

    public interface OnEmptyAndErrorRetryClickListener extends INetEmptyView.OnEmptyRetryClickListener, INetErrorView.OnErrorRetryClickListener {
    }
}
