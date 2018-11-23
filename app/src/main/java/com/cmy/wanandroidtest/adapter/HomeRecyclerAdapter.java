package com.cmy.wanandroidtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.bean.HomePageArticleBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: java类作用描述
 * @Author: chenmingying
 * @CreateDate: 2018-11-22 10:51
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.VH> {
    private Context mContext;
    private List<HomePageArticleBean.DatasBean> mBeanList = new ArrayList<>();

    public HomeRecyclerAdapter(Context context,List<HomePageArticleBean.DatasBean> data) {
        mContext =context;
        mBeanList =data;
    }

    @NonNull

    @Override
    public HomeRecyclerAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homepage, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.VH viewHolder, int position) {
        viewHolder.mTvTag.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mBeanList.get(position).getTitle())) {
            viewHolder.mTvContent.setText(mBeanList.get(position).getTitle());
        }
        if (!TextUtils.isEmpty(mBeanList.get(position).getAuthor())) {
            viewHolder.mTvAuthor.setText(mBeanList.get(position).getAuthor());
        }
        if (!TextUtils.isEmpty(mBeanList.get(position).getNiceDate())) {
            viewHolder.mTvTime.setText(mBeanList.get(position).getNiceDate());
        }
        if (!TextUtils.isEmpty(mBeanList.get(position).getChapterName())) {
            String classifyName = mBeanList.get(position).getSuperChapterName() + " / " + mBeanList.get(position).getChapterName();
            viewHolder.mTvType.setText(classifyName);
        }
        if (mBeanList.get(position).getSuperChapterName().contains(mContext.getString(R.string.project))) {
            viewHolder.mTvTag.setVisibility(View.VISIBLE);
            viewHolder.mTvTag.setText(mContext.getString(R.string.project));
            viewHolder.mTvTag.setTextColor(mContext.getResources().getColor(R.color.green));
            viewHolder.mTvTag.setBackgroundResource(R.drawable.drawable_shape_green);
        } else if (mBeanList.get(position).getSuperChapterName().contains(mContext.getString(R.string.hot))) {
            viewHolder.mTvTag.setVisibility(View.VISIBLE);
            viewHolder.mTvTag.setText(mContext.getString(R.string.hot));
            viewHolder.mTvTag.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.mTvTag.setBackgroundResource(R.drawable.drawable_shape_red);
        }
//        viewHolder.addOnClickListener(R.id.tv_type);
//        viewHolder.addOnClickListener(R.id.image_collect);
//        viewHolder.setImageResource(R.id.image_collect, article.isCollect() ? R.drawable.icon_collect : R.drawable.icon_no_collect);

    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    public void replaceData(List<HomePageArticleBean.DatasBean> datas) {
        if (datas != mBeanList) {
            Log.d("szjjy", "replaceData: "+"  =");
            mBeanList.clear();
            mBeanList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addData(List<HomePageArticleBean.DatasBean> datas) {
        mBeanList.addAll(datas);
        notifyItemRangeInserted(mBeanList.size() - datas.size() , datas.size());
    }

    class VH extends RecyclerView.ViewHolder {
        TextView mTvAuthor;
        TextView mTvType;
        TextView mTvContent;
        ImageView mImageCollect;
        TextView mTvTime;
        TextView mTvTag;
        public VH(@NonNull View itemView) {
            super(itemView);
            mTvAuthor=itemView.findViewById(R.id.tv_author);
            mTvType=itemView.findViewById(R.id.tv_type);
            mTvContent=itemView.findViewById(R.id.tv_content);
            mImageCollect=itemView.findViewById(R.id.image_collect);
            mTvTime=itemView.findViewById(R.id.tv_time);
            mTvTag=itemView.findViewById(R.id.tv_tag);
        }
    }
}

