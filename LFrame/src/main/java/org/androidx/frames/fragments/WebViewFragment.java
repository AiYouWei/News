package org.androidx.frames.fragments;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;

import org.androidx.frames.R;
import org.androidx.frames.base.BaseTitleFragment;
import org.androidx.frames.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示网页
 *
 * @author slioe shu
 */
public class WebViewFragment extends BaseTitleFragment {
    private WebView wvContent;
    private List<String> urls;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultForUri(Activity.RESULT_OK, new Bundle());
                getActivity().finish();
            }
        });
        addDefaultMiddle(getArguments().getString("title"));
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_webview_layout);
        String url = getArguments().getString("url");
        urls = new ArrayList<>();
        urls.add(url);

        wvContent = findView(R.id.wvContent);

        wvContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        WebSettings webSettings = wvContent.getSettings();
        WebView wvContent = findView(R.id.wvContent);
        wvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        wvContent.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        wvContent.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        wvContent.getSettings().setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        wvContent.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        wvContent.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        wvContent.getSettings().setUseWideViewPort(true);
        wvContent.getSettings().setAppCacheEnabled(true);//是否使用缓存
        wvContent.getSettings().setDomStorageEnabled(true);//DOM Storage
        wvContent.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);//

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

        LogUtil.e(true, "WebViewFragment", "p " + url);
    }

    /**
     * 自定义WebViewClient
     */
    private class AutoWebViewClient extends WebViewClient {
        public AutoWebViewClient() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
        } else {
            ViewGroup view = (ViewGroup) getActivity().getWindow().getDecorView();
            view.removeAllViews();
            setResultForUri(Activity.RESULT_OK, new Bundle());
            getActivity().finish();
        }
    }
}