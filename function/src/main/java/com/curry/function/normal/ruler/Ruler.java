package com.curry.function.normal.ruler;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseLandActivity;
import com.curry.util.cache.SharedPreferencesHelper;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-30 16:58
 * @description: 尺子活动
 **/
public class Ruler extends BaseLandActivity {
    public static final String CORRECTION = "correction";
    public static final String PER_MILLIMETER = "perMeter"; // 一毫米的像素坐标长度
    public final int RES_CORRECTION = 1;
    private final String TAG = "ruler";

    private Button mClear, mCorrection;
    private RulerView mRuler;

    @Override
    protected int LayoutId() {
        return R.layout.ruler;
    }

    @Override
    protected void initView() {
        mClear = findViewById(R.id.clear);
        mCorrection = findViewById(R.id.correction);
        mRuler = findViewById(R.id.ruler);

        mClear.setBackgroundColor(App.getThemeColor());
        mCorrection.setBackgroundColor(App.getThemeColor());

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRuler.clearMarks();
            }
        });
        mCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Ruler.this, Correction.class);
                startActivityForResult(i, RES_CORRECTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_CORRECTION && resultCode == RESULT_OK) {
            mRuler.setMillimeter((int) SharedPreferencesHelper.get(this,
                    Ruler.PER_MILLIMETER,
                    mRuler.getMillimeter()));
            mRuler.invalidate();
        }
    }
}
