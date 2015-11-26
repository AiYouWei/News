package com.news.adapters;

import android.content.Context;
import android.view.View;

import com.TYDaily.R;
import com.news.activities.ReplyCommentActivity.Category;
import com.news.entities.ReplyCommentType;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * 评论我的/回复我的adapter
 *
 * @author slioe shu
 */
public class ReplyCommentAdapter extends BaseListAdapter<ReplyCommentType> {
    private Category category;

    public ReplyCommentAdapter(Context context, int layoutId, Category category) {
        super(context, layoutId);
        this.category = category;
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, ReplyCommentType data) {
        switch (category) {
            case REPLY:
                holder.setViewVisibility(R.id.tvContent, View.GONE);
                break;
            case COMMENT:
                holder.setViewVisibility(R.id.tvContent, View.VISIBLE);
                holder.setText(R.id.tvContent, "回复：" + data.getComtcontent());
                break;
            default:
                break;
        }
        holder.setText(R.id.tvTitle, "原文：" + data.getNtitle());
        holder.setText(R.id.tvTime, data.getComttime());
    }
}
