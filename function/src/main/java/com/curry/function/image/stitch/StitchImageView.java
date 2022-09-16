package com.curry.function.image.stitch;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.*;
import android.net.Uri;
import android.view.View;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static android.view.View.MeasureSpec.getSize;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-26 10:31
 * @description: 拼接图片产生的imageView
 **/
public class StitchImageView extends View {
    private int margin, backgroundColor, size;
    private boolean orientation;// true为竖向

    private int reference;//参考大小，为了让图片对齐
    private int mWidth = 0, mHeight = 0;//View 的大小
    private int mImageWidth = 0, mImageHeight = 0;//image拼接去掉margin后的长宽高
    private int widthMeasureSpec, heightMeasureSpec;//当前measure出的容器大小
    private Bitmap ret;// 储存拼接后的结果
    private Canvas origin;// 原始的画布，可能需要对画布进行一些缩放来展示imageView，需要储存原始画布
    private List<Bitmap> mImages;

    /**
     * 竖向，无间距，背景为白色，原图大小
     *
     * @param context
     */
    public StitchImageView(Context context) {
        this(context, true, 0, 0, 100);
    }

    /**
     * 初始化这个View
     *
     * @param context
     * @param orientation     拼接模式（横false， 竖true）
     * @param margin          图片间距
     * @param backgroundColor 背景颜色
     * @param size            图片大小（1-100）,图片的缩放比例，100就是原图
     */
    public StitchImageView(Context context, boolean orientation, int margin, int backgroundColor, int size) {
        super(context);
        this.orientation = orientation;
        this.margin = margin;
        this.backgroundColor = backgroundColor;
        this.size = size;
        this.mImages = new LinkedList<>();
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ret = Bitmap.createBitmap(orientation ? reference : mWidth * reference / mHeight,
                orientation ? mHeight * reference / mWidth : reference,
                Bitmap.Config.ARGB_8888);
        origin = new Canvas(ret);
        int position = 0;
        Bitmap tmp;
        Bitmap gap = null;
        if (margin > 0) {
            if (orientation) {
                gap = Bitmap.createBitmap(reference, margin, Bitmap.Config.ARGB_8888);
                gap.eraseColor(backgroundColor);//填充颜色
            } else {
                gap = Bitmap.createBitmap(margin, reference, Bitmap.Config.ARGB_8888);
                gap.eraseColor(backgroundColor);//填充颜色
            }
        }
        for (Bitmap image : mImages) {
            // 第一张永远不裁剪不改变
            if (mImages.indexOf(image) == 0) {
                origin.drawBitmap(image, 0, 0, null);
                if (orientation) {
                    position += image.getHeight();
                } else {
                    position += image.getWidth();
                }
                continue;
            }

            if (margin <= 0) {
                // 表示电影模式
//                System.out.println(margin + "/" + (-margin * image.getHeight() / 100) + "/" + image.getHeight());
                int height = -margin * image.getHeight() / 100;
                tmp = Bitmap.createBitmap(image, 0, height, image.getWidth(), image.getHeight() - height);
            } else {
                tmp = image;
                if (orientation) {
                    origin.drawBitmap(gap, 0, position, null);
                    position += margin;
                } else {
                    origin.drawBitmap(gap, position, 0, null);
                    position += margin;
                }
            }

            if (orientation) {
                origin.drawBitmap(tmp, 0, position, null);
                position += tmp.getHeight();
            } else {
                origin.drawBitmap(tmp, position, 0, null);
                position += tmp.getWidth();
            }
        }
        //已经画好原始画布，可能需要对画布进行缩放
        Matrix matrix = new Matrix();
        matrix.postScale(orientation ? mWidth / (float) reference : mHeight / (float) reference,
                orientation ? mWidth / (float) reference : mHeight / (float) reference);

        Bitmap result = Bitmap.createBitmap(ret, 0, 0, ret.getWidth(), ret.getHeight(), matrix, true);
        canvas.drawBitmap(result, 0, 0, null);
    }

    /**
     * 重新测量大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mImages == null || mImages.size() == 0) return;
//        System.out.println(MeasureSpec.getSize(widthMeasureSpec) + ":" + MeasureSpec.getSize(heightMeasureSpec));
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        overMeasure(true);
    }

    /**
     * 根据uri添加图片
     *
     * @param uris
     */
    public void addImage(String[] uris) {
        ContentResolver cr = getContext().getContentResolver();
        List<Bitmap> images = new LinkedList<>();
        try {
            for (String uri : uris) {
                Bitmap d = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(uri)));
                if (mImages == null || mImages.size() == 0) {
                    if (orientation) {
                        reference = d.getWidth();
                    } else {
                        reference = d.getHeight();
                    }
                }
                Matrix matrix = new Matrix();
                if (orientation) {
                    matrix.postScale(reference / d.getWidth(), reference / d.getWidth());
                } else {
                    matrix.postScale(reference / d.getHeight(), reference / d.getHeight());
                }
                d = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(), matrix, true);
                images.add(d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImages.addAll(images);
        invalidate();
    }

    /**
     * 获取拼接后的图片
     *
     * @return
     */
    public Bitmap getBitmap() {
        return ret;
    }

    /**
     * 设置图片间距
     *
     * @param margin
     */
    public void setMargin(int margin) {
        this.margin = margin;
        overMeasure(false);
        invalidate();
    }

    /**
     * 设置图片大小(只影响拼接后的图片,不影响展示效果)
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
        invalidate();
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor
     */
    public void setBackColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }


    /**
     * 测量的算法
     *
     * @param firstTime
     */
    private void overMeasure(boolean firstTime) {
        if (firstTime) {
            mWidth = mHeight = mImageHeight = mImageWidth = 0;

            if (orientation) {
                mImageWidth = mWidth = reference;
                for (Bitmap image : mImages) {
                    mImageHeight += image.getHeight() * reference / image.getWidth();
                }
            } else {
                mImageHeight = mHeight = reference;
                for (Bitmap image : mImages) {
                    mImageWidth += image.getWidth() * reference / image.getHeight();
                }
            }
        }
        if (margin < 0) {
            mWidth = reference;
            mHeight = mImageHeight;
            for (Bitmap image : mImages) {
                if(mImages.indexOf(image) == 0) continue;
                mHeight = mHeight - image.getHeight() * (-margin) / 100;
            }
        } else {
            if (orientation) {
                mWidth = reference;
                mHeight = mImageHeight + (mImages.size() - 1) * margin;
            } else {
                mHeight = reference;
                mWidth = mImageWidth + (mImages.size() - 1) * margin;
            }
        }

//        System.out.println(mWidth + "|" + mHeight);

        // 不要让view超出边界
        if (mWidth > MeasureSpec.getSize(widthMeasureSpec)) {
            mHeight = mHeight * MeasureSpec.getSize(widthMeasureSpec) / mWidth;
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (mHeight > MeasureSpec.getSize(heightMeasureSpec)) {
            mWidth = mWidth * MeasureSpec.getSize(heightMeasureSpec) / mHeight;
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }

        // 根据用户设定进行缩放
        mWidth = mWidth * size / 100;
        mHeight = mHeight * size / 100;

//        System.out.println(mImageWidth + "//" + mImageHeight);
//        System.out.println(mWidth + "/" + mHeight);

        setMeasuredDimension((MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) ?
                        getSize(widthMeasureSpec) : mWidth,
                (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) ?
                        getSize(heightMeasureSpec) : mHeight);
    }
}
