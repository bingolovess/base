package com.bingo.aspectj;

import com.bingo.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by bingo on 2018/7/2.
 * Time:2018/7/2
 * 避免重复点击
 */
@Aspect
public class SingleClickAspectj {

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    @Pointcut("execution(@com.lehand.aspectj.annotation.SingleClick * *(..))")
    public void methodAnnotatedWithAvoidRepeatClick() {
    }

    @Around("methodAnnotatedWithAvoidRepeatClick()")
    public void wavePointcutAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (!isFastClick()) {
            //获得权限，执行原方法
            joinPoint.proceed();
        } else {
            LogUtils.e("SingleClickAspectj","时间间隔太短");
        }
    }
}
