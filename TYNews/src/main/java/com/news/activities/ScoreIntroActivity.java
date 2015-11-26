package com.news.activities;

import android.text.Html;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUrls;
import com.news.utils.HttpCallback;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author slioe shu
 */
public class ScoreIntroActivity extends BaseTitleActivity {
    private TextView tvIntro;
    private FinalHttp http;

    @Override
    protected void initTitles() {
        super.initTitles();
        PushAgent.getInstance(getActivity()).onAppStart();
        addDefaultLeft(null);
        addDefaultMiddle("积分规则说明");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_score_intro);

        http = new FinalHttp();

        tvIntro = findView(R.id.tvIntro);
    }

    @Override
    protected void loadData() {
        super.loadData();
        http.get(TYUrls.SCORE_INTRO, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    tvIntro.setText(Html.fromHtml(json.optString("intro")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
