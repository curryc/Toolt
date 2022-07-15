package com.curry.toolt.base;

import android.app.Application;
import com.curry.function.App;
import com.curry.function.Collect;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 20:50
 * @description: 在打开这个应用前需要实现这个类,通过这个类进行初始化的操作
 **/
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        App.init(this);
        Collect.init();
    }
}
