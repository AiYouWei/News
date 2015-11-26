package com.news.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.TYDaily.R;
import com.news.entities.SearchType;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 多布局Adapter
 *
 * @author slioe shu
 */
public class NewsSearchAdapter extends BaseListAdapter<SearchType> {

    public NewsSearchAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, SearchType data) {
        if (TextUtils.isEmpty(data.getNid())) {
            holder.setViewVisibility(R.id.tvTime, View.GONE);
            holder.setViewVisibility(R.id.ivDel, View.VISIBLE);
        } else {
            holder.setViewVisibility(R.id.tvTime, View.VISIBLE);
            holder.setViewVisibility(R.id.ivDel, View.GONE);
            holder.setText(R.id.tvTime, data.getCreatetime());
        }
        holder.setText(R.id.tvTitle, data.getTitle());
    }
}
