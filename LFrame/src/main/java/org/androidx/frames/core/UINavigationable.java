package org.androidx.frames.core;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 界面标题栏接口
 *
 * @author slioe shu
 * @since 2015-08-30
 */
public interface UINavigationable {

    /**
     * 添加标题栏左边的文字
     *
     * @param text     设置的文字
     * @param listener 文字的事件响应
     */
    void addTextToLeft(String text, OnClickListener listener);

    /**
     * 添加标题栏中间的文字
     *
     * @param text     设置的文字
     * @param listener 文字的事件响应
     */
    void addTextToMiddle(String text, OnClickListener listener);

    /**
     * 添加标题栏右边的文字
     *
     * @param text     设置的文字
     * @param listener 文字的事件响应
     */
    void addTextToRight(String text, OnClickListener listener);

    /**
     * 添加标题栏中间的文字，不带点击事件
     *
     * @param text 设置的文字
     */
    void addDefaultMiddle(String text);

    /**
     * 添加标题栏左边的图片
     *
     * @param imgResID 设置的图片
     * @param listener 图片的事件响应
     */
    void addImageToLeft(int imgResID, OnClickListener listener);

    /**
     * 添加标题栏中间的图片
     *
     * @param imgResID 设置的图片
     * @param listener 图片的事件响应
     */
    void addImageToMiddle(int imgResID, OnClickListener listener);

    /**
     * 添加标题栏右边的图片
     *
     * @param imgResID 设置的图片
     * @param listener 图片的事件响应
     */
    void addImageToRight(int imgResID, OnClickListener listener);

    /**
     * 添加标题栏左边的事件响应，默认为退出当前UI事件
     *
     * @param listener 图片的事件响应
     */
    void addDefaultLeft(OnClickListener listener);

    /**
     * 添加标题栏左边的自定义控件
     *
     * @param view     添加的自定义控件
     * @param listener 控件的事件响应
     */
    void addViewToLeft(View view, OnClickListener listener);

    /**
     * 添加标题栏中间的自定义控件
     *
     * @param view     添加的自定义控件
     * @param listener 控件的事件响应
     */
    void addViewToMiddle(View view, OnClickListener listener);

    /**
     * 添加标题栏右边的自定义控件
     *
     * @param view     添加的自定义控件
     * @param listener 控件的事件响应
     */
    void addViewToRight(View view, OnClickListener listener);

}
