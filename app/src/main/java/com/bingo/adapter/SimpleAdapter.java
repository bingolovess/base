package com.bingo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingo on 2018/5/13.
 * 针对ListView 的Adapter
 */

public class SimpleAdapter<T> extends BaseAdapter {
    private static final String TAG = SimpleAdapter.class.getCanonicalName();
    //外部数据
    private List<T> data;
    private Context mContext;
    private int layoutId;
    private ListView mListView;
    //view对应的ViewHolder缓存
    private Map<View, ViewHolder> mViewHolderMaps;

    private SimpleAdapter(Context context, int layoutId, List<T> data) {
        this.mContext = context;
        this.layoutId = layoutId;
        this.data = data;
        mViewHolderMaps = new HashMap<>();
    }

    public static SimpleAdapter create(Context context, int layoutId, List data) {
        return new SimpleAdapter(context, layoutId, data);
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent,
                    false);
            viewHolder = new ViewHolder(convertView, position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.position = position;
        }
        if (mViewInjector != null) {
            mViewInjector.convert(viewHolder, getItem(position), position);
        }
        mViewHolderMaps.put(convertView, viewHolder);
        return viewHolder.mView;
    }

    public SimpleAdapter<T> attachTo(ListView listView) {
        if (listView != null) {
            this.mListView = listView;
            listView.setAdapter(this);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, mViewHolderMaps.get(view), data.get(i), i);
                    }
                }
            });
        }
        return this;
    }

    public SimpleAdapter<T> setOnItemClick(OnClickListener listener) {
        if (listener != null) {
            this.onClickListener = listener;
        }
        return this;
    }

    /**
     * 设置动态数据绑定操作
     *
     * @param mViewInjector
     * @return
     */
    public SimpleAdapter<T> setViewBinder(ViewInjector mViewInjector) {
        setViewInjector(mViewInjector);
        return this;
    }

    /**
     * 刷新数据
     *
     * @param list
     * @return
     */
    public void updateData(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        } else {
            this.data.clear();
            this.data.addAll(list);
            if (mViewHolderMaps != null) {
                mViewHolderMaps.clear();
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void removeAll() {
        if (this.data != null) {
            this.data.clear();
            if (mViewHolderMaps != null) {
                mViewHolderMaps.clear();
            }
            this.notifyDataSetChanged();
        }
    }

    /**
     * 数据绑定接口
     */
    private ViewInjector mViewInjector;

    private void setViewInjector(ViewInjector mViewInjector) {
        this.mViewInjector = mViewInjector;
    }

    public interface ViewInjector<T> {
        void convert(ViewHolder viewHolder, T item, int position);
    }

    /**
     * listView item 点击事件
     */
    private OnClickListener onClickListener;

    public interface OnClickListener<T> {
        void onClick(View view, ViewHolder<T> holder, T t, int position);
    }
}
