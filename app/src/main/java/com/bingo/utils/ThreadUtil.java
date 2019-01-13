package com.bingo.utils;
import android.os.Looper;

/**
 * Created by bingo on 2018/12/26.
 * Time:2018/12/26
 */

public class ThreadUtil {
    /**
     * 是否是主线程
     * @return
     */
    public static boolean isMainThread(){
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
