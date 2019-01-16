package com.bingo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bingo on 2019/1/7.
 * Time:2019/1/7
 * TODO 布局中引入，不可以直接使用Butterknife @Click(R.id.xxx)的形式处理点击事件，待优化
 */

public class CellView extends LinearLayout {
    private String typeName, typeValue;
    private Drawable leftImageDrawable, rightImageDrawable;
    private boolean hasBottomLine, defaultSwitch;
    private ImageView leftImageView, rightImageView;
    private TextView typeNameTv, typeValueTv, defaultIconView;
    private View cellView, line;
    private SwitchButton switchButton;
    private int defaultNormal, defaultSelect;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.default_ui);
        typeName = typedArray.getString(R.styleable.default_ui_type_name);
        typeValue = typedArray.getString(R.styleable.default_ui_type_value);
        hasBottomLine = typedArray.getBoolean(R.styleable.default_ui_bottom_line, false);
        defaultSwitch = typedArray.getBoolean(R.styleable.default_ui_default_switch, false);
        defaultNormal = typedArray.getColor(R.styleable.default_ui_default_normal, -1);
        defaultSelect = typedArray.getColor(R.styleable.default_ui_default_select, -1);
        leftImageDrawable = typedArray.getDrawable(R.styleable.default_ui_left_image);
        rightImageDrawable = typedArray.getDrawable(R.styleable.default_ui_right_image);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_cell_item, this);
        cellView = rootView.findViewById(R.id.default_item);
        leftImageView = rootView.findViewById(R.id.default_left_iv);
        rightImageView = rootView.findViewById(R.id.default_right_iv);
        typeNameTv = rootView.findViewById(R.id.default_type_name);
        typeValueTv = rootView.findViewById(R.id.default_type_value);
        line = rootView.findViewById(R.id.default_line);
        defaultIconView = rootView.findViewById(R.id.default_type_icon);
        switchButton = rootView.findViewById(R.id.default_switch);
        if (hasBottomLine) {
            line.setVisibility(VISIBLE);
        } else {
            line.setVisibility(GONE);
        }
        if (leftImageDrawable != null) {
            leftImageView.setVisibility(VISIBLE);
            leftImageView.setImageDrawable(leftImageDrawable);
        }
        if (rightImageDrawable != null) {
            rightImageView.setVisibility(VISIBLE);
            rightImageView.setImageDrawable(rightImageDrawable);
            /*LinearLayout.LayoutParams layoutParams = (LayoutParams) typeValueTv.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            typeValueTv.setLayoutParams(layoutParams);*/
            switchButton.setVisibility(GONE);
        }
        if (defaultSwitch) {
            rightImageView.setVisibility(GONE);
            switchButton.setVisibility(VISIBLE);
        }
        if (defaultNormal != defaultSelect) {
            cellView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            cellView.setBackgroundColor(defaultNormal);
                            break;
                        case MotionEvent.ACTION_DOWN:
                            cellView.setBackgroundColor(defaultSelect);
                            break;
                        default:
                            break;
                    }
                    return false;//不拦截其他事件
                }
            });
        }
        if (!TextUtils.isEmpty(typeName)) {
            typeNameTv.setText(typeName);
        }
        if (!TextUtils.isEmpty(typeValue)) {
            typeValueTv.setText(typeValue);
        }
    }

    /**
     * 动态生成状态选择器
     */
    public Drawable creatPressedSelector(Context context, int pressed, int normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(context, pressed));//  状态  , 设置按下的图片
        drawable.addState(new int[]{}, ContextCompat.getDrawable(context, normal));//默认状态,默认状态下的图片
        //根据SDK版本设置状态选择器过度动画/渐变选择器/渐变动画
        if (Build.VERSION.SDK_INT > 10) {
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);
        }
        return drawable;
    }

    /**
     * 设置类别
     *
     * @param text
     */
    public void setTypeName(CharSequence text) {
        if (typeNameTv != null) {
            typeNameTv.setText(text);
        }
    }

    /**
     * 设置属性值
     *
     * @param text
     */
    public void setTypeValue(CharSequence text) {
        if (typeValueTv != null) {
            typeValueTv.setText(text);
        }
    }

    /**
     * 设置背景
     *
     * @param resId
     */
    public void setBackgroudDrawable(int resId) {
        if (cellView != null) {
            cellView.setBackgroundResource(resId);
        }
    }

    /**
     * 设置背景
     *
     * @param drawable
     */
    public void setBackgroudDrawable(Drawable drawable) {
        if (cellView != null) {
            cellView.setBackground(drawable);
        }
    }

    /**
     * 设置显示默认的icon
     */
    public void showDefaultIcon(int visible) {
        if (defaultIconView != null) {
            defaultIconView.setVisibility(visible);
        }
    }

    public void setDefaultIconText(CharSequence text) {
        if (defaultIconView != null) {
            defaultIconView.setText(text);
        }
    }

    public SwitchButton getSwitchButton() {
        return switchButton;
    }

    /**
     * 点击事件
     *
     * @param lisenter
     */
    public void setOnItemClickListener(OnClickListener lisenter) {
        if (cellView != null) {
            cellView.setOnClickListener(lisenter);
        }
    }
}
