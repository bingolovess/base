package com.bingo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对RecyclerView 的通用Adapter，支持单一类型适配器
 * 2、RvAdapter.create(this,layout,list).setLayoutManager(layoutManager).attachTo(rv_list);
 * <p>
 * Created by bingo on 2018/6/13.
 * Time:2018/6/13
 */

public class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    private static final String TAG = "RvAdapter";
    private RecyclerView.LayoutManager layoutManager;
    //LinearLayoutManager方向
    private int mOrientation = LinearLayoutManager.VERTICAL;
    //布局view
    private int mViewType;

    private RvAdapter(Context context, int layoutView, List<T> list) {
        this.mContext = context;
        this.mViewType = layoutView;
        mDatas = new ArrayList<>();
        if (list != null) {
            this.mDatas = list;
        }
    }

    public static <T> RvAdapter create(Context context, int layoutView, List<T> list) {
        return new RvAdapter(context, layoutView, list);
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RvHolder.getHolder(mContext, viewType, parent);
    }

    @Override
    public void onBindViewHolder(final RvHolder holder, final int position) {
        try {
            final T t = mDatas.get(position);
            //数据绑定
            if (mViewInjector != null) {
                mViewInjector.convert(holder, t, position);
            }
            //点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(holder, t, position);
                    }
                }
            });
            //长按事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mOnLongClickListener!=null){
                        mOnLongClickListener.onLongClick(holder,t,position);
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 绑定适配器
     *
     * @param recyclerView
     * @return
     */
    public RvAdapter attachTo(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (layoutManager == null) {
                layoutManager = new LinearLayoutManager(mContext);
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(this);
        }
        return this;
    }

    /**
     * 数据绑定
     *
     * @param injector
     * @return
     */
    public RvAdapter viewInjector(ViewInjector<T> injector) {
        this.mViewInjector = injector;
        return this;
    }

    /**
     * 点击事件
     *
     * @param listener
     * @return
     */
    public RvAdapter setOnClickListener(OnClickListener<T> listener) {
        this.mOnClickListener = listener;
        return this;
    }

    /**
     * 点击事件
     *
     * @param listener
     * @return
     */
    public RvAdapter setOnLongClickListener(OnLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
        return this;
    }

    /**
     * 设置LayoutManager
     *
     * @param manager
     * @return
     */
    public RvAdapter setLayoutManager(RecyclerView.LayoutManager manager) {
        if (manager != null) {
            layoutManager = manager;
        }
        return this;
    }

    /**
     * 设置LayoutManager
     *
     * @param orientation
     * @return
     */
    public RvAdapter setLinearLayoutManager(int orientation) {
        this.mOrientation = orientation;
        //第二个参数表示水平布局，第三个参数表示是否反转
        layoutManager = new LinearLayoutManager(mContext, mOrientation, false);
        return this;
    }

    /**
     * 设置LayoutManager
     *
     * @param spanCount
     * @return
     */
    public RvAdapter setGridLayoutManager(int spanCount) {
        layoutManager = new GridLayoutManager(mContext, spanCount);
        return this;
    }

    /**
     * 设置方向
     *
     * @param orientation
     * @return
     */
    public RvAdapter setOrientation(int orientation) {
        this.mOrientation = orientation;
        return this;
    }

    /**
     * 删除数据
     *
     * @param position
     */
    public void delete(int position) {
        if (mDatas == null) {
            Log.d(TAG, "数据源不存在！");
            return;
        }
        if (position >= mDatas.size() || position < 0) {
            Log.d(TAG, "position异常");
            return;
        }
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    public void updata(List<T> list) {
        if (list != null) {
            mDatas.clear();
            mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    /**
     * 数据绑定接口
     */
    private ViewInjector mViewInjector;

    public interface ViewInjector<T> {
        void convert(RvHolder holder, T t, int position);
    }

    /**
     * View点击事件
     */
    private OnClickListener mOnClickListener;

    public interface OnClickListener<T> {
        void onClick(RvHolder holder, T t, int position);
    }

    /**
     * View长按点击事件
     */
    private OnLongClickListener mOnLongClickListener;

    public interface OnLongClickListener<T> {
        void onLongClick(RvHolder holder, T t, int position);
    }
}
