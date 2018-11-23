package com.cmy.wanandroidtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cmy.wanandroidtest.event.NetworkEvent;
import com.cmy.wanandroidtest.utils.LogUtil;
import com.cmy.wanandroidtest.utils.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-10-22 15:29
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.getInstance().d("szjjy NetworkBroadcastReceiver="+NetWorkUtil.isNetworkConnected());
        if (NetWorkUtil.isNetworkConnected()){
            EventBus.getDefault().post(new NetworkEvent(true));
        }else {
            EventBus.getDefault().post(new NetworkEvent(false));
        }
    }
}
