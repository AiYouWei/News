package com.news.adapters;

import android.content.Context;

import com.TYDaily.R;
import com.news.entities.CollectionType;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 我的收藏
 *
 * @author slioe shu
 */
public class MineMessageAdapter extends BaseListAdapter<CollectionType> {

    public MineMessageAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, CollectionType data) {
        holder.setText(R.id.tvTittle, data.getTitle());
        holder.setText(R.id.tvSubTittle, data.getContent());
        holder.setText(R.id.tvTime, data.getCreate_time());
    }
}
