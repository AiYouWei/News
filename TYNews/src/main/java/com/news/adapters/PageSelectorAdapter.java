package com.news.adapters;

import android.content.Context;

import com.TYDaily.R;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

import java.util.List;

/**
 * @author slioe shu
 */
public class PageSelectorAdapter extends BaseListAdapter<String> {
    public PageSelectorAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, String data) {
        holder.setText(R.id.tvPage, data);
    }
}
