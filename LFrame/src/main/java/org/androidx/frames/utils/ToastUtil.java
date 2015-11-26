package org.androidx.frames.utils;

import android.widget.Toast;

import org.androidx.frames.base.BaseApplication;

/**
 * Toast工具
 *
 * @author slioe shu
 */
public class ToastUtil {

    /**
     * 短时间显示Toast
     *
     * @param msg 需显示的信息
     */
    public static void ToastShort(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param msg 需显示的信息
     */
    public static void ToastLong(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
    }

}
