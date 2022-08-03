package com.curry.function.normal.ruler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    public static final String PER_MILLIMETER = "perMeter"; // 一毫米的像素坐标长度
    private final String TAG = "ruler";

    private Button mClear, mCorrection;
    private RulerView mRuler;
    private ActivityResultLauncher mLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLauncher == null) {
            mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result == null || result.getData() == null) return;
                    if (result.getResultCode() == RESULT_OK) {
                        mRuler.setMillimeter((int) SharedPreferencesHelper.get(Ruler.this,
                                Ruler.PER_MILLIMETER,
                                mRuler.getMillimeter()));
                        mRuler.invalidate();
                    }
                }
            });
        }
    }

    @Override
    protected int LayoutId() {
        return R.layout.ruler;
    }

    @Override
    protected void initView() {
        mClear = findViewById(R.id.clear);
        mCorrection = findViewById(R.id.correction);
        mRuler = findViewById(R.id.ruler);

        setBackgroundColor(App.getThemeColor(), R.id.clear, R.id.correction);

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRuler.clearMarks();
            }
        });
        mCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLauncher.launch(new Intent(Ruler.this, Correction.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
