package com.news.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.TYDaily.R;
import com.news.entities.NewsBType.Item.NewsItem;

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;

/**
 * @author slioe shu
 */
public class MoreSubjectAdapter extends BaseListAdapter<NewsItem> {
    private FinalBitmap loader;

    public MoreSubjectAdapter(Context context) {
        super(context, R.layout.subject_detail_child);
        this.loader = FinalBitmap.create(context);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, NewsItem data) {
        ImageView iv = holder.findView(R.id.ivContent);
        loader.display(iv, data.getNews_icon());
        holder.setText(R.id.tvTitle, data.getNews_title());
        holder.setText(R.id.tvContent, data.getNews_subtitle());
    }
}
