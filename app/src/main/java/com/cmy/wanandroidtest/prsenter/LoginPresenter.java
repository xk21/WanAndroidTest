package com.cmy.wanandroidtest.prsenter;

import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.ILoginView;
import com.cmy.wanandroidtest.login.UserInfo;
import com.cmy.wanandroidtest.net.ApiService;
import com.cmy.wanandroidtest.net.DataResponse;
import com.cmy.wanandroidtest.net.HttpObserver;
import com.cmy.wanandroidtest.net.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-27 15:42
 */
public class LoginPresenter extends BasePresenter<ILoginView> {
    @Override
    public void onDestroy() {

    }

    public void login(String name, String password) {
        RetrofitManager.createApi(ApiService.class)
                .login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<DataResponse<UserInfo>>() {
                    @Override
                    public void onNext(DataResponse<UserInfo> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            obtainView().loginOk(baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().loginErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() != null) {
                            obtainView().loginErr(e.getMessage());
                        }
                    }
                });
    }
}
