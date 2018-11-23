package com.cmy.wanandroidtest.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
 
    private SparseArray<View> views;
 
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }
 
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    public void setImage(int viewId, Object params) {
        ImageView iv = getView(viewId);
        if (params instanceof String) {
        } else if (params instanceof Integer) {
            iv.setImageResource((Integer) params);
        } else if (params instanceof Bitmap) {
            iv.setImageBitmap((Bitmap) params);
        } else if (params instanceof Drawable) {
            iv.setImageDrawable((Drawable) params);
        } else {
            try {
                throw new Exception("params is wrong!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    public View getRootView() {
        return itemView;
    }
}