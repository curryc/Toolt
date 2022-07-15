package com.curry.toolt.adpter.mutitype;

import com.curry.toolt.base.BaseViewProvider;

import java.util.List;

/**
 * 类型池,在这里实现类型和provider的绑定
 */
public interface TypePool {
    void register(Class<?> clx, BaseViewProvider provider);
    List<BaseViewProvider> getProviders();
    BaseViewProvider getProvider(int index);
    BaseViewProvider getProvider(Class<?> clx);
    int indexOf(Class<?> clx);
}
