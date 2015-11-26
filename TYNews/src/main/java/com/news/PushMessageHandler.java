package com.news;

import android.content.Context;
import android.content.Intent;

import com.news.activities.NewsImageActivity;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.androidx.frames.activities.WebViewActivity;
import org.androidx.frames.utils.LogUtil;

import java.util.Map.Entry;

/**
 * @author slioe shu
 */
public class PushMessageHandler extends UmengNotificationClickHandler {
    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        super.dealWithCustomAction(context, uMessage);
    }

    @Override
    public void launchApp(Context context, UMessage uMessage) {
        super.launchApp(context, uMessage);
        for (Entry<String, String> entry : uMessage.extra.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            LogUtil.e(true, "PushMessageHandler", "launchApp key = " + key + "   value = " + value);
            if ("newsid".equals(key)) {
                Intent intent = new Intent(context, NewsImageActivity.class);
                intent.putExtra("ID", value);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", value);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    @Override
    public void openActivity(Context context, UMessage uMessage) {
        super.openActivity(context, uMessage);
        LogUtil.e(true, "PushMessageHandler", "openActivity " + uMessage);
    }

    @Override
    public void openUrl(Context context, UMessage uMessage) {
        super.openUrl(context, uMessage);
        LogUtil.e(true, "PushMessageHandler", "openUrl ");
    }

    @Override
    public void autoUpdate(Context context, UMessage uMessage) {
        super.autoUpdate(context, uMessage);
        LogUtil.e(true, "PushMessageHandler", "autoUpdate ");
    }

    @Override
    public void dismissNotification(Context context, UMessage uMessage) {
        super.dismissNotification(context, uMessage);
        LogUtil.e(true, "PushMessageHandler", "dismissNotification ");
    }

    @Override
    public void handleMessage(Context context, UMessage uMessage) {
        super.handleMessage(context, uMessage);
    }

    public PushMessageHandler() {
        super();
    }
}
