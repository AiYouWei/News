package com.news.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.TYDaily.R;
import com.news.entities.VoiceType;
import com.news.fragments.TabVoiceFragment.Category;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 民声对应的adapter
 *
 * @author slioe shu
 */
public class PeopleVoiceAdapter extends BaseListAdapter<VoiceType> {
    private Category category;

    public PeopleVoiceAdapter(Context context, int layoutId, Category category) {
        super(context, layoutId);
        this.category = category;
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, VoiceType data) {
        switch (category) {
            case CLUE:
                holder.setViewVisibility(R.id.tvCategory, View.GONE);
                break;
            case URBAN:
                holder.setViewVisibility(R.id.tvCategory, View.VISIBLE);
                holder.setText(R.id.tvCategory, data.getLabel());
                break;
            case SUP:
                holder.setViewVisibility(R.id.tvCategory, View.VISIBLE);
                holder.setText(R.id.tvCategory, data.getLabel());
                break;
            default:
                break;
        }
        if (TextUtils.isEmpty(data.getImg1())) {
            holder.setViewVisibility(R.id.ivContent, View.GONE);
        } else {
            holder.setViewVisibility(R.id.ivContent, View.VISIBLE);
            holder.setImageRes(R.id.ivContent, data.getImg1());
        }
        holder.setText(R.id.tvContent, data.getTitle());
    }
}
