package org.androidx.frames.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.androidx.frames.core.UINavigationImpl;
import org.androidx.frames.core.UINavigationable;
import org.androidx.frames.views.NavigationBarView;

/**
 * 带标题栏的Fragment
 *
 * @author slioe shu
 */
public abstract class BaseTitleFragment extends BaseFragment implements UINavigationable {
    private UINavigationImpl uiNavigation;
    protected NavigationBarView barView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        uiNavigation = new UINavigationImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uiNavigation.setTitleActivity((BaseActivity) getActivity());
        uiNavigation.setActivityView(getView());
        uiNavigation.registNavigationBar();
        initTitles();
    }

    /**
     * 初始化标题栏
     */
    protected void initTitles() {
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
