package com.curry.util.image;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.curry.util.file.FileUtil;
import com.curry.util.log.Logger;

import java.io.*;
import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-08-06 16:49
 * @description: 图片工具
 **/
public class ImageUtils {
    /**
     * 获取Bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath) {
        if (isSpace(filePath))
            return null;
        return BitmapFactory.decodeFile(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取图片路径
     *
     * @param context Context
     * @param uri     图片 Uri
     * @return 图片路径
     */
    public static String getImagePath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 储存一个bitmap,在Q版本之前后之后时不一样的，前者直接用输出流刷，后者用MediaStore，需要编写图片的其他信息
     * @param context
     * @param bitmap
     * @param filename
     * @param description
     * @throws IOException
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String filename, String description) throws IOException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // 首先创建一个需要的抽象文件,目录可能需要重新创建
            File saveDir = FileUtil.getExtPicturesPath(context);
            saveDir = new File(saveDir, "toolt");
            if (!saveDir.exists() && !saveDir.mkdirs()) {
                try {
                    throw new Exception("create directory fail!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Logger.d("SMG", saveDir.getAbsolutePath());
            File outputFile = new File(saveDir, filename);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 直接吧图片刷到文件中
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            // 通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outputFile)));
        } else {
            // 编写ContentValues
            String path = Environment.DIRECTORY_PICTURES + "toolt";
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, description);
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, path);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
            // 将文件插入到外部共享储存空间(防止外部污染)
            Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri insertUri = context.getContentResolver().insert(external, contentValues);
            // 文件刷入
            OutputStream fos = (OutputStream) null;
            if (insertUri != null) {
                try {
                    fos = context.getContentResolver().openOutputStream(insertUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            }
        }
    }

    /**
     * 获取图片 Uri
     *
     * @param context Context
     * @param path    图片路径
     * @return 图片 Uri
     */
    public static Uri getImageUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
