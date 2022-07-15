package com.curry.function;

import android.app.Application;
import android.content.Context;
import com.curry.function.bean.Function;
import com.curry.function.bean.FunctionCatalog;
import com.curry.function.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 20:00
 * @description: to get all the information
 **/
public class App {
    public static final int ANIM_DURATION = 300;

    private static Context sContext;
    private static List<FunctionCatalog> sCatalogs;
    private static List<Function> sFunctions;

    public static void init(Application application) {
        App.sContext = application;
        obtainFunctions();
    }

    /**
     * 获取Context
     *
     * @return
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取所有功能分类
     *
     * @return
     */
    public static List<FunctionCatalog> getFunctionCatalog() {
        if (checkInvalid()) return null;
        if (sCatalogs == null) {
            obtainFunctions();
        }
        return sCatalogs;
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public static List<Function> getFunctions() {
        if (checkInvalid()) return null;
        if (sFunctions == null) {
            obtainFunctions();
        }
        return sFunctions;
    }

    /**
     * 通过id获取一个function
     *
     * @param id
     */
    public static Function getFunctionById(int id) {
        if (id <= 0) return null;
        for (Function function : sFunctions) {
            if (function.getId() == id) return function;
        }
        return null;
    }


    /**
     * 获取dot的颜色
     *
     * @param level 级别
     */
    public static int getDotColor(final int level) {
        switch (level) {
            default:
                return -1;
            case 1:
                return R.color.dot_level_1;
            case 2:
                return R.color.dot_level_2;
            case 3:
                return R.color.dot_level_3;
        }
    }

    /**
     * 检查当前App是否有效
     *
     * @return
     */
    private static boolean checkInvalid() {
        return sContext == null;
    }

    /**
     * 获取所有功能并分类他们
     */
    private static void obtainFunctions() {
        sCatalogs = new ArrayList<>();
        sFunctions = Arrays.asList(Config.functions);
        List<Integer> mCollect = Collect.getCollectIds();

        traverse:
        for (Function function : sFunctions) {
            if (mCollect != null && mCollect.size() != 0 && mCollect.contains(function.getId())) {
                function.setCollect(true);
            }
            // 找找这个catalog是否已经存在
            for (FunctionCatalog catalog : sCatalogs) {
                if (catalog.getId() == function.getCatalog()) {
                    catalog.addFunction(function);
                    continue traverse;
                }
            }
            // 如果不存在
            FunctionCatalog catalog = new FunctionCatalog(function.getCatalog(),
                    Config.catalog[function.getCatalog()],
                    Config.catalogIcon[function.getCatalog()],
                    new ArrayList<>());
            catalog.addFunction(function);
            sCatalogs.add(catalog);
        }
    }

}
