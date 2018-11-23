package com.cmy.wanandroidtest.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmy.wanandroidtest.event.NetworkEvent;
import com.cmy.wanandroidtest.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;
    /**
     * 视图是否加载完毕
     */
    private boolean isViewPrepare = false;
    /**
     * 数据是否加载过了
     */
    private boolean hasLoadData = false;
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBind();
        initView();
        lazyLoadDate();
    }

    private void lazyLoadDate() {
        if (getUserVisibleHint()) {
            lazyLoad();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent event) {
        LogUtil.getInstance().d("szjjy hhh=" + event.getIsconnected());
        if (event.getIsconnected()) {
            lazyLoad();
        }

    }

    protected abstract void lazyLoad();

    public void initBind() {
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    protected abstract T createPresenter();

    public boolean useEventBus() {
        return true;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
