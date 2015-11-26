package com.news.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.entities.ColumnsType;

import java.util.List;

public class DragAdapter extends BaseAdapter {
    /**
     * TAG
     */
    private final static String TAG = "DragAdapter";
    private boolean isItemShow = false;
    private final Context context;
    private int holdPosition;
    private boolean isChanged = false;
    private boolean isListChanged = false;
    boolean isVisible = true;
    public List<ColumnsType> channelList;
    public int remove_position = -1;

    public DragAdapter(Context context, List<ColumnsType> channelList) {
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
        View view = View.inflate(context, R.layout.adapter_channel_item, null);
        TextView item_text = (TextView) view.findViewById(R.id.text_item);
        view.findViewById(R.id.icon_new).setVisibility(View.VISIBLE);
        ColumnsType channel = getItem(position);
        item_text.setText(channel.getName());
        if (position == 0) {
            // item_text.setTextColor(context.getResources().getColor(R.color.black));
            item_text.setEnabled(false);
            view.findViewById(R.id.icon_new).setVisibility(View.INVISIBLE);
        }
        if (isChanged && (position == holdPosition) && !isItemShow) {
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
            isChanged = false;
        }
        if (!isVisible && (position == -1 + channelList.size())) {
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
        }
        if (remove_position == position) {
            item_text.setText("");
        }
        return view;
    }

    /**
     * ���Ƶ���б�
     */
    public void addItem(ColumnsType channel) {
        channelList.add(channel);
        isListChanged = true;
        notifyDataSetChanged();
    }

    /**
     * �϶����Ƶ������
     */
    public void exchange(int dragPostion, int dropPostion) {
        holdPosition = dropPostion;
        ColumnsType dragItem = getItem(dragPostion);
        if (dragPostion < dropPostion) {
            channelList.add(dropPostion + 1, dragItem);
            channelList.remove(dragPostion);
        } else {
            channelList.add(dropPostion, dragItem);
            channelList.remove(dragPostion + 1);
        }
        isChanged = true;
        isListChanged = true;
        notifyDataSetChanged();
    }

    public List<ColumnsType> getChannnelLst() {
        return channelList;
    }

    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
    }

    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        isListChanged = true;
        notifyDataSetChanged();
    }

    /**
     * ����Ƶ���б�
     */
    public void setListDate(List<ColumnsType> list) {
        channelList = list;
    }

    /**
     * ��ȡ�Ƿ�ɼ�
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * �����Ƿ����ı�
     */
    public boolean isListChanged() {
        return isListChanged;
    }

    /**
     * �����Ƿ�ɼ�
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * ��ʾ���µ�ITEM
     */
    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }
}