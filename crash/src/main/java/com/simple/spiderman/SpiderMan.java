package com.simple.spiderman;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static SpiderMan spiderMan = new SpiderMan();

    private static Context mContext;
    //是不是debug版本，debug立即打开错误activity
    private static boolean isDebug = false;
    private Thread.UncaughtExceptionHandler mExceptionHandler;
//    private OnCrashListener mOnCrashListener;

    private SpiderMan() {
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @param context
     * @param isDubug BuildConfig.DEBUG
     * @return
     */
    public static SpiderMan init(Context context, boolean isDubug) {
        mContext = context;
        isDebug = isDubug;
        return spiderMan;
    }

    public static SpiderMan init(Context context) {
        return init(context, BuildConfig.DEBUG);
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {

        CrashModel model = parseCrash(ex);
        if (isDebug) {
            handleException(model);
        }
//        if (mOnCrashListener != null) {
//            mOnCrashListener.onCrash(t, ex, model);
//        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void handleException(CrashModel model) {
        Intent intent = new Intent(mContext, CrashActivity.class);
        intent.putExtra(CrashActivity.CRASH_MODEL, model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    private CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        try {
            model.setEx(ex);
            model.setTime(new Date().getTime());
            if (ex.getCause() != null) {
                ex = ex.getCause();
            }
            model.setExceptionMsg(ex.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            String exceptionType = ex.getClass().getName();

            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                StackTraceElement element = ex.getStackTrace()[0];

                model.setLineNumber(element.getLineNumber());
                model.setClassName(element.getClassName());
                model.setFileName(element.getFileName());
                model.setMethodName(element.getMethodName());
                model.setExceptionType(exceptionType);
            }

            model.setFullException(sw.toString());
        } catch (Exception e) {
            return model;
        }
        return model;
    }

//    public interface OnCrashListener {
//        void onCrash(Thread t, Throwable ex, CrashModel model);
//    }
//
//    public void setOnCrashListener(OnCrashListener listener) {
//        this.mOnCrashListener = listener;
//    }


}