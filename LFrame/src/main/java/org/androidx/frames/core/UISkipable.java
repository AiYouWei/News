package org.androidx.frames.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.androidx.frames.base.BaseActivity;

/**
 * 界面跳转接口
 *
 * @author slioe shu
 */
public interface UISkipable {

    /**
     * 获取当前Activtiy/Fragment对象
     *
     * @return 当前对象的基类 {@link BaseActivity}
     */
    Activity getActivity();

    /**
     * 通过URI跳转到指定的Activity
     *
     * @param uri 跳转URI
     */
    void startActivityByUri(@NonNull String uri);

    /**
     * 通过URI并附带数据跳转到指定的Activity
     *
     * @param uri  跳转URI
     * @param data 跳转时附带的数据
     */
    void startActivityByUri(String uri, Bundle data);

    /**
     * 通过URI跳转到指定的Activity, 且会在{@link #onReceiveForUri}中接收到指定Activity返回的数据
     *
     * @param uri         跳转URI
     * @param requestCode 大于0，当前Activity会在{@link #onReceiveForUri}中接收到返回数据，反之则不会
     */
    void startActivityForResultByUri(String uri, int requestCode);

    /**
     * 通过URI并附带数据跳转到指定的Activity, 且会在{@link #onReceiveForUri}中接收到指定Activity返回的数据
     *
     * @param uri         跳转URI
     * @param requestCode 大于等于0，当前Activity会在{@link #onReceiveForUri}中接收到数据，反之则不会
     */
    void startActivityForResultByUri(String uri, int requestCode, Bundle data);

    /**
     * 接收指定Activtiy返回的数据，仅在调用{@link #startActivityForResultByUri}时有效
     *
     * @param requestCode 的request
     * @param resultCode  通常取值RESULT_CANCELED或RESULT_OK
     * @param data        返回给上一Activity的数据
     */
    void onReceiveForUri(int requestCode, int resultCode, Bundle data);

    /**
     * 将数据传到上一Activity，仅在上一Activity调用{@link #startActivityForResultByUri}时有效
     *
     * @param resultCode 通常取值 RESULT_CANCELED或RESULT_OK
     * @param data       返回给上一Activity的数据
     */
    void setResultForUri(int resultCode, Bundle data);
}
