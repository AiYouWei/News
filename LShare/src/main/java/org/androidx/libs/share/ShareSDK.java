package org.androidx.libs.share;

import android.app.Activity;

import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.androidx.libs.share.core.ShareDispatcher;
import org.androidx.libs.share.core.SharePlatform;

/**
 * 分享核心类， 提供分享接口
 *
 * @author slioe shu
 */
public final class ShareSDK {

    public static void share(SharePlatform platform, ShareInfoType shareInfo, Activity activity) {

    }

    // 一键分享
    public static void shareToAll() {

    }

    // 分享至新浪微博
    public static void shareToSinaWeibo(Activity activity, ShareInfoType shareInfo, IWeiboShareAPI wbapi, AuthInfo authInfo, Oauth2AccessToken accessToken) {
        ShareDispatcher.shareSinaWeibo(activity, shareInfo, wbapi, authInfo, accessToken);
    }

    // 分享至QQ好友
    public static void shareToQQ(Activity activity, ShareInfoType shareInfo) {
        ShareDispatcher.shareQQ(activity, shareInfo);
    }

    // 分享至QQ空间
    public static void shareToQZone(Activity activity, ShareInfoType shareInfo) {
        ShareDispatcher.shareQZone(activity, shareInfo);
    }

    // 分享至微信好友
    public static void shareToWXFriend(Activity activity, ShareInfoType shareInfo) {
        ShareDispatcher.shareWeChat(activity, shareInfo, SharePlatform.WXFriend);
    }

    // 分享至微信朋友圈
    public static void shareToWXGroup(Activity activity, ShareInfoType shareInfo) {
        ShareDispatcher.shareWeChat(activity, shareInfo, SharePlatform.WXGroup);
    }

    // 分享至手机短信
    public static void shareToMessage(Activity activity, ShareInfoType shareInfo) {
        ShareDispatcher.shareMessage(activity, shareInfo);
    }
}
