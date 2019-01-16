package com.bingo.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bingo on 2019/1/15.
 * Time:2019/1/15
 */

public class ProgressDialog {
    private TextView mTipsView;
    private LoadingView mLoadingView;
    private Dialog mLoadingDialog;

    public ProgressDialog(Context context) {
        this(context, "");
    }

    public ProgressDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.lib_pub_progress_dialog, null);
        mLoadingView = root.findViewById(R.id.loadingView);
        mTipsView = root.findViewById(R.id.tips);
        mTipsView.setText(msg);
        if (TextUtils.isEmpty(msg)) {
            mTipsView.setVisibility(View.GONE);
        }
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.ProgressDialog);
        mLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去掉白色背景
        // 设置返回键是否无效
        mLoadingDialog.setCancelable(true);
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
