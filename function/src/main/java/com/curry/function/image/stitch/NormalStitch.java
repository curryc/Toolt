package com.curry.function.image.stitch;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-25 16:34
 * @description: 横竖的照片拼接
 **/
public class NormalStitch extends BaseBackActivity {
    public static final String MODE = "mode";
    private final String[] FORMAT = {"jpg", "png"};

    private SeekBar mSize, mMargin;
    private Button mBackground;
    private Spinner mFormat;
    private FrameLayout mContainer;

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
        mFormat = findViewById(R.id.format);


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
}
