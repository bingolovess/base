package com.bingo.ui.adapter;

/**
 * Created by bingo on 2019/1/14.
 * Time:2019/1/14
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);
}
