package com.curry.util.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-28 21:49
 * @description: 用来对日期进行各种操作
 **/
public class Dater {
    public static int calculateDifference(Date one, Date compared){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(one);
        long time1 = cal.getTimeInMillis();
        cal.setTime(compared);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
