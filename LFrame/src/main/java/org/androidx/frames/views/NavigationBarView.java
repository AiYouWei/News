package org.androidx.frames.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import org.androidx.frames.R;


/**
 * 通用标题栏控件
 *
 * @author slioe shu
 */
public class NavigationBarView extends RelativeLayout {
    private RelativeLayout rlLeft, rlMiddle, rlRight;

    public NavigationBarView(Context context) {
        super(context);
    }

    public NavigationBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rlLeft = (RelativeLayout) findViewById(R.id.rlLeft);
        rlMiddle = (RelativeLayout) findViewById(R.id.rlMiddle);
        rlRight = (RelativeLayout) findViewById(R.id.rlRight);
    }

    /**
     * 添加View到标题栏左边
     *
     * @param view 被添加的View
     */
    public void addToLeft(View view) {
        addViewToLayout(rlLeft, view);
    }

    /**
     * 添加View到标题栏中间
     *
     * @param view 被添加的View
     */
    public void addToMiddle(View view) {
        addViewToLayout(rlMiddle, view);
    }

    /**
     * 添加View到标题栏右边
     *
     * @param view 被添加的View
     */
    public void addToRight(View view) {
        addViewToLayout(rlRight, view);
    }

    /**
     * 添加View至指定的RelativeLayout
     *
     * @param layout 需添加View的RelativeLayout
     * @param view   被添加的View
     */
    private void addViewToLayout(RelativeLayout layout, View view) {
        layout.removeAllViews();
        layout.addView(view);
    }

    public interface Navigationable {
        /**
         * 获取导航栏控件
         *
         * @return 导航栏控件
         */
        NavigationBarView getNavigationBar();

        /**
         * 注册导航栏控件
         * param activity 标题栏所在UI
         */
        void registNavigationBar();
    }
}