package com.bingo.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.study.R;


public class LoadingLayout extends LinearLayout {
    //菊花转
    protected LoadingView mLoadingView;
    //提示语设置
    protected TextView mTipView;

    public LoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View root = LayoutInflater.from(context).inflate(R.layout.layout_loading, this);
        mLoadingView = (LoadingView) root.findViewById(R.id.loadingView);
        mTipView = (TextView) root.findViewById(R.id.tips);
    }

    @Override
    public void setVisibility(int visibility) {
        switch (visibility) {
            case VISIBLE:
                mLoadingView.restart();
                break;
            case GONE:
            case INVISIBLE:
                mLoadingView.stop();
                break;
        }
        super.setVisibility(visibility);
    }

    public void setLoadingColor(int color) {
        if (mLoadingView != null) {
            mLoadingView.setLoadingColor(color);
            mTipView.setTextColor(getResources().getColor(color));
        }
    }

    /**
     * 设置提示语
     *
     * @param msg
     */
    public void show(String msg) {
        if (TextUtils.isEmpty(msg) || mTipView == null) return;
        mTipView.setText(msg);
    }
}