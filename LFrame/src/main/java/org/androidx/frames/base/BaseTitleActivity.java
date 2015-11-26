package org.androidx.frames.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import org.androidx.frames.core.UINavigationImpl;
import org.androidx.frames.core.UINavigationable;
import org.androidx.frames.views.NavigationBarView;

/**
 * 带标题栏的Activity
 *
 * @author slioe shu
 */
public abstract class BaseTitleActivity extends BaseActivity implements UINavigationable {
    private UINavigationImpl uiNavigation;
    protected NavigationBarView barView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initViews();
        super.onCreate(savedInstanceState);

        initTitles();
    }

    /**
     * 初始化标题栏View
     */
    protected void initTitles() {
        uiNavigation = new UINavigationImpl();
        uiNavigation.setTitleActivity(this);
        uiNavigation.registNavigationBar();
        barView = uiNavigation.getNavigationBar();
    }

    //=====添加标题栏=====
    @Override
    public void addTextToLeft(String text, OnClickListener listener) {
        uiNavigation.addTextToLeft(text, listener);
    }

    @Override
    public void addTextToMiddle(String text, OnClickListener listener) {
        uiNavigation.addTextToMiddle(text, listener);
    }

    @Override
    public void addTextToRight(String text, OnClickListener listener) {
        uiNavigation.addTextToRight(text, listener);
    }

    @Override
    public void addDefaultMiddle(String text) {
        uiNavigation.addDefaultMiddle(text);
    }

    @Override
    public void addImageToLeft(int imgResID, OnClickListener listener) {
        uiNavigation.addImageToLeft(imgResID, listener);
    }

    @Override
    public void addImageToMiddle(int imgResID, OnClickListener listener) {
        uiNavigation.addImageToMiddle(imgResID, listener);
    }

    @Override
    public void addImageToRight(int imgResID, OnClickListener listener) {
        uiNavigation.addImageToRight(imgResID, listener);
    }

    @Override
    public void addDefaultLeft(OnClickListener listener) {
        uiNavigation.addDefaultLeft(listener);
    }

    @Override
    public void addViewToLeft(View view, OnClickListener listener) {
        uiNavigation.addViewToLeft(view, listener);
    }

    @Override
    public void addViewToMiddle(View view, OnClickListener listener) {
        uiNavigation.addViewToMiddle(view, listener);
    }

    @Override
    public void addViewToRight(View view, OnClickListener listener) {
        uiNavigation.addViewToRight(view, listener);
    }
}
