package com.news.utils;

import com.news.TYAppliaction;
import com.news.TYUrls;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.utils.LogUtil;

/**
 * @author slioe shu
 */
public final class ShareSuccessUtil {

    public static void success() {
        String url = TYUrls.BASE_URL + "?r=site/success&uid=" + TYAppliaction.getInstance().getSettings().USER_ID.getValue();
        new FinalHttp().post(url, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                LogUtil.e(true, "ShareSuccessUtil", "success onFailure = " + strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtil.e(true, "ShareSuccessUtil", "success onSuccess = " + response);
            }
        });
    }
}
