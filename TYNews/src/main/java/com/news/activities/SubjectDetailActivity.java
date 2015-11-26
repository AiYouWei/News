package com.news.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.SubjectDetailAdapter;
import com.news.entities.NewsBType;
import com.news.entities.NewsBType.Item;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;

/**
 * 专题报道
 *
 * @author slioe shu
 */
public class SubjectDetailActivity extends BaseTitleActivity implements OnGroupClickListener {
    private String sid;
    private FinalHttp http;
    private ExpandableListView elvContent;
    private SubjectDetailAdapter adapter;
    private ImageView ivHeader;
    private WebView wvHeader;
    private JsonParser parser;
    private FinalBitmap loader;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_subject_detail);

        http = new FinalHttp();
        sid = getIntent().getStringExtra("TYPE");
        parser = JsonParser.getInstance();
        loader = FinalBitmap.create(getActivity());

        View header = View.inflate(getActivity(), R.layout.subject_detail_header, null);
        ivHeader = (ImageView) header.findViewById(R.id.ivHeader);
        wvHeader = (WebView) header.findViewById(R.id.wvHeader);
        elvContent = findView(R.id.elvContent);
        adapter = new SubjectDetailAdapter(getActivity());
        elvContent.setAdapter(adapter);
        elvContent.addHeaderView(header);

        elvContent.setOnGroupClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        LogUtil.e(true, "SubjectDetailActivity", "loadData sid = " + sid);
        http.post(TYUrls.COLUMN_NEWSB, new HttpParams("specialid", sid).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                if (response.contains("error_code")) {
                    ToastUtil.ToastShort("该专题不存在");
                    finish();
                    return;
                }
                NewsBType newsb = parser.parseFromJson(response, NewsBType.class);
                if (newsb != null) {
                    loader.display(ivHeader, newsb.getImg());
                    wvHeader.loadData(newsb.getIntro(), "text/html; charset=UTF-8", null);
                    adapter.setDatas(newsb.getItem());
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < newsb.getItem().size(); i++) {
                        elvContent.expandGroup(i);
                    }
                }
            }
        });
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Item item = adapter.getGroup(groupPosition);
        Bundle bundle = new Bundle();
        bundle.putString(MoreSubjectActivity.MORE_ID, item.getItem_tid());
        bundle.putString(MoreSubjectActivity.MORE_TITLE, item.getItem_title());
        startActivityByUri(TYUris.MORE_SUBJECT, bundle);
        return true;
    }
}
