package com.cmy.wanandroidtest.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.base.BaseActivity;
import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.constant.EventConstant;
import com.cmy.wanandroidtest.event.MessageEvent;
import com.cmy.wanandroidtest.interfaces.ILoginView;
import com.cmy.wanandroidtest.login.UserInfo;
import com.cmy.wanandroidtest.prsenter.LoginPresenter;
import com.cmy.wanandroidtest.utils.JumpUtil;
import com.cmy.wanandroidtest.utils.LogUtil;
import com.cmy.wanandroidtest.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity<ILoginView,LoginPresenter> implements View.OnClickListener,ILoginView {

    private LoginPresenter mPresenter;
    private Button mButton;
    private TextView mTvRegister;
    private EditText mEtEnsureUsername;
    private EditText mEtEnsurePassword;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mPresenter = createPresenter();
        mPresenter.attachView( this);
    }

    @Override
    protected void initView() {
        Toolbar mToolbarLogin = findViewById(R.id.toolbar_login);
        mToolbarLogin.setTitle(getString(R.string.login));
        mToolbarLogin.setNavigationOnClickListener(V->finish());
        mEtEnsureUsername = findViewById(R.id.et_ensure_username);
        mEtEnsurePassword = findViewById(R.id.et_ensure_password);
        mTvRegister = findViewById(R.id.tv_register);
        mButton = findViewById(R.id.btn_login);

        mTvRegister.setOnClickListener(this);
        mButton.setOnClickListener(this);

    }




    @Override
    protected LoginPresenter createPresenter() {
        mPresenter = new LoginPresenter();
        return mPresenter ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
//                JumpUtil.overlay(this, RegisterActivity.class);
                break;
            case R.id.btn_login:
                LogUtil.getInstance().d("szjjyh mPresenter="+mPresenter);
                if (check())
                    mPresenter.login(username, password);
                break;
        }
    }

    private boolean check() {
        username = mEtEnsureUsername.getText().toString().trim();
        password = mEtEnsurePassword.getText().toString().trim();
        if (username.length() < 6 || password.length() < 6) {
            Toast.makeText(this,"请输入六位及以上的密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void loginOk(UserInfo userInfo) {
        Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();
        SharedPreferenceUtil.put(this, Constant.USERNAME, userInfo.getUsername());
        SharedPreferenceUtil.put(this, Constant.PASSWORD, userInfo.getPassword());
        SharedPreferenceUtil.put(this, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGINSUCCESS, ""));
        finish();
    }

    @Override
    public void loginErr(String info) {
        Toast.makeText(this, getString(R.string.login_fail)+info,Toast.LENGTH_SHORT).show();
    }
}
