package com.bingo.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * Created by bingo on 2019/1/2.
 * Time:2019/1/2
 */

public class AppConfig {
    private static AppConfig config;
    private String path;
    private Context mContext;
    /*屏幕适配（仿头条的适配）*/
    public float sNoncompatDensity, sNoncompatScaledDensity;
    public int value;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (config == null) {
            synchronized (AppConfig.class) {
                if (config == null) {
                    config = new AppConfig();
                }
            }
        }
        return config;
    }

    //初始化相关数据
    public void init(Application app) {
        this.mContext = app.getApplicationContext();
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                boolean has = ActManager.getInstance().checkActivity(activity.getClass());
                if (!has) {
                    LogUtils.e("不在栈中");
                    ActManager.getInstance().pushActivity(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActManager.getInstance().existActivity(activity);
            }
        });
        //创建缓存目录
        //createCacheDir();
        //设置崩溃异常处理
        //ExceptionCrashHandler.getInstance().init(app);
        setCustomDensity(app);
    }

    /**
     * 屏幕适配
     *
     * @param application
     */
    private void setCustomDensity(@NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if ((application.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            value = 540;
        } else {
            value = 360;
        }
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / value;
        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        //final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        //activityDisplayMetrics.density = targetDensity;
        //activityDisplayMetrics.scaledDensity = targetScaledDensity;
        //activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 创建缓存目录 默认使用包名作为文件名(保持唯一性)
     */
    private void createCacheDir() {
        if (mContext != null) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getPackageName(mContext);
            File destDir = new File(path);
            if (!destDir.exists()) {
                destDir.mkdirs();
                LogUtils.e("文件创建成功!");
            } else {
                LogUtils.e("文件已存在!");
            }
        }
    }
    public String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
