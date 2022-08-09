package com.curry.function.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.curry.util.log.Logger;

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
     * 批量设置背景颜色
     * @param color
     * @param layoutId
     * @return
     */
    protected boolean setBackgroundColor(int color, int...layoutId){
        for (int i : layoutId) {
            try {
                findViewById(i).setBackgroundColor(color);
            } catch (Exception e) {
                Logger.e( "can set src");
                return false;
            }
        }
        return true;
    }

    /**
     * 批量设置单击监听
     * @param listener
     * @param ids
     * @return
     */
    protected boolean setClickListener(View.OnClickListener listener, int...ids){
        if (ids == null) {
            return false;
        } else {
            for (int id : ids) {
                findViewById(id).setOnClickListener(listener);
            }
            return true;
        }
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
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 结束当前活动需要调用的方法
     */
    protected void onFinish(){}
}