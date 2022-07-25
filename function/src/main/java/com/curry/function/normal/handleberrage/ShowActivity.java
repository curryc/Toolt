package com.curry.function.normal.handleberrage;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.curry.function.R;
import com.curry.function.base.BaseActivity;
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
public class ShowActivity extends BaseActivity
        implements View.OnTouchListener {
    private ScrollTextView mTextView;
    private Bundle data;

    @Override
    protected int getLayoutId() {
        return R.layout.handle_barrage_show;
    }

    @Override
    protected String getWindowTitle() {
        return null;
    }

    @Override
    protected void initView() {
        initWindow();
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

    private void initWindow() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_MOVE){
//            event.get
//        }
        return true;
    }
}
