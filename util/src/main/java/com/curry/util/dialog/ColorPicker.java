package com.curry.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import com.curry.util.view.ColorPickerView;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-23 21:12
 * @description: 颜色选择器
 **/

public class ColorPicker extends Dialog {
    private final String TAG = "ColorPicker";

    private Context mContext;
    private String mTitle;//标题
    private int mInitialColor;//初始颜色
    private ColorPickerView.OnColorChangedListener mListener;

    private ColorPickerView colorPickerView;

    /**
     * 初始颜色黑色
     *
     * @param context
     * @param title    对话框标题
     * @param listener 回调
     */
    public ColorPicker(Context context, String title, ColorPickerView.OnColorChangedListener listener) {
        this(context, Color.BLACK, title, listener);
    }

    /**
     * @param context
     * @param initialColor 初始颜色
     * @param title        标题
     * @param listener     回调
     */
    public ColorPicker(Context context, int initialColor, String title, ColorPickerView.OnColorChangedListener listener) {
        super(context);
        this.mContext = context;
        this.mInitialColor = initialColor;
        this.mListener = listener;
        this.mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager manager = getWindow().getWindowManager();
        int width = 0;
        int height = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            height = (int) (manager.getCurrentWindowMetrics().getBounds().height() * 0.5f);
            width = (int) (manager.getCurrentWindowMetrics().getBounds().width() * 0.7f);
        } else {
            height = (int) (manager.getDefaultDisplay().getHeight() * 0.5f);
            width = (int) (manager.getDefaultDisplay().getWidth() * 0.7f);
        }
        colorPickerView = new ColorPickerView(mContext, height, width, 0);
        if(mListener!= null) colorPickerView.setOnColorChangedListener(mListener);
        setContentView(colorPickerView);
        setTitle(mTitle);
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getInitialColor() {
        return mInitialColor;
    }

    public void setInitialColor(int mInitialColor) {
        this.mInitialColor = mInitialColor;
    }

    public void setOnColorChangedListener(ColorPickerView.OnColorChangedListener listener) {
        this.colorPickerView.setOnColorChangedListener(listener);
    }
}