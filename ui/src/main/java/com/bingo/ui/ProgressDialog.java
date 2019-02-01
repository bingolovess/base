package com.bingo.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by bingo on 2019/1/15.
 * Time:2019/1/15
 */

public class ProgressDialog {
    private Dialog mLoadingDialog;
    //默认菊花转
    public static final int TYPE_DEFAULT = 0;
    //Android 自定义rotate 进度
    public static final int TYPE_ROTATE = 1;

    public ProgressDialog(Context context) {
        this(context, "", TYPE_DEFAULT);
    }

    public ProgressDialog(Context context, int type) {
        this(context, "", type);
    }

    public ProgressDialog(Context context, String msg, int type) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.lib_pub_progress_dialog, null);
        LoadingView mLoadingView = root.findViewById(R.id.loadingView);
        ProgressBar mProgressBar = root.findViewById(R.id.progressBar);
        TextView mTipsView = root.findViewById(R.id.tips);
        //提示信息
        if (TextUtils.isEmpty(msg)) {
            mTipsView.setVisibility(View.GONE);
        } else {
            mTipsView.setText(msg);
        }
        switch (type) {
            case TYPE_ROTATE:
                mLoadingView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case TYPE_DEFAULT:
            default:
                mLoadingView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                break;
        }
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.ProgressDialog);
        mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去掉白色背景
        // 设置返回键是否无效
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(root, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void hide() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
