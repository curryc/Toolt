package com.curry.toolt.provider;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.curry.function.App;
import com.curry.function.bean.Function;
import com.curry.toolt.R;
import com.curry.toolt.base.BaseViewProvider;
import com.curry.toolt.base.RecyclerViewHolder;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-07-12 08:57
 * @description: 在全部应用中展示所有功能
 **/
public class FunctionProvider extends BaseViewProvider<Function> {
    /**
     * 通过Contest和RecyclerView.ViewHolder的LayoutID
     *
     * @param mContext 　用来获取inflater
     */
    public FunctionProvider(Context mContext) {
        super(mContext, R.layout.holder_func);
    }

    @Override
    public void bindView(RecyclerViewHolder holder, Function data) {
        holder.setText(R.id.func_title, data.getTitle());
        if (App.getDotColor(data.getLevel()) == -1) {
            holder.getViewById(R.id.func_dot).setVisibility(View.GONE);
        } else {
            holder.setSrc(R.id.func_dot, App.getDotColor(data.getLevel()));
        }
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, data.getStarter());
                mContext.startActivity(i);
            }
        }, holder.getRootView());
    }
}
