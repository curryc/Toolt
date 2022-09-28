package com.curry.function.normal.memorial;

import android.content.Context;
import android.graphics.Color;
import com.curry.function.R;
import com.curry.util.adpter.RecyclerViewHolder;
import com.curry.util.base.BaseViewProvider;
import com.curry.util.time.Dater;
import com.curry.util.time.Formatter;
import com.curry.util.view.CornerLabel;

import java.util.Date;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-28 15:00
 * @description:
 **/
public class MemorialDayProvider extends BaseViewProvider<MemorialDay> {
    /**
     * 通过Contest和RecyclerView.ViewHolder的LayoutID
     *
     * @param mContext 　用来获取inflater
     */
    public MemorialDayProvider(Context mContext) {
        super(mContext, R.layout.memorial_day_day);
    }

    @Override
    public void bindView(RecyclerViewHolder holder, MemorialDay data) {
        holder.setText(R.id.name, data.getName());
        holder.setText(R.id.date, Formatter.getSimpleDateString(data.getDate()));
        CornerLabel label = holder.getViewById(R.id.label);
        label.set(getColor(data), getLabelText(data));
    }

    private int getColor(MemorialDay data) {
        switch (data.getImportance()) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.RED;
            case 4:
                return Color.BLACK;
            default:
                return Color.LTGRAY;
        }
    }

    private String getLabelText(MemorialDay data) {
        if (Dater.calculateDifference(data.getDate(), new Date()) == 0) {
            return "today!";
        }
        if (data.getDate().before(new Date())) {
            // 这个日子在今天之前
            return "before " + Dater.calculateDifference(data.getDate(), new Date());
        } else {
            //这个日子在今天之后
            return "after " + Dater.calculateDifference(data.getDate(), new Date());
        }
    }
}
