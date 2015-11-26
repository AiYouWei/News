package org.androidx.frames.libs.volley;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * HTTP栈接口
 *
 * @author slioe shu
 */
public interface HttpStack {

    /**
     * 通过给定的参数执行请求
     *
     * @param request           需执行的请求
     * @param additionalHeaders 额外添加的头部信息
     * @return HTTP请求的响应
     * @throws IOException
     * @throws AuthFailureError
     */
    HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError;

}
