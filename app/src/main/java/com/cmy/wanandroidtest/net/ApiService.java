package com.cmy.wanandroidtest.net;

import com.cmy.wanandroidtest.bean.BannerBean;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.login.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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


    /**
     * 登录
     */
    @Headers({"baseUrl:normal"})
    @POST("user/login")
    @FormUrlEncoded
    Observable<DataResponse<UserInfo>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<DataResponse> collectArticle(@Path("id") int id);

    /**
     * 取消收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<DataResponse> cancelCollectArticle(@Path("id") int id);
}
