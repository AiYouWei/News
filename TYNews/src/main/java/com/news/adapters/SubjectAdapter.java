package com.news.adapters;

import android.content.Context;

import com.TYDaily.R;
import com.news.entities.SubjectType;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 专题列表适配器
 *
 * @author slioe shu
 */
public class SubjectAdapter extends BaseListAdapter<SubjectType> {

    public SubjectAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, SubjectType data) {
        holder.setText(R.id.tvDesc, data.getSpecialtitle());
        holder.setImageRes(R.id.ivContent, data.getSpecialimag());
    }
}
