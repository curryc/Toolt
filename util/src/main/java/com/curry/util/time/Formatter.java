package com.curry.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 16:10
 * @description: 各种时间、日期的格式化工具
 **/
public class Formatter {
    /**
     * 获取类似于网络上的时间格式
     * EEE, dd MM yyyy HH:mm:ss z
     * @param date
     * @return
     */
    public static String getWebDateFormatString(Date date){
        return new SimpleDateFormat("EEE, dd MM yyyy HH:mm:ss z", Locale.ENGLISH).format(date);
    }

    /**
     * 获取可以用于文件的时间格式
     * yyyyMMdd_HHmmss
     * @param date
     * @return
     */
    public static String getFileFormatString(Date date){
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(date);
    }
}
