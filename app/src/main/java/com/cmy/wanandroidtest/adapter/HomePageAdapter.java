package com.cmy.wanandroidtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;
import com.cmy.wanandroidtest.test.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-22 11:04
 */
public class HomePageAdapter extends BaseRecyclerViewAdapter<HomePageArticleBean.DatasBean> {
    private Context mContext;
    private List<HomePageArticleBean.DatasBean> mBeanList = new ArrayList<>();
    protected PhotoitemClickListener mOnItemClickListener;

    public HomePageAdapter(Context context, List beans) {
        super(context, beans);
        mContext = context;
        mBeanList = beans;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_homepage;
    }

    @Override
    protected void onBindDataToView(ViewHolder holder, HomePageArticleBean.DatasBean article) {
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
        holder.setClickListener(R.id.tv_tag);
//        holder.setImageResource(R.id.image_collect, article.isCollect() ? R.drawable.icon_collect : R.drawable.icon_no_collect);
    }

    @Override
    protected void onSingleViewClick(View view, int position) {
        super.onSingleViewClick(view, position);
        Log.d("szjjyh","onSingleViewClick");
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setOnItemClickListener(PhotoitemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface PhotoitemClickListener {
        public void onItemClick(View itemView, int position) ;
        public void onLongItemClick(View itemView, int position) ;
    }
}
