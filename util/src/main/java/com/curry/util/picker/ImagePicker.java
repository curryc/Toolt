package com.curry.util.picker;

import android.app.Activity;
import android.content.Intent;
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

    public static final int SELECT_REQUEST_CODE = 0x15;//选择图片请求码
    public static final String PICTURE_RESULT = "picture_result";//选择的图片结果
    private int mRequestCode;
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    /**
     * 创建 ImagePicker（用于 Activity）
     *
     * @param activity    Activity
     * @param requestCode 请求码，用于结果回调 onActivityResult() 中判断
     * @return ImagePicker
     */
    public static ImagePicker create(Activity activity, int requestCode) {
        return new ImagePicker(activity, requestCode);
    }

    /**
     * 创建 ImagePicker（用于 Fragment）
     *
     * @param fragment    Fragment
     * @param requestCode 请求码，用于结果回调 onActivityResult() 中判断
     * @return ImagePicker
     */
    public static ImagePicker create(Fragment fragment, int requestCode) {
        return new ImagePicker(fragment, requestCode);
    }

    private ImagePicker(Activity activity, int requestCode) {
        this(activity, (Fragment) null, requestCode);
    }

    private ImagePicker(Fragment fragment, int requestCode) {
        this(fragment.getActivity(), fragment, requestCode);
    }

    private ImagePicker(Activity activity, Fragment fragment, int requestCode) {
        this.mActivity = new WeakReference(activity);
        this.mFragment = new WeakReference(fragment);
        this.mRequestCode = requestCode;
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
        Activity activity = this.mActivity.get();
        Fragment fragment = this.mFragment.get();
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.ENABLE_CROP, cropEnabled);
        intent.putExtra(ImagePickerActivity.CROP_WIDTH, cropWidth);
        intent.putExtra(ImagePickerActivity.CROP_HEIGHT, cropHeight);
        intent.putExtra(ImagePickerActivity.RATIO_WIDTH, ratioWidth);
        intent.putExtra(ImagePickerActivity.RATIO_HEIGHT, ratioHeight);
        if (fragment != null) {
            fragment.startActivityForResult(intent, mRequestCode);
        } else {
            activity.startActivityForResult(intent, mRequestCode);
        }
    }
}
