package org.androidx.frames.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.widget.BaseAdapter;

import org.androidx.frames.core.UniversalViewHolder;
import org.androidx.frames.entity.BaseMultiType;

import java.util.List;

/**
 * 多Item布局的Adapter
 *
 * @author slioe shu
 */
public abstract class BaseMultiAdapter<T extends BaseMultiType> extends BaseAdapter {
    protected Context context;
    protected List<T> datas;
    protected SparseArray<Integer> views;
    private SparseArray<UniversalViewHolder> holders;

    public BaseMultiAdapter(SparseArray<Integer> views) {
        this(null, views, null);
    }

    public BaseMultiAdapter(Context context, SparseArray<Integer> views) {
        this(context, views, null);
    }

    public BaseMultiAdapter(Context context, SparseArray<Integer> views, List<T> datas) {
        holders = new SparseArray<>();
        this.context = context;
        this.views = views;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if (datas == null) {
            return 0;
        } else {
            return datas.size();
        }
    }

    @Override
    public T getItem(int position) {
        if (datas == null) {
            return null;
        }
        T t = null;
        try {
            t = datas.get(position);
        } catch (Exception e) {
        }
        return t;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return views.size();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getViewType();
    }
    // TODO: 2015-09-06 与万能ViewHolder结合

    public void setData(List<T> data) {
        datas = data;
    }

    public void add(T data) {
        if (datas != null && data != null) {
            datas.add(data);
        }
    }

    public void add(int index, T data) {
        if (datas != null && data != null) {
            datas.add(index, data);
        }
    }

    public void addAll(List<T> data) {
        if (data == null) {
            return;
        }
        if (datas == null) {
            datas = data;
        } else {
            datas.addAll(data);
        }
    }

    public void addAll(int position, List<T> data) {
        if (datas != null && data != null) {
            datas.addAll(position, data);
        }
    }

    public List<T> getData() {
        return datas;
    }

    public void remove(T t) {
        if (datas != null) {
            datas.remove(t);
        }
    }

    public void removeAll() {
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }

    public void removeAll(List<T> data) {
        if (datas != null) {
            datas.removeAll(data);
        }
    }
}
