package com.curry.function.image.stitch;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-26 10:31
 * @description: 拼接图片产生的imageView
 **/
public class StitchImageView extends View {
    private int mode, margin, backgroundColor, size;
    private List<Bitmap> mImages;

    public StitchImageView(Context context) {
        this(context, 1, 0, 0, 100);
    }

    /**
     * 初始化这个View
     * @param context
     * @param mode 拼接模式（横， 竖， 字幕）
     * @param margin 图片间距
     * @param backgroundColor 背景颜色
     * @param size 图片大小（1-100）,图片的缩放比例，100就是原图
     */
    public StitchImageView(Context context, int mode, int margin, int backgroundColor, int size) {
        super(context);
        this.mode = mode;
        this.margin = margin;
        this.backgroundColor = backgroundColor;
        this.size = size;
        this.mImages = new LinkedList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 根据uri添加图片
     * @param uris
     */
    public void addImage(String[] uris) {
        ContentResolver cr = getContext().getContentResolver();
        List<Bitmap> images = new LinkedList<>();
        try {
            for (String uri : uris) {
                Bitmap d = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(uri)));
                images.add(d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImages.addAll(images);
    }
}
