package com.curry.util.event;

import androidx.annotation.Nullable;
import com.curry.util.base.BaseEvent;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-19 00:52
 * @description: 匹配Regex的event
 **/
public class MatchRegexEvent extends BaseEvent<String[]> {
    public MatchRegexEvent(@Nullable @org.jetbrains.annotations.Nullable String id, boolean ok) {
        super(id, ok);
    }
}
