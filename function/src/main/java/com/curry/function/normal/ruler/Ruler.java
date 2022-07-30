package com.curry.function.normal.ruler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.FrameLayout;
import com.curry.function.R;
import com.curry.function.base.BaseLandActivity;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-30 16:58
 * @description: 尺子活动
 **/
public class Ruler extends BaseLandActivity {
    private final String TAG = "ruler";
    private final String PER_MILLIMETER = "perMeter"; // 一毫米的像素坐标长度

    @Override
    protected int LayoutId() {
        return R.layout.ruler;
    }

    @Override
    protected void initView() {
        FrameLayout container = findViewById(R.id.ruler_container);
//        container.draw(getRuler());
    }

    private Canvas getRuler(){
        Canvas canvas = new Canvas();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        canvas.drawLine(0, 0, 100, 100, paint);


        return canvas;
    }
}
