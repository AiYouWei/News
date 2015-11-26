package org.androidx.frames.core;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;

/**
 * 沉浸式状态栏管理
 *
 * @author slioe shu
 */
public class SystemBarTintManager {
    /**
     * 默认的状态栏颜色
     */
    public final static int DEFAULT_TINT_COLOR = 0x00000000;
    private boolean isStatusBarAvailable, isNavBarAvailable;
    private final SystemBarConfig config;
    private View statusBarTintView, navBarTintView;

    @TargetApi(19)
    public SystemBarTintManager(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int[] attrs = {android.R.attr.windowTranslucentStatus, android.R.attr.windowTranslucentNavigation};
            TypedArray typedArray = activity.obtainStyledAttributes(attrs);
            try {
                isStatusBarAvailable = typedArray.getBoolean(0, false);
                isNavBarAvailable = typedArray.getBoolean(1, false);
            } finally {
                typedArray.recycle();
            }

            WindowManager.LayoutParams winParams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if ((winParams.flags & bits) != 0) {
                isStatusBarAvailable = true;
            }
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if ((winParams.flags & bits) != 0) {
                isNavBarAvailable = true;
            }
        }

        config = new SystemBarConfig(activity);
        if (!config.hasNavigtionBar()) {
            isNavBarAvailable = false;
        }

        if (isStatusBarAvailable) {
            setupStatusBarView(activity, decorViewGroup);
        }
        if (isNavBarAvailable) {
            setupNavBarView(activity, decorViewGroup);
        }
    }

    /**
     * 设置状态栏
     *
     * @param context        context
     * @param decorViewGroup 窗口装饰View
     */
    private void setupStatusBarView(Context context, ViewGroup decorViewGroup) {
        statusBarTintView = new View(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, config.getStatusBarHeight());
        params.gravity = Gravity.TOP;
        if (isNavBarAvailable && !config.isNavigationAtBottom()) {
            params.rightMargin = config.getNavigationBarWidth();
        }
        statusBarTintView.setLayoutParams(params);
        statusBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
        statusBarTintView.setVisibility(View.GONE);
        decorViewGroup.addView(statusBarTintView);
    }

    /**
     * 设置导航栏
     *
     * @param context        context
     * @param decorViewGroup 窗口装饰View
     */
    private void setupNavBarView(Context context, ViewGroup decorViewGroup) {
        navBarTintView = new View(context);
        LayoutParams params;
        if (config.isNavigationAtBottom()) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT, config.getNavigationBarHeight());
            params.gravity = Gravity.BOTTOM;
        } else {
            params = new LayoutParams(config.getNavigationBarWidth(), LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.RIGHT;
        }
        navBarTintView.setLayoutParams(params);
        navBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
        navBarTintView.setVisibility(View.GONE);
        decorViewGroup.addView(navBarTintView);
    }

    /**
     * 状态栏颜色管理是否可用
     *
     * @param enabled true状态栏颜色管理可用，否则不可用
     */
    public void setStatusBarTintEnabled(boolean enabled) {
        if (isStatusBarAvailable) {
            statusBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置状态栏
     *
     * @param res 状态栏颜色的资源ID
     */
    public void setStatusBarTintResource(int res) {
        if (isStatusBarAvailable) {
            statusBarTintView.setBackgroundResource(res);
        }
    }

    /**
     * 导航栏颜色管理是否可用
     *
     * @param enabled true状态栏颜色管理可用，否则不可用
     */
    public void setNavigationBarTintEnabled(boolean enabled) {
        if (isNavBarAvailable) {
            navBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 状态栏的相关配置
     */
    public static class SystemBarConfig {
        private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
        private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
        private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
        private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";

        private final int mStatusBarHeight;
        private final boolean mHasNavigationBar;
        private final int mNavigationBarHeight;
        private final int mNavigationBarWidth;
        private final boolean mInPortrait;
        private final float mSmallestWidthDp;

        private SystemBarConfig(Activity activity) {
            Resources res = activity.getResources();
            mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
            mSmallestWidthDp = getSmallestWidthDp(activity);
            mStatusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
            mNavigationBarHeight = getNavigationBarHeight(activity);
            mNavigationBarWidth = getNavigationBarWidth(activity);
            mHasNavigationBar = (mNavigationBarHeight > 0);
        }

        @TargetApi(14)
        private int getNavigationBarHeight(Context context) {
            Resources res = context.getResources();
            int result = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
                    String key;
                    if (mInPortrait) {
                        key = NAV_BAR_HEIGHT_RES_NAME;
                    } else {
                        key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                    }
                    return getInternalDimensionSize(res, key);
                }
            }
            return result;
        }

        @TargetApi(14)
        private int getNavigationBarWidth(Context context) {
            Resources res = context.getResources();
            int result = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
                    return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
                }
            }
            return result;
        }

        private int getInternalDimensionSize(Resources res, String key) {
            int result = 0;
            int resourceId = res.getIdentifier(key, "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
            return result;
        }

        @SuppressLint("NewApi")
        private float getSmallestWidthDp(Activity activity) {
            DisplayMetrics metrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            } else {
                // TODO this is not correct, but we don't really care pre-kitkat
                activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            }
            float widthDp = metrics.widthPixels / metrics.density;
            float heightDp = metrics.heightPixels / metrics.density;
            return Math.min(widthDp, heightDp);
        }

        /**
         * Should a navigation bar appear at the bottom of the screen in the current
         * device configuration? A navigation bar may appear on the right side of
         * the screen in certain configurations.
         *
         * @return True if navigation should appear at the bottom of the screen, False otherwise.
         */
        public boolean isNavigationAtBottom() {
            return (mSmallestWidthDp >= 600 || mInPortrait);
        }

        /**
         * Get the height of the system status bar.
         *
         * @return The height of the status bar (in pixels).
         */
        public int getStatusBarHeight() {
            return mStatusBarHeight;
        }

        /**
         * Does this device have a system navigation bar?
         *
         * @return True if this device uses soft key navigation, False otherwise.
         */
        public boolean hasNavigtionBar() {
            return mHasNavigationBar;
        }

        /**
         * Get the height of the system navigation bar.
         *
         * @return The height of the navigation bar (in pixels). If the device does not have
         * soft navigation keys, this will always return 0.
         */
        public int getNavigationBarHeight() {
            return mNavigationBarHeight;
        }

        /**
         * Get the width of the system navigation bar when it is placed vertically on the screen.
         *
         * @return The width of the navigation bar (in pixels). If the device does not have
         * soft navigation keys, this will always return 0.
         */
        public int getNavigationBarWidth() {
            return mNavigationBarWidth;
        }
    }
}
