package com.curry.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
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
public class CornerLabel extends View {
    private float mLabelWidth, mHorizontalOffset, mVerticalOffset;
    private boolean mLeanRight;
    private int mForeColor;
    private String mText;
    private int height, width;
    private Paint mForegroundPaint, mBackgroundPaint, mTextPaint;

    public CornerLabel(@NonNull Context context) {
        super(context);
        init();
    }

    public CornerLabel(@NonNull @NotNull Context context, float labelWidth, float horizontalOffset, boolean leanRight) {
        this(context);
        mLabelWidth = labelWidth;
        mHorizontalOffset = horizontalOffset;
        mLeanRight = leanRight;
    }

    public CornerLabel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
        init();
    }

    public CornerLabel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs);
        init();
    }

    public CornerLabel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }

    /**
     * 设置标签
     * @param color
     * @param text
     */
    public void set(int color, String text){
        this.mForeColor = color;
        mForegroundPaint.setColor(color);
        this.mText = text;
        invalidate();
    }

    /**
     * 设置颜色
     * @param color
     */
    public void setForeColor(int color){
        this.mForeColor = color;
        mForegroundPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置颜色
     * @param text
     */
    public void setText(String text){
        this.mText = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制表面
        canvas.drawPath(getForegroundPath(), mForegroundPaint);
        // 绘制背景
        canvas.drawPath(getBackgroundPath(), mBackgroundPaint);
        // 绘制文字
        Rect r = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), r);
        canvas.drawTextOnPath(mText, getTextPath(), 0, (r.bottom - r.top) / 2, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 获取标签正面的path
     * @return
     */
    private Path getForegroundPath() {
        if (height < mHorizontalOffset ||
                width < mVerticalOffset ||
                height < mVerticalOffset ||
                width < mHorizontalOffset) return null;

        float bottomX, topRightX;
        if (mLeanRight) {
            bottomX = 0;
            topRightX = width - mVerticalOffset;
        } else {
            bottomX = width;
            topRightX = mVerticalOffset + mLabelWidth / (float) Math.sin(Math.atan2(width - mVerticalOffset, height - mHorizontalOffset));
        }

        Path path = new Path();
        path.moveTo(bottomX, height - mHorizontalOffset);
        if (mLabelWidth < (height - mHorizontalOffset) * Math.cos(Math.atan2(width - mVerticalOffset, height - mHorizontalOffset))) {
            path.lineTo(bottomX, height - mHorizontalOffset - mLabelWidth / (float) Math.cos(Math.atan2(width - mVerticalOffset, height - mHorizontalOffset)));
            if (mLeanRight) {
                path.lineTo(topRightX - mLabelWidth / (float) Math.sin(Math.atan2(width - mVerticalOffset, height - mHorizontalOffset)), 0);
                path.lineTo(topRightX, 0);
            } else {
                path.lineTo(topRightX, 0);
                path.lineTo(topRightX - mLabelWidth / (float) Math.sin(Math.atan2(width - mVerticalOffset, height - mHorizontalOffset)), 0);
            }
        } else {
            path.lineTo(bottomX, 0);
        }
        path.lineTo(bottomX, height - mHorizontalOffset);

        return path;
    }

    /**
     * 获取标签背面的path
     * @return
     */
    private Path getBackgroundPath() {
        if (height < mHorizontalOffset ||
                width < mVerticalOffset ||
                height < mVerticalOffset ||
                width < mHorizontalOffset) return null;
        float bottomX, topX;
        if (mLeanRight) {
            bottomX = 0;
            topX = width - mVerticalOffset;
        } else {
            bottomX = width;
            topX = mVerticalOffset;
        }

        Path path = new Path();
        path.moveTo(bottomX, height - mHorizontalOffset);
        path.lineTo(mLeanRight ? bottomX + mHorizontalOffset : bottomX - mHorizontalOffset, height);
        path.lineTo(mLeanRight ? bottomX + mHorizontalOffset : bottomX - mHorizontalOffset,
                height - mHorizontalOffset - mHorizontalOffset * (height - mHorizontalOffset) / (width - mVerticalOffset));
        path.lineTo(bottomX, height - mHorizontalOffset);

        path.moveTo(topX, 0);
        path.lineTo(mLeanRight ? width : 0, mVerticalOffset);
        path.lineTo(mLeanRight ? topX - mVerticalOffset * (width - mVerticalOffset) / (height - mHorizontalOffset) :
                        topX + mVerticalOffset * (width - mVerticalOffset) / (height - mHorizontalOffset),
                mVerticalOffset);
        path.lineTo(topX, 0);

        return path;
    }

    /**
     * 获取文字的path
     * @return
     */
    private Path getTextPath() {
        Path path = new Path();
        PointF center = new PointF();
        float textWidth = mTextPaint.measureText(mText);
        if (mLeanRight) {
            center.x = (width - mVerticalOffset - (mLabelWidth / (float) Math.sin(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset)) / 2)) / 2;
            center.y = (height - mHorizontalOffset - (mLabelWidth / (float) Math.cos(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset)) / 2)) / 2;
        } else {
            center.x = width - ((width - mVerticalOffset - (mLabelWidth / (float) Math.sin(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset)) / 2)) / 2);
            center.y = (height - mHorizontalOffset - (mLabelWidth / (float) Math.cos(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset)) / 2)) / 2;
        }
        float xOffset = (textWidth / 2) * (float) Math.sin(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset));
        float yOffset = (textWidth / 2) * (float) Math.cos(Math.atan2(height - mHorizontalOffset, width - mVerticalOffset));
        path.moveTo(
                center.x - xOffset,
                mLeanRight ? center.y + yOffset : center.y - yOffset);
        path.lineTo(
                center.x + xOffset,
                mLeanRight ? center.y - yOffset : center.y + yOffset);

        return path;
    }

    /**
     * 获取各种自定义属性
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs) {
        // 取出自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CornerLabel);
        mLabelWidth = ta.getDimension(R.styleable.CornerLabel_label_width, 10f);
        mHorizontalOffset = ta.getDimension(R.styleable.CornerLabel_horizontalOffset, 3f);
        mVerticalOffset = ta.getDimension(R.styleable.CornerLabel_verticalOffset, 3f);
        mLeanRight = ta.getBoolean(R.styleable.CornerLabel_lean_right, true);
        mForeColor = ta.getColor(R.styleable.CornerLabel_fore_color, Color.RED);
        mText = ta.getString(R.styleable.CornerLabel_text);
        if (mText == null) mText = "";
    }

    /**
     * 初始化各种画笔
     */
    private void init() {
        mForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForegroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mForegroundPaint.setColor(mForeColor);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBackgroundPaint.setColor(Color.GRAY);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mLabelWidth * 0.5f);
    }
}