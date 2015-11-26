package com.news.adapters;

import android.content.Context;

import com.TYDaily.R;
import com.news.entities.GovernmentType;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;


/**
 * 县区市局适配器
 *
 * @author slioe shu
 */
public class GovernmentAdapter extends BaseListAdapter<GovernmentType> {

    public GovernmentAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, GovernmentType data) {
        holder.setText(R.id.tvDesc, data.getName());
        holder.setImageRes(R.id.ivLogo, data.getIcon());
    }
}
