package com.cmy.wanandroidtest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.cmy.wanandroidtest.MyApplication;

public class NetWorkUtil {
    public static final int NET_CNNT_BAIDU_OK = 1; // NetworkAvailable
    public static final int NET_CNNT_BAIDU_TIMEOUT = 2; // no NetworkAvailable
    public static final int NET_NOT_PREPARE = 3; // Net no ready
    public static final int NET_ERROR = 4; // net error
    public static final int TIMEOUT = 3000; // NetworkAvailable


    /**
     * check NetworkAvailable
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return  !(activeNetworkInfo==null||!activeNetworkInfo.isAvailable());
    }

    /**
     * check NetworkConnected
     * @param
     * @return
     */
    public static boolean isNetworkConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return  !(activeNetworkInfo==null||!activeNetworkInfo.isConnected());
    }

    public static boolean isWifi(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

}
