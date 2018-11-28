package com.cmy.wanandroidtest.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;
    private List<T> datas;
    private int layoutId;
    protected OnItemClickListner onItemClickListner;//单击事件
    protected OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识
    protected int mLastAnimatedPosition = -1;
    private RequestLoadMoreListener mRequestLoadMoreListener;

    public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        itemAnimation(holder.itemView, position);
        bindData(holder, datas.get(position), position);
    }

    public void replaceData(List<T> data) {
        if (data != datas) {
            Log.d("szjjy", "replaceData: " + "  =");
            datas.clear();
            datas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        datas.addAll(data);
        notifyItemRangeInserted(datas.size() - data.size(), data.size());
    }
    public List<T> getDate() {
       return datas;
    }

    protected void itemAnimation(View itemView, int position) {
        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            itemView.setTranslationY(getScreenHeight());
            itemView.animate()
                    .translationY(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }

    private int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected abstract void bindData(BaseViewHolder holder, T data, int position);

    //    上拉加载
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        openLoadMore(requestLoadMoreListener);
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
//        mNextLoadEnable = true;
//        mLoadMoreEnable = true;
//        mLoading = false;
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }


    //点击事件接口
    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }
}