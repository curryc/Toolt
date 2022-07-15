package com.curry.function.bean;

import androidx.annotation.Nullable;
import com.curry.util.log.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 20:01
 * @description: a catalog for serval functions
 **/
public class FunctionCatalog implements Serializable {
    private int id;
    private String mTitle;
    private int mIcon;
    private List<Function> mFunctions;

    public FunctionCatalog(int id, String title,int icon, List<Function> functions) {
        this.id = id;
        this.mTitle = title;
        this.mIcon = icon;
        this.mFunctions = functions;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Function> getFunctions() {
        return mFunctions;
    }

    public int getId() {
        return id;
    }

    /**
     * 向这个catalog里面添加一个功能
     * @param f
     */
    public void addFunction(Function f) {
        for (Function function : mFunctions) {
            if (function.equals(f)) {
                Logger.v("you have add this function to the function catalog");
                return;
            }
        }
        mFunctions.add(f);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof FunctionCatalog)
            return this.id == ((FunctionCatalog) obj).getId();
        else
            return false;
    }
}
