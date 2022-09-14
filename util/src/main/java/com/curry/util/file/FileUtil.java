package com.curry.util.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 20:03
 * @description: 文件工具, 通过这个文件获取系统中各种目录
 **/
public class FileUtil {
    private FileUtil() {
    }

    /**
     * @return 程序系统文件目录
     */
    public static String getFileDir(Context context) {
        return String.valueOf(context.getFilesDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统文件目录绝对路径
     */
    public static String getFileDir(Context context, String customPath) {
        String path = context.getFilesDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

    /**
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context) {
        return String.valueOf(context.getCacheDir());
    }

    /**
     * 获取图片目录
     *
     * @return 图片目录（/storage/emulated/0/Pictures）
     */
    public static File getExtPicturesPath(Context context) {
        File extPicturesPath = new File(getExternalFileDir(context, Environment.DIRECTORY_PICTURES));
        return extPicturesPath;
    }

    /**
     * 获取缓存图片的目录
     *
     * @param context Context
     * @return 缓存图片的目录
     */
    public static String getImageCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context, String customPath) {
        String path = context.getCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

    /**
     * @param context    上下文
     * @param type 外部共享储存类型
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir(Context context, String type) {
        String path = context.getExternalFilesDir(type) + type;
        return path;
    }

    /**
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context) {
        return String.valueOf(context.getExternalCacheDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context, String customPath) {
        String path = context.getExternalCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

    /**
     * @return 公共下载文件夹
     */
    public static String getPublicDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 创建文件夹
     *
     * @param DirPath 文件夹路径
     */
    public static void mkdir(String DirPath) {
        File file = new File(DirPath);
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
    }

    /**
     * 格式化文件路径
     * 示例：  传入 "sloop" "/sloop" "sloop/" "/sloop/"
     * 返回 "/sloop"
     */
    private static String formatPath(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        while (path.endsWith("/"))
            path = new String(path.toCharArray(), 0, path.length() - 1);
        return path;
    }

    /**
     * @return 存储卡是否挂载(存在)
     */
    public static boolean isMountSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

}
