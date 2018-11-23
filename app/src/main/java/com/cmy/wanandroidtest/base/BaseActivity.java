package com.cmy.wanandroidtest.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cmy.wanandroidtest.event.NetworkEvent;
import com.cmy.wanandroidtest.receiver.NetworkBroadcastReceiver;
import com.cmy.wanandroidtest.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initBind();
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册网络状态监听广播
        LogUtil.getInstance().d("szjjy onResume");
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkBroadcastReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNetworkBroadcastReceiver != null) {
            unregisterReceiver(mNetworkBroadcastReceiver);
            mNetworkBroadcastReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter !=null){
            mPresenter.detach();
            mPresenter.onDestroy();
            mPresenter = null;
            System.gc();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent event){
        LogUtil.getInstance().d("szjjy hhh");
        if (!event.getIsconnected()){
            Toast.makeText(this, "没有网络",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "已连接",Toast.LENGTH_SHORT).show();
        }

    }



    /**
     * 获取当前Activity的UI布局
     */
    protected abstract int getLayoutId();

    protected abstract void initData();
    protected abstract void initView();
    public void initBind() {
        if (useEventBus()){
            EventBus.getDefault().register(this);
        }
//        mPresenter= createPresenter();
//        mPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    public boolean useEventBus(){
        return true;
    }
}
