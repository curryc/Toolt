package com.curry.function.image.stitch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.*;
import android.widget.FrameLayout;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.curry.function.App;
import com.curry.function.R;
import com.curry.function.base.BaseBackActivity;
import com.curry.util.adpter.RecyclerViewHolder;
import com.curry.util.adpter.SingleTypeAdapter;
import com.curry.util.base.BaseViewProvider;
import com.curry.util.bean.Picture;
import com.curry.util.image.ImageShowActivity;
import com.curry.util.picker.ImagePicker;
import com.curry.util.view.RoundRecImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-03 20:23
 * @description: 图片拼接活动, 进行图片的拼接
 **/
public class Stitch extends BaseBackActivity implements View.OnClickListener {
    private final String TAG = "Stitch";

    private FloatingActionButton mMake;
    private RecyclerView mRecyclerView;
    private SingleTypeAdapter mAdapter;

    private List<Picture> mImages;
    private List<String> mImagesUri;

    private ActivityResultLauncher mLauncher;

    @Override
    protected void initData() {
        super.initData();
        mImages = new LinkedList<>();
        mImagesUri = new LinkedList<>();
        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result == null || result.getData() == null) return;
                Picture pic = (Picture) result.getData().getExtras().get(ImagePicker.PICTURE_RESULT);
                mImages.add(pic);
                mImagesUri.add(pic.getUri().toString());
                mAdapter.addData(pic);
            }
        });

    }

    @Override
    protected void initView() {
        mMake = findViewById(R.id.make);
        mRecyclerView = findViewById(R.id.images);
        mAdapter = new SingleTypeAdapter();
        mAdapter.register(Picture.class, new RoundRecImageProvider(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);

        int[][] states = new int[][]{new int[]{}};
        int[] tints = new int[]{App.getThemeColor()};
        ColorStateList colorStateList = new ColorStateList(states, tints);
        mMake.setBackgroundTintList(colorStateList);
        mMake.setOnClickListener(this);
    }

    private void selectImage() {
        ImagePicker.create(mLauncher, Stitch.this)
                .selectPicture();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        if (itemId == R.id.clear_all) {
            mImages.clear();
            mAdapter.clearData();
        } else if (itemId == R.id.add_image) {
            selectImage();
        }
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_stitch, menu);
        return super.onCreateOptionsMenu(menu);
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
    public void onClick(View v) {
        // make
//        if(mImages.size() == 0) return;
        Intent i = new Intent(this, ChoseModeDialog.class);
        i.putExtra(ChoseModeDialog.DATA, mImagesUri.toArray(new String[0]));
        startActivity(i);
    }

    class RoundRecImageProvider extends BaseViewProvider<Picture> {
        private View.OnClickListener mImageClickListener;

        /**
         * 通过Contest和RecyclerView.ViewHolder的LayoutID
         *
         * @param mContext 　用来获取inflater
         */
        public RoundRecImageProvider(Context mContext) {
            super(mContext, R.layout.image_stitich_round_rec_image);
            mImageClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Stitch.this, ImageShowActivity.class);
                    intent.putExtra(ImageShowActivity.SHOW_IMAGES, mImagesUri.toArray(new String[0]));
                    overridePendingTransition(R.anim.slide_in_right, 0);
                    startActivity(intent);
                    return;
                }
            };
        }

        @Override
        public void bindView(RecyclerViewHolder holder, Picture data) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    mRecyclerView.getMeasuredWidth() / 3,
                    mRecyclerView.getMeasuredWidth() / 3
            );
            holder.getRootView().setLayoutParams(params);
            ((RoundRecImageView) holder.getViewById(R.id.image)).setImageURI(data.getUri());

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImages.remove(data);
                    mAdapter.deleteData(data);
                    mImagesUri.remove(data.getUri());
                }
            }, R.id.delete);
            holder.setOnClickListener(mImageClickListener, R.id.image);
        }
    }
}
