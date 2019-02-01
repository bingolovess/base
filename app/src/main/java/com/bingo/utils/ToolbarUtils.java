package com.bingo.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.study.R;

/**
 * Created by bingo on 2019/1/2.
 * Time:2019/1/2
 */

public class ToolbarUtils {
    private Toolbar mToolbar;
    private LinearLayout mLeftLayout, mRightLayout;
    private ImageView mLeftImageView, mRightImageView;
    private TextView mLeftTextView, mRightTextView, mTitle;

    public ToolbarUtils(Activity activity) {
        init(activity);
    }

    public ToolbarUtils(View view) {
        init(view);
    }

    /**
     * activity
     *
     * @param activity
     */
    private ToolbarUtils init(Activity activity) {
        if (activity != null) {
            mToolbar = activity.findViewById(R.id.toolbar);
            if (mToolbar != null) {
                mLeftLayout = mToolbar.findViewById(R.id.toolbar_left_ll);
                mRightLayout = mToolbar.findViewById(R.id.toolbar_right_ll);
                mLeftImageView = mToolbar.findViewById(R.id.toolbar_left_img);
                mRightImageView = mToolbar.findViewById(R.id.toolbar_right_img);
                mLeftTextView = mToolbar.findViewById(R.id.toolbar_left_tv);
                mRightTextView = mToolbar.findViewById(R.id.toolbar_right_tv);
                mTitle = mToolbar.findViewById(R.id.toolbar_title);
            }
        }
        return this;
    }

    /**
     * View
     *
     * @param view
     */
    private ToolbarUtils init(View view) {
        if (view != null) {
            mToolbar = view.findViewById(R.id.toolbar);
            if (mToolbar != null) {
                mLeftLayout = mToolbar.findViewById(R.id.toolbar_left_ll);
                mRightLayout = mToolbar.findViewById(R.id.toolbar_right_ll);
                mLeftImageView = mToolbar.findViewById(R.id.toolbar_left_img);
                mRightImageView = mToolbar.findViewById(R.id.toolbar_right_img);
                mLeftTextView = mToolbar.findViewById(R.id.toolbar_left_tv);
                mRightTextView = mToolbar.findViewById(R.id.toolbar_right_tv);
                mTitle = mToolbar.findViewById(R.id.toolbar_title);
            }
        }
        return this;
    }

    public void setLeftImageView(int imageRes) {
        if (mToolbar != null) {
            mLeftImageView.setImageResource(imageRes);
        }
    }

    public void setRightImageView(int imageRes) {
        if (mToolbar != null) {
            mRightImageView.setImageResource(imageRes);
        }
    }

    public void setLeftTextView(String txt) {
        if (mToolbar != null) {
            mLeftTextView.setText(txt);
        }
    }

    public void setRightTextView(String txt) {
        if (mToolbar != null) {
            mRightTextView.setText(txt);
        }
    }

    public void setTitle(String txt) {
        if (mToolbar != null) {
            mTitle.setText(txt);
        }
    }

    public void setToolbarColor(int colorRes) {
        if (mToolbar != null) {
            mToolbar.setBackgroundColor(colorRes);
        }
    }

    public void setToolbarDrawable(Drawable drawable) {
        if (mToolbar != null) {
            mToolbar.setBackground(drawable);
        }
    }

    public void setToolbarColor2(int resId) {
        if (mToolbar != null) {
            mToolbar.setBackgroundResource(resId);
        }
    }

    public void setOnLeftClickListener(View.OnClickListener listener) {
        if (mToolbar != null) {
            mLeftLayout.setOnClickListener(listener);
        }
    }

    public void setOnRightClickListener(View.OnClickListener listener) {
        if (mToolbar != null) {
            mRightLayout.setOnClickListener(listener);
        }
    }
}
