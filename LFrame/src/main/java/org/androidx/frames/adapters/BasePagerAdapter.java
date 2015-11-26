package org.androidx.frames.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * ViewPager的Adapter的基类
 *
 * @author slioe shu
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    private List<T> datas;

    public BasePagerAdapter() {
    }

    public BasePagerAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取当前位置的Item
     *
     * @param positon 当前位置的postion
     * @return 当前位置的Item
     */
    protected T getItem(int positon) {
        return datas.get(positon);
    }

    /**
     * 设置数据
     *
     * @param datas 数据集合
     */
    public void setData(List<T> datas) {
        this.datas = datas;
    }
}
