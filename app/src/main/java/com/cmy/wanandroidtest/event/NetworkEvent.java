package com.cmy.wanandroidtest.event;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-10-22 15:34
 */
public class NetworkEvent {
    private boolean isConnected ;

    public NetworkEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean getIsconnected(){
        return isConnected;
    }
}
