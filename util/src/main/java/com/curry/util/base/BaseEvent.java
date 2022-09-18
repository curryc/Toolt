package com.curry.util.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-06-30 16:50
 * @description: EventBus的基础Event
 **/
public class BaseEvent<T> {
    public static final int NETWORK_WRONG_CODE = -1;
    public static final int EVERYTHING_RIGHT = 200;

    protected String id;
    protected boolean ok;
    protected int code;
    protected T data;

    public BaseEvent(@Nullable String id, @NonNull int code, @Nullable T value) {
        this.id = id;
        this.ok = null != value;
        this.code = code;
        this.data = value;
    }

    public BaseEvent(@Nullable String id, boolean ok) {
        this.id = id;
        this.ok = ok;
    }

    public BaseEvent setEvent(@NonNull int code, @Nullable T value) {
        this.code = code;
        this.data = value;
        this.ok = null != value;
        return this;
    }

    /**
     * 获取返回码详情
     *
     * @return 描述信息
     */
    public String getCodeDescribe() {
        switch (code) {
            case -1:
                return "可能是网络未连接，或者数据转化失败";
            case 200:
            case 201:
                return "请求成功，或执行成功。";
            case 400:
                return "参数不符合 API 的要求、或者数据格式验证没有通过";
            case 401:
                return "用户认证失败，或缺少认证信息，比如 access_token 过期，或没传，可以尝试用 refresh_token 方式获得新的 access_token";
            case 403:
                return "当前用户对资源没有操作权限";
            case 404:
                return "资源不存在";
            case 500:
                return "服务器异常";
            case 402:
                return "用户尚未登录";
            default:
                return "未知异常(" + code + ")";
        }
    }

    public String getId() {
        return id;
    }

    public boolean isOk() {
        return ok;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
