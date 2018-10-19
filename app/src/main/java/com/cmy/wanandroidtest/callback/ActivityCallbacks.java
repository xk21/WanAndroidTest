package com.cmy.wanandroidtest.callback;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;



/**
 * Created by suxinwei on 2017/5/11.
 */

public class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {

    private BroadcastReceiver mHomekeyReceiver;

    private void initHomeKeyReceiver(Activity activity) {
        if (mHomekeyReceiver == null) {
//            mHomekeyReceiver = new HomeKeyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            activity.registerReceiver(mHomekeyReceiver, filter);
        }
    }

    private void removeHomeKeyReceiver(Activity activity) {
        if (mHomekeyReceiver != null) {
            activity.unregisterReceiver(mHomekeyReceiver);
            mHomekeyReceiver = null;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        ActivityUtils.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        initHomeKeyReceiver(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        removeHomeKeyReceiver(activity);
    }


    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        ActivityUtils.removeActivity(activity);
    }
}
