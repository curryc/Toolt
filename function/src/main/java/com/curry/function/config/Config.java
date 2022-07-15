package com.curry.function.config;

import com.curry.function.R;
import com.curry.function.bean.Function;
import com.curry.function.device.sinaudio.SinAudio;
import com.curry.function.normal.handleberrage.HandleBarrage;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 19:27
 * @description: 配置这个应用
 **/
public class Config {
    // 功能分类的标题
    public final static String[] catalog = {"常用应用","查询应用","计算应用","图片应用","文字应用","设备应用","文件应用","其他应用"};

    // 功能分类的图标
    public final static int[] catalogIcon = {
            R.drawable.ic_normal,
            R.drawable.ic_query,
            R.drawable.ic_calculate,
            R.drawable.ic_picture,
            R.drawable.ic_word,
            R.drawable.ic_device,
            R.drawable.ic_file,
            R.drawable.ic_other
    };


    // 功能分类的级别
    public final static String[] level = {"普通功能", "新鲜功能", "VIP功能", "测试功能"};

    // 每个功能
    public final static Function[] functions = {
            new Function(R.string.handle_barrage, 0, 0, HandleBarrage.class),
            new Function(R.string.sin_audio, 5, 1, SinAudio.class),
            new Function(R.string.image_cropping, 3, 0, HandleBarrage.class),
            new Function(R.string.ruler, 0, 1, HandleBarrage.class)
    };
}
