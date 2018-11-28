package com.cmy.wanandroidtest.interfaces;

import com.cmy.wanandroidtest.login.UserInfo;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-27 15:41
 */
public interface ILoginView {
    void loginOk(UserInfo userInfo);

    void loginErr(String info);
}
