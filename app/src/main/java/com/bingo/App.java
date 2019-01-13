package com.bingo;

import android.app.Application;

import com.bingo.utils.AppConfig;

/**
 * Created by bingo on 2018/12/27.
 * Time:2018/12/27
 */

public class App extends Application {
    private static App app;
    public static App get(){
        return  app;
    }
    //app启动的开始时间
    public long startTime;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        startTime = System.currentTimeMillis();
        AppConfig.getInstance().init(this);
    }
}
