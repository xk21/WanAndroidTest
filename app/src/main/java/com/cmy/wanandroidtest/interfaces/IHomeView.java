package com.cmy.wanandroidtest.interfaces;

import com.cmy.wanandroidtest.bean.BannerBean;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.login.UserInfo;

import java.util.List;

public interface IHomeView {

        void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

        void loginSuccess(UserInfo userInfo);

        void loginErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }