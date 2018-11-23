package com.cmy.wanandroidtest.event;

import android.os.Bundle;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-10-22 15:34
 */
public class MainEvent {
    private int mType;
    private Bundle mBundle;

    public MainEvent(int type, Bundle bundle) {
        // TODO Auto-generated constructor stub
        mType = type;
        mBundle = bundle;
    }

    public MainEvent(int type) {
        mType = type;
        mBundle = null;
    }

    public int getType() {
        return mType;
    }
    public Bundle getBundle() {
        return mBundle;
    }

}
