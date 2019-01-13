package com.bingo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bingo.utils.ToolbarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: bingo
 * Time:  2017-12-20
 */
public abstract class BaseFragment extends Fragment {
    //唯一标识
    public static String type = "";
    protected Activity mActivity;
    protected View mRootView;
    protected Unbinder mUnbinder;
    public ToolbarUtils mToolbarUtils;
    protected abstract int initLayoutId();

    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = this.getClass().getSimpleName();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initLayoutId();
        mRootView = inflater.inflate(initLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mToolbarUtils = ToolbarUtils.create().init(mRootView);
        initData();
        return mRootView;
    }

    /**
     * 跳转到指定的Activity
     *
     * @param clazz
     * @param isFinish 是否销毁当前页面
     */
    protected void moveTo(Class<?> clazz, boolean isFinish) {
        startActivity(new Intent(getActivity(), clazz));
        if (isFinish) {
            this.getActivity().finish();
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param clazz
     * @param isFinish 是否销毁当前页面
     */
    protected void moveTo(Class<?> clazz, boolean isFinish, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra("data",bundle);
        startActivity(intent);
        if (isFinish) {
            this.getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
