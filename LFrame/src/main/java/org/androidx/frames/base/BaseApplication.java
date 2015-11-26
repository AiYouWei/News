package org.androidx.frames.base;

import android.app.Application;

import org.androidx.frames.HttpRequester;
import org.androidx.frames.core.UISkipDispatcher;
import org.androidx.frames.preferences.BaseSharedSettings;

/**
 * APP的Application
 *
 * @author slioe shu
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;
    private UISkipDispatcher dispatcher;
    private HttpRequester httpRequester;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        httpRequester = HttpRequester.getInstance();
        setCommonParams();
        dispatcher = UISkipDispatcher.init(getApplicationContext());
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public UISkipDispatcher getDispatcher() {
        return dispatcher;
    }

    public HttpRequester getHttpRequester() {
        return httpRequester;
    }

    public abstract <T extends BaseSharedSettings> T getSettings();

    /**
     * HTTP请求添加公用的参数
     */
    public abstract void setCommonParams();

}
