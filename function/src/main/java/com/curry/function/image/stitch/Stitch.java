package com.curry.function.image.stitch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.bean.Picture;
import com.curry.util.picker.ImagePicker;

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
    private final int IMAGE_REQUEST_CODE = 0x2;

    private List<Picture> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = new LinkedList<>();
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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.clear_all) {

        } else if (itemId == R.id.add_image) {
            ImagePicker.create(this, IMAGE_REQUEST_CODE)
                    .selectPicture();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_CODE){
//            System.out.println(data);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_stitch, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
