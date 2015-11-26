package org.androidx.frames;

import org.androidx.frames.activities.ShareChannelActivity;
import org.androidx.frames.activities.WebViewActivity;
import org.androidx.frames.base.BaseApplication;
import org.androidx.frames.utils.ResUtil;

/**
 * UI跳转URI
 *
 * @author slioe shu
 */
public abstract class BaseUris {
    private final static String APP_URI_PREFIX = "app_uri_prefix";
    public final static String WEBVIEW = registPageUri(WebViewActivity.class);// 显示网页
    public final static String SHARE_CHANNEL = registPageUri(ShareChannelActivity.class); // 分享


    private enum Category {
        NAVI, PAGE, MENU
    }

    /**
     * 注册UI跳转URI
     *
     * @param classz Class对象
     * @return 通过类名生成的URI
     */
    protected static String registPageUri(Class classz) {
        return getUri(Category.PAGE, classz.getName());
    }

    /**
     * 注册Tab跳转URI
     *
     * @param classz Class对象
     * @return 通过类名生成的URI
     */
    protected static String registNaviUri(Class classz) {
        return getUri(Category.NAVI, classz.getName());
    }

    private static String getUri(Category category, String name) {
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        String appName = ResUtil.getStringByName(APP_URI_PREFIX);
        BaseApplication.getInstance().getDispatcher().addRegistMappings(suffix, name);
        return "mod://" + appName + "." + category.toString() + "." + suffix;
    }
}
