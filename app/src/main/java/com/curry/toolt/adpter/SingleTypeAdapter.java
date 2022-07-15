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
 * @create: 2022-07-12 08:49
 * @description: 单一类型的适配器，只能显示一种数据
 **/
public class SingleTypeAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements TypePool{
    private TypePool mTypePool;
    private List<Object> mItems;

    public SingleTypeAdapter() {
        super();
        mItems = new ArrayList<>();
        mTypePool = new MultipleType();
    }

    @Override
    public int getItemViewType(int position) {
        assert mItems != null && mItems.get(position) != null;
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

    public void addData(List<?> data){
        mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        mItems.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        assert mItems != null;
        return mItems.size();
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
