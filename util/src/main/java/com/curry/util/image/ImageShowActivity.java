package com.curry.util.image;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.curry.util.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-10 18:47
 * @description: 展示一张图片的活动
 **/
public class ImageShowActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SHOW_IMAGES = "show";

    private List<ImageView> mImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        setTheme(R.style.CenterActivity);

        initData();

        setContentView(getContentView());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }

    private void initData() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        mImages = new ArrayList<>();

        String[] uris = getIntent().getStringArrayExtra(SHOW_IMAGES);
        for (String uri : uris) {
            ImageView image = new ImageView(ImageShowActivity.this);
            image.setLayoutParams(params);
            image.setImageURI(Uri.parse(uri));
            image.setOnClickListener(ImageShowActivity.this);

            mImages.add(image);
        }
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    private View getContentView() {
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(new Adapter());

        return viewPager;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mImages.get(position));
            return mImages.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mImages.get(position));
        }
    }
}
