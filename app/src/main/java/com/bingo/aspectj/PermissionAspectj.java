package com.bingo.aspectj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.bingo.aspectj.annotation.Permission;
import com.bingo.utils.LogUtils;
import com.bingo.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * description:  权限切片（android6.0以上危险权限）
 * 记得Manifest加入权限 兼容低版本
 */
@Aspect
public class PermissionAspectj {
    private static final String TAG = "PermissionAspectj";

    @Pointcut("execution(@com.lehand.aspectj.annotation.Permission * *(..))")
    public void methodAnnotatedWithPermission() {
    }

    @Around("methodAnnotatedWithPermission()")
    public void wavePointcutAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        //Log.e(TAG, "进入切入点的方法");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 类名
        final String className = methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        final String methodName = methodSignature.getName();
        // 功能名
        Permission permissions = methodSignature.getMethod()
                .getAnnotation(Permission.class);
        final String[] value = permissions.value();
        //Log.e(TAG, "------>" + value[0]);
        final Activity activity = (Activity) joinPoint.getTarget();
        //Log.e(TAG, "------>" + activity.getPackageName());
        final long start = System.currentTimeMillis();
        if (!PermissionUtils.checkPermissions(activity, value)) {
            new AlertDialog.Builder(activity)
                    .setTitle("提示")
                    .setMessage("为了应用可以正常使用，请您点击确认申请权限。")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionUtils.requestPermissionsResult(activity, 1, value
                                    , new PermissionUtils.OnPermissionListener() {
                                        @Override
                                        public void onPermissionGranted() {
                                            try {
                                                //获得权限，执行原方法
                                               joinPoint.proceed();
                                               userBehaviorStatistics(className,methodName,start);
                                            } catch (Throwable e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onPermissionDenied() {
                                            PermissionUtils.showTipsDialog(activity);
                                            userBehaviorStatistics(className,methodName,start);
                                        }
                                    });
                        }
                    })
                    .create()
                    .show();
        } else {
            //Toast.makeText(activity, "已经授权！", Toast.LENGTH_SHORT).show();
            joinPoint.proceed();
            userBehaviorStatistics(className,methodName,"已授权",start);
        }
    }

    /**
     * 用户行为统计
     *
     * @param className 外部调用类名
     * @param methodName 外部注解进入的方法名
     * @param startTime
     */
    private void userBehaviorStatistics(String className, String methodName, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        LogUtils.e(TAG, String.format("%s类中%s方法执行,耗时：%dms", className, methodName, duration));
    }
    /**
     * 用户行为统计
     *
     * @param className 外部调用类名
     * @param methodName 外部注解进入的方法名
     * @param insideName 内部逻辑处理名
     * @param startTime
     */
    private void userBehaviorStatistics(String className, String methodName,String insideName,long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        LogUtils.e(TAG, String.format("%s类中%s方法执行，%s情况下执行,耗时：%dms", className, methodName,insideName, duration));
    }
}
