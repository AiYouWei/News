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
import com.news.entities.ContentType;
import com.news.entities.ContentType.Pages;

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.base.BaseActivity;

import java.util.List;

/**
 * @author slioe shu
 */
public class PaperContentAdapter extends BaseExpandableListAdapter {
    private List<ContentType> datas;
    private Context context;
    private FinalBitmap loader;

    public PaperContentAdapter(Context context) {
        this.context = context;
        loader = FinalBitmap.create(context);
    }

    public void setDatas(@NonNull List<ContentType> datas) {
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
        return datas.get(groupPosition).getArticles().size();
    }

    @Override
    public ContentType getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public Pages getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).getArticles().get(childPosition);
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
            convertView = View.inflate(context, R.layout.adapter_paper_title, null);
            gh = new GroupHolder();
            gh.tvName = (TextView) convertView.findViewById(R.id.tvName);
            gh.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            convertView.setTag(gh);
        } else {
            gh = (GroupHolder) convertView.getTag();
        }
        ContentType ct = getGroup(groupPosition);
        gh.tvName.setText(ct.getPageName());
        gh.tvNum.setText(ct.getPageNo());
//        convertView.setClickable(true);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder ch;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_paper_content, null);
            ch = new ChildHolder();
            ch.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            ch.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            ch.ivIcon = (ImageView) convertView.findViewById(R.id.ivContent);
            convertView.setTag(ch);
        } else {
            ch = (ChildHolder) convertView.getTag();
        }
        Pages pages = getChild(groupPosition, childPosition);
        ch.tvTitle.setText(pages.getTitle().trim());
        if (TextUtils.isEmpty(pages.getSubtitle())) {
            ch.tvContent.setVisibility(View.GONE);
        } else {
            ch.tvContent.setVisibility(View.VISIBLE);
            ch.tvContent.setText(pages.getSubtitle().trim());
        }
        if (TextUtils.isEmpty(pages.getImg())) {
            ch.ivIcon.setVisibility(View.GONE);
        } else {
            ch.ivIcon.setVisibility(View.VISIBLE);
            loader.display(ch.ivIcon, pages.getImg());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Bundle normal = new Bundle();
        normal.putString("ID", getChild(groupPosition, childPosition).getId());
        ((BaseActivity) context).startActivityByUri(TYUris.NEWS_IMAGE, normal);
        return false;
    }

    private class ChildHolder {
        TextView tvTitle;
        TextView tvContent;
        ImageView ivIcon;
    }

    private class GroupHolder {
        TextView tvNum, tvName;
    }

}
