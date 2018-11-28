package com.cmy.wanandroidtest.interfaces;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-27 9:12
 */
public interface IArticleView {
    void collectArticleOK(String info);

    void collectArticleErr(String info);

    void cancelCollectArticleOK(String info);

    void cancelCollectArticleErr(String info);
}
