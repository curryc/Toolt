package com.curry.toolt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.curry.toolt.R;
import com.curry.toolt.adpter.FragmentAdapter;
import com.curry.toolt.base.BaseActivity;
import com.curry.toolt.ui.NavFragment;
import com.curry.toolt.ui.collect.CollectFragment;
import com.curry.toolt.ui.home.HomeFragment;
import com.curry.toolt.ui.more.MoreFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main Activity";

    private NavFragment mNavBar;
    private ViewPager2 mViewPager;
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mNavBar = new NavFragment();
        mDrawer = findViewById(R.id.drawer);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle(), getPagers()));
        mViewPager.setCurrentItem(1, false);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mNavBar.doSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        mNavBar.setUp(new NavFragment.OnTabChanged() {
            @Override
            public void onTabChanged(int newPosition) {
                mViewPager.setCurrentItem(newPosition, true);
            }
        });
        addFragment(R.id.main_nav_container, mNavBar);

        mToolBar = findViewById(R.id.title_toolbar);
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.open();
            }
        });

        initMenu(((NavigationView) findViewById(R.id.drawer_nav)).getMenu());

        initWindow();
    }

    /**
     * 初始化Menu
     * @param menu 菜单
     */
    private void initMenu(Menu menu){
        menu.findItem(R.id.drawer_all).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, AllFunctionsActivity.class));
                return true;
            }
        });
        menu.findItem(R.id.drawer_theme).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, ThemeActivity.class));
                return true;
            }
        });
        menu.findItem(R.id.drawer_setting).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            }
        });
        menu.findItem(R.id.drawer_about).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            }
        });
        menu.findItem(R.id.drawer_finish).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return true;
            }
        });
    }


    /**
     * 初始化窗口
     */
    private void initWindow() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            mDrawer.getWindowInsetsController().hide(
//                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
//        }  else {
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            mDrawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
    }

    /**
     * 获取pager用来展示在主屏幕的pagerAdapter
     */
    private List<FragmentAdapter.PagerInfo> getPagers() {
        List<FragmentAdapter.PagerInfo> l = new ArrayList<>();
        Bundle bundle = new Bundle();
        l.add(new FragmentAdapter.PagerInfo("COLLECT", CollectFragment.class, bundle));
        l.add(new FragmentAdapter.PagerInfo("HOME", HomeFragment.class, bundle));
        l.add(new FragmentAdapter.PagerInfo("MORE", MoreFragment.class, bundle));
        return l;
    }
}