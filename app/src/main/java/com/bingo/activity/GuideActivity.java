package com.bingo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bingo.base.BaseActivity;
import com.bingo.study.R;
import com.bingo.utils.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by bingo on 2019/1/7.
 * Time:2019/1/7
 * Android 引导页
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.btn_next)
    Button mNext;

    @BindView(R.id.guide_dots_ll)
    LinearLayout mGuideDotsLL;
    @BindView(R.id.guide_dot_light)
    ImageView mGuideDotLight;

    private int mDistance;
    private List<View> mViewList;
    private int[] resDataList = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        mViewPager.setAdapter(new ViewPagerAdatper(mViewList));
        initDotEvent();
        //mViewPager.setPageTransformer(true, new DepthPageTransformer());//viewPager的纵深效果
    }

    private void initDotEvent() {
        mGuideDotLight.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mGuideDotsLL.getChildAt(1).getLeft() - mGuideDotsLL.getChildAt(0).getLeft();
                mGuideDotLight.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGuideDotLight.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mGuideDotLight.setLayoutParams(params);
                int showPos = mViewList.size() - 1;
                if (position == showPos) {
                    mNext.setVisibility(View.VISIBLE);
                }
                if (position != showPos && mNext.getVisibility() == View.VISIBLE) {
                    mNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGuideDotLight.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mGuideDotLight.setLayoutParams(params);
                int showPos = mViewList.size() - 1;
                if (position == showPos) {
                    mNext.setVisibility(View.VISIBLE);
                }
                if (position != showPos && mNext.getVisibility() == View.VISIBLE) {
                    mNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = LayoutInflater.from(this);
        for (int res : resDataList) {
            View view = lf.inflate(R.layout.layout_guide_item, null);
            ImageView img = view.findViewById(R.id.guide_image);
            img.setImageResource(res);
            mViewList.add(view);
        }
        for (int i = 0, len = mViewList.size(); i < len; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.guide_gray_dot);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 40, 0);
            mGuideDotsLL.addView(dot, layoutParams);
        }
    }

    public class ViewPagerAdatper extends PagerAdapter {
        private List<View> mViewList;

        public ViewPagerAdatper(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // 页面远离左侧页面
                page.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // 页面在由中间页滑动到左侧页面 或者 由左侧页面滑动到中间页
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                //页面在由中间页滑动到右侧页面 或者 由右侧页面滑动到中间页
                // 淡出效果
                page.setAlpha(1 - position);
                // 反方向移动
                page.setTranslationX(pageWidth * -position);
                // 0.75-1比例之间缩放
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // 页面远离右侧页面
                page.setAlpha(0);
            }
        }
    }
}
