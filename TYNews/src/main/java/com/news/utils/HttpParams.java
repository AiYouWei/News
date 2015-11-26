package com.news.utils;

import android.os.Build.VERSION;

import com.news.TYConstants;

import net.tsz.afinal.http.AjaxParams;

/**
 * @author slioe shu
 */
public class HttpParams extends AjaxParams {
    public HttpParams(Object... keysAndValues) {
        super(keysAndValues);
    }

    public HttpParams addCommonParam() {
        put("v", TYConstants.APP_VER);
        put("app_id", TYConstants.DEVICE_OS);
        put("model", android.os.Build.MODEL);
        put("reqtime", String.valueOf(System.currentTimeMillis()));
        put("version", String.valueOf(VERSION.RELEASE));
        return this;
    }
}
