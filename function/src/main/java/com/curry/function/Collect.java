package com.curry.function;

import com.curry.function.bean.Function;
import com.curry.util.cache.ACache;
import com.curry.util.file.FileUtil;
import com.curry.util.log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-12 19:53
 * @description: 收藏应用或获取收藏的应用
 **/
public class Collect {
    private final static String TAG = "collects";

    private static ACache sCache;
    private static List<Integer> sCollectIds;
    private static List<Function> sCollects;

    /**
     * 收藏一个function
     * @param function
     */
    public static void putCollect(Function function){
        if(sCollectIds.contains(function)){
            Logger.i("you have collected this function");
            return;
        }else{
            sCollectIds.add(function.getId());
            sCollects.add(function);
            StringBuilder sb = new StringBuilder();
            for (int id : sCollectIds) {
                sb.append(id);
                sb.append("\n");
            }
            sCache.put(TAG, sb.toString());
        }
        set();
    }

    /**
     * 删除一个功能
     * @param function
     */
    public static void deleteCollect(Function function){
        if(sCollectIds.contains(function.getId())){
            sCollectIds.remove(function.getId());
            sCollects.remove(function);
        }else{
            Logger.i("don't have this function in collect");
        }
        StringBuilder sb = new StringBuilder();
        for (int id : sCollectIds) {
            sb.append(id);
            sb.append("\n");
        }
        sCache.put(TAG, sb.toString());
        set();
    }

    /**
     * 删除多个collect
     * @param functions
     */
    public static void deleteCollects(List<Function> functions){
        for (Function function : functions) {
            if(sCollectIds.contains(function.getId())){
                sCollectIds.remove(function.getId());
                sCollects.remove(function);
            }else{
                Logger.i("don't have this one function in collect");
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int id : sCollectIds) {
            sb.append(id);
            sb.append("\n");
        }
        sCache.put(TAG, sb.toString());
        set();
    }

    /**
     * 收藏多个function
     * @param functions
     */
    public static void putCollects(List<Function> functions){
        for (Function function : functions) {
            if(sCollectIds.contains(function.getId())){
                Logger.i("you have collected this function");
                continue;
            }else{
                sCollectIds.add(function.getId());
                sCollects.add(function);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int id : sCollectIds) {
            sb.append(id);
            sb.append("\n");
        }
        sCache.put(TAG, sb.toString());
        set();
    }

    /**
     * 获取收藏的所有functions的id
     * @return
     */
    public static List<Integer> getCollectIds(){
        if(sCache == null || sCollectIds == null || sCollectIds.size() == 0) return null;
        return sCollectIds;
    }

    /**
     * 获取所有的function
     * @return
     */
    public static List<Function> getCollects(){
        if(sCache == null || sCollectIds == null || sCollectIds.size() == 0) return null;
        return sCollects;
    }

    /**
     * 初始化读取收藏的应用
     */
    public static void init(){
        sCache = ACache.get(new File(FileUtil.getCacheDir(App.getContext())));
        sCollectIds = new ArrayList<>();
        sCollects = new ArrayList<>();
        if(sCache.getAsString(TAG) == null || sCache.getAsString(TAG).equals("")) return;
        StringTokenizer st = new StringTokenizer(sCache.getAsString(TAG), "\n");
        for(int i = 0; i<st.countTokens();i++){
            int next = Integer.parseInt(st.nextToken());
            sCollectIds.add(next);
            sCollects.add(App.getFunctionById(next));
        }
        set();
    }

    /**
     * 设置动态的收藏
     */
    private static void set(){
        App.setCollects(sCollectIds);
    }
}
