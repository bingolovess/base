package com.bingo.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bingo.study.R;
import com.bingo.utils.ACache;
import com.bingo.utils.ActManager;
import com.bingo.utils.StatusBarUtil;
import com.bingo.utils.StatusBarUtils;
import com.bingo.utils.ToastUtils;
import com.bingo.utils.ToolbarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Bundle saveState;
    protected ToolbarUtils mToolbarUtils;
    private Unbinder mUnbinder;
    //private static Handler mainThreadhandler = new Handler();
    public static ACache aCache;

    protected void initData() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setTheme(R.style.ActivityAnimTheme);
        super.onCreate(savedInstanceState);
        saveState = savedInstanceState;
        mContext = this;
        aCache = ACache.get(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        //if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            //StatusBarUtil.setStatusBarColor(this,0x55000000);
        //}
        initData();
        mToolbarUtils = ToolbarUtils.create().init(this);
    }

    /**
     * 通过资源id获取状态栏的高度
     *
     * @return
     */
    protected int getStatusHeight() {
        //通过资源的id，获取id资源
        Resources resources = getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(resId);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Toast
     *
     * @param msg
     */
    protected void Toasting(final String msg) {
        /*mainThreadhandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(mContext,msg);
            }
        });*/
    }

    /**
     * 根据资源id获取string
     *
     * @param resId
     * @return
     */
    protected String getSringRes(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 根据资源id获取color
     *
     * @param resId
     * @return
     */
    protected int getColorRes(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param clazz
     */
    public void moveTo(Class<?> clazz) {
        moveTo(clazz,false);
    }
    /**
     * 跳转到指定的Activity
     *
     * @param clazz
     * @param isFinish 是否销毁当前页面
     */
    public void moveTo(Class<?> clazz, boolean isFinish) {
        startActivity(new Intent(this, clazz));
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 弹起键盘
     */
    protected void showInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }
    /**
     * 此方法只是关闭软键盘 可以在finish之前调用一下
     */
    private void hideInputKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideInputKeyboard();
        // 极端情况下，系统会杀死APP进程，并不执行onDestroy()，
        // 因此需要使用onStop()来释放资源，从而避免内存泄漏。
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
