package com.curry.toolt.base;

import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.curry.function.App;
import com.curry.function.bean.Function;
import com.curry.toolt.R;
import com.curry.toolt.adpter.SingleTypeAdapter;
import com.curry.toolt.provider.FunctionProvider;

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