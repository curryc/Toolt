package com.curry.util.database;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-25 16:38
 * @description: 基础的cursorWrapper，用来可以将cursor转化为一个Data，此类必须继承才能正常使用(不能抽象因为在DataBase中需要)
 **/
public abstract class BaseCursorWrapper<D extends Object> extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public BaseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public abstract D getData();
}
