
package com.news.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 栏目横向滑动控件
 *
 * @author slioe shu
 */
public class ColumnScrollView extends HorizontalScrollView {
    private View ll_content;
    private View ll_more;
    private View rl_column;
    private int mScreenWitdh = 0;
    private Activity activity;

    public ColumnScrollView(Context context) {
        super(context);
    }

    public ColumnScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        shade_ShowOrHide();
        if (paramInt1 == 0) {
            return;
        }
        if (ll_content.getWidth() - paramInt1 + ll_more.getWidth() + rl_column.getLeft() == mScreenWitdh) {
            return;
        }
    }

    public void setParam(Activity activity, int mScreenWitdh, View paramView1, View paramView4, View paramView5) {
        this.activity = activity;
        this.mScreenWitdh = mScreenWitdh;
        ll_content = paramView1;
        ll_more = paramView4;
        rl_column = paramView5;
    }

    public void shade_ShowOrHide() {
        if (!activity.isFinishing() && ll_content != null) {
            measure(0, 0);
        } else {
            return;
        }
        if (getLeft() == 0) {
            return;
        }
        if (getRight() == getMeasuredWidth() - mScreenWitdh) {
            return;
        }
    }
}
