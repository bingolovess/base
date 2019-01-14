package com.bingo.ui.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by bingo on 2019/1/14.
 * Time:2019/1/14
 */

public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
