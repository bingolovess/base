package com.bingo.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by bingo on 2019/1/2.
 * Time:2019/1/2
 */

public class LoadingUtils {
    private static LoadingUtils utils;
    private KProgressHUD mKProgressHUD;

    private LoadingUtils() {
    }

    public static LoadingUtils getInstance() {
        if (utils == null) {
            synchronized (LoadingUtils.class) {
                if (utils == null) {
                    utils = new LoadingUtils();
                }
            }
        }
        return utils;
    }

    public void show(Context context,String msg) {
        mKProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //.setLabel(msg)
                .setDetailsLabel(msg)
                //.setCancellable(false)
                .setAnimationSpeed(1)
                //.setDimAmount(0.5f)
                .show();
    }
    public void hide(){
        if(mKProgressHUD!=null&&mKProgressHUD.isShowing()){
            mKProgressHUD.dismiss();
        }
    }
}
