package com.curry.util.log;

import android.util.Log;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-06-30 20:05
 * @description: 日志器
 **/
public class Logger {
    private static final int LEVEL_VERBOSE = 0;
    private static final int LEVEL_DEBUG = 1;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_WARN = 3;
    private static final int LEVEL_ERROR = 4;

    private static final String DEFAULT_TAG = "curry";

    private Logger(){}


    public static void v(String message) {
        log(LEVEL_VERBOSE,  message);
    }

    public static void d(String message) {
        log(LEVEL_DEBUG, message);
    }

    public static void i(String message) {
        log(LEVEL_INFO,  message);
    }

    public static void w(String message) {
        log(LEVEL_WARN, message);
    }

    public static void e(String message) {
        log(LEVEL_ERROR,  message);
    }

    public static void v(String tag, String message) {
        log(LEVEL_VERBOSE, tag, message);
    }

    public static void d(String tag, String message) {
        log(LEVEL_DEBUG, tag, message);
    }

    public static void i(String tag, String message) {
        log(LEVEL_INFO, tag, message);
    }

    public static void w(String tag, String message) {
        log(LEVEL_WARN, tag, message);
    }

    public static void e(String tag, String message) {
        log(LEVEL_ERROR, tag, message);
    }

    public static void log(int level, String message){
        log(level, DEFAULT_TAG, message);
    }

    private static void log(int level, String tag, String message) {
        switch (level) {
            case LEVEL_VERBOSE:
                Log.v(tag, message);
                break;
            case LEVEL_DEBUG:
                Log.d(tag, message);
                break;
            case LEVEL_INFO:
                Log.i(tag, message);
                break;
            case LEVEL_WARN:
                Log.w(tag, message);
                break;
            case LEVEL_ERROR:
                Log.e(tag, message);
                break;
        }

    }
}
