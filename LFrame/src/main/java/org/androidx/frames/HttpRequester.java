package org.androidx.frames;

import org.androidx.frames.libs.volley.FinalRequest;
import org.androidx.frames.libs.volley.HttpCallback;
import org.androidx.frames.libs.volley.HttpParams;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求管理器
 *
 * @author slioe shu
 */
public final class HttpRequester {
    private static HttpRequester instance = null;
    private final FinalRequest request;

    private HttpRequester() {
        request = new FinalRequest();
    }

    public static HttpRequester getInstance() {
        return instance == null ? instance = new HttpRequester() : instance;
    }

    public void addHeader(String key, String value) {
        request.addHeader(key, value);
    }

    public void setDefaultParams(Map<String, String> params) {
        request.setDefaultParams(params);
    }

    public void get(String url, HttpCallback callback) {
        request.get(url, null, null, callback);
    }

    public void get(String url, HttpParams params, HttpCallback callback) {
        request.get(url, null, params, callback);
    }

    public void get(String url, HashMap<String, String> header, HttpParams params, HttpCallback callback) {
        request.get(url, header, params, callback);
    }

    public void post(String url, HttpCallback callback) {
        request.post(url, null, null, callback);
    }

    public void post(String url, HttpParams params, HttpCallback callback) {
        request.post(url, null, params, callback);
    }

    public void post(String url, HashMap<String, String> header, HttpParams params, HttpCallback callback) {
        request.post(url, header, params, callback);
    }
}
