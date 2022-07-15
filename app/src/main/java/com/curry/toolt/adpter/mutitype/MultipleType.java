package com.curry.toolt.adpter.mutitype;

import com.curry.toolt.base.BaseViewProvider;
import com.curry.util.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-03 17:06
 * @description: 完整的实现这个接口,用来绑定数据和一个provider,通过provider来展示这个数据
 * @see {@link com.curry.toolt.base.BaseViewProvider}
 **/
public class MultipleType implements TypePool{
    private List<Class<?>> data;
    private List<BaseViewProvider> providers;

    public MultipleType(){
        data = new ArrayList<>();
        providers = new ArrayList<>();
    }

    /**
     * 用来绑定一个类型和一个provider
     * @param clx 数据
     * @param provider 数据展示的provider
     */
    @Override
    public void register(Class<?> clx, BaseViewProvider provider) {
        if(!data.contains(clx)){
            data.add(clx);
            providers.add(provider);
        }else{
            providers.set(data.indexOf(clx), provider);
            Logger.v("you reset a provider for " + clx.getSimpleName());
        }
    }

    /**
     * 获取所有provider
     * @return
     */
    @Override
    public List<BaseViewProvider> getProviders() {
        return providers;
    }

    /**
     * 通过索引获取一个provider
     * @param index 索引
     * @return 返回的provider
     */
    @Override
    public BaseViewProvider getProvider(int index) {
        return providers.get(index);
    }

    /**
     * 通过一个类型获取一个provider
     * @param clx 数据类型
     * @return 这种数据对应的provider
     */
    @Override
    public BaseViewProvider getProvider(Class<?> clx) {
        return getProvider(data.indexOf(clx));
    }

    /**
     * 获取一个数据类型的索引
     * @param clx 数据的类型
     * @return 索引
     */
    @Override
    public int indexOf(Class<?> clx) {
        int index = data.indexOf(clx);
        if(index > 0) return index;
        else{
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).isAssignableFrom(clx)){
                    return i;
                }
            }
        }
        Logger.e("can't find the index of this class: " + clx.getSimpleName());
        return -1;
    }
}
