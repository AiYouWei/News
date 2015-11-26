package com.news.activities;

import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;

import com.TYDaily.R;
import com.news.TYUrls;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.news.utils.ShareUtils;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.libs.share.ShareInfoType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slioe shu
 */
public class VoteActivity extends BaseTitleActivity {
    private WebView wvContent;
    private List<String> urls;
    private boolean isFirst = true;
    private String sid;
    private FinalHttp http;
    private String json;
    private String id;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle(getIntent().getStringExtra("title"));
        addTextToRight("分享", rightListener);
    }

    private OnClickListener rightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.e(true, "VoteActivity", "onClick response = " + json + "   " + isFirst);
            /* "shareTitle": "候选人二",
    "shareSubtitle": "候选人二 介绍",
    "shareImg": "http://123.57.17.124/tydaily2/sites/default/files/styles/thumbnail/public/p860212125.jpg",
    "shareLink": "http://123.57.17.124/epaper2/index.php?r=vote/ishare&id=291"*/

            try {
                JSONObject j = new JSONObject(json);
                if (!TextUtils.isEmpty(json)) {
                    ShareInfoType shareInfo = new ShareInfoType();
                    shareInfo.setTitle(j.optString("shareTitle"));
                    shareInfo.setContent(j.optString("shareSubtitle"));
                    shareInfo.setUrl(j.optString("shareLink"));
                    shareInfo.setNetImage(j.optString("shareImg"));
                    ShareUtils.showShare(getActivity(), shareInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_webview_layout);

        http = new FinalHttp();
        String url = getIntent().getStringExtra("url");
        sid = getIntent().getStringExtra("id");
        urls = new ArrayList<>();
        urls.add(url);

        wvContent = findView(R.id.wvContent);

        wvContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        WebSettings webSettings = wvContent.getSettings();
        WebView wvContent = findView(R.id.wvContent);
        wvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        wvContent.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        wvContent.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        wvContent.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        wvContent.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        wvContent.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        wvContent.getSettings().setAppCacheEnabled(true);//是否使用缓存
        wvContent.getSettings().setDomStorageEnabled(true);//DOM Storage

        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        wvContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvContent.setWebViewClient(new AutoWebViewClient());
        try {
            wvContent.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadData11(); // 获取分享数据
    }

    private void loadData11() {
        String url;
        HttpParams params = new HttpParams();
        if (isFirst) {
            url = TYUrls.VOTE_PARA;
            params.put("sid", sid);
        } else {
            url = TYUrls.VOTE_ITEM;
            params.put("id", id);
        }

        http.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                json = response;
            }
        });
    }

    /**
     * 自定义WebViewClient
     */
    private class AutoWebViewClient extends WebViewClient {
        public AutoWebViewClient() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            HitTestResult hit = wvContent.getHitTestResult();
            int hitType = hit.getType();
            if (hit.getType() != 0) { // 非重定向
                urls.add(url);
                isFirst = false;
                LogUtil.e(true, "AutoWebViewClient", "shouldOverrideUrlLoading url = " + url);
                id = url.substring(url.indexOf("id=291") + 3);
                LogUtil.e(true, "AutoWebViewClient", "shouldOverrideUrlLoading id = " + id);
                loadData11();
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {
            handler.proceed();//  接受证书, 打开https
        }
    }

    public void onBackPressed() {
        if (wvContent.canGoBack() && urls.size() > 1) {
            urls.remove(urls.size() - 1);
            wvContent.goBack();
            isFirst = true;
            loadData11();
        } else {
            ViewGroup view = (ViewGroup) getActivity().getWindow().getDecorView();
            view.removeAllViews();
            getActivity().finish();
        }
    }
}
