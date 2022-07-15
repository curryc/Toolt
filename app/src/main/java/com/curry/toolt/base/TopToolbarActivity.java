package com.curry.toolt.base;

import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import com.curry.toolt.R;

public abstract class TopToolbarActivity extends BaseActivity {
    private Toolbar mToolbar;

    @Override
    protected final int getLayoutId() {
        return R.layout.activity_all_functions;
    }

    @Override
    protected final void initView() {
        mToolbar = findViewById(R.id.title_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initToolbar(mToolbar);
        initContainer(findViewById(R.id.content_container));
    }

    protected abstract void initContainer(FrameLayout container);

    protected abstract void initToolbar(Toolbar toolbar);
}