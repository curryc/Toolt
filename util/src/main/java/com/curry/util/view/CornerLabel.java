package com.curry.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.curry.util.R;
import org.jetbrains.annotations.NotNull;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-26 22:07
 * @description: 可以用来放在View角上的折角标签
 **/
public class CornerLabel extends FrameLayout {
    private float mLabelWidth, mThickNess;
    private boolean mLeanRight;
    private int mBackColor;
    private int height, width;
    private Paint mForegroundPaint, mBackgroundPaint, mTextPaint;

    public CornerLabel(@NonNull Context context) {
        super(context);
    }

    public CornerLabel(@NonNull @NotNull Context context, float labelWidth, float thickNess, boolean leanRight) {
        this(context);
        mLabelWidth = labelWidth;
        mThickNess = thickNess;
        mLeanRight = leanRight;
    }

    public CornerLabel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 取出自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CornerLabel);
        mLabelWidth = ta.getDimension(R.styleable.CornerLabel_label_width, 10f);
        mThickNess = ta.getDimension(R.styleable.CornerLabel_thickness, 3f);
        mLeanRight = ta.getBoolean(R.styleable.CornerLabel_lean_right, true);
        mBackColor = ta.getColor(R.styleable.CornerLabel_back_color, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }
}