package com.news.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.entities.NewsBType.Item;
import com.news.entities.NewsBType.Item.NewsItem;

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.base.BaseActivity;

import java.util.List;

/**
 * @author slioe shu
 */
public class SubjectDetailAdapter extends BaseExpandableListAdapter {
    private List<Item> datas;
    private Context context;
    private FinalBitmap loader;

    public SubjectDetailAdapter(Context context) {
        this.context = context;
        loader = FinalBitmap.create(context);
    }

    public void setDatas(@NonNull List<Item> datas) {
        this.datas = datas;
    }

    @Override
    public int getGroupCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (datas == null) {
            return 0;
        }
        return datas.get(groupPosition).getItem_news().size();
    }

    @Override
    public Item getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public NewsItem getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).getItem_news().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder gh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.subject_detail_group, null);
            gh = new GroupHolder();
            gh.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(gh);
        } else {
            gh = (GroupHolder) convertView.getTag();
        }
        Item ct = getGroup(groupPosition);
        gh.tvTitle.setText(ct.getItem_title());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder ch;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.subject_detail_child, null);
            ch = new ChildHolder();
            ch.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            ch.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            ch.ivContent = (ImageView) convertView.findViewById(R.id.ivContent);
            convertView.setTag(ch);
        } else {
            ch = (ChildHolder) convertView.getTag();
        }
        NewsItem ni = getChild(groupPosition, childPosition);
        ch.tvTitle.setText(ni.getNews_title().trim());
        if (TextUtils.isEmpty(ni.getNews_subtitle())) {
            ch.tvContent.setVisibility(View.GONE);
        } else {
            ch.tvContent.setVisibility(View.VISIBLE);
            ch.tvContent.setText(ni.getNews_subtitle().trim());
        }
        if (TextUtils.isEmpty(ni.getNews_icon())) {
            ch.ivContent.setVisibility(View.GONE);
        } else {
            ch.ivContent.setVisibility(View.VISIBLE);
            loader.display(ch.ivContent, ni.getNews_icon());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Bundle normal = new Bundle();
        normal.putString("ID", getChild(groupPosition, childPosition).getNews_id());
        ((BaseActivity) context).startActivityByUri(TYUris.NEWS_IMAGE, normal);
        return false;
    }

    private class ChildHolder {
        TextView tvTitle;
        TextView tvContent;
        ImageView ivContent;
    }

    private class GroupHolder {
        TextView tvTitle;
    }
}
