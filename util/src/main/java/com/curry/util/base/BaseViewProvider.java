package com.curry.util.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.curry.util.adpter.RecyclerViewHolder;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-03 16:32
 * @description: 每种数据都对应一个Provider在这里实现数据展示的操作
 **/
public abstract class BaseViewProvider<T>{
    protected Context mContext;
    private LayoutInflater inflater;
    private int mLayoutId;

    /**
     * 通过Contest和RecyclerView.ViewHolder的LayoutID
     * @param mContext　用来获取inflater
     * @param mLayoutId　展示一种数据的布局
     */
    public BaseViewProvider(Context mContext, int mLayoutId) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.mLayoutId = mLayoutId;
    }

    /**
     * 获取一个RecyclerViewHolder, RecyclerView需要
     * @param parent
     * @return
     */
    public RecyclerViewHolder createViewHolder(ViewGroup parent){
        View view = inflater.inflate(mLayoutId, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        onViewHolderCreated(holder);
        return holder;
    }

    /**
     * 当创建好RecyclerViewHolder后,可能调用这个方法
     * @param holder
     */
    protected void onViewHolderCreated(RecyclerViewHolder holder){}

    /**
     * 绑定数据,通过一个RecyclerViewHolder和一种数据来绑定
     * @param holder 这个RecyclerViewHolder
     * @param data 需要绑定的数据
     */
    public abstract void bindView(RecyclerViewHolder holder, T data);
}
