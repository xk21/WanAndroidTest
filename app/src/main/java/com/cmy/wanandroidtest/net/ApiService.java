package com.cmy.wanandroidtest.net;

import com.cmy.wanandroidtest.bean.BannerBean;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-10-25 15:52
 */
public interface ApiService {

    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<DataResponse<HomePageArticleBean>> getArticleList(@Path("page") int num);

    @GET("banner/json")
    Observable<DataResponse<List<BannerBean>>> getBanner();
}
