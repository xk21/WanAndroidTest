package com.cmy.wanandroidtest.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

    }

    /**
     * 获取当前Activity的UI布局
     */
    protected abstract int getLayoutId();

    protected abstract void initData();
    protected abstract void initUI();

    public void initBind() {

    }
}
