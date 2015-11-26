package org.androidx.frames.libs.volley;

import android.text.TextUtils;

import org.androidx.frames.libs.volley.Response.ErrorListener;
import org.androidx.frames.libs.volley.Response.Listener;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ResUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 通用的请求
 *
 * @author slioe shu
 */
public class UniversalRequest extends Request<String> {
    private final Listener<String> listener; // 请求成功的监听

    public UniversalRequest(int method, String url, final HttpCallback callback) {
        super(method, url, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(-1, error.getMessage(), error.getCause());
            }
        });
        this.listener = new Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String code = ResUtil.getStringByName("resonse_code");
                    String msg = ResUtil.getStringByName("resonse_msg");
                    String success = ResUtil.getStringByName("resonse_success");
                    String codeContent = json.optString(code);
                    if (TextUtils.isEmpty(codeContent)) {
                        callback.onSuccess(response);
                    } else {
                        if (success.equals(codeContent)) {
                            callback.onSuccess(response);
                        } else {
                            ToastUtil.ToastShort(json.optString(msg));
                            LogUtil.e(true, "UniversalRequest", "url = " + getOriginUrl() + "    response = " + json.optString(msg));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
