package com.curry.toolt.activity;

import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.curry.function.App;
import com.curry.function.bean.Function;
import com.curry.toolt.R;
import com.curry.toolt.adpter.SingleTypeAdapter;
import com.curry.toolt.base.BaseActivity;
import com.curry.toolt.base.BaseNavActivity;
import com.curry.toolt.base.TopToolbarActivity;
import com.curry.toolt.provider.FunctionProvider;

public class AllFunctionsActivity extends TopToolbarActivity {
    private RecyclerView mRecyclerView;

    protected void initContainer(FrameLayout container){
        mRecyclerView = new RecyclerView(this);
        container.addView(mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        SingleTypeAdapter mAdapter = new SingleTypeAdapter();
        mAdapter.register(Function.class, new FunctionProvider(this));
        mAdapter.addData(App.getFunctions());

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(getString(R.string.drawer_all));
    }
}