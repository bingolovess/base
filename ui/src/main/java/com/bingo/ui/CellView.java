package com.bingo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bingo on 2019/1/7.
 * Time:2019/1/7
 */

public class CellView extends LinearLayout {

    private String typeName, typeValue;
    private Drawable backgroudDrawable, leftImageDrawable, rightImageDrawable;
    private boolean hasBottomLine,defaultSwitch;
    private ImageView leftImageView, rightImageView;
    private TextView typeNameTv, typeValueTv, defaultIconView;
    private View itemView, line;
    private SwitchButton switchButton;

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
        defaultSwitch = typedArray.getBoolean(R.styleable.default_ui_default_switch,false);
        backgroudDrawable = typedArray.getDrawable(R.styleable.default_ui_backgroud);
        leftImageDrawable = typedArray.getDrawable(R.styleable.default_ui_left_image);
        rightImageDrawable = typedArray.getDrawable(R.styleable.default_ui_right_image);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_cell_item, this);
        itemView = rootView.findViewById(R.id.default_item);
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
        if(defaultSwitch){
            rightImageView.setVisibility(GONE);
            switchButton.setVisibility(VISIBLE);
        }
        if (backgroudDrawable != null) {
            itemView.setBackground(backgroudDrawable);
        }
        if (!TextUtils.isEmpty(typeName)) {
            typeNameTv.setText(typeName);
        }
        if (!TextUtils.isEmpty(typeValue)) {
            typeValueTv.setText(typeValue);
        }
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
        if (itemView != null) {
            itemView.setBackgroundResource(resId);
        }
    }

    /**
     * 设置背景
     *
     * @param drawable
     */
    public void setBackgroudDrawable(Drawable drawable) {
        if (itemView != null) {
            itemView.setBackground(drawable);
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
    public SwitchButton getSwitchButton(){
        return switchButton;
    }
}
