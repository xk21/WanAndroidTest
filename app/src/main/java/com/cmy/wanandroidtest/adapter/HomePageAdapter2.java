package com.cmy.wanandroidtest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.base.BaseRecyclerAdapter;
import com.cmy.wanandroidtest.base.BaseViewHolder;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-22 11:04
 */
public class HomePageAdapter2 extends BaseRecyclerAdapter<HomePageArticleBean.DatasBean> {
    private Context mContext;
    private List<HomePageArticleBean.DatasBean> mBeanList = new ArrayList<>();

    public HomePageAdapter2(Context context, List<HomePageArticleBean.DatasBean> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
        mBeanList = datas;
    }


    @Override
    protected void bindData(BaseViewHolder holder, HomePageArticleBean.DatasBean article, int position) {
        holder.getView(R.id.tv_tag).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(article.getTitle())) {
            holder.setText(R.id.tv_content, article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            holder.setText(R.id.tv_author, article.getAuthor());
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            holder.setText(R.id.tv_time, article.getNiceDate());
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            String classifyName = article.getSuperChapterName() + " / " + article.getChapterName();
            holder.setText(R.id.tv_type, classifyName);
        }
        if (article.getSuperChapterName().contains(mContext.getString(R.string.project))) {
            holder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_tag, mContext.getString(R.string.project));
//            holder.set(R.id.tv_tag,mContext.getResources().getColor(R.color.green));
            holder.getView(R.id.tv_tag).setBackgroundResource(R.drawable.drawable_shape_green);
        } else if (article.getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            holder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_tag, mContext.getString(R.string.hot));
//            holder.setTextColor(R.id.tv_tag,mContext.getResources().getColor(R.color.red));
//            holder.setImage(R.id.tv_tag,R.drawable.drawable_shape_red);
        }
//        holder.addOnClickListener(R.id.tv_type);
//        holder.addOnClickListener(R.id.image_collect);
//        holder.setClickListener(R.id.tv_tag);
        holder.getView(R.id.tv_tag).setOnClickListener(v->
                onItemClickListner.onItemClickListner(holder.getView(R.id.tv_tag),position));
    }
}
