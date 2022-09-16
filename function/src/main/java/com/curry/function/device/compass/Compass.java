package com.curry.function.device.compass;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.curry.function.R;
import com.curry.function.base.BaseFullScreenActivity;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 16:37
 * @description: 指南针
 **/
public class Compass extends BaseFullScreenActivity {
    private CompassView compass;
    @Override
    protected int LayoutId() {
        return R.layout.compass;
    }

    @Override
    protected void initView() {
        compass = new CompassView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        params.leftMargin = params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.horizontal_margin_mid);
        ((FrameLayout)findViewById(R.id.container)).addView(compass, params);
    }
}
