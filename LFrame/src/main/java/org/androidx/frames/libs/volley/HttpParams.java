package org.androidx.frames.libs.volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HTTP请求参数，支持字符串和文件
 *
 * @author slioe shu
 */
public class HttpParams {
    private final static String CHAR_SET = "UTF-8";
    private ConcurrentHashMap<String, String> stringParams;
    private ConcurrentHashMap<String, FileWrapper> fileParams;

    public HttpParams() {
        stringParams = new ConcurrentHashMap<>();
        fileParams = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, String> getStringParams() {
        return stringParams;
    }

    public ConcurrentHashMap<String, FileWrapper> getFileParams() {
        return fileParams;
    }

    public HttpParams(Map<String, String> params) {
        this();
        stringParams.putAll(params);
    }

    public HttpParams(String key, String value) {
        this();
        put(key, value);
    }

    public HttpParams(Object... keyValues) {
        this();
        putAll(keyValues);
    }

    /**
     * 添加字符串参数
     *
     * @param key   参数的key
     * @param value 参数的value
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            stringParams.put(key, value);
        }
    }

    /**
     * 添加字符串参数
     *
     * @param params 字符串Map对象
     */
    public void putAll(Map<String, String> params) {
        stringParams.putAll(params);
    }

    /**
     * 添加字符串参数
     *
     * @param keyValues 以键值对的形式组成
     */
    public void putAll(Object... keyValues) {
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("keyValues的数量必须是偶数");
        }
        for (int i = 0; i < keyValues.length; i += 2) {
            String key = String.valueOf(keyValues[i]);
            String value = String.valueOf(keyValues[i + 1]);
            put(key, value);
        }
    }

    /**
     * 添加文件参数
     *
     * @param key      文件参数的key
     * @param filePath 文件路径
     */
    public void putFile(String key, String filePath) {
        try {
            File file = new File(filePath);
            put(key, new FileInputStream(file), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加文件参数
     *
     * @param key  文件参数的key
     * @param file 文件对象
     */
    public void put(String key, File file) {
        try {
            put(key, new FileInputStream(file), file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加文件参数
     *
     * @param key    文件参数的key
     * @param stream 文件流对象
     */
    public void put(String key, InputStream stream) {
        put(key, stream, null);
    }

    /**
     * 添加文件参数
     *
     * @param key      文件参数的key
     * @param stream   文件流对象
     * @param fileName 文件名
     */
    public void put(String key, InputStream stream, String fileName) {
        put(key, stream, fileName, null);
    }

    /**
     * 添加文件对象到请求中.
     *
     * @param key         文件参数的key
     * @param stream      文件流对象
     * @param fileName    文件名
     * @param contentType Http content type
     */
    public void put(String key, InputStream stream, String fileName, String contentType) {
        if (key != null && stream != null) {
            fileParams.put(key, new FileWrapper(stream, fileName, contentType));
        }
    }

    public boolean isEmpty() {
        return stringParams.isEmpty();
    }

    /**
     * 字符串参数转化为字符串
     *
     * @param url 请求的URL
     * @return 转化后的URL
     */
    public String paramsToString(String url) {
        StringBuilder builder = new StringBuilder(url);
        try {
            if (stringParams.size() > 0) {
                for (ConcurrentHashMap.Entry<String, String> entry : stringParams.entrySet()) {
                    String urlBuilder = builder.toString();
                    if (urlBuilder.contains("?") && !urlBuilder.endsWith("?")) {
                        builder.append("&");
                    } else {
                        builder.append("?");
                    }
                    builder.append(entry.getKey());
                    builder.append("=");
                    builder.append(URLEncoder.encode(entry.getValue(), CHAR_SET));
                }
                return builder.toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 文件包装
     */
    private static class FileWrapper {
        public InputStream inputStream;
        public String fileName;
        public String contentType;

        public FileWrapper(InputStream inputStream, String fileName, String contentType) {
            this.inputStream = inputStream;
            this.fileName = fileName;
            this.contentType = contentType;
        }

        /**
         * 获取文件的名称
         *
         * @return 文件的名称，空字符串表示没有文件名称
         */
        public String getFileName() {
            return fileName != null ? fileName : "";
        }
    }
}
