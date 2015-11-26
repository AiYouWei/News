package com.news.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYConstants.FontSize;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.utils.HttpCallback;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 我-设置
 *
 * @author slioe shu
 */
public class MineSettingActivity extends BaseTitleActivity implements OnClickListener {
    private final static int CODE_FONTSIZE = 0XAA;
    private TYSettings settings;
    private TextView tvFontSize;
    private int fontSize;
    private RelativeLayout rlAbout;
    private ImageView ivVer;
    private TextView tvVer;
    private RelativeLayout rlUpdate;
    private Button btnLogout;
    private FinalHttp http;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("设置");
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_mine_setting);

        settings = TYAppliaction.getInstance().getSettings();
        fontSize = settings.FONT_SIZE.getValue();
        http = new FinalHttp();

        RelativeLayout rlFontSize = findView(R.id.rlFontSize);
        RelativeLayout rlFeedback = findView(R.id.rlFeedback);
        tvFontSize = findView(R.id.tvFontSize);
        rlAbout = findView(R.id.rlAboutMe);
        tvVer = findView(R.id.tvVerNum);
        ivVer = findView(R.id.ivUpdateNew);
        rlUpdate = findView(R.id.rlUpdateVer);
        btnLogout = findView(R.id.btnLogout);

        setFontSize(fontSize, tvFontSize);
        rlFontSize.setOnClickListener(this);
        rlFeedback.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlFontSize:
                startActivityForResultByUri(TYUris.FONT_SIZE, CODE_FONTSIZE, BundleUtil.createBundle(FontSizeActivity.KEY_FONTSIZE, fontSize));
                break;
            case R.id.rlAboutMe:
                startActivityByUri(TYUris.ABOUT_US);
                break;
            case R.id.rlUpdateVer:
                update();
                break;
            case R.id.rlFeedback:
                startActivityByUri(TYUris.FEEDBACK);
                break;
            case R.id.btnLogout:
                settings.USER_HEAD.setValue("");
                settings.USER_SCORE.setValue("");
                settings.USER_NICK.setValue("");
                settings.USER_ID.setValue("");
                startActivityByUri(TYUris.USER_LOGIN);
                setResultForUri(RESULT_OK, new Bundle());
                finish();
                break;
            default:
                break;
        }
    }

    private void update() {
        http.post(TYUrls.APP_UPDATE, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if ("1".equals(json.optString("error_code"))) {
                        ToastUtil.ToastShort(json.optString("error_msg"));
                    } else {
                        http.download(json.optString("link"), null, null, new AjaxCallBack<File>() {
                            @Override
                            public boolean isProgress() {
                                return super.isProgress();
                            }

                            @Override
                            public int getRate() {
                                return super.getRate();
                            }

                            @Override
                            public AjaxCallBack<File> progress(boolean progress, int rate) {
                                return super.progress(progress, rate);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                            }

                            @Override
                            public void onLoading(long count, long current) {
                                super.onLoading(count, current);
                            }

                            @Override
                            public void onSuccess(File file) {
                                super.onSuccess(file);
                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                super.onFailure(t, errorNo, strMsg);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onReceiveForUri(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_FONTSIZE) {
                fontSize = data.getInt(FontSizeActivity.KEY_FONTSIZE, FontSize.NORMAL.ordinal());
                settings.FONT_SIZE.setValue(fontSize);
                setFontSize(fontSize, tvFontSize);
            }
        }
    }

    private void setFontSize(int fontSize, TextView tvFontSize) {
        if (fontSize == FontSize.LARGE.ordinal()) {
            tvFontSize.setText("超大");
        } else if (fontSize == FontSize.BIG.ordinal()) {
            tvFontSize.setText("大号");
        } else if (fontSize == FontSize.NORMAL.ordinal()) {
            tvFontSize.setText("中号");
        } else if (fontSize == FontSize.SMALL.ordinal()) {
            tvFontSize.setText("小号");
        } else {
            tvFontSize.setText("中号");
        }
    }
}
