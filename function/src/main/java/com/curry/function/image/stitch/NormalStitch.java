package com.curry.function.image.stitch;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.image.ImageUtils;
import com.curry.util.picker.ColorPicker;
import com.curry.util.time.Formatter;
import com.curry.util.view.ColorPickerView;

import java.io.IOException;
import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-25 16:34
 * @description: 横竖的照片拼接
 **/
public class NormalStitch extends BaseBackActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String MODE = "mode";

    private SeekBar mSize, mMargin;
    private Button mBackground, mSave;
    private FrameLayout mContainer;

    private StitchImageView image;

    private String[] uris;

    @Override
    protected int LayoutId() {
        return R.layout.image_stitch_normal;
    }

    @Override
    protected void initView() {
        mContainer = findViewById(R.id.container);
        mSize = findViewById(R.id.size);
        mMargin = findViewById(R.id.margin);
        mBackground = findViewById(R.id.back_color);
        mSave = findViewById(R.id.save);

        image = new StitchImageView(this,
                getIntent().getBooleanExtra(MODE, true),
                mMargin.getProgress() * 3,
                ((ColorDrawable) mBackground.getBackground()).getColor(),
                mSize.getProgress());
        image.addImage(uris);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        image.setLayoutParams(params);
        mContainer.addView(image);

        mBackground.setOnClickListener(this);
        mSize.setOnSeekBarChangeListener(this);
        mMargin.setOnSeekBarChangeListener(this);

        mSave.setBackgroundColor(App.getThemeColor());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ImageUtils.saveBitmap(NormalStitch.this, image.getBitmap(), Formatter.getFileFormatString(new Date()) + ".jpg", "made by toolt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        uris = getIntent().getStringArrayExtra(ChoseModeDialog.DATA);
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.image_stitch_title);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mBackground)) {
            ColorDrawable d = (ColorDrawable) v.getBackground();
            ColorPicker colorPicker = new ColorPicker(this, d.getColor(), getString(R.string.handle_barrage_text_color), new ColorPickerView.OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    v.setBackgroundColor(color);
                    image.setBackColor(color);
                }
            });
            colorPicker.setTitle("hello");
            colorPicker.show();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.equals(mSize)){
            image.setSize(seekBar.getProgress());
        }else if(seekBar.equals(mMargin)){
            image.setMargin(seekBar.getProgress() * 3);
        }
    }
}
