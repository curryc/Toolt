package com.curry.function.normal.handleberrage;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.curry.function.R;
import com.curry.function.base.BaseActivity;
import com.curry.util.log.Logger;

import java.lang.reflect.Field;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-24 01:38
 * @description: 专门用来展示弹幕的全屏活动
 **/
public class ShowActivity extends BaseActivity {
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
        Bundle data = getIntent().getExtras();

        FrameLayout container = findViewById(R.id.barrage_container);
        container.setBackgroundColor(data.getInt(HandleBarrage.BACK_COLOR));

        TextView mTextView = new TextView(this);
        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mTextView.setMarqueeRepeatLimit(-1);
        mTextView.setSingleLine();
        mTextView.setFocusableInTouchMode(true);
        mTextView.setHorizontallyScrolling(true);
        mTextView.requestFocus();
        mTextView.setSelected(true);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.requestFocus();
            }
        });

        mTextView.setText(data.getString(HandleBarrage.TEXT));
        mTextView.setTextSize(data.getInt(HandleBarrage.TEXT_SIZE) * 6);
        mTextView.setTextColor(data.getInt(HandleBarrage.TEXT_COLOR));
        setMarqueeSpeed(mTextView, data.getInt(HandleBarrage.TEXT_SPEED));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mTextView.setLayoutParams(params);
        container.addView(mTextView);
    }

    private void initWindow(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        }else{
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

    private void setMarqueeSpeed(TextView tv, float speed) {
        if (tv != null) {
            try {
                Field f = null;
                if (tv instanceof AppCompatTextView) {
                    f = tv.getClass().getSuperclass().getDeclaredField("mMarquee");
                } else {
                    f = tv.getClass().getDeclaredField("mMarquee");
                }
                if (f != null) {
                    f.setAccessible(true);
                    Object marquee = f.get(tv);
                    if (marquee != null) {
                        String scrollSpeedFieldName = "mScrollUnit";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            scrollSpeedFieldName = "mPixelsPerSecond";
                        }
                        Field mf = marquee.getClass().getDeclaredField(scrollSpeedFieldName);
                        mf.setAccessible(true);
                        mf.setFloat(marquee, speed);
                    }
                } else {
                    Logger.e("Marquee", "mMarquee object is null.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
