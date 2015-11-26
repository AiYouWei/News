package org.androidx.frames.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidx.frames.HttpRequester;
import org.androidx.frames.ImagesLoader;
import org.androidx.frames.JsonParser;
import org.androidx.frames.core.UISkipImpl;
import org.androidx.frames.core.UISkipable;

/**
 * Fragment基类， 实现注解(设置布局、获取View、设置点击时间)、 提供跳转、
 *
 * @author slioe shu
 */
@SuppressWarnings("deprecation")
public class BaseFragment extends Fragment implements UISkipable {
    private UISkipImpl uiSkip;
    protected View view;
    protected static BaseApplication application;
    protected static JsonParser jsonParser;
    protected static ImagesLoader imageLoader;
    protected static HttpRequester httpRequester;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            uiSkip = UISkipImpl.getInstance();
            uiSkip.setActivity((BaseActivity) activity);
            application = BaseApplication.getInstance();
            httpRequester = application.getHttpRequester();
            jsonParser = JsonParser.getInstance();
            imageLoader = ImagesLoader.getInstance();
        } else {
            throw new RuntimeException("The attached activity must extends BaseActivity or BaseTitleActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    /**
     * 初始化layout布局，进行控件获取，赋值和监听事件
     */
    protected void initViews() {
    }

    /**
     * 加载数据，在初始化layout布局之后执行
     */
    protected void loadData() {
    }

    public void setContentView(int layoutResID) {
        view = View.inflate(getActivity(), layoutResID, null);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int resId) {
        return (T) view.findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    //=====Fragment跳转=====
    @Override
    public void startActivityByUri(@NonNull String uri) {
        uiSkip.startActivityByUri(uri);
    }

    @Override
    public void startActivityByUri(String uri, Bundle data) {
        uiSkip.startActivityByUri(uri, data);
    }

    @Override
    public void startActivityForResultByUri(String uri, int request) {
        uiSkip.startActivityForResultByUri(uri, request);
    }

    @Override
    public void startActivityForResultByUri(String uri, int request, Bundle data) {
        uiSkip.startActivityForResultByUri(uri, request, data);
    }

    @Override
    public void onReceiveForUri(int request, int result, Bundle data) {
    }

    @Override
    public void setResultForUri(int result, Bundle data) {
        uiSkip.setResultForUri(result, data);
    }
}
