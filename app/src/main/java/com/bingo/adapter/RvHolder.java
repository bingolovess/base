package com.bingo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView 的ViewHolder
 * Created by bingo on 2018/6/13.
 * Time:2018/6/13
 */

public class RvHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;
    private View mConvertView;

    public RvHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }
    /**
     * 获取viewHolder
     */
    public static RvHolder getHolder(Context context, int layoutId, ViewGroup parent) {
        return new RvHolder(context, LayoutInflater.from(context).inflate(layoutId, parent,
                false));
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * get view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * set text
     */
    public RvHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置Enabled
     *
     * @param viewId
     * @param enable
     * @return
     */
    public RvHolder setEnabled(int viewId, boolean enable) {
        View v = getView(viewId);
        v.setEnabled(enable);
        return this;
    }

    /**
     * 点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public RvHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View v = getView(viewId);
        v.setOnClickListener(listener);
        return this;
    }

    /**
     * set image res
     */
    public RvHolder setImageView(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * set image res
     */
    public RvHolder setBackgroundRes(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setBackgroundResource(resId);
        return this;
    }

    /**
     * set image bitmap
     */
    public RvHolder setImageDraw(int viewId, Drawable drawable) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     * set check
     */
    public RvHolder setIsCheck(int viewId, boolean isCheck) {
        CheckBox iv = getView(viewId);
        iv.setChecked(isCheck);
        return this;
    }
}
