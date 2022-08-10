package com.curry.util.picker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.curry.util.image.ImagePickerActivity;

import java.lang.ref.WeakReference;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-03 20:26
 * @description: 选择设备中的图片
 **/
public class ImagePicker extends AppCompatActivity {
    public static final int CANCEL = 0;//取消
    public static final int CAMERA = 1;//拍照
    public static final int ALBUM = 2;//相册

    public static final String PICTURE_RESULT = "picture_result";//选择的图片结果

    private Context mContext;
    private ActivityResultLauncher mLauncher;

    public ImagePicker(ActivityResultLauncher launcher, Context context) {
        this.mLauncher = launcher;
        this.mContext = context;
    }

    /**
     * 创建 ImagePicker（用于 Fragment）
     *
     * @param launcher    ActivityResultLauncher
     * @return ImagePicker
     */
    public static ImagePicker create(ActivityResultLauncher launcher, Context context) {
        return new ImagePicker(launcher, context);
    }

    /**
     * 选择图片（默认不裁剪）
     */
    public void selectPicture() {
        selectPicture(false, 0, 0, 0, 0);
    }

    /**
     * 选择图片（根据参数决定是否裁剪）
     *
     * @param cropEnabled 是否裁剪
     */
    public void selectPicture(boolean cropEnabled) {
        selectPicture(cropEnabled, 0, 0, 0, 0);
    }

    /**
     * 选择图片（指定宽高及宽高比例裁剪）
     *
     * @param cropEnabled 是否裁剪
     * @param cropWidth   裁剪宽
     * @param cropHeight  裁剪高
     * @param ratioWidth  宽比例
     * @param ratioHeight 高比例
     */
    public void selectPicture(boolean cropEnabled, int cropWidth, int cropHeight, int ratioWidth, int ratioHeight) {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.ENABLE_CROP, cropEnabled);
        intent.putExtra(ImagePickerActivity.CROP_WIDTH, cropWidth);
        intent.putExtra(ImagePickerActivity.CROP_HEIGHT, cropHeight);
        intent.putExtra(ImagePickerActivity.RATIO_WIDTH, ratioWidth);
        intent.putExtra(ImagePickerActivity.RATIO_HEIGHT, ratioHeight);
        mLauncher.launch(intent);
    }
}
