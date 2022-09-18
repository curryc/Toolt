package com.curry.util.event;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.curry.util.base.BaseEvent;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-19 00:53
 * @description:
 **/
public class GetBitmapByUrlEvent extends BaseEvent<Bitmap> {
    public GetBitmapByUrlEvent(@Nullable @org.jetbrains.annotations.Nullable String id, boolean ok) {
        super(id, ok);
    }
}
