package com.cmy.wanandroidtest.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.adapter.HomePageAdapter2;
import com.cmy.wanandroidtest.base.BaseFragment;
import com.cmy.wanandroidtest.base.BaseRecyclerAdapter;
import com.cmy.wanandroidtest.bean.BannerBean;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.interfaces.IHomeView;
import com.cmy.wanandroidtest.login.UserInfo;
import com.cmy.wanandroidtest.prsenter.HomePrsenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends BaseFragment<IHomeView,HomePrsenter> implements IHomeView ,BaseRecyclerAdapter.OnItemClickListner{

    private HomePrsenter mHomePrsenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<HomePageArticleBean.DatasBean> articleList;
//    private HomePageAdapter mAdapter;
//    private HomeRecyclerAdapter mAdapter;
    private HomePageAdapter2 mAdapter;

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }
    @Override
    protected void lazyLoad() {
        Log.d("szjjy", "lazyLoad: 1");
        mPresenter.getHomepageList(0);
    }

    @Override
    protected HomePrsenter createPresenter() {
        if (mHomePrsenter ==null) {
            mHomePrsenter = new HomePrsenter();
        }
        return mHomePrsenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomePrsenter.autoRefresh();
            }
        });
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new HomePageAdapter(getContext(), articleList);
        mAdapter = new HomePageAdapter2(getContext(), articleList,R.layout.item_homepage);
        mAdapter.setOnItemClickListner(this);
//        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        articleList = new ArrayList<>();
    }

    @Override
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {
        Log.d("szjjy", "getHomepageListOk: "+dataBean+"  ="+isRefresh);
        if (mAdapter == null) {
            return;
        }
        if (true) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
//        showNormal();
    }

    @Override
    public void getHomepageListErr(String info) {

    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {

    }

    @Override
    public void getBannerErr(String info) {

    }

    @Override
    public void loginSuccess(UserInfo userInfo) {

    }

    @Override
    public void loginErr(String info) {

    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }

    @Override
    public void onItemClickListner(View v, int position) {
        if (v.getId()==R.id.tv_tag) {
            Toast.makeText(getContext(), v + ":" + articleList.get(position).getTags() + ":" + position, Toast.LENGTH_SHORT).show();
        }
    }
}