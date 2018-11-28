package com.cmy.wanandroidtest.prsenter;

import android.util.Log;

import com.cmy.wanandroidtest.MyApplication;
import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.bean.BannerBean;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.IHomeView;
import com.cmy.wanandroidtest.login.UserInfo;
import com.cmy.wanandroidtest.net.ApiService;
import com.cmy.wanandroidtest.net.DataResponse;
import com.cmy.wanandroidtest.net.HttpObserver;
import com.cmy.wanandroidtest.net.RetrofitManager;
import com.cmy.wanandroidtest.net.RxUtils;
import com.cmy.wanandroidtest.utils.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-21 15:36
 */
public class HomePrsenter extends BasePresenter<IHomeView> {
    private boolean isRefresh = true;
    private int currentPage=0;

    @Override

    public void onDestroy() {

    }

    public void loadMore() {

        isRefresh = false;
        currentPage++;
        Log.d("szjjy", "loadMore: ..="+currentPage);
        getHomepageList(currentPage);
    }
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
//        getBanner();
        getHomepageList(currentPage);
    }
    public void getHomepageList(int page){
        RetrofitManager.createApi(ApiService.class)
                .getArticleList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<DataResponse<HomePageArticleBean>>() {
                    @Override
                    public void onNext(DataResponse<HomePageArticleBean> homePageArticleBeanDataResponse) {
                        if (homePageArticleBeanDataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            Log.d("szjjy", "getHomepageListOk: onNext");
                            obtainView().getHomepageListOk(homePageArticleBeanDataResponse.getData(), isRefresh);
                        } else if (homePageArticleBeanDataResponse.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().getHomepageListErr(homePageArticleBeanDataResponse.getErrorMsg());
                            Log.d("szjjy", "getHomepageListOk: getErrorMsg");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("szjjy", "onError: onError");
                    }
                });
    }

    public void collectArticle(int id) {
        RetrofitManager.createApi(ApiService.class)
                .collectArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<DataResponse>() {
                    @Override
                    public void onNext(DataResponse baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            obtainView().collectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().collectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        obtainView().collectArticleErr(e.getMessage());
                    }
                });
    }
    public void cancelCollectArticle(int id) {
        RetrofitManager.createApi(ApiService.class)
                .cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<DataResponse>() {
                    @Override
                    public void onNext(DataResponse baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            obtainView().cancelCollectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().cancelCollectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        obtainView().cancelCollectArticleErr(e.getMessage());
                    }
                });
    }

    public void loginAndLoad() {
        Log.d("szjjy", "loginAndLoad: 1");
        String username = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.USERNAME, Constant.DEFAULT);
        String password = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.PASSWORD, Constant.DEFAULT);

        Observable<DataResponse<UserInfo>> observableUser = RetrofitManager.createApi(ApiService.class).login(username, password);
//        Observable<DataResponse<List<BannerBean>>> observableBanner = ApiStore.createApi(ApiService.class).getBanner();
        Observable<DataResponse<HomePageArticleBean>> observableArticle = RetrofitManager.createApi(ApiService.class).getArticleList(currentPage);
        Observable.zip(observableUser, observableArticle, (userInfoBaseResp, homepageData) -> {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put(Constant.LOGINDATA, userInfoBaseResp);
            hashMap.put(Constant.HOMEPAGEDATA, homepageData);
            return hashMap;
        }).compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<Map<String, Object>>() {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        /**
                         * 登录信息
                         */
                        DataResponse<UserInfo> userInfo = RxUtils.cast(map.get(Constant.LOGINDATA));
                        if (userInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            obtainView().loginSuccess(userInfo.getData());
                        } else if (userInfo.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().loginErr(userInfo.getErrorMsg());
                        }
                        /**
                         * banner信息
                         */
//                        BaseResp<List<BannerBean>> bannerResp = RxUtils.cast(map.get(Constant.BANNERDATA));
//                        if (bannerResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
//                            mView.getBannerOk(bannerResp.getData());
//                        } else if (bannerResp.getErrorCode() == Constant.REQUEST_ERROR) {
//                            mView.getBannerErr(bannerResp.getErrorMsg());
//                        }
                        /**
                         * 主页列表信息
                         */
                        DataResponse<HomePageArticleBean> articleResp = RxUtils.cast(map.get(Constant.HOMEPAGEDATA));
                        if (articleResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            obtainView().getHomepageListOk(articleResp.getData(), isRefresh);
                        } else if (articleResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            obtainView().getHomepageListErr(articleResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        obtainView().getHomepageListErr(e.getMessage());
                    }
                });
    }
}
