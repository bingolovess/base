package com.bingo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.bingo.study.R;

import java.util.Calendar;

/**
 * Created by bingo on 2018/1/16.
 */

public class NotifyUtil {
    private static NotifyUtil util = new NotifyUtil();

    public static NotifyUtil getInstance() {
        return util;
    }

    private NotifyUtil() {
    }

    /*通知栏ID*/
    private int notification_id = 10001;
    private Context mContext;
    private Notification.Builder builder;
    private NotificationManager manager;

    /**
     * 普通带进度的通知栏
     *
     * @param context
     */
    public void initNotificationProgrss(Context context,Class<?> target) {
        mContext = context;
        builder = new Notification.Builder(context);
        //这个可以视具体情况而定
        Intent intent = new Intent(context,target);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                0, intent, 0);
        builder.setSmallIcon(R.mipmap.ic_launcher)// 设置通知小图标,在下拉之前显示的图标
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))// 落下后显示的图标
                //.setContentTitle(mContext.getResources().getString(R.string.app_name) + ".apk")
                //.setOngoing(true)// 不能被用户x掉，会一直显示，如音乐播放等
                .setAutoCancel(true)// 自动取消
                .setOnlyAlertOnce(true)// 只alert一次
                .setContentInfo("")// 添加到了右下角
                // .setSound(uri);//声音提示
                // .setSound(sound, streamType);//科设置 streamtype
                // .setStyle(style);//style设置
                // .setVibrate(long[]);//设置震动
                //.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setProgress(100, 0, false)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT > 21) {
            /*任何情况下都显示通知*/
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            /*只有当没锁屏时显示*/
            //builder.setVisibility(Notification.VISIBILITY_PRIVATE);
            /*在pin,password等安全锁和没有锁屏的情况下才显示*/
            //builder.setVisibility(Notification.VISIBILITY_SECRET);
        }
        Notification mNotification = builder.build();
        mNotification.flags = Notification.FLAG_NO_CLEAR; //设置不可手动清除
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 利用RemoteView 自定义通知栏
     */
    public void initRemoteViewsNotification(Context context,Class<?> target) {
        mContext = context;
        builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))// 落下后显示的图标
                //.setOngoing(true)// 不能被用户x掉，会一直显示，如音乐播放等
                .setAutoCancel(true)// 自动取消
                .setOnlyAlertOnce(true)// 只alert一次
                .setContentInfo("")// 添加到了右下角
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        if (Build.VERSION.SDK_INT > 21) {
            /*任何情况下都显示通知*/
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            /*只有当没锁屏时显示*/
            //builder.setVisibility(Notification.VISIBILITY_PRIVATE);
            /*在pin,password等安全锁和没有锁屏的情况下才显示*/
            //builder.setVisibility(Notification.VISIBILITY_SECRET);
        }
       /* RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notify_remote_view);
        mRemoteViews.setImageViewResource(R.id.logo, R.drawable.large_icon);
        mRemoteViews.setTextViewText(R.id.notify_title, "这是自定义view的title");
        mRemoteViews.setTextViewText(R.id.notify_content, "这里是自定义view的内容");
        mRemoteViews.setTextViewText(R.id.notify_time, getCurrentTime());
        builder.setContent(mRemoteViews);*/
        Intent intent = new Intent(context,target);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification mNotification = builder.build();
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 显示原生的notification
     */
    public void initNotification(Context context) {
        mContext = context;
        // API 16之前的方式好多都过时
        builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))// 落下后显示的图标
                //.setOngoing(true)// 不能被用户x掉，会一直显示，如音乐播放等
                .setAutoCancel(true)// 自动取消
                .setOnlyAlertOnce(true)// 只alert一次
                .setContentInfo("")// 添加到了右下角
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        if (Build.VERSION.SDK_INT > 21) {
            /*任何情况下都显示通知*/
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            /*只有当没锁屏时显示*/
            //builder.setVisibility(Notification.VISIBILITY_PRIVATE);
            /*在pin,password等安全锁和没有锁屏的情况下才显示*/
            //builder.setVisibility(Notification.VISIBILITY_SECRET);
        }
        Notification mNotification = builder.build();
        //mNotification.flags = Notification.FLAG_AUTO_CANCEL;//自动清除
        mNotification.flags = Notification.FLAG_NO_CLEAR; //设置不可手动清除
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 外部调用显示通知栏
     *
     * @param title
     * @param content
     */
    public void show(String title, String content) {
        builder.setTicker("新消息通知")//收到通知的时候用于显示于屏幕顶部通知栏的内容
                .setContentTitle(title)
                .setContentText(content);
        refresh();
    }

    /**
     * 外部调用带进度条的显示通知栏
     * 先调用 initNotificationProgrss
     *
     * @param title
     * @param content
     * @param progress
     */
    public void showProgress(String title, String content, int progress) {
        builder.setTicker("新消息通知:" + mContext.getResources().getString(R.string.app_name) + ".apk 开始下载")
                .setContentTitle(title)
                .setContentText(content)
                .setProgress(100, progress, false);
        refresh();
    }

    /**
     * 刷新通知栏数据
     */
    public void refresh() {
        manager.notify(notification_id, builder.build());
    }

    /**
     * 取消通知栏
     */
    public void cancle() {
        manager.cancel(notification_id);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private String getCurrentTime() {
        String time = "unknown";
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        time = hour + ":" + minute;
        return time;
    }
}
