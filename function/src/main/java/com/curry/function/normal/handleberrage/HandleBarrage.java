package com.curry.function.normal.handleberrage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.view.ColorPickerView;
import com.curry.util.picker.ColorPicker;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-23 22:20
 * @description: 手持弹幕
 **/
public class HandleBarrage extends BaseBackActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String TEXT = "text";
    public static final String TEXT_SIZE = "textSize";
    public static final String TEXT_SPEED = "textSpeed";
    public static final String TEXT_COLOR = "textColor";
    public static final String BACK_COLOR = "backColor";
    public static final String MODE_SCROLLING = "scroll";
    private final String TAG = "barrage";

    private EditText mText;
    private SeekBar mSize, mSpeed;
    private TextView mSizeHint, mSpeedHint;
    private Button mColor, mBackColor, mShow;
    private RadioGroup mMode;

    @Override
    protected int LayoutId() {
        return R.layout.handle_barrage;
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.handle_barrage);
    }

    @Override
    protected void initView() {
        mText = findViewById(R.id.text);
        mSize = findViewById(R.id.size);
        mSizeHint = findViewById(R.id.size_hint);
        mSpeed = findViewById(R.id.speed);
        mSpeedHint = findViewById(R.id.speed_hint);
        mColor = findViewById(R.id.text_color);
        mBackColor = findViewById(R.id.back_color);
        mShow = findViewById(R.id.show);
        mMode = findViewById(R.id.mode);

        setBackgroundColor(App.getThemeColor(), R.id.show);
        setClickListener(this, R.id.text_color, R.id.back_color, R.id.show);

        mSizeHint.setText(getString(R.string.handle_barrage_hint_size) + ": " + mSize.getProgress());
        mSpeedHint.setText(getString(R.string.handle_barrage_hint_speed) + ": " + mSpeed.getProgress());
        mMode.check(R.id.scroll_mode);

        mSize.setOnSeekBarChangeListener(this);
        mSpeed.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mColor.getId()) {
            ColorDrawable d = (ColorDrawable) v.getBackground();
            ColorPicker colorPicker = new ColorPicker(this, d.getColor(), getString(R.string.handle_barrage_text_color), new ColorPickerView.OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    v.setBackgroundColor(color);
                }
            });
            colorPicker.setTitle("hello");
            colorPicker.show();
        } else if (v.getId() == mBackColor.getId()) {
            ColorDrawable d = (ColorDrawable) v.getBackground();
            ColorPicker colorPicker = new ColorPicker(this, d.getColor(), getString(R.string.handle_barrage_back_color), new ColorPickerView.OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    v.setBackgroundColor(color);
                }
            });
            colorPicker.show();
        } else if (v.getId() == mShow.getId()) {
            Intent i = new Intent(this, ShowActivity.class);
            Bundle data = new Bundle();
            data.putString(TEXT, mText.getText().toString());
            data.putInt(TEXT_SIZE, mSize.getProgress());
            data.putInt(TEXT_SPEED, mSpeed.getProgress());
            data.putInt(TEXT_COLOR, ((ColorDrawable) mColor.getBackground()).getColor());
            data.putInt(BACK_COLOR, ((ColorDrawable) mBackColor.getBackground()).getColor());
            if (mMode.getCheckedRadioButtonId() == R.id.scroll_mode) {
                data.putBoolean(MODE_SCROLLING, true);
            } else {
                data.putBoolean(MODE_SCROLLING, false);
            }
            i.putExtras(data);
            startActivity(i);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == mSize.getId()) {
            mSizeHint.setText(getString(R.string.handle_barrage_hint_size) + ": " + progress);
        } else {
            mSpeedHint.setText(getString(R.string.handle_barrage_hint_speed) + ": " + progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}