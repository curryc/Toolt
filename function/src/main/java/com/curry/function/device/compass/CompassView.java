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
    private float direction;
    private float size, tickHeight, labelCircleRadius;
    private Paint tickPaint, circlePaint, textPaint, labelPaint, mainTextPaint, diffPaint, colorTrianglePaint;
    private RectF innerCircle, outerCircle, tickCircle;
    private PointF center;

    public CompassView(Context context) {
        this(context, 500, Color.RED, 60);
    }

    public CompassView(Context context, float size, int color, int direction) {
        super(context);
        this.direction = direction;
        this.size = size;
        initPaint(color);
    }

    private void initPaint(int color) {
        // 对画圆盘画初始化
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.GRAY);
        circlePaint.setStrokeWidth(6);

        // 对画圆盘画初始化
        diffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        diffPaint.setStyle(Paint.Style.STROKE);
        diffPaint.setColor(color);
        diffPaint.setStrokeWidth(7);

        // 对画圆盘画初始化
        colorTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorTrianglePaint.setStyle(Paint.Style.FILL);
        colorTrianglePaint.setColor(color);
        colorTrianglePaint.setStrokeWidth(1);

        // 对刻度画笔进行初始化
        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(Color.BLACK);
        tickPaint.setStrokeWidth(2);

        // 对字的画笔进行初始化
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(35);

        // 对标签字的画笔进行初始化
        labelPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.GRAY);
        labelPaint.setTextSize(26);

        // 对中间大的字体的画笔进行初始化
        mainTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mainTextPaint.setColor(Color.BLACK);
        mainTextPaint.setTextSize(100);
    }

    private void initCircle() {
        // 对内圈进行初始化
        innerCircle = new RectF(size * 0.2f, size * 0.2f, size * 0.8f, size * 0.8f);
        // 对外圈进行初始化
        outerCircle = new RectF(size * 0.1f, size * 0.1f, size * 0.9f, size * 0.9f);
        // 刻度圈
        tickCircle = new RectF(size * 0.22f, size * 0.22f, size * 0.78f, size * 0.78f);
        // 标识圈
        labelCircleRadius = 0.2f * size;
        // 圆心
        center = new PointF(size * 0.5f, size * 0.5f);
        // tick长度
        tickHeight = size * 0.03f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = Math.min(w, h);
        initCircle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println(direction);
        // 外圈固定
        canvas.drawArc(outerCircle, -75, 330, false, circlePaint);
        canvas.drawPath(getTrianglePath((outerCircle.left + outerCircle.right) / 2, outerCircle.top, 0), tickPaint);
        // 内圈随角度改变而改变
        canvas.drawArc(innerCircle, -75 + direction, 330, false, circlePaint);
        if (direction > 15 && direction <= 180) {
            canvas.drawArc(innerCircle, -90, direction - 15, false, diffPaint);
        } else if (direction > 180 && direction < 345) {
            canvas.drawArc(innerCircle, direction + 15, 345 - direction, false, diffPaint);
        }
        float innerRadius = (innerCircle.right - innerCircle.left) / 2;
        double angle = Math.toRadians(direction);
        canvas.drawPath(getTrianglePath(center.x + (innerRadius * (float) Math.sin(angle)),
                        center.y - (innerRadius * (float) Math.cos(angle)),
                        angle),
                ((int) direction) == 0 ? tickPaint : colorTrianglePaint);
        // 画中间显示的大的文字度数
        String strDirection = (int) direction + "°";
        Rect r = new Rect();
        mainTextPaint.getTextBounds(strDirection, 0, strDirection.length(), r);
        canvas.drawText(strDirection,
                center.x - mainTextPaint.measureText(strDirection) / 2,
                center.y + (r.bottom - r.top) / 2,
                mainTextPaint);
        // 利用循环画刻度和标识
        for (float t = direction; t < 360 + direction; t++) {
            double rad = Math.toRadians(t);
            canvas.drawLine(
                    center.x + (center.x - tickCircle.left) * (float) Math.sin(rad),
                    center.y - (center.x - tickCircle.left) * (float) Math.cos(rad),
                    center.x + (center.x - tickCircle.left - tickHeight) * (float) Math.sin(rad),
                    center.y - (center.x - tickCircle.left - tickHeight) * (float) Math.cos(rad),
                    tickPaint
            );
        }
        for (int t = 0; t <= 330; t += 30) {
            switch (t) {
                case 0:
                    canvas.drawTextOnPath("N", getTextPath(textPaint.measureText("N"), t + direction), 0, 0, textPaint);
                    break;
                case 90:
                    canvas.drawTextOnPath("E", getTextPath(textPaint.measureText("E"), t + direction), 0, 0, textPaint);
                    break;
                case 180:
                    canvas.drawTextOnPath("S", getTextPath(textPaint.measureText("S"), t + direction), 0, 0, textPaint);
                    break;
                case 270:
                    canvas.drawTextOnPath("W", getTextPath(textPaint.measureText("W"), t + direction), 0, 0, textPaint);
                    break;
                default:
                    canvas.drawTextOnPath(String.valueOf(t), getTextPath(labelPaint.measureText(String.valueOf(t)), t + direction), 0, 0, labelPaint);
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

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
     *
     * @param x     横坐标（距离左边）
     * @param y     纵坐标（距离上边）
     * @param angle 三角形朝向
     * @return
     */
    private Path getTrianglePath(float x, float y, double angle) {
        Path path = new Path();
        float height = size * 0.03f;
        float halfLength = size * 0.025f;

        path.moveTo(x + (float) Math.sin(angle) * height, y - (float) Math.cos(angle) * height);
        path.lineTo(x - (float) Math.cos(angle) * halfLength, y - (float) Math.sin(angle) * halfLength);
        path.lineTo(x + (float) Math.cos(angle) * halfLength, y + (float) Math.sin(angle) * halfLength);
        path.lineTo(x + (float) Math.sin(angle) * height, y - (float) Math.cos(angle) * height);

        return path;
    }

    /**
     * 获取一个文字的path,用来写标签
     *
     * @param length
     * @param angle
     * @return
     */
    private Path getTextPath(float length, float angle) {
        Path path = new Path();
        double rad = Math.toRadians(angle);
        path.moveTo(center.x + labelCircleRadius * (float) Math.sin(rad) - length * (float) Math.cos(rad) / 2,
                center.y - labelCircleRadius * (float) Math.cos(rad) - length * (float) Math.sin(rad) / 2);
        path.lineTo(center.x + labelCircleRadius * (float) Math.sin(rad) + length * (float) Math.cos(rad) / 2,
                center.y - labelCircleRadius * (float) Math.cos(rad) + length * (float) Math.sin(rad) / 2);
        return path;
    }

    /**
     * 设置指南针当前方向
     *
     * @param angle
     */
    public void setDirection(float angle) {
        angle = angle <= 0 ? angle < 0 ? -angle : 0 : 360 - angle;
        if (Math.abs(angle - direction) <= 0.7) return;
        this.direction = angle;
        postInvalidate();
    }

    /**
     * 获取当前方向
     *
     * @return
     */
    public float getDirection() {
        return this.direction;
    }
}
