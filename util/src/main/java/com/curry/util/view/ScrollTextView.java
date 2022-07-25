package com.curry.util.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-25 23:24
 * @description: 一个可以调节滚动速度的TextView
 **/
@SuppressLint("AppCompatCustomView")
public class ScrollTextView extends TextView implements Runnable {
    private int currentScrollX;// 当前滚动的位置
    private boolean stop = false;
    private int textWidth;
    private boolean isMeasure = false;
    private int speed;

    public ScrollTextView(Context context, String text,int textSize, int textColor, int scrollSpeed){
        super(context);
        setText(text);
        setTextSize(textSize);
        setTextColor(textColor);
        speed = scrollSpeed;
    }

    public ScrollTextView(Context context) {
        super(context);
        speed = 5;
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        speed= 5;
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        speed = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
// todo auto-generated method stub
        super.onDraw(canvas);
        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }

    /**
     * 获取文字宽度
     */
    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
    }

    @Override
    public void run() {
        currentScrollX += 2;// 滚动速度
        this.scrollTo(currentScrollX, 0);

        if (stop) {
            return;
        }
        if (getScrollX() >= textWidth) {
            this.scrollTo(-getWidth(), 0);
            currentScrollX = -getWidth();
        }
        postDelayed(this, 30 - speed);
    }

    // 开始滚动
    public void startScroll() {
        stop = false;
        this.removeCallbacks(this);
        post(this);
    }

    // 停止滚动
    public void stopScroll() {
        stop = true;
    }

    // 从头开始滚动
    public void startScrollFrom0() {
        currentScrollX = -getWidth();
        startScroll();
    }
}
