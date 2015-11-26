package org.androidx.frames.core;

import android.view.View;
import android.view.View.OnClickListener;

import org.androidx.frames.R;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.core.UINavigationCategory.Category;
import org.androidx.frames.views.NavigationBarView;
import org.androidx.frames.views.NavigationBarView.Navigationable;

/**
 * 界面标题栏实现
 *
 * @author slioe shu
 */
public class UINavigationImpl implements UINavigationable, Navigationable {
    private static UINavigationImpl instance;
    private NavigationBarView barView;
    private UINavigationCategory barCategory;
    private BaseActivity activity;
    private View view;

    public UINavigationImpl() {
        barCategory = UINavigationCategory.getInstance();
    }

    public void setTitleActivity(BaseActivity activity) {
        this.activity = activity;
    }

    public void setActivityView(View view) {
        this.view = view;
    }

    @Override
    public NavigationBarView getNavigationBar() {
        return barView;
    }

    @Override
    public void registNavigationBar() {
        if (view != null) {
            barView = (NavigationBarView) view.findViewById(R.id.nbvNavigation);
        } else {
            if (activity != null) {
                barView = (NavigationBarView) activity.findViewById(R.id.nbvNavigation);
            }
        }

        if (barView == null) {
            throw new RuntimeException("the res of NavigationBarView not found");
        }
    }

    @Override
    public void addTextToLeft(String text, OnClickListener listener) {
        barCategory.addText(activity, barView, text, listener, Category.LEFT);
    }

    @Override
    public void addTextToMiddle(String text, OnClickListener listener) {
        barCategory.addText(activity, barView, text, listener, Category.MIDDLE);
    }

    @Override
    public void addTextToRight(String text, OnClickListener listener) {
        barCategory.addText(activity, barView, text, listener, Category.RIGHT);
    }

    @Override
    public void addDefaultMiddle(String text) {
        barCategory.addText(activity, barView, text, null, Category.MIDDLE);
    }

    @Override
    public void addImageToLeft(int imgResID, OnClickListener listener) {
        barCategory.addImage(activity, barView, imgResID, listener, Category.LEFT);
    }

    @Override
    public void addImageToMiddle(int imgResID, OnClickListener listener) {
        barCategory.addImage(activity, barView, imgResID, listener, Category.MIDDLE);
    }

    @Override
    public void addImageToRight(int imgResID, OnClickListener listener) {
        barCategory.addImage(activity, barView, imgResID, listener, Category.RIGHT);
    }

    @Override
    public void addDefaultLeft(OnClickListener listener) {
        if (listener == null) {
            listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            };
        }
        barCategory.addImage(activity, barView, R.mipmap.navi_back_default, listener, Category.LEFT);
    }

    @Override
    public void addViewToLeft(View view, OnClickListener listener) {
        barCategory.addView(activity, barView, view, listener, Category.LEFT);
    }

    @Override
    public void addViewToMiddle(View view, OnClickListener listener) {
        barCategory.addView(activity, barView, view, listener, Category.MIDDLE);
    }

    @Override
    public void addViewToRight(View view, OnClickListener listener) {
        barCategory.addView(activity, barView, view, listener, Category.RIGHT);
    }
}
