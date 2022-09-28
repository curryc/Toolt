package com.curry.function.normal.memorial;

import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-28 15:01
 * @description: 描述一个纪念日的bean
 **/
public class MemorialDay {
    private Date mDate;
    private int mImportance;
    private String mDescription;
    private boolean mRepeatEveryYear;
    private String mName;

    public MemorialDay(Date date, String name, int importance, String description, boolean repeatEveryYear) {
        mDate = date;
        mName = name;
        mImportance = importance;
        mDescription = description;
        mRepeatEveryYear = repeatEveryYear;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getImportance() {
        return mImportance;
    }

    public void setImportance(int importance) {
        mImportance = importance;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isRepeatEveryYear() {
        return mRepeatEveryYear;
    }

    public void setRepeatEveryYear(boolean repeatEveryYear) {
        mRepeatEveryYear = repeatEveryYear;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
