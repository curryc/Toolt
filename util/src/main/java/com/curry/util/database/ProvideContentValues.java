package com.curry.util.database;

import android.content.ContentValues;

public interface ProvideContentValues<D extends Object> {
    ContentValues getContentValues(D data);
}
