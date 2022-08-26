package com.curry.function.image.stitch;

import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-25 16:36
 * @description: 电影字幕拼接
 **/
public class LinesStitch extends BaseBackActivity {
    @Override
    protected int LayoutId() {
        return R.layout.image_stitch_lines;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.image_stitch_title);
    }
}
