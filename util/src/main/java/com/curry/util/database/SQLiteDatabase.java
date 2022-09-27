package com.curry.util.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Toolt
 * }
 * @author: 陈博文
 * @create: 2022-09-21 18:44
 * @description: 数据库操作
 * D为数据库储存的数据的bean， Helper为SQLiteOpenHelper的子类， Wrapper是BaseCursorWrapper 的子类
 * 创建一个类继承SQLiteOpenHelper{@link SQLiteOpenHelper}， 在其中的onCreate方法中创建数据库（如果有表就不创建了）
 * 创建一个类继承BaseCursorWrapper{@link BaseCursorWrapper}， 这个类的泛型必须对应泛型D
 * implements接口ProvideContentValues{@link ProvideContentValues}，在其中的getContentValues方法中写出获取D这个泛型的ContentValues{@link ContentValues}的方法
 * 然后调用这个方法的init方法，之后可以进行各种数据库操作
 **/
public class SQLiteDatabase<D extends Object, Helper extends SQLiteOpenHelper, Wrapper extends BaseCursorWrapper<D>> {
    private static final int VERSION = 1;

    private String mTableName;
    private android.database.sqlite.SQLiteDatabase mDatabase;
    private ProvideContentValues mProvider;
    private Class<? extends BaseCursorWrapper<D>> mWrapperClx;

    /**
     * 初始化一个数据库
     *
     * @param SQLiteOpenHelper 这里面的OnCreate可以在检查数据库不存在的时候创建数据库
     * @param provider         一个ContentValues的provider，对一个Data转化为ContentValues
     * @return
     */
    public static SQLiteDatabase init(SQLiteOpenHelper SQLiteOpenHelper, ProvideContentValues provider, Class<? extends BaseCursorWrapper> wrapperClx) {
        return new SQLiteDatabase(SQLiteOpenHelper, provider, wrapperClx);
    }

    private SQLiteDatabase(Helper helper, ProvideContentValues<D> provider, Class<? extends BaseCursorWrapper<D>> wrapperClx) {
        this.mDatabase = helper.getWritableDatabase();
        this.mTableName = helper.getDatabaseName();
        this.mProvider = provider;
        this.mWrapperClx = wrapperClx;
    }

    /**
     * 获取数据库中的数据
     *
     * @param col
     * @param value
     * @return
     */
    public List<D> getData(String col, String value) {
        List<D> items = new ArrayList<>();
        Wrapper cursor = queryData(col, new String[]{value});
        if(cursor == null) return null;
        try {
            cursor.moveToFirst();
            //移动cursor,读取所有条目
            while (!cursor.isAfterLast()) {
                items.add(cursor.getData());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<D> getData() {
        return getData(null, null);
    }

    /**
     * 增加数据
     *
     * @param data
     */
    public void addData(D data) {
        ContentValues values = mProvider.getContentValues(data);
        mDatabase.insert(mTableName, null, values);
    }

    /**
     * 删除数据
     *
     * @param whereClause
     * @param whereArgs
     */
    public void deleteData(String whereClause, String[] whereArgs) {
        mDatabase.delete(mTableName, whereClause, whereArgs);
    }

    /**
     * 删除数据
     *
     * @param whereClause
     * @param whereArg
     */
    public void deleteData(String whereClause, String whereArg) {
        deleteData(whereClause, new String[]{whereArg});
    }

    /**
     * 更新数据
     *
     * @param data
     * @param whereClause
     * @param whereArgs
     */
    public void updateData(D data, String whereClause, String[] whereArgs) {
        mDatabase.update(mTableName,
                mProvider.getContentValues(data),
                whereClause,
                whereArgs);
    }

    /**
     * 更新数据
     *
     * @param data
     * @param whereClause
     * @param whereArg
     */
    public void updateData(D data, String whereClause, String whereArg) {
        updateData(data, whereClause, new String[]{whereArg});
    }

    /**
     * 得到whereClause中为whereArgs的cursor，利用反射找到CursorWrapper
     *
     * @param whereClause 数据库的列
     * @param whereArgs   数据库中的列为哪一范围
     * @return
     */
    private Wrapper queryData(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                mTableName,
                null, //null意味着所有column
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        try {
            return (Wrapper) mWrapperClx.getConstructor(Cursor.class).newInstance(cursor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            return null;
        }
    }
}
