package com.curry.function.normal.memorial;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.adpter.SingleTypeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-25 19:12
 * @description: 纪念日
 **/
public class MemorialDayActivity extends BaseBackActivity {
    private RecyclerView mDays;
    private FloatingActionButton mAdd;
    private SingleTypeAdapter mAdapter;

    @Override
    protected int LayoutId() {
        return R.layout.memorial_day;
    }

    @Override
    protected void initView() {
        mDays = findViewById(R.id.days);
        mAdd = findViewById(R.id.add);

        mAdapter = new SingleTypeAdapter();
        mAdapter.register(MemorialDay.class, new MemorialDayProvider(this));
        mDays.setLayoutManager(new LinearLayoutManager(this));
        mDays.setAdapter(mAdapter);

        mAdapter.addData(new MemorialDay(new Date(), "name", 1, "description", false));
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.memorial_day);
    }
}
