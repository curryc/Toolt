package com.curry.function.base;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import com.curry.function.App;
import com.curry.function.R;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-30 15:27
 * @description: 带有返回按钮的活动
 **/
public abstract class BaseBackActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            int[] colors = {App.getThemeColor("colorPrimaryDark"),
                    App.getThemeColor("colorPrimary")
                    };
            GradientDrawable background = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            background.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            actionBar.setBackgroundDrawable(background);
            actionBar.setTitle(getWindowTitle());
            actionBar.setHomeButtonEnabled(true);//设置左上角的图标是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标

            initActionbar(actionBar);
        }
    }

    @Override
    protected void init() {
        setTheme(R.style.BackAppTheme);
    }

    /**
     * 获取当前窗口的标题
     *
     * @return
     */
    protected abstract String getWindowTitle();

    /**
     * 可能需要改变toolbar样式
     * @param actionbar
     */
    protected void initActionbar(ActionBar actionbar){};
}
