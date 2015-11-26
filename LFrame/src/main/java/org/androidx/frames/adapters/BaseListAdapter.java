package org.androidx.frames.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidx.frames.core.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter基类
 *
 * @author slioe shu
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context context;
    protected int position;
    private int layoutId;
    protected List<T> datas;

    public BaseListAdapter(int layoutId) {
        this(null, layoutId, null);
    }

    public BaseListAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public BaseListAdapter(Context context, int layoutId, List<T> datas) {
        this.datas = new ArrayList<>();
        this.context = context;
        this.layoutId = layoutId;
        if (datas != null) {
            this.datas.addAll(datas);
        }
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        T t = null;
        if (datas != null) {
            t = datas.get(position);
        }
        return t;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UniversalViewHolder holder = getViewHolder(position, convertView, parent);
        this.position = position;
        setViewValue(holder, getItem(position));
        return holder.getConvertView();
    }

    public int getPosition() {
        return position;
    }

    public abstract void setViewValue(UniversalViewHolder holder, T data);

    private UniversalViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return UniversalViewHolder.getInstance(context, convertView, parent, layoutId, position);
    }

    /**
     * 设置集合数据
     *
     * @param datas 数据集合
     */
    public void setData(@NonNull List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
    }

    /**
     * 添加一条数据
     *
     * @param data 一条数据
     */
    public void add(@NonNull T data) {
        datas.add(data);
    }

    /**
     * 添加一条数据到指定位置
     *
     * @param index 数据添加的位置
     * @param data  一条数据
     */
    public void add(int index, @NonNull T data) {
        datas.add(index, data);
    }

    /**
     * 添加集合数据
     *
     * @param datas 数据集合
     */
    public void addAll(@NonNull List<T> datas) {
        this.datas.addAll(datas);
    }

    /**
     * 添加集合数据到指定位置
     *
     * @param position 数据添加的位置
     * @param datas    数据集合
     */
    public void addAll(int position, @NonNull List<T> datas) {
        this.datas.addAll(position, datas);
    }

    /**
     * 获取列表中所有数据
     *
     * @return 列表中所有数据
     */
    public List<T> getData() {
        return datas;
    }

    /**
     * 删除数据
     *
     * @param t 删除的数据
     */
    public void remove(@NonNull T t) {
        datas.remove(t);
    }

    /**
     * 清空所有数据
     */
    public void removeAll() {
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }

    /**
     * 删除集合数据
     *
     * @param datas 数据集合
     */
    public void removeAll(@NonNull List<T> datas) {
        if (this.datas != null) {
            this.datas.removeAll(datas);
        }
    }
}
