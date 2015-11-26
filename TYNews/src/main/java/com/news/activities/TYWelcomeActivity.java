package com.news.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYConstants;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.entities.WelcomeType;
import com.news.entities.WelcomeType.Images;
import com.news.utils.AppUpdateUtil;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.news.utils.WeatherUtil;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.androidx.frames.JsonParser;
import org.androidx.frames.adapters.ViewsPagerAdapter;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.DialogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 欢迎页面
 *
 * @author slioe shu
 */
public class TYWelcomeActivity extends BaseActivity implements OnPageChangeListener {
    private final static long DELAY_MILLIS_WELCOME = 3000L;
    private final static long DELAY_MILLIS_ADVERT = 3000L;
    private ViewsPagerAdapter adapter;
    private List<ImageView> views;
    private static Handler runHandler = new Handler();
    private List<Images> images;
    private static int index = 0, totals = 1;
    private ViewPager vpContent;
    private Dialog updateDialog;
    private TYSettings settings;
    private FinalBitmap loader;
    private FinalHttp http;
    private JsonParser parser;
    private ProgressDialog dialog;
    private boolean isfinish = true;

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent mPushAgent = PushAgent.getInstance(getActivity());
        mPushAgent.enable();
        String device_token = UmengRegistrar.getRegistrationId(getActivity());
        PushAgent.getInstance(getActivity()).onAppStart();

        setContentView(R.layout.activity_ty_welcome);

        settings = TYAppliaction.getInstance().getSettings();
        loader = FinalBitmap.create(this);
        parser = JsonParser.getInstance();
        http = new FinalHttp();
        loadColumns();
        loadWeather();

        views = new ArrayList<>();

        vpContent = findView(R.id.vpContent);
        adapter = new ViewsPagerAdapter();
        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(this);
        vpContent.setCurrentItem(index);

        dialog = new ProgressDialog(getActivity());
        dialog.setIcon(R.mipmap.ty_icon);
        dialog.setTitle("正在下载更新文件");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
    }

    @Override
    protected void loadData() {
        super.loadData();
        index = 0;
        http.post(TYUrls.WELCOME, new HttpParams().addCommonParam(), new HttpCallback() {

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                images = new ArrayList<>();
                final WelcomeType wt = parser.parseFromJson(response, WelcomeType.class);
                for (int i = 0; i < wt.getAds().size(); i++) {
                    if (!TextUtils.isEmpty(wt.getAds().get(i).getImgUrl())) {
                        images.add(wt.getAds().get(i));
                    }
                }
                totals = images.size();
                if (totals == 0) {
                    startActivityByUri(TYUris.HOME_TABS);
                    if (runHandler != null) {
                        runHandler.removeCallbacks(skipNext);
                        runHandler = null;
                    }
                    finish();
                    return;
                }
                for (int i = 0; i < images.size(); i++) {
                    Images is = images.get(i);
                    final ImageView iv = new ImageView(getActivity());
                    iv.setTag(i);
                    iv.setScaleType(ScaleType.CENTER_CROP);
                    loader.display(iv, is.getImgUrl());
                    final String url = is.getClickUrl();
                    if (TextUtils.isEmpty(url)) {
                        iv.setOnClickListener(null);
                    } else {
                        iv.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                runHandler.removeCallbacks(skipNext);
                                startActivityForResultByUri(TYUris.WEBVIEW, 1, BundleUtil.createBundle("url", url, "title", ""));
                            }
                        });
                    }
                    views.add(iv);
                }
                totals = views.size();
                adapter.setData(views);
                adapter.notifyDataSetChanged();


                // 判断版本
                try {
                    int localVer = Integer.parseInt(TYConstants.APP_VER.replace(".", ""));
                    int serverVer = Integer.parseInt(wt.getVersionName().replace(".", ""));
                    if (localVer < serverVer) { // 下载APK进行升级
                        updateDialog = DialogUtil.showNormalDialog(getActivity(), "是否升级到最新版本", "残忍的拒绝", "去升级", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateDialog.dismiss();
                                runHandler.postDelayed(skipNext, DELAY_MILLIS_WELCOME);
                            }
                        }, new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                http.download(wt.getAppDownUrl(), getFilesDir() + "/update.apk", new AjaxCallBack<File>() {

                                    @Override
                                    public AjaxCallBack<File> progress(boolean progress, int rate) {
                                        return super.progress(true, 1);
                                    }

                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        dialog.show();
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        super.onFailure(t, errorNo, strMsg);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onSuccess(File t) {
                                        super.onSuccess(t);
                                        dialog.dismiss();
                                        AppUpdateUtil.installPackage(getActivity(), getFilesDir() + "/update.apk");
                                    }

                                    @Override
                                    public void onLoading(long count, long current) {
                                        dialog.setProgress((int) (current * 100 / count));
                                    }
                                });
                                updateDialog.dismiss();
                            }
                        });
                    } else {
                        runHandler.postDelayed(skipNext, DELAY_MILLIS_ADVERT);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 跳转
     */
    private Runnable skipNext = new Runnable() {
        @Override
        public void run() {
            index += 1;
            if (index < totals && isfinish) {
                vpContent.setCurrentItem(index);
                if (runHandler == null) {
                    runHandler = new Handler();
                }
                runHandler.postDelayed(skipNext, DELAY_MILLIS_ADVERT);
            } else {
                runHandler.removeCallbacks(skipNext);
                startActivityByUri(TYUris.HOME_TABS);
                finish();
            }
        }
    };

    /**
     * 加载新闻栏目
     */
    private void loadColumns() {
        http.post(TYUrls.NEWS_COLUMNS, new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    String columns = new JSONObject(response).optString("columns");
                    settings.ALL_COLUMNS.setValue(columns);
                    settings.CUSTOM_COLUMNS.setValue(columns);
                } catch (JSONException e) {
                }
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        if (index == images.size()) {
            isfinish = false;
            index += 1;
            startActivityByUri(TYUris.HOME_TABS);
            if (runHandler != null) {
                runHandler.removeCallbacks(skipNext);
                runHandler = null;
            }
            finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void finish() {
        super.finish();
        if (runHandler != null) {
            runHandler.removeCallbacks(skipNext);
            runHandler = null;
        }
    }

    private void loadWeather() {
        http.get(WeatherUtil.getUrl(), new com.news.utils.HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONArray array = new JSONObject(response).optJSONObject("f").optJSONArray("f1");
                    JSONObject json = array.getJSONObject(0);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINESE);
                    String time = format.format(new Date());
                    String weather = settings.WEATHER.getValue();
                    if (TextUtils.isEmpty(weather) || !weather.startsWith(time)) {
                        String a = json.optString("fa");
                        String b = json.optString("fb");
                        String c = json.optString("fc");
                        String d = json.optString("fd");
                        if (TextUtils.isEmpty(c)) {
                            c = "--";
                        }
                        if (TextUtils.isEmpty(a)) {
                            a = b;
                        }
                        settings.WEATHER.setValue(time + "#" + c + "°/" + d + "°#" + a + "@" + b);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onReceiveForUri(int requestCode, int resultCode, Bundle data) {
        if (RESULT_OK == resultCode && requestCode == 1) {
            runHandler.post(skipNext);
        }
    }
}
