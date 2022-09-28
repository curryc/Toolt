package com.curry.function.config;

import com.curry.function.R;
import com.curry.function.bean.Function;
import com.curry.function.calculate.calculator.ScientificCalculator;
import com.curry.function.device.compass.Compass;
import com.curry.function.device.sinaudio.SinAudio;
import com.curry.function.image.bilibili.Bilibili;
import com.curry.function.image.stitch.Stitch;
import com.curry.function.normal.handleberrage.HandleBarrage;
import com.curry.function.normal.memorial.MemorialDayActivity;
import com.curry.function.normal.ruler.Ruler;

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


    /**
     * 功能分类的级别
     */
    public final static String[] level = {"普通功能", "新鲜功能", "VIP功能", "测试功能"};

    /**
     * 每个功能
     */
    public final static Function[] functions = {
            // 常用应用
            new Function(R.string.handle_barrage, 0, 0, HandleBarrage.class),
            new Function(R.string.ruler, 0, 1, Ruler.class),
            new Function(R.string.memorial_day, 0, 1, MemorialDayActivity.class),
            // 查询应用
            // 计算应用
            new Function(R.string.calculator, 2, 0, ScientificCalculator.class),
            // 图片应用
            new Function(R.string.bilibili, 3, 0, Bilibili.class),
            new Function(R.string.image_stitch, 3, 1, Stitch.class),
            // 文字应用
            // 设备应用
            new Function(R.string.sin_audio, 5, 1, SinAudio.class),
            new Function(R.string.compass, 5, 1, Compass.class)
            // 文件应用
            // 其他应用
    };
}
