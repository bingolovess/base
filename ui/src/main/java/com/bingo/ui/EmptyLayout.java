package com.bingo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.ui.utils.Util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class EmptyLayout extends FrameLayout {

    /*************** Type ***************/
    public final static int STATE_LOADING = 0x10; // Default State: loading state
    public final static int STATE_EMPTY = 0x11; // Default State: no data
    public final static int STATE_NET_ERROR = 0x12; // Default State: network error
    public final static int STATE_CONTENT = 0x13; // Default State: content

    /*************** Centered type ***************/
    private final static int CENT_TYPE_MAIN = 1;
    private final static int CENT_TYPE_LOCAL = 2;
    private final static float[] AJUST_HEIGHT = new float[]{0, 50, 70};

    @IntDef({STATE_LOADING, STATE_EMPTY, STATE_NET_ERROR,
            EmptyLayout.STATE_CONTENT})
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {

    }

    private int layoutId, contentId;
    private int centerType; // Centered type
    private float adjustHeightT; // Correction height
    private float adjustHeightB; // Correction height
    private int resIdEmpty, resIdNetError;

    private LinearLayout llytDsl;
    private ImageView ivIcon;
    private TextView tvDesc;
    private Button button;
    private LoadingLayout ldlLoading;
    private View rootView, contentView;//内容视图

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        contentId = typedArray.getResourceId(R.styleable.EmptyLayout_lib_pub_content_layout, -1);
        centerType = typedArray.getInteger(R.styleable.EmptyLayout_lib_pub_ceterType, 0);
        adjustHeightT = typedArray.getDimension(R.styleable.EmptyLayout_lib_pub_adjustHeightT, 0);
        adjustHeightB = typedArray.getDimension(R.styleable.EmptyLayout_lib_pub_adjustHeightB, 0);
        resIdEmpty = typedArray.getResourceId(R.styleable.EmptyLayout_lib_pub_emptyDrawable, R.drawable.lib_pub_ic_no_data);
        resIdNetError = typedArray.getResourceId(R.styleable.EmptyLayout_lib_pub_netErroDrawable, R.drawable.lib_pub_ic_network_err);
        typedArray.recycle();
        init(context);
    }

    protected void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.lib_empty_layout, this);
        if (contentId != -1) {
            contentView = LayoutInflater.from(context).inflate(contentId, null);
        }
        View vT = rootView.findViewById(R.id.v_dsl_t);
        View vB = rootView.findViewById(R.id.v_dsl_b);

        llytDsl = (LinearLayout) rootView.findViewById(R.id.llyt_dsl);
        ivIcon = (ImageView) rootView.findViewById(R.id.iv_dsl_icon);
        tvDesc = (TextView) rootView.findViewById(R.id.tv_dsl_desc);
        button = (Button) rootView.findViewById(R.id.btn_dsl);
        ldlLoading = (LoadingLayout) rootView.findViewById(R.id.ldl_loading);
        if (contentView != null) {
            addView(contentView);
        }
        ViewGroup.LayoutParams paramsT = vT.getLayoutParams();
        ViewGroup.LayoutParams paramsB = vB.getLayoutParams();
        switch (centerType) {
            case CENT_TYPE_MAIN:
                paramsB.height = Util.dip2px(context, AJUST_HEIGHT[CENT_TYPE_MAIN]);
                break;
            case CENT_TYPE_LOCAL:
                paramsB.height = Util.dip2px(context, AJUST_HEIGHT[CENT_TYPE_LOCAL]);
                break;
            default:
                // Do nothing, default center 0/0
                break;
        }
        if (adjustHeightT != 0 || adjustHeightB != 0) {
            // Priority is greater than and overrides centerType
            paramsT.height = (int) adjustHeightT;
            paramsB.height = (int) adjustHeightB;
        }
        vT.setLayoutParams(paramsT); // Set the top correction height
        vB.setLayoutParams(paramsB); // Set bottom correction height
    }

    /*点击重试*/
    public void setOnRetryClick(OnClickListener listener) {
        if (button != null) {
            button.setOnClickListener(listener);
        }
    }

    private void showLoading() {
        setVisibility(VISIBLE);
        contentView.setVisibility(GONE);
        ldlLoading.setVisibility(VISIBLE);
        llytDsl.setVisibility(GONE);
    }

    private void showEmpty() {
        setVisibility(VISIBLE);
        contentView.setVisibility(GONE);
        ldlLoading.setVisibility(GONE);
        llytDsl.setVisibility(VISIBLE);
        ivIcon.setImageResource(resIdEmpty);
        tvDesc.setText(getResources().getString(R.string.lib_pub_no_data));
        button.setVisibility(GONE);
    }

    private void showNetError() {
        setVisibility(VISIBLE);
        contentView.setVisibility(GONE);
        ldlLoading.setVisibility(GONE);
        llytDsl.setVisibility(VISIBLE);
        ivIcon.setImageResource(resIdNetError);
        tvDesc.setText(getResources().getString(R.string.lib_pub_net_error));
        button.setText(getResources().getString(R.string.lib_pub_retry));
        button.setVisibility(VISIBLE);
    }

    private void showContent() {
        setVisibility(VISIBLE);
        ldlLoading.setVisibility(GONE);
        llytDsl.setVisibility(GONE);
        contentView.setVisibility(VISIBLE);
    }

    /**
     * Display image
     */
    public EmptyLayout icon(@DrawableRes int resId) {
        ivIcon.setImageResource(resId);
        return this;
    }

    /**
     * Display image
     */
    public EmptyLayout icon(@Nullable Drawable drawable) {
        ivIcon.setImageDrawable(drawable);
        return this;
    }

    /**
     * Display image
     */
   /* public EmptyLayout icon(String url) {
        Glide.with(getContext())
                .load(url)
                .apply(new RequestOptions().dontTransform().dontAnimate())
                .into(ivIcon);
        return this;
    }*/

    /**
     * Display gif
     */
   /* public EmptyLayout gif(int resId) {
        Glide.with(getContext())
                .asGif()
                .load(resId)
                .into(ivIcon);
        return this;
    }*/

    /**
     * Display gif
     */
    /*public EmptyLayout gif(String url) {
        Glide.with(getContext())
                .asGif()
                .load(url)
                .into(ivIcon);
        return this;
    }*/

    /**
     * Set prompt text
     */
    public EmptyLayout desc(CharSequence text) {
        tvDesc.setText(text);
        return this;
    }

    /**
     * Set button text, visibility state
     */
    public EmptyLayout button(CharSequence text, int visibility) {
        button.setText(text);
        button.setVisibility(visibility);
        return this;
    }

    /**
     * Set state
     */
    public EmptyLayout setState(@State int state) {
        switch (state) {
            case STATE_CONTENT:
                showContent();
                break;
            case STATE_LOADING:
                showLoading();
                break;
            case STATE_EMPTY:
                showEmpty();
                break;
            case STATE_NET_ERROR:
                showNetError();
                break;
            default:
                break;
        }
        return this;
    }
}
