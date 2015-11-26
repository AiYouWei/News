package org.androidx.frames.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.androidx.frames.HttpRequester;
import org.androidx.frames.ImagesLoader;
import org.androidx.frames.JsonParser;
import org.androidx.frames.core.SystemBarTintManager;
import org.androidx.frames.core.UISkipImpl;
import org.androidx.frames.core.UISkipable;
import org.androidx.frames.fragments.StatusDialogFrament;
import org.androidx.frames.utils.ResUtil;
import org.androidx.frames.utils.ResUtil.ResType;

/**
 * Activity基类，提供便捷的调用方法
 *
 * @author slioe shu
 */
public class BaseActivity extends FragmentActivity implements UISkipable {
    private StatusDialogFrament statusDialog; // 状态对话框
    private UISkipImpl uiSkip; // Activity跳转
    @Deprecated
    protected static BaseApplication application;
    @Deprecated
    protected static JsonParser jsonParser;
    @Deprecated
    protected static ImagesLoader imageLoader;
    @Deprecated
    protected static HttpRequester httpRequester;
    private boolean isInitViews = true; // initViews方法执行的标识
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUtils(); // 初始化相关工具类
        addImmersiveBar();// 添加沉浸式状态栏
        uiSkip = UISkipImpl.getInstance();
        uiSkip.setActivity(this);

        if (isInitViews) {
            initViews();
        }
        loadData();
    }

    private void initUtils() {
        application = BaseApplication.getInstance();
        httpRequester = application.getHttpRequester();
        jsonParser = JsonParser.getInstance();
        imageLoader = ImagesLoader.getInstance();
        statusDialog = new StatusDialogFrament();
    }

    /**
     * 添加沉浸式状态栏
     */
    private void addImmersiveBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(winParams);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(ResUtil.getResIdByType(ResType.COLOR, "navi_default_bg"));
        tintManager.setNavigationBarTintEnabled(true);
    }

    /**
     * 初始化layout布局，进行控件获取，赋值和监听事件
     */
    protected void initViews() {
        isInitViews = false;
    }

    /**
     * 加载数据，在初始化layout布局之后执行
     */
    protected void loadData() {
    }

    /**
     * 根据资源ID获取控件
     *
     * @param resId 资源ID
     * @param <T>   控件的类型
     * @return ID对应的控件
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int resId) {
        return (T) findViewById(resId);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //======Activity状态对话框=========
    public void showDialog() {
        statusDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void dismissDialog() {
        statusDialog.dismiss();
    }

    //======Activity跳转=========
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void startActivityByUri(@NonNull String uri) {
        uiSkip.startActivityByUri(uri);
    }

    @Override
    public void startActivityByUri(@NonNull String uri, @Nullable Bundle data) {
        uiSkip.startActivityByUri(uri, data);
    }

    @Override
    public void startActivityForResultByUri(@NonNull String uri, int requestCode) {
        uiSkip.startActivityForResultByUri(uri, requestCode);
    }

    @Override
    public void startActivityForResultByUri(@NonNull String uri, int requestCode, @Nullable Bundle data) {
        uiSkip.startActivityForResultByUri(uri, requestCode, data);
    }

    @Override
    public void onReceiveForUri(int requestCode, int resultCode, Bundle data) {

    }

    @Override
    public void setResultForUri(int resultCode, Bundle data) {
        uiSkip.setResultForUri(resultCode, data);
    }
}
