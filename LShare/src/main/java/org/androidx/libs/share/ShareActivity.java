package org.androidx.libs.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.shulq.sharesdk.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

import org.androidx.libs.share.core.AccessTokenKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author slioe shu
 */
public class ShareActivity extends Activity implements IWeiboHandler.Request, Response {
    public static final String SHARE_INFO = "info";
    private String WB = "2550864631";
    private IWeiboShareAPI wbapi;
    String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    String SCOPE = "email,direct_messages_read,direct_messages_write, friendships_groups_read,friendships_groups_write," +
            "statuses_to_me_read, follow_app_official_microblog, invitation_write";
    SsoHandler mSsoHandler;
    AuthInfo authInfo;
    ShareInfoType shareInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_share);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        TextView tvCancel = (TextView) findViewById(R.id.tvCancel);
        TextView tvWXFriend = (TextView) findViewById(R.id.tvWXFriend);
        TextView tvWXGroup = (TextView) findViewById(R.id.tvWXGroup);
        TextView tvQQ = (TextView) findViewById(R.id.tvQQ);
        TextView tvQZone = (TextView) findViewById(R.id.tvQZone);
        TextView tvSinaWeibo = (TextView) findViewById(R.id.tvSinaWeibo);
        TextView tvMessage = (TextView) findViewById(R.id.tvMessage);

        shareInfo = (ShareInfoType) getIntent().getSerializableExtra(SHARE_INFO);
        Log.e("分享信息", shareInfo.toString());

        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvWXFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.shareToWXFriend(ShareActivity.this, shareInfo);
                finish();
            }
        });
        tvWXGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.shareToWXGroup(ShareActivity.this, shareInfo);
                finish();
            }
        });
        tvQQ.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.shareToQQ(ShareActivity.this, shareInfo);
                finish();
            }
        });
        tvQZone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.shareToQZone(ShareActivity.this, shareInfo);
                finish();
            }
        });
        tvSinaWeibo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                authInfo = new AuthInfo(ShareActivity.this, WB, REDIRECT_URL, SCOPE);
                mSsoHandler = new SsoHandler(ShareActivity.this, authInfo);
                // SSO 授权, 仅客户端
                mSsoHandler.authorizeClientSso(new AuthListener());
                wbapi = WeiboShareSDK.createWeiboAPI(ShareActivity.this, WB);

                wbapi.registerApp();
                wbapi.handleWeiboResponse(getIntent(), ShareActivity.this);
            }
        });
        tvMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.shareToMessage(ShareActivity.this, shareInfo);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        wbapi.handleWeiboRequest(getIntent(), this);
    }

    @Override
    public void onRequest(BaseRequest baseRequest) {
//        switch (baseRequest.e) {
//            case WBConstants.ErrorCode.ERR_OK:
//                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//                Toast.makeText(this, "失败" + baseResp.errMsg, Toast.LENGTH_LONG)
//                        .show();
//                break;
//        }
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
                Log.e("aaaaaaaaaaa", "1");
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
                Log.e("aaaaaaaaaaa", "2");
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this, "失败" + baseResp.errMsg, Toast.LENGTH_LONG)
                        .show();
                Log.e("aaaaaaaaaaa", "3");
                break;
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            // 判断AccessToken是否有效
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(ShareActivity.this, mAccessToken);
                Toast.makeText(ShareActivity.this, "获取token成功", Toast.LENGTH_SHORT).show();
                // TODO
                shareSinaWeibo11(ShareActivity.this, shareInfo, wbapi, authInfo, mAccessToken);
                finish();
//                shareToSinaWeibo(ShareActivity.this, shareInfo, wbapi, authInfo, mAccessToken);
            } else {
                System.out.println("认证失败");
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(ShareActivity.this, "取消", Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(ShareActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    public void shareSinaWeibo11(final Activity activity, ShareInfoType shareInfo, IWeiboShareAPI wbapi, AuthInfo authInfo, Oauth2AccessToken accessToken) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = shareInfo.getTitle() + "\n" + shareInfo.getContent();
        weiboMessage.textObject = textObject;
        Bitmap b = null;

        if (shareInfo.getLocalImage() != null) {
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(shareInfo.getLocalImage());
            weiboMessage.imageObject = imageObject;
        } else if (!TextUtils.isEmpty(shareInfo.getNetImage())) {
            b = returnBitmap(shareInfo.getNetImage());
            if (b != null) {
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(b);
                weiboMessage.imageObject = imageObject;
            }
        }

        WebpageObject webPage = new WebpageObject();
        webPage.identify = Utility.generateGUID();

        webPage.title = shareInfo.getTitle();
        webPage.description = shareInfo.getContent();
        if (shareInfo.getLocalImage() != null) {
            webPage.setThumbImage(shareInfo.getLocalImage());
        } else if (!TextUtils.isEmpty(shareInfo.getNetImage())) {
            if (b != null) {
                webPage.setThumbImage(b);
            }
        }

        webPage.actionUrl = shareInfo.getUrl();
        weiboMessage.mediaObject = webPage;

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;


        if (wbapi.isWeiboAppInstalled()) { // 已安装微博客户端

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


    private Bitmap returnBitmap(@NonNull String url) {
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
