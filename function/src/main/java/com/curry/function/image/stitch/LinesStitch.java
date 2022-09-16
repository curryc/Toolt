package com.curry.function.image.stitch;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.image.ImageUtils;
import com.curry.util.time.Formatter;

import java.io.IOException;
import java.util.Date;

import static com.curry.function.image.stitch.NormalStitch.MODE;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-25 16:36
 * @description: 电影字幕拼接
 **/
public class LinesStitch extends BaseBackActivity
        implements SeekBar.OnSeekBarChangeListener {
    private FrameLayout mContainer;
    private SeekBar mSize, mLineHeight;
    private Button mSave;
    private StitchImageView image;

    private String[] uris;

    @Override
    protected int LayoutId() {
        return R.layout.image_stitch_lines;
    }

    @Override
    protected void initView() {
        mContainer = findViewById(R.id.container);
        mSize = findViewById(R.id.size);
        mLineHeight = findViewById(R.id.height);
        mSave = findViewById(R.id.save);

        image = new StitchImageView(this,
                getIntent().getBooleanExtra(MODE, true),
                -mLineHeight.getProgress(),
                Color.WHITE,
                mSize.getProgress());
        image.addImage(uris);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        image.setLayoutParams(params);
        mContainer.addView(image);

        mSize.setOnSeekBarChangeListener(this);
        mLineHeight.setOnSeekBarChangeListener(this);

        mSave.setBackgroundColor(App.getThemeColor());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.saveBitmap(LinesStitch.this, image.getBitmap(), Formatter.getFileFormatString(new Date()), "made by toolt");
                toastShort("saved in Pictures/toolt");
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.equals(mSize)) {
            image.setSize(seekBar.getProgress());
        } else if (seekBar.equals(mLineHeight)) {
            image.setMargin(-seekBar.getProgress());
        }
    }
}
