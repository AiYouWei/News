package org.androidx.frames.core;

import android.content.Context;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import net.tsz.afinal.FinalBitmap;

/**
 * 通用的ViewHolder
 *
 * @author slioe shu
 */
public class UniversalViewHolder {
    private SparseArray<View> views;
    private View convertView;
    private static int position;
    private FinalBitmap finalBitmap;

    public UniversalViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        views = new SparseArray<>();
        finalBitmap = FinalBitmap.create(context);
        convertView = View.inflate(context, layoutId, null);
        convertView.setTag(this);

    }

    public static UniversalViewHolder getInstance(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        UniversalViewHolder.position = position;
        if (convertView == null) {
            return new UniversalViewHolder(context, parent, layoutId, position);
        }
        return (UniversalViewHolder) convertView.getTag();
    }

    public int getPosition() {
        return position;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 获取View
     *
     * @param viewId 控件ID
     * @param <T>    View对象
     * @return ID对应的View
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    //====控件赋值======

    /**
     * 设置TextView的内容
     *
     * @param viewId TextView的ID
     * @param text   设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setText(int viewId, String text) {
        TextView view = findView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置TextView的内容,格式化的HTMl字体
     *
     * @param viewId TextView的ID
     * @param text   设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setTextFromHtml(int viewId, String text) {
        TextView view = findView(viewId);
        view.setText(Html.fromHtml(text));
        return this;
    }

    /**
     * 设置TextView的内容和点击事件
     *
     * @param viewId   TextView的ID
     * @param text     设置的内容
     * @param listener 点击事件
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setText(int viewId, String text, View.OnClickListener listener) {
        TextView view = findView(viewId);
        view.setText(text);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * @param listener 点击事件
     * @param viewId   View的ID
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setOnClickListener(View.OnClickListener listener, int... viewId) {
        for (int id : viewId) {
            View view = findView(id);
            view.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置TextView左边的图片
     *
     * @param viewId TextViewId
     * @param resId  图片id
     * @return ViewHolder
     */
    public UniversalViewHolder setTextDrawableLeft(int viewId, int resId) {
        TextView view = findView(viewId);
        return this;
    }

    /**
     * 设置ImageView的内容
     *
     * @param viewId TextView的ID
     * @param resId  设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setImageRes(int viewId, int resId) {
        ImageView view = findView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置ImageView的内容
     *
     * @param viewId TextView的ID
     * @param resId  设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setImageRes(int viewId, int resId, OnClickListener listener) {
        ImageView view = findView(viewId);
        view.setImageResource(resId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置ImageView的内容
     *
     * @param viewId TextView的ID
     * @param resId  设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setImageBack(int viewId, int resId) {
        ImageView view = findView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置ImageView的内容
     *
     * @param viewId TextView的ID
     * @param url    设置的内容
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setImageRes(int viewId, String url) {
        ImageView view = findView(viewId);
        finalBitmap.display(view, url);
        return this;
    }

    /**
     * 设置ImageView的内容和点击事件
     *
     * @param viewId   TextView的ID
     * @param url      设置的内容
     * @param listener 点击事件
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setImageRes(int viewId, String url, View.OnClickListener listener) {
        ImageView view = findView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public UniversalViewHolder setCheckText(int viewId, String text) {
        CheckBox cb = findView(viewId);
        cb.setText(text);
        return this;
    }

    public UniversalViewHolder setChecked(int viewId, boolean isChecked) {
        CheckBox cb = findView(viewId);
        cb.setChecked(isChecked);
        return this;
    }

    public UniversalViewHolder setViewVisibility(int viewId, int visibility) {
        findView(viewId).setVisibility(visibility);
        return this;
    }

    public UniversalViewHolder setViewBackground(int viewResID, int drawableResID) {
        findView(viewResID).setBackgroundResource(drawableResID);
        return this;
    }

    /**
     * 设置RatingBar的评分
     *
     * @param viewId RatingBar的ID
     * @param rating 评分
     * @return UniversalViewHolder
     */
    public UniversalViewHolder setRating(int viewId, int rating) {
        RatingBar view = findView(viewId);
        view.setRating(rating);
        return this;
    }
}
