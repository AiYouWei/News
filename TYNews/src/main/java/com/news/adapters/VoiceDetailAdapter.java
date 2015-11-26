package com.news.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.TYDaily.R;
import com.news.entities.VoiceComtType;
import com.news.fragments.TabVoiceFragment.Category;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 民声adapter
 *
 * @author slioe shu
 */
public class VoiceDetailAdapter extends BaseListAdapter<VoiceComtType> {
    private Category category;

    public VoiceDetailAdapter(Context context, int layoutId, Category category) {
        super(context, layoutId);
        this.category = category;
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, VoiceComtType data) {
        if (category != Category.CLUE) {
            holder.setText(R.id.tvContent, data.getComtcontent());
            if (TextUtils.isEmpty(data.getComtname())) {
                holder.setViewVisibility(R.id.tvTitle, View.GONE);
            } else {
                holder.setViewVisibility(R.id.tvTitle, View.VISIBLE);
                holder.setText(R.id.tvTitle, data.getComtname());
            }
        } else {
            holder.setViewVisibility(R.id.tvTitle, View.GONE);
            holder.setText(R.id.tvContent, data.getComtcontent());
        }
    }
}
