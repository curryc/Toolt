package com.curry.function.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.curry.function.R;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-11 17:41
 * @description: 底部的Dialog
 **/
public abstract class BaseBottomDialog extends BaseActivity {
    @Override
    final protected void init() {
        setTheme(R.style.BottomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
