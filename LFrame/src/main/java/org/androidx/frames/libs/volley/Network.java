package org.androidx.frames.libs.volley;

/**
 * 执行请求的接口
 *
 * @author slioe shu
 */
public interface Network {

    /**
     * 执行指定的请求
     *
     * @param request 请求的对象
     * @return NetworkResponse对象，带有数据和缓存数据，永不为空
     * @throws VolleyError
     */
    NetworkResponse performRequest(Request<?> request) throws VolleyError;
}
