package com.bingo.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

/**
 * Created by bingo on 2019/1/2.
 * Time:2019/1/2
 */

public class AppConfig {
    private static AppConfig config;
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mypath";
    private Application app;
    private AppConfig(){}
    public static AppConfig getInstance(){
        if(config == null){
            synchronized (AppConfig.class){
                if(config == null){
                    config = new AppConfig();
                }
            }
        }
        return config;
    }
    //初始化相关数据
    public void init(Application app){
        this.app = app;
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
               boolean has =  ActManager.getInstance().checkActivity(activity.getClass());
                if(!has){
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
        createCacheDir();
        //设置崩溃异常处理
        //ExceptionCrashHandler.getInstance().init(app);
    }
    /**
     * 创建缓存目录
     */
    private void createCacheDir() {
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
            LogUtils.e("文件创建成功!");
        } else {
            LogUtils.e("文件已存在!");
        }
    }
}
