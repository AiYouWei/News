package org.androidx.frames.libs.volley;

import android.content.Context;
import android.text.TextUtils;

import org.androidx.frames.base.BaseApplication;
import org.androidx.frames.libs.volley.Request.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求最终调用的类
 *
 * @author slioe shu
 */
public final class FinalRequest {
    private final Map<String, String> headers;
    private Context context;
    private ConcurrentHashMap<String, String> defaultParams;
    private RequestQueue requestQueue;

    public FinalRequest() {
        headers = new HashMap<>();
        context = BaseApplication.getInstance().getApplicationContext();
        defaultParams = new ConcurrentHashMap<>();
        requestQueue = Volley.newRequestQueue(context);
    }

    public void addHeader(String key, String value) {
        if (TextUtils.isEmpty(key) && TextUtils.isEmpty(value)) {
            headers.put(key, value);
        }
    }

    public void setDefaultParams(Map<String, String> params) {
        defaultParams.putAll(params);
    }

    public void get(String url, HttpCallback callback) {
        request(Method.GET, url, null, null, callback);
    }

    public void get(String url, HttpParams params, HttpCallback callback) {
        request(Method.GET, url, null, params, callback);
    }

    public void get(String url, HashMap<String, String> header, HttpParams params, HttpCallback callback) {
        request(Method.GET, url, header, params, callback);
    }

    public void post(String url, HttpCallback callback) {
        request(Method.POST, url, null, null, callback);
    }

    public void post(String url, HttpParams params, HttpCallback callback) {
        request(Method.POST, url, null, params, callback);
    }

    public void post(String url, Map<String, String> header, HttpParams params, HttpCallback callback) {
        request(Method.POST, url, header, params, callback);
    }

    private void request(int method, String url, final Map<String, String> header, HttpParams params, HttpCallback callback) {
        final HttpParams totalParams = new HttpParams(defaultParams);
        if (params != null && !params.getStringParams().isEmpty()) {
            totalParams.putAll(params.getStringParams());
        }
        if (method == Method.GET) {
            url = totalParams.paramsToString(url);
        }

        UniversalRequest request = new UniversalRequest(method, url, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null && !header.isEmpty()) {
                    headers.putAll(header);
                }
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> stringParams = totalParams.getStringParams();
                if (stringParams != null && stringParams.size() > 0) {
                    return encodeParameters(stringParams, getParamsEncoding());
                }
                return null;
            }
        };
        requestQueue.add(request);
    }
}
