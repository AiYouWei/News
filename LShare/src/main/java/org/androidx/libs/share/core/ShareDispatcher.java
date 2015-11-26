package org.androidx.libs.share.core;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.androidx.libs.share.ShareInfoType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 分享实现
 *
 * @author slioe shu
 */
public class ShareDispatcher {
    private static String WX = "wxd8b80d4b611ff2e8";
    private static String QQ = "1103377162";

    /**
     * 微信分享，分享至朋友圈和微信好友 , 只支持Bitmap分享
     *
     * @param activity  Activity
     * @param shareInfo 分享的信息
     * @param platform  分享的平台类型 {@link SharePlatform#WXFriend}或者{@link SharePlatform#WXGroup}
     */
    public static void shareWeChat(Activity activity, ShareInfoType shareInfo, SharePlatform platform) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(activity, WX);
        if (!wxapi.isWXAppInstalled()) {
            Toast.makeText(activity, "设备中没有安装微信客户端或者安装的版本过低", Toast.LENGTH_SHORT).show();
            return;
        }
        wxapi.registerApp(WX);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareInfo.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareInfo.getTitle();
        msg.description = shareInfo.getContent();
        if (shareInfo.getLocalImage() != null) {
            msg.setThumbImage(shareInfo.getLocalImage());
        } else if (!TextUtils.isEmpty(shareInfo.getNetImage())) {
            Bitmap b = returnBitmap(shareInfo.getNetImage());
            if (b != null) {
                msg.setThumbImage(b);
            }
        }
        Req req = new Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = platform == SharePlatform.WXFriend ? Req.WXSceneSession : Req.WXSceneTimeline;
        wxapi.sendReq(req);
    }

    /**
     * QQ好友分享，分享到QQ好友
     *
     * @param activity  Activity
     * @param shareInfo 分享的信息
     */
    public static void shareQQ(final Activity activity, final ShareInfoType shareInfo) {
        Tencent tencent = Tencent.createInstance(QQ, activity.getApplicationContext());
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareInfo.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareInfo.getContent());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareInfo.getUrl());
        if (!TextUtils.isEmpty(shareInfo.getNetImage())) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareInfo.getNetImage());
        }
        tencent.shareToQQ(activity, params, null);
    }

    /**
     * QQ空间分享，分享到QQ空间
     *
     * @param activity  Activity
     * @param shareInfo 分享的信息
     */
    public static void shareQZone(Activity activity, ShareInfoType shareInfo) {
        Tencent tencent = Tencent.createInstance(QQ, activity.getApplicationContext());
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareInfo.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareInfo.getContent());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareInfo.getUrl());
        ArrayList<String> imgs = new ArrayList<>();
        imgs.add(shareInfo.getNetImage());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgs);
        tencent.shareToQzone(activity, params, null);
    }

    /**
     * 短信分享，分享到短信
     *
     * @param activity  Activity
     * @param shareInfo 分享的信息
     */
    public static void shareMessage(Activity activity, ShareInfoType shareInfo) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", shareInfo.getTitle() + "\n" + shareInfo.getUrl());
        activity.startActivity(intent);
    }


    /**
     * 新浪微博分享，分享到新浪微博
     *
     * @param activity  Activity
     * @param shareInfo 分享的信息
     */
    public static void shareSinaWeibo(final Activity activity, ShareInfoType shareInfo, IWeiboShareAPI wbapi, AuthInfo authInfo, Oauth2AccessToken accessToken) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = shareInfo.getTitle() + "\n" + shareInfo.getContent();
        weiboMessage.textObject = textObject;

        if (shareInfo.getLocalImage() == null) {
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(shareInfo.getLocalImage());
            weiboMessage.imageObject = imageObject;
        }

        WebpageObject webPage = new WebpageObject();
        webPage.identify = Utility.generateGUID();

        webPage.actionUrl = shareInfo.getUrl();
        weiboMessage.mediaObject = webPage;

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;


//        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(activity.getApplicationContext());

        if (wbapi.isWeiboAppInstalled()) { // 已安装微博客户端
            wbapi.registerApp();
            try {
                if (wbapi.getWeiboAppSupportAPI() >= 10351) {
                    Log.e("CCCCCCCCCCC", "客户端分享");
                    wbapi.sendRequest(activity, request);
                } else {
                    Toast.makeText(activity, "SDK不支持", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {// 未安装微博客户端
            wbapi.sendRequest(activity, request, authInfo, accessToken.getToken(), new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException arg0) {
                    Toast.makeText(activity, " 分享失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete(Bundle bundle) {
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(activity.getApplicationContext(), newToken);
                    Toast.makeText(activity, " 分享成功", Toast.LENGTH_SHORT).show();  // TODO 分享成功
                }

                @Override
                public void onCancel() {
                    Toast.makeText(activity, " 分享取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static Bitmap returnBitmap(@NonNull String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
