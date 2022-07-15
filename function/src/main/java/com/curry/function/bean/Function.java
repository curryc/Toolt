package com.curry.function.bean;

import androidx.annotation.Nullable;
import com.curry.function.App;

import java.io.Serializable;
import java.util.UUID;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-04 19:45
 * @description: describe a function
 * @see:level {@link com.curry.function.config.Config}
 **/
public class Function implements Serializable {
    private int id;
    private int iconId;
    private String title;
    private String description;
    private int catalog;
    private int level;
    private Class<?> starter;
    private boolean collect;

    public Function(int iconId, int titleId, int descriptionId, int catalog, int level, Class<?> starter) {
        this.id = titleId;
        this.iconId = iconId;
        this.title = App.getContext().getString(titleId);
        this.catalog = catalog;
        this.level = level;
        this.starter = starter;
        this.collect = false;

        if (descriptionId != -1)
            this.description = App.getContext().getString(descriptionId);
        else
            this.description = "";
    }

    public Function(int titleId, int catalog, int level, Class<?> starter) {
        this(-1,titleId, -1, catalog, level, starter);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCatalog() {
        return catalog;
    }

    public int getLevel() {
        return level;
    }

    public Class<?> getStarter() {
        return starter;
    }

    public void setCollect(boolean isCollect) {
        this.collect = isCollect;
    }

    public boolean isCollect() {
        return collect;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Function)
            return this.id == (((Function) obj).getId());
//            return this.title.equals(((Function) obj).getTitle());
        else
            return false;
    }
}
