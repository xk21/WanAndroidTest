package com.cmy.wanandroidtest.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.cmy.wanandroidtest.constant.Constant;
import com.cmy.wanandroidtest.interfaces.IHomeView;
import com.cmy.wanandroidtest.login.UserInfo;
import com.cmy.wanandroidtest.prsenter.HomePrsenter;
import com.cmy.wanandroidtest.ui.activity.ArticleDetailsActivity;
import com.cmy.wanandroidtest.ui.activity.LoginActivity;
import com.cmy.wanandroidtest.utils.JumpUtil;
import com.cmy.wanandroidtest.utils.SharedPreferenceUtil;

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
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem=0;
    private boolean isLoading;
    private int clickPosition;

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }
    @Override
    protected void lazyLoad() {
        Log.d("szjjy", "lazyLoad: 1");
//        (Boolean) SharedPreferenceUtil.get(getContext(), Constant.ISLOGIN, Constant.FALSE)
        if (SharedPreferenceUtil.get(getContext(), Constant.USERNAME, Constant.DEFAULT).equals(Constant.DEFAULT)) {
            mPresenter.getHomepageList(0);
        } else {
            mPresenter.loginAndLoad();
        }
//        mPresenter.getHomepageList(0);
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
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new HomePageAdapter(getContext(), articleList);
        mAdapter = new HomePageAdapter2(getContext(), articleList,R.layout.item_homepage);
        mAdapter.setOnItemClickListner(this);
//        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (articleList.size()==mAdapter.getItemCount()) {
//                    mHomePrsenter.loadMore();
                    Log.i("szjjyh", "-----------onScrollStateChanged-----------");
                    Log.i("szjjyh", "newState: " + newState);
                }

            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem+1==mAdapter.getItemCount()&& isLoading) {
                    Log.i("szjjyh", "onScrolled: " + lastVisibleItem+" ="+mAdapter.getItemCount());
                    isLoading = false;
                    mHomePrsenter.loadMore();
                }
                if (lastVisibleItem+1!=mAdapter.getItemCount()) {
                    isLoading = true;
                }


            }

        });

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
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    private void showNormal() {
        mSwipeRefreshLayout.setRefreshing(false);
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
        Log.d("szjjy", "loginSuccess: ");
    }

    @Override
    public void loginErr(String info) {
        Log.d("szjjy", "loginErr: "+info);
    }

    @Override
    public void collectArticleOK(String info) {
        Log.d("szjjy", "collectArticleOK: ");
//        Toast.show(activity, getString(R.string.collect_success));
        mAdapter.getDate().get(clickPosition).setCollect(true);
        mAdapter.notifyItemChanged(clickPosition);
    }

    @Override
    public void collectArticleErr(String info) {
        Log.d("szjjy", "collectArticleErr: ");
//        ToastUtil.show(activity, getString(R.string.please_login));
//        JumpUtil.overlay(activity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        Log.d("szjjy", "cancelCollectArticleOK: ");
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        Log.d("szjjy", "cancelCollectArticleErr: ");
    }

    @Override
    public void onItemClickListner(View v, int position) {
        clickPosition = position;
        Log.d("szjjy", "onItemClickListner: ");
        if (v.getId()==R.id.tv_tag) {
            Toast.makeText(getContext(), v + ":" + mAdapter.getDate().get(position).getTags() + ":" + position, Toast.LENGTH_SHORT).show();
        }else if (v.getId()==R.id.item_card_homepage){
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getDate().get(position).getTitle());
            bundle.putString(Constant.ARTICLE_LINK, mAdapter.getDate().get(position).getLink());
            bundle.putInt(Constant.ARTICLE_ID, mAdapter.getDate().get(position).getId());
            bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getDate().get(position).isCollect());
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, getString(R.string.share_view));
            startActivity(new Intent(getContext(), ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
        }else if(v.getId()==R.id.image_collect){
            Log.d("szjjy", "image_collect: ");
            if ((Boolean) SharedPreferenceUtil.get(getContext(), Constant.ISLOGIN, Constant.FALSE)) {
                if (mAdapter.getDate().get(position).isCollect()) {
                    mPresenter.cancelCollectArticle(mAdapter.getDate().get(position).getId());
                } else {
                    mPresenter.collectArticle(mAdapter.getDate().get(position).getId());
                }
            } else {
                Toast.makeText(getContext(), "请先登录",Toast.LENGTH_SHORT).show();
                JumpUtil.overlay(getContext(), LoginActivity.class);
            }
        }
    }
}