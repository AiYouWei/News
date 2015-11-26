package org.androidx.frames.core;

import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.androidx.frames.R;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.utils.DeviceUtil;
import org.androidx.frames.utils.ResUtil;
import org.androidx.frames.utils.ResUtil.ResType;
import org.androidx.frames.views.NavigationBarView;

/**
 * 给类型标题栏的实现，文字， 图片， 自定义
 *
 * @author slioe shu
 * @since 2015-08-31
 */
class UINavigationCategory {
    private static UINavigationCategory instance;
    private final static int SIZE = DeviceUtil.dp2px(6);

    enum Category {
        LEFT, MIDDLE, RIGHT
    }

    // TODO: 2015-08-31 标题栏类型:  图文， 多按钮， 自定义
    private UINavigationCategory() {
    }

    public static UINavigationCategory getInstance() {
        return instance == null ? instance = new UINavigationCategory() : instance;
    }

    /**
     * 为标题栏添加文字
     *
     * @param activity 添加文字的Activity
     * @param barView  标题栏控件
     * @param text     添加的文字
     * @param listener 标题栏点击事件
     * @param category 添加的位置
     */
    protected void addText(BaseActivity activity, NavigationBarView barView, String text, OnClickListener listener, Category category) {
        TextView tv = (TextView) View.inflate(activity, R.layout.view_navigation_text, null);
        tv.setText(text);
        tv.setTextColor(activity.getResources().getColor(ResUtil.getResIdByType(ResType.COLOR, "navi_text_color")));
        if (listener != null) {
            tv.setOnClickListener(listener);
        }
        switch (category) {
            case LEFT:
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtil.getDimenByName("navi_side_text"));
                tv.setPadding(SIZE * 2, SIZE, SIZE, SIZE);
                barView.addToLeft(tv);
                break;
            case MIDDLE:
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtil.getDimenByName("navi_middle_text"));
                tv.setPadding(SIZE, SIZE, SIZE, SIZE);
                barView.addToMiddle(tv);
                break;
            case RIGHT:
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtil.getDimenByName("navi_side_text"));
                tv.setPadding(SIZE, SIZE, SIZE * 2, SIZE);
                barView.addToRight(tv);
                break;
            default:
                break;
        }
    }

    /**
     * 为标题栏添加图片
     *
     * @param activity 添加图片的Activity
     * @param barView  标题栏控件
     * @param imgResID 添加的图片
     * @param listener 标题栏点击事件
     * @param category 添加的位置
     */
    protected void addImage(BaseActivity activity, NavigationBarView barView, int imgResID, OnClickListener listener, Category category) {
        ImageView iv = (ImageView) View.inflate(activity, R.layout.view_navigation_image, null);
        iv.setImageResource(imgResID);
        iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        if (listener != null) {
            iv.setOnClickListener(listener);
        }
        switch (category) {
            case LEFT:
                iv.setPadding(SIZE * 2, SIZE, SIZE, SIZE);
                barView.addToLeft(iv);
                break;
            case MIDDLE:
                iv.setPadding(SIZE, SIZE, SIZE, SIZE);
                barView.addToMiddle(iv);
                break;
            case RIGHT:
                iv.setPadding(SIZE, SIZE, SIZE * 2, SIZE);
                barView.addToRight(iv);
                break;
            default:
                break;
        }
    }

    /**
     * 为标题栏添加自定义控件
     *
     * @param activity 添加自定义控件的Activity
     * @param barView  标题栏控件
     * @param view     添加的自定义控件
     * @param listener 标题栏点击事件
     * @param category 添加的位置
     */
    protected void addView(BaseActivity activity, NavigationBarView barView, View view, OnClickListener listener, Category category) {
        if (listener != null) {
            view.setOnClickListener(listener);
        }
        switch (category) {
            case LEFT:
                view.setPadding(SIZE * 2, SIZE, SIZE, SIZE);
                barView.addToLeft(view);
                break;
            case MIDDLE:
                view.setPadding(SIZE, SIZE, SIZE, SIZE);
                barView.addToMiddle(view);
                break;
            case RIGHT:
                view.setPadding(SIZE, SIZE, SIZE * 2, SIZE);
                barView.addToRight(view);
                break;
            default:
                break;
        }
    }

}
