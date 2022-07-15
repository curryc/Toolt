package com.curry.toolt.adpter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.curry.toolt.adpter.mutitype.MultipleType;
import com.curry.toolt.adpter.mutitype.TypePool;
import com.curry.toolt.base.BaseViewProvider;
import com.curry.toolt.base.RecyclerViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-03 16:43
 * @description: 一个带有四个部分的Adapter, 这其中包含一个头部, 一个中部, 然后是需要大量展示的数据, 然后是尾部
 * @see {@link com.curry.toolt.base.BaseViewProvider}
 **/
public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements TypePool {
    private final String TAG = "homeAdapter";

    private TypePool mTypePool;
    private List<Object> mItems;

    private boolean hasHeader;
    private boolean hasFooter;
    private boolean hasMid;

    public HeaderFooterAdapter() {
        super();
        mTypePool = new MultipleType();
        mItems = new ArrayList<>();
        hasHeader = hasFooter = hasMid = false;
    }

    @Override
    public int getItemViewType(int position) {
        assert mItems != null;
        return indexOf(mItems.get(position).getClass());
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BaseViewProvider provider = getProvider(viewType);
        return provider.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        BaseViewProvider provider = getProvider(mItems.get(position).getClass());
        provider.bindView(holder, mItems.get(position));
    }

    @Override
    public int getItemCount() {
        assert mItems != null;
        return mItems.size();
    }


    /**
     * 注册头部
     *
     * @param object   头部的数据
     * @param provider 头部的provider
     */
    public void registerHeader(Object object, BaseViewProvider provider) {
        mTypePool.register(object.getClass(), provider);
        if(hasHeader){
            mItems.set(0, object);
        }else{
            mItems.add(0, object);
        }
        hasHeader = true;
        notifyDataSetChanged();
    }

    /**
     * 注册尾部
     *
     * @param object   尾部的数据
     * @param provider 尾部的provider
     */
    public void registerFooter(Object object, BaseViewProvider provider) {
        hasFooter = true;
        mItems.add(mItems.size(), object);
        mTypePool.register(object.getClass(), provider);
        notifyDataSetChanged();
    }

    /**
     * 注册中部
     *
     * @param object   中部的数据
     * @param provider 中部的provider
     */
    public void registerMid(Object object, BaseViewProvider provider) {
        hasMid = true;
        mItems.add(1, object);
        mTypePool.register(object.getClass(), provider);
        notifyDataSetChanged();
    }

    /**
     * 想正式数据中添加数据
     *
     * @param data 数据
     */
    public void addData(List<?> data) {
        if (hasFooter) {
            mItems.addAll(mItems.size() - 1, data);
        } else {
            mItems.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 清除正式数据中的数据
     */
    public void clearData() {
        if (mItems == null) return;
        int start = 0, end = mItems.size();
        if (hasHeader) start++;
        if (hasMid) start++;
        if (hasFooter) end--;
        mItems.removeAll(mItems.subList(start, end));
        notifyDataSetChanged();
    }

    @Override
    public void register(Class<?> clx, BaseViewProvider provider) {
        mTypePool.register(clx, provider);
    }

    @Override
    public List<BaseViewProvider> getProviders() {
        return mTypePool.getProviders();
    }

    @Override
    public BaseViewProvider getProvider(int index) {
        return mTypePool.getProvider(index);
    }

    @Override
    public BaseViewProvider getProvider(Class<?> clx) {
        return mTypePool.getProvider(clx);
    }

    @Override
    public int indexOf(Class<?> clx) {
        return mTypePool.indexOf(clx);
    }
}
