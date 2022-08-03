package com.curry.function.normal.ruler;

import android.view.View;
import android.widget.*;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseLandActivity;
import com.curry.util.cache.SharedPreferencesHelper;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-02 15:37
 * @description: 用来校对尺子的活动
 **/
public class Correction extends BaseLandActivity {
    private SeekBar mSeekbar;
    private Spinner mSpinner;
    private Button mCancel, mConfirm;

    private final String[] mode = new String[]{"5cm", "IDCard"};

    @Override
    protected int LayoutId() {
        return R.layout.ruler_correction;
    }

    @Override
    protected void initView() {
        mSeekbar = findViewById(R.id.correction_length);
        mSpinner = findViewById(R.id.spinner);
        mCancel = findViewById(R.id.ruler_correction_cancel);
        mConfirm = findViewById(R.id.ruler_correcttion_confirm);


        mSeekbar.setProgress(50 * (int) SharedPreferencesHelper.get(this,
                Ruler.PER_MILLIMETER,
                RulerView.DEFAULT_MILLIMETER));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.ruler_spinner_item,
                mode);
        adapter.setDropDownViewResource(R.layout.ruler_spinner_dropdown_item);
//        mSpinner.setPrompt(mode[0]);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);

        mCancel.setBackgroundColor(App.getThemeColor());
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mConfirm.setBackgroundColor(App.getThemeColor());
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeekbar.getProgress() == 0) {
                    toastShort(getString(R.string.ruler_correction_zero_hint));
                } else {
                    if (mSpinner.getSelectedItem().equals(mode[0])) {
                        SharedPreferencesHelper.put(Correction.this, Ruler.PER_MILLIMETER, mSeekbar.getProgress() / 50);
                    } else if (mSpinner.getSelectedItem().equals(mode[1])) {
                        SharedPreferencesHelper.put(Correction.this, Ruler.PER_MILLIMETER, mSeekbar.getProgress() / 85.6);
                    }
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
