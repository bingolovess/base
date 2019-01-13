package com.bingo.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bingo.study.R;

/**
 * Created by bingo on 2018/12/26.
 * Time:2018/12/26
 */

public class ToastUtils {
    public static void show(Context ctx, String message) {
        showMessage(ctx, message);
    }
    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    private static void showMessage(Context ctx, String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = LayoutInflater.from(ctx);//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast, null); //加載layout下的布局
        TextView title = view.findViewById(R.id.toastText);
        title.setText(messages); //toast内容
        Toast toast = new Toast(ctx);
        //toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        //获取屏幕高度
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的2/3，不会出现不适配的问题
        toast.setGravity(Gravity.TOP, 0, height / 4 *3);
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }
}
