package org.androidx.frames.libs.volley;

/**
 * HTTP请求回调
 *
 * @author slioe shu
 */
public abstract class HttpCallback {
    private boolean isProgress = true;
    private int rate = 1000 * 1;//每秒

    public boolean isProgress() {
        return isProgress;
    }

    /**
     * 设置进度,而且只有设置了这个了以后，onLoading才能有效。
     *
     * @param isProgress 是否启用进度显示
     * @param rate       进度更新频率
     */
    public HttpCallback progress(boolean isProgress, int rate) {
        this.isProgress = isProgress;
        this.rate = rate;
        return this;
    }

    /**
     * 请求开始
     */
    public void onStart() {
    }

    /**
     * 下载时下载进度
     *
     * @param total   下载文件总大小
     * @param current 下载文件已下载大小
     */
    public void onLoading(long total, long current) {
    }

    /**
     * 请求成功返回的数据，支持String和JSONObject
     *
     * @param response 成功返回的数据
     */
    public void onSuccess(String response) {
    }

    /**
     * 请求失败返回的数据
     *
     * @param errorCode 返回的错误码
     * @param msg       返回的错误信息
     * @param tr        返回的异常信息
     */
    public void onFailure(int errorCode, String msg, Throwable tr) {
    }
}
