package com.bingo.gesture.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.bingo.gesture.model.Point;

/**
 * @ClassName: GestureLockPainter
 * @Description: 手势解锁绘制者（GestureLockView默认使用）
 * @Author: wangnan7
 * @Date: 2017/9/21
 */

public class GestureLockPainter extends Painter {

    /**
     * 绘制正常状态的点
     *
     * @param point       单位点
     * @param canvas      画布
     * @param normalPaint 正常状态画笔
     */
    @Override
    public void drawNormalPoint(Point point, Canvas canvas, Paint normalPaint) {
        // 1.记录画笔的原始属性（绘制过程中需要修改的属性）,绘制结束时进行还原
        Paint.Style style = normalPaint.getStyle();
        float originStrokeWidth = normalPaint.getStrokeWidth();
        int color = normalPaint.getColor();
        //绘制空心圆内部
        normalPaint.setStyle(Paint.Style.FILL);
        normalPaint.setColor(Color.WHITE);//设置默认的圆背景为白色
        canvas.drawCircle(point.x, point.y, point.radius-originStrokeWidth, normalPaint);
        // 2.绘制空心圆边界
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(color);//设置默认的圆环颜色//Color.parseColor("#91D5FF")
        normalPaint.setStrokeWidth(point.radius / 32);
        canvas.drawCircle(point.x, point.y, point.radius, normalPaint);
        // 3.结束绘制，还原画笔属性
        normalPaint.setStyle(style);
        normalPaint.setStrokeWidth(originStrokeWidth);
    }

    /**
     * 绘制按下状态的点
     *
     * @param point      单位点
     * @param canvas     画布
     * @param pressPaint 按下状态画笔
     */
    @Override
    public void drawPressPoint(Point point, Canvas canvas, Paint pressPaint) {
        // 1.记录画笔的原始属性（绘制过程中需要修改的属性）,绘制结束时进行还原
        Paint.Style style = pressPaint.getStyle();
        float originStrokeWidth = pressPaint.getStrokeWidth();
        int color = pressPaint.getColor();
        //绘制空心圆内部
        pressPaint.setStyle(Paint.Style.FILL);
        pressPaint.setColor(Color.WHITE);//设置默认的圆背景为白色
        canvas.drawCircle(point.x, point.y, point.radius-originStrokeWidth, pressPaint);
        // 2.绘制实心点
        pressPaint.setStyle(Paint.Style.FILL);
        pressPaint.setColor(color);//设置默认的圆环颜色 //Color.parseColor("#1E8BDE")
        canvas.drawCircle(point.x, point.y, point.radius / 3, pressPaint);
        // 3.绘制空心圆边界
        pressPaint.setStyle(Paint.Style.STROKE);
        pressPaint.setStrokeWidth(point.radius / 16);
        canvas.drawCircle(point.x, point.y, point.radius, pressPaint);
        // 4.结束绘制，还原画笔属性
        pressPaint.setStyle(style);
        pressPaint.setStrokeWidth(originStrokeWidth);
    }

    /**
     * 绘制出错状态的点
     *
     * @param point      单位点
     * @param canvas     画布
     * @param errorPaint 错误状态画笔
     */
    @Override
    public void drawErrorPoint(Point point, Canvas canvas, Paint errorPaint) {
        // 1.记录画笔的原始属性（绘制过程中需要修改的属性）,绘制结束时进行还原
        Paint.Style style = errorPaint.getStyle();
        float originStrokeWidth = errorPaint.getStrokeWidth();
        int color = errorPaint.getColor();
        //绘制空心圆内部
        errorPaint.setStyle(Paint.Style.FILL);
        errorPaint.setColor(Color.WHITE);//设置默认的圆背景为白色
        canvas.drawCircle(point.x, point.y, point.radius-originStrokeWidth, errorPaint);

        // 2.绘制实心点
        errorPaint.setStyle(Paint.Style.FILL);
        errorPaint.setColor(color);//设置默认的圆环颜色 //Color.parseColor("#FF0000")
        canvas.drawCircle(point.x, point.y, point.radius / 3, errorPaint);
        // 3.绘制空心圆
        errorPaint.setStyle(Paint.Style.STROKE);
        errorPaint.setStrokeWidth(point.radius / 16);
        canvas.drawCircle(point.x, point.y, point.radius, errorPaint);

        // 4.结束绘制，还原画笔属性
        errorPaint.setStyle(style);
        errorPaint.setStrokeWidth(originStrokeWidth);
    }
}
