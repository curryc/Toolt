package com.curry.function.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.WindowInsetsController;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.curry.function.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    private Fragment mFragment;
    private List<TurnBackListener> mTurnBackListeners = new ArrayList<>();
    private Toast mToast;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 改进: 继承不彻底
        setContentView(getLayoutId());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.header_action_bar);//设置标题样式
            TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.display_title);//获取标题布局的textview
            textView.setText(getWindowTitle());//设置标题名称，menuTitle为String字符串
            actionBar.setHomeButtonEnabled(true);//设置左上角的图标是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayShowCustomEnabled(true);// 使自定义的普通View能在title栏显示，即actionBar.setCustomView能起作用
        }
        initData();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return false;
    }

    /**
     * 在一个特定位置添加一个fragment
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
     * @param text
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }

    /**
     * 短时间Toast
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
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 获取当前窗口的标题
     * @return
     */
    protected  abstract String getWindowTitle();

    /**
     * 初始化视图
     */
    protected abstract void initView();
}