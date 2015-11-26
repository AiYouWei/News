package org.androidx.frames;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json解析器， 解析JSON数据
 *
 * @author slioe shu
 */
public final class JsonParser {
    private static JsonParser instance = null;
    private Gson gson = null;


    private JsonParser() {
        gson = new Gson();
    }

    public static JsonParser getInstance() {
        return instance == null ? instance = new JsonParser() : instance;
    }

    public <T> T parseFromJson(String json, Class<T> t) {
        return gson.fromJson(json, t);
    }

    public <T> T parseFromJson(String json, Type t) {
        return gson.fromJson(json, t);
    }

    public <T> String parseToJson(List<T> ts) {
        return gson.toJson(ts);
    }
}
