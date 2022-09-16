package com.curry.function.image.bilibli;

import com.curry.function.base.BaseBackActivity;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-14 18:07
 * @description: 获取Bilibili视频封面
 **/
public class Bilibili extends BaseBackActivity {
    private final String PREFIX = "https://www.bilibili.com/video/";
    private final String REX = "http:\\/\\/i[0-9]\\.hdslb.com\\/bfs\\/archive\\/[0-9a-z]*\\.jpg";

    @Override
    protected int LayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getWindowTitle() {
        return null;
    }
}
