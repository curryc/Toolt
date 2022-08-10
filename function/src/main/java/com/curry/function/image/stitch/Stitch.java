package com.curry.function.image.stitch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.bean.Picture;
import com.curry.util.picker.ImagePicker;
import com.curry.util.view.FlowLayout;
import com.curry.util.view.RoundImageView;
import com.curry.util.view.RoundRecImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-03 20:23
 * @description: 图片拼接活动, 进行图片的拼接
 **/
public class Stitch extends BaseBackActivity {
    private final String TAG = "Stitch";

    private FlowLayout mFlow;
    private final int MARGIN = 15;
    private final int CHILD_SIZE = 350;

    private List<Picture> mImages;
    private ActivityResultLauncher mLauncher;
    private LinearLayout.LayoutParams mParams; // 每个图片大小
    private View.OnClickListener mImageClickListener;

    @Override
    protected void initData() {
        super.initData();
        mImages = new LinkedList<>();
        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result == null || result.getData() == null) return;
                Picture pic = (Picture) result.getData().getExtras().get(ImagePicker.PICTURE_RESULT);
                mImages.add(pic);
                RoundRecImageView imageView = new RoundRecImageView(Stitch.this);
                imageView.setLayoutParams(mParams);
                imageView.setImageURI(pic.getUri());
                imageView.setOnClickListener(mImageClickListener);
                mFlow.addView(imageView,mFlow.getChildCount() - 1);
            }
        });
        mParams = new LinearLayout.LayoutParams(CHILD_SIZE, CHILD_SIZE);
        mParams.setMargins(MARGIN,MARGIN,MARGIN,MARGIN);
        mImageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                return;
            }
        };
    }

    @Override
    protected int LayoutId() {
        return R.layout.image_stitch;
    }

    @Override
    protected String getWindowTitle() {
        return getString(R.string.image_stitch);
    }

    @Override
    protected void initView() {
        mFlow = findViewById(R.id.image_flow);

        int[] colors = {App.getThemeColor(),
                App.getThemeColor("colorPrimaryDark")};
        GradientDrawable background = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        background.setGradientType(GradientDrawable.RECTANGLE);
        background.setCornerRadius(RoundRecImageView.RADIUS);

        RoundImageView i = new RoundImageView(this);
        i.setLayoutParams(mParams);
        i.setBackground(background);
        i.setImageDrawable(getDrawable(R.drawable.ic_circle_add));
        i.setOnClickListener(v -> selectImage());
        mFlow.addView(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        if (itemId == R.id.clear_all) {
            mImages.clear();
            mFlow.removeViews(0, mFlow.getChildCount() - 1);
        } else if (itemId == R.id.add_image) {
            selectImage();
        }
        return true;
    }

    private void selectImage() {
        ImagePicker.create(mLauncher, Stitch.this)
                .selectPicture();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_stitch, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
