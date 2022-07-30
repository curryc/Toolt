package com.curry.function.normal.handleberrage;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.curry.function.R;
import com.curry.function.base.BaseActivity;
import com.curry.function.base.BaseLandActivity;
import com.curry.util.log.Logger;
import com.curry.util.view.ScrollTextView;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Timer;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-24 01:38
 * @description: 专门用来展示弹幕的全屏活动
 **/
public class ShowActivity extends BaseLandActivity
        implements View.OnTouchListener {
    private ScrollTextView mTextView;
    private Bundle data;

    @Override
    protected int LayoutId() {
        return R.layout.handle_barrage_show;
    }

    @Override
    protected void initView() {
        data = getIntent().getExtras();

        FrameLayout container = findViewById(R.id.barrage_container);
        container.setBackgroundColor(data.getInt(HandleBarrage.BACK_COLOR));

        mTextView = new ScrollTextView(this,
                data.getString(HandleBarrage.TEXT),
                data.getInt(HandleBarrage.TEXT_SIZE) * 6,
                data.getInt(HandleBarrage.TEXT_COLOR),
                data.getInt(HandleBarrage.TEXT_SPEED));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mTextView.setLayoutParams(params);
        container.addView(mTextView);
        container.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data.getBoolean(HandleBarrage.MODE_SCROLLING)) {
            mTextView.setSingleLine();
            mTextView.startScrollFrom0();
        } else {
            mTextView.setSingleLine(false);
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
