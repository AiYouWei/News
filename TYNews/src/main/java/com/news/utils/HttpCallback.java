package com.news.utils;

import net.tsz.afinal.http.AjaxCallBack;

import org.androidx.frames.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求的通用返回
 *
 * @author slioe shu
 */
public abstract class HttpCallback extends AjaxCallBack<String> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onLoading(long count, long current) {
        super.onLoading(count, current);
    }

    @Override
    public void onFailure(Throwable t, int errorNo, String strMsg) {
        super.onFailure(t, errorNo, strMsg);
        LogUtil.e(true, "HttpCallback", "onFailure error = " + strMsg);
    }

    @Override
    public void onSuccess(String response) {
        super.onSuccess(response);
        try {
            LogUtil.e(true, "HttpCallback", "请求返回 response = " + new JSONObject(response).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
