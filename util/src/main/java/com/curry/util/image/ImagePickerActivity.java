package com.curry.util.image;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.curry.util.R;
import com.curry.util.bean.Picture;
import com.curry.util.permission.PermissionUtils;
import com.curry.util.picker.ImagePicker;

import java.io.File;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-06 17:11
 * @description: 图片选择活动
 **/
public class ImagePickerActivity extends AppCompatActivity {
    private final int PERMISSION_CODE_FIRST = 0x14;//权限请求码
    private ImagePickerDialog mSelectPictureDialog;
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次
    public static final String CROP_WIDTH = "crop_width";
    public static final String CROP_HEIGHT = "crop_Height";
    public static final String RATIO_WIDTH = "ratio_Width";
    public static final String RATIO_HEIGHT = "ratio_Height";
    public static final String ENABLE_CROP = "enable_crop";
    private int mCropWidth;
    private int mCropHeight;
    private int mRatioWidth;
    private int mRatioHeight;
    private boolean mCropEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_select);

        mCropEnabled = getIntent().getBooleanExtra(ENABLE_CROP, true);
        mCropWidth = getIntent().getIntExtra(CROP_WIDTH, 200);
        mCropHeight = getIntent().getIntExtra(CROP_HEIGHT, 200);
        mRatioWidth = getIntent().getIntExtra(RATIO_WIDTH, 1);
        mRatioHeight = getIntent().getIntExtra(RATIO_HEIGHT, 1);

        //请求应用需要的所有权限
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            selectPicture();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            selectPicture();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    /**
     * 选择图片，弹出一个dialog选择是通过相册选择还是通过拍照选择
     */
    public void selectPicture() {
        mSelectPictureDialog = new ImagePickerDialog(this, R.style.ActionSheetDialogStyle);
        mSelectPictureDialog.setOnItemClickListener(new ImagePickerDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int type) {
                if (type == ImagePicker.CAMERA) {
                    ImagePickerUtils.getByCamera(ImagePickerActivity.this);
                } else if (type == ImagePicker.ALBUM) {
                    ImagePickerUtils.getByAlbum(ImagePickerActivity.this);
                } else if (type == ImagePicker.CANCEL) {
                    finish();
                    ImagePickerActivity.this.overridePendingTransition(0, R.anim.activity_out);//activity延迟150毫秒退出，为了执行完Dialog退出的动画
                }
            }
        });
    }

    /**
     * 选择图片的时候按返回键,设置好选择好的图片
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == ImagePickerUtils.GET_BY_ALBUM
                    || requestCode == ImagePickerUtils.GET_BY_CAMERA
                    || requestCode == ImagePickerUtils.CROP) {
                finish();
            }
        }
        String picturePath = ImagePickerUtils.onActivityResult(this, requestCode, resultCode, data, mCropEnabled, mCropWidth, mCropHeight, mRatioWidth, mRatioHeight);
        if (!TextUtils.isEmpty(picturePath)) {
            Picture bean = new Picture();
            bean.setPath(picturePath);
            bean.setCut(mCropEnabled);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                bean.setUri(ImageUtils.getImageUri(this, picturePath));
            } else {
                bean.setUri(Uri.fromFile(new File(picturePath)));
            }

            Intent intent = new Intent();
            intent.putExtra(ImagePicker.PICTURE_RESULT, bean);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
