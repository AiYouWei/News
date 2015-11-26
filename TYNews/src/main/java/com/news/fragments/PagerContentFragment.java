package com.news.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.PageSelectorAdapter;
import com.news.utils.HttpCallback;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.views.XListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 报纸版面
 *
 * @author slioe shu
 */
public class PagerContentFragment extends BaseFragment implements OnClickListener {
    private WebView wvContent;
    private ArrayAdapter adapter;
    private TextView tvDate, tvPage, tvSelector;
    private PopupWindow selectUI;
    private String date;
    private String page = "";
    private List<String> pnums;
    private FinalHttp http;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_pager_content);

        wvContent = findView(R.id.wvContent);
        tvDate = findView(R.id.tvDate);
        tvPage = findView(R.id.tvPage);
        tvSelector = findView(R.id.tvSelector);
        http = new FinalHttp();

        WebSettings webSettings = wvContent.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        wvContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvContent.setWebViewClient(new AutoWebViewClient());
        wvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        wvContent.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        wvContent.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        wvContent.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        wvContent.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        wvContent.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        wvContent.getSettings().setAppCacheEnabled(true);//是否使用缓存
        wvContent.getSettings().setDomStorageEnabled(true);//DOM Storage
        wvContent.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);//

        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sex, R.layout.adapter_spinner_sex);
        adapter.setDropDownViewResource(R.layout.adapter_spinner_sex);
        getPagerDesc("01");
        this.date = getArguments().getString("date");
        tvSelector.setOnClickListener(this);
    }

    public void update(String date, String page) {
        if (page == null) {
            page = this.page;
        }
        this.page = page;
        tvDate.setText(date);
        String time = date.replace("年", "").replace("月", "").replace("日", "").substring(0, 8);
        wvContent.loadUrl(TYUrls.BASE_URL + "?r=news/paper&date=" + time + "&pageNo=" + page);

        // 获取版本号
        String urlNum = TYUrls.BASE_URL + "?r=pname/pno&date=" + time;
        http.get(urlNum, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray array = json.optJSONArray("pageNos");
                    pnums = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject j = array.getJSONObject(i);
                        pnums.add(j.optString("pageNo"));
                    }
                    if (pnums.size() > 0) {
                        initSelector();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            if (url.startsWith("http://www.baidu.com/?id")) {
                Bundle normal = new Bundle();
                normal.putString("ID", url.substring(url.indexOf("=") + 1));
                startActivityByUri(TYUris.NEWS_IMAGE, normal);
            } else {
                view.loadUrl(url);
            }
            HitTestResult hit = wvContent.getHitTestResult();
            int hitType = hit.getType();
            if (hit.getType() != 0) { // 非重定向
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {
            handler.proceed();//  接受证书, 打开https
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    private void initSelector() {
        final View view = View.inflate(getActivity(), R.layout.popup_page_selector, null);
        final XListView xlvContent = (XListView) view.findViewById(R.id.xlvContent);
        xlvContent.setXListViewConfig(false, false, false);
        PageSelectorAdapter adapter = new PageSelectorAdapter(getActivity(), R.layout.adapter_selector_text, pnums);
        xlvContent.setAdapter(adapter);


        selectUI = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        selectUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectUI.setFocusable(true);
        selectUI.setOutsideTouchable(true);
        selectUI.setTouchable(true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUI.dismiss();
            }
        });

        xlvContent.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pageNo = pnums.get(position - xlvContent.getHeaderViewsCount());
                getPagerDesc(pageNo);
                selectUI.dismiss();
            }
        });
    }

    private void getPagerDesc(final String pageNo) {
        String url = TYUrls.BASE_URL + "?r=pname/view&date=" + date + "&pageNo=" + pageNo;
        http.get(url, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                update(date, pageNo);
                tvPage.setText(pageNo + "版  " + response);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelector:
                if (selectUI.isShowing()) {
                    selectUI.dismiss();
                } else {
                    selectUI.showAsDropDown(v);
                }
                break;
            default:
                break;
        }
    }
}
