package com.curry.function.normal.ruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.curry.util.cache.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-30 20:05
 * @description: 尺子View, 用来绘制一个尺子
 **/
public class RulerView extends View {
    public static final int DEFAULT_MILLIMETER = 20;

    private final int LONG = 100;
    private final int MID = 50;
    private final int SHORT = 35;

    private int length; // 尺子长度
    private int width; // 尺子宽度
    private int millimeter;

    // 两种画笔
    private Paint main;
    private Paint mark;

    private List<Float> lines;

    public RulerView(Context context) {
        super(context);
        init();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < length / millimeter; i++) {
            if (i % 10 == 0) {
                // 这里是厘米
                canvas.drawLine(i * millimeter, 0, i * millimeter, LONG, main);
            } else if (i % 5 == 0) {
                // 这里是半厘米
                canvas.drawLine(i * millimeter, 0, i * millimeter, MID, main);
            } else {
                // 这里是普通毫米
                canvas.drawLine(i * millimeter, 0, i * millimeter, SHORT, main);
            }
        }

        for (Float line : lines) {
            canvas.drawLine(line, 0, line, width, mark);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 注意这里的长和宽应该反过来，因为尺子是横着的
        length = MeasureSpec.getSize(widthMeasureSpec);
        width = MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 初始化
     */
    private void init() {
        millimeter = (int) SharedPreferencesHelper.get(getContext(),
                Ruler.PER_MILLIMETER,
                DEFAULT_MILLIMETER);
        lines = new ArrayList<>();

        main = new Paint();
        main.setAntiAlias(true);
        main.setColor(Color.BLACK);
        main.setStyle(Paint.Style.STROKE);
        main.setStrokeWidth(5);

        mark = new Paint();
        mark.setAntiAlias(true);
        mark.setColor(Color.RED);
        mark.setStyle(Paint.Style.STROKE);
        main.setStrokeWidth(3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                lines.add(event.getX());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                lines.remove(lines.size() - 1);
                lines.add(event.getX());
                invalidate();
                break;
        }
        return true;
    }

    // 清除所有记号
    public void clearMarks(){
        lines.clear();
        invalidate();
    }

    public void setMillimeter(int millimeter) {
        this.millimeter = millimeter;
    }

    public int getMillimeter() {
        return millimeter;
    }
}
