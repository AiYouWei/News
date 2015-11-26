package org.androidx.frames.utils;

import android.content.res.Resources.NotFoundException;

import org.androidx.frames.base.BaseApplication;

import java.util.Locale;

/**
 * 资源工具，主要是根据资源名称获取相关资源ID或Value
 *
 * @author slioe shu
 */
public class ResUtil {
    public enum ResType {
        STRING, BOOL, COLOR, MIPMAP, DRAWABLE, DIMEN, STRINGARRAY
    }

    public static int getResIdByType(ResType type, String resName) {
        return BaseApplication.getInstance().getResources().getIdentifier(BaseApplication.getInstance().getPackageName() + ":" + type.toString().toLowerCase(Locale.CHINESE) + "/" + resName, null, null);
    }

    /**
     * 根据资源名字获取资源的boolean值
     *
     * @param resName 资源名字
     * @return 资源名字对应的boolean值， 默认为false
     */
    public static boolean getBooleanByName(String resName) {
        try {
            return BaseApplication.getInstance().getResources().getBoolean(getResIdByType(ResType.BOOL, resName));
        } catch (NotFoundException e) {
        }
        return false;
    }

    /**
     * 根据资源名字获取资源的String值
     *
     * @param resName 资源名字
     * @return 资源名字对应的String值， 默认为""
     */
    public static String getStringByName(String resName) {
        try {
            return BaseApplication.getInstance().getString(getResIdByType(ResType.STRING, resName));
        } catch (NotFoundException e) {
        }
        return "";
    }

    /**
     * 根据资源名字获取资源的Dimen值
     *
     * @param resName 资源名字
     * @return 资源名字对应的String值， 默认为0
     */
    public static int getDimenByName(String resName) {
        try {
            return BaseApplication.getInstance().getResources().getDimensionPixelSize(getResIdByType(ResType.DIMEN, resName));
        } catch (NotFoundException e) {
        }
        return 0;
    }

}
