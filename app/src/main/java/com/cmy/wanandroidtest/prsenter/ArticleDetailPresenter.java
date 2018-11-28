package com.cmy.wanandroidtest.prsenter;

import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.IArticleView;
import com.cmy.wanandroidtest.net.ApiService;
import com.cmy.wanandroidtest.net.DataResponse;
import com.cmy.wanandroidtest.net.HttpObserver;
import com.cmy.wanandroidtest.net.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-27 9:15
 */
public class ArticleDetailPresenter extends BasePresenter <IArticleView>{
    @Override
    public void onDestroy() {

    }

    public void collectArticle(int id) {
        RetrofitManager.createApi(ApiService.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
}
