package org.androidx.frames.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import org.androidx.frames.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备相关工具
 *
 * @author slioe shu
 */
public final class DeviceUtil {

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕像度， 单位px
     */
    public static int getScreenWidth() {
        Context context = BaseApplication.getInstance();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度， 单位px
     */
    public static int getScreenHeight() {
        Context context = BaseApplication.getInstance();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getScreenDensity() {
        Context context = BaseApplication.getInstance();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取字体缩放比例
     *
     * @return 字体缩放比例
     */
    public static float getFontScale() {
        Context context = BaseApplication.getInstance();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * dp转px
     *
     * @param dpValue 需转化的dp值
     * @return dp对应的px值
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * getScreenDensity() + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue 需转化的px值
     * @return px对应的dp值
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue 需转化的px值
     * @return px对应的sp值
     */
    public static int px2sp(float pxValue) {
        return (int) (pxValue / getFontScale() + 0.5f);
    }

    /**
     * 判断是否为手机号码
     *
     * @param phoneNo 需识别的手机号码
     * @return true: 是手机号码 false: 不是手机号码
     */
    public static boolean isPhoneNo(String phoneNo) {
        String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }
}
