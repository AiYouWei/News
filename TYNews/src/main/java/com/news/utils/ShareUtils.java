package com.news.utils;

import android.app.Activity;
import android.content.Intent;

import org.androidx.libs.share.ShareActivity;
import org.androidx.libs.share.ShareInfoType;

/**
 * @author slioe shu
 */
public class ShareUtils {

    public static void showShare(final Activity context, final ShareInfoType shareInfo) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(ShareActivity.SHARE_INFO, shareInfo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
