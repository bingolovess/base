package com.bingo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.bingo.App;
import com.bingo.study.MainActivity;
import com.bingo.study.R;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;
    /*判断是否已经登录中*/
    private boolean isLogin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Splash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        long time = 0;
        if (App.get().startTime > 0) {
            time = System.currentTimeMillis() - App.get().startTime;
            App.get().startTime = 0;
        }
        if (time > 2000) {
            moveTo(MainActivity.class);
        } else {
            mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveTo(MainActivity.class);
                }
            }, 2000 - time);
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param clazz
     */
    public void moveTo(Class<?> clazz) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        startActivity(new Intent(this, clazz));
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
