package com.cmy.wanandroidtest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.cmy.wanandroidtest.callback.ActivityCallbacks;

import java.util.logging.Logger;

public class MyApplication extends Application {

    private static MyApplication instances;
    private int appCount = 0;
    private boolean showForceLogoutDialog = false;
    private boolean updateLocalData = false;
    private String message = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityCallbacks());
    }
}
