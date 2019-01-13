package com.bingo.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bingo.study.R;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by bingo on 2019/1/6.
 * Time:2019/1/6
 */

public class ProgressDialog extends Dialog {

    public static ProgressDialog create(Context context) {
        return new ProgressDialog(context);
    }

    private ProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_progress);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        setCanceledOnTouchOutside(false);
        initViews();
    }

    private LoadingView mLoadingView;
    private TextView mTipView;

    private void initViews() {
        mLoadingView = findViewById(R.id.loadingView);
        mTipView = findViewById(R.id.tips);
    }

    public ProgressDialog setCancellable(boolean isCancellable) {
        this.setCancelable(isCancellable);
        this.setOnCancelListener(null);
        return this;
    }

    public ProgressDialog setCancellable(DialogInterface.OnCancelListener listener) {
        this.setCancelable(null != listener);
        this.setOnCancelListener(listener);
        return this;
    }

    public ProgressDialog setLabel(String label) {
        if (mTipView != null) {
            if (TextUtils.isEmpty(label)) {
                mTipView.setText("");
                mTipView.setVisibility(View.GONE);
            } else {
                mTipView.setText(label);
            }
        }
        return this;
    }

    public ProgressDialog setLoadingColor(int color) {
        if (mLoadingView != null) {
            mLoadingView.setLoadingColor(color);
        }
        return this;
    }
}
