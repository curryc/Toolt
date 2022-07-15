package com.curry.toolt.base;

import com.curry.toolt.R;

public abstract class BaseNavActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_functions;
    }

    @Override
    protected void initView() {
        initContent(R.id.content_container);
    }

    protected abstract void initContent(int id);
}