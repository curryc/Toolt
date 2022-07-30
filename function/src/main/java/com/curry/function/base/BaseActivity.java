package com.curry.function.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.curry.function.App;
import com.curry.function.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    private Fragment mFragment;
    private List<TurnBackListener> mTurnBackListeners = new ArrayList<>();
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setContentView(LayoutId());

        initData();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onFinish();
                finish();
        }
        return false;
    }

    /**
     * 在一个特定位置添加一个fragment
     *
     * @param containerId
     * @param fragment
     */
    public void addFragment(int containerId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(containerId, fragment);
                } else {
                    transaction.add(containerId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }


    /**
     * 两种toast
     *
     * @param text
     * @param time
     */
    private void toast(String text, int time) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, time);
        } else {
            mToast.setText(text);
            mToast.setDuration(time);
        }
        mToast.show();
    }

    /**
     * 长时间Toast
     *
     * @param text
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }

    /**
     * 短时间Toast
     *
     * @param text
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 点击回退的接口,通过这个接口可以设置回退实现的动作
     */
    public interface TurnBackListener {
        boolean onTurnBack();
    }

    /**
     * 向Activity中添加回退动作
     *
     * @param l
     */
    public void addOnTurnBackListener(TurnBackListener l) {
        this.mTurnBackListeners.add(l);
    }

    /**
     * 进入activity
     *
     * @return
     */
    public <T extends Serializable> void openActivity(@Nullable Context context, Class<?> clz, String key, T value) {
        if (context == null) context = this;
        Intent i = new Intent(context, clz);
        i.putExtra(key, value);
        context.startActivity(i);
    }

    /**
     * 获取当前Activity的layout
     *
     * @return
     */
    protected abstract int LayoutId();
    /**
     * 当前Theme
     */
    protected abstract void init();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 获取当前窗口的标题
     *
     * @return
     */
    protected abstract String getWindowTitle();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 结束当前活动需要调用的方法
     */
    protected void onFinish(){}
}