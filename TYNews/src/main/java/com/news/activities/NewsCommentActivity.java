package com.news.activities;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.adapters.NewsCommentAdapter;
import com.news.entities.Comment;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.frames.views.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 评论
 *
 * @author slioe shu
 */
public class NewsCommentActivity extends BaseTitleActivity implements OnClickListener {
    private NewsCommentAdapter adapter;
    private ArrayList<Comment> nnt;
    private EditText etComment;
    private FinalHttp http;
    private TYSettings settings;
    private String newsId;

    @Override
    protected void initTitles() {
        super.initTitles();
        barView.setBackgroundColor(Color.BLACK);
        addDefaultLeft(null);
        addDefaultMiddle("评论");
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_news_comment);

        http = new FinalHttp();
        settings = TYAppliaction.getInstance().getSettings();

        nnt = (ArrayList<Comment>) getIntent().getSerializableExtra("NAME");
        String t = getIntent().getStringExtra("T");
        String a = getIntent().getStringExtra("A");
        newsId = getIntent().getStringExtra("B");

        XListView xlvContent = findView(R.id.xlvContent);
        View header = View.inflate(this, R.layout.adapter_comment_header, null);
        xlvContent.addHeaderView(header);
        xlvContent.setXListViewConfig(false, false, false);
        TextView tvT = (TextView) header.findViewById(R.id.tvT);
        TextView tvA = (TextView) header.findViewById(R.id.tvA);
        Button btnComment = findView(R.id.btnComment);
        etComment = findView(R.id.etComment);

        btnComment.setOnClickListener(this);

        tvT.setText(t);
        if (TextUtils.isEmpty(a)) {
            tvA.setVisibility(View.GONE);
        } else {
            tvA.setText(a);
        }

        adapter = new NewsCommentAdapter(this, R.layout.adapter_comment_item);
        xlvContent.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnComment:
                if (TextUtils.isEmpty(etComment.getText().toString())) {
                    ToastUtil.ToastShort("请输入评论内容");
                    return;
                }
                String url = TYUrls.BASE_URL + "?r=news/addcomt";
                HttpParams params = new HttpParams("comtuserid", settings.USER_ID.getValue(), "newsid", newsId, "comtcontent", etComment.getText().toString(), "parentcomtid", "0");
                http.post(url, params, new HttpCallback() {
                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }

                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);
                        try {
                            etComment.setText("");
                            JSONObject json = new JSONObject(response);
                            if ("0".equals(json.optString("error_code"))) {
                                ToastUtil.ToastShort("评论成功");
                            } else {
                                ToastUtil.ToastShort(json.optString("error_msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        adapter.setData(nnt);
        adapter.notifyDataSetChanged();
    }
}
