package com.cmy.wanandroidtest.base;

import java.lang.ref.WeakReference;

/**
 * Created by chenmingying on 2018/3/9.
 */

public abstract class BasePresenter<V> {
    protected WeakReference<V> weakRefView;
    public void attachView(V view){
        weakRefView = new WeakReference<V>(view);
    }

    public void detach() {
        if(isAttach()) {
            weakRefView.clear();
            weakRefView = null;
        }
    }
    public V obtainView(){
        return isAttach()?weakRefView.get():null;
    }

    protected boolean isAttach() {
        return weakRefView != null &&
                weakRefView.get() != null;
    }

    public abstract void onDestroy();
}

