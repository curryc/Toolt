package com.curry.function.device.compass;

import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 17:05
 * @description: 指南针是一个圆, 需要手动画
 **/
public class CompassView extends View {
    private int direction;
    private float size, tickHeight, textSize;
    private Paint tickPaint, circlePaint, textPaint;
    private RectF innerCircle, outerCircle;

    public CompassView(Context context) {
        this(context, 500, Color.RED, 0);
    }

    public CompassView(Context context, float size, int color, int direction) {
        super(context);
        this.direction = direction;
        this.size = size;
        initPaint(color);
    }

    private void initPaint(int color){
        // 对画圆盘画初始化
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStrokeWidth(2);

        // 对刻度画笔进行初始化
        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(Color.BLACK);
        tickPaint.setStrokeWidth(3);

        // 对字的画笔进行初始化
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(20);
    }

    private void initCircle() {
        // 对内圈进行初始化
        innerCircle = new RectF(size * 0.2f, size * 0.2f, size * 0.8f, size * 0.8f);

        // 对外圈进行初始化
        outerCircle = new RectF(size * 0.1f, size * 0.1f, size * 0.9f, size * 0.9f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = Math.min(w, h);
        initCircle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("draw size:" + size);
        canvas.drawArc(outerCircle, -60, 300, false, circlePaint);
        canvas.drawPath(getTrianglePath((outerCircle.left + outerCircle.right) / 2, outerCircle.top), tickPaint);
        canvas.drawArc(innerCircle, -75, 330, false, circlePaint);
        canvas.drawPath(getTrianglePath((innerCircle.left + innerCircle.right) / 2, innerCircle.top), tickPaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        System.out.println((widthMode == ViewGroup.LayoutParams.WRAP_CONTENT) + "/" + (heightMode == ViewGroup.LayoutParams.WRAP_CONTENT));
//        System.out.println(MeasureSpec.getSize(widthMeasureSpec) + "//" + MeasureSpec.getSize(heightMeasureSpec));

        if (widthMode == ViewGroup.LayoutParams.WRAP_CONTENT
                && heightMode == ViewGroup.LayoutParams.WRAP_CONTENT) {
        } else if (widthMode == ViewGroup.LayoutParams.MATCH_PARENT
                && heightMode == ViewGroup.LayoutParams.WRAP_CONTENT) {
            size = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMode == ViewGroup.LayoutParams.WRAP_CONTENT
                && heightMode == ViewGroup.LayoutParams.MATCH_PARENT) {
            size = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension((int) size, (int) size);
        initCircle();
    }

    /**
     * 获取三角形的path
     * @param x 横坐标（距离左边）
     * @param y 纵坐标（距离上边）
     * @return
     */
    private Path getTrianglePath(float x, float y){
        Path path = new Path();
        path.moveTo(x, y - size * 0.03f);
        path.lineTo(x-size * 0.025f, y);
        path.lineTo(x + size * 0.025f, y);
        path.lineTo(x, y - size* 0.03f);
        return path;
    }
}
