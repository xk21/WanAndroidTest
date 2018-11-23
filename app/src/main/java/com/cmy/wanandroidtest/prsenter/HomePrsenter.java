package com.cmy.wanandroidtest.prsenter;

import android.util.Log;

import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.IHomeView;
import com.cmy.wanandroidtest.net.ApiService;
import com.cmy.wanandroidtest.net.DataResponse;
import com.cmy.wanandroidtest.net.HttpObserver;
import com.cmy.wanandroidtest.net.RetrofitManager;
import com.cmy.wanandroidtest.net.RxUtils;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-21 15:36
 */
public class HomePrsenter extends BasePresenter<IHomeView> {
    private boolean isRefresh;
    private int currentPage;

    @Override

    public void onDestroy() {

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

                    }
                });
    }
}
