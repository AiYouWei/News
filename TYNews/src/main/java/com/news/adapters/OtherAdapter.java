package com.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.entities.ColumnsType;

import java.util.List;

public class OtherAdapter extends BaseAdapter {
    private final Context context;
    public List<ColumnsType> channelList;
    private TextView item_text;
    /**
     * 锟角凤拷杉锟?
     */
    boolean isVisible = true;
    /**
     * 要删锟斤拷锟斤拷position
     */
    public int remove_position = -1;

    public OtherAdapter(Context context, List<ColumnsType> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public ColumnsType getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_channel_item, null);
        item_text = (TextView) view.findViewById(R.id.text_item);
        ColumnsType channel = getItem(position);
        item_text.setText(channel.getName());
        if (!isVisible && (position == -1 + channelList.size())) {
            item_text.setText("");
        }
        if (remove_position == position) {
            item_text.setText("");
        }
        return view;
    }

    /**
     * 锟斤拷取频锟斤拷锟叫憋拷
     */
    public List<ColumnsType> getChannnelLst() {
        return channelList;
    }

    /**
     * 锟斤拷锟狡碉拷锟斤拷斜锟?
     */
    public void addItem(ColumnsType channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /**
     * 锟斤拷锟斤拷删锟斤拷锟斤拷position
     */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
        // notifyDataSetChanged();
    }

    /**
     * 删锟斤拷频锟斤拷锟叫憋拷
     */
    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }

    /**
     * 锟斤拷锟斤拷频锟斤拷锟叫憋拷
     */
    public void setListDate(List<ColumnsType> list) {
        channelList = list;
    }

    /**
     * 锟斤拷取锟角凤拷杉锟?
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 锟斤拷锟斤拷锟角凤拷杉锟?
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
