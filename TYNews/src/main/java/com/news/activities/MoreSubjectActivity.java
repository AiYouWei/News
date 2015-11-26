package com.news.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.MoreSubjectAdapter;
import com.news.entities.NewsBType.Item.NewsItem;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.views.XListView;
import org.androidx.frames.views.XListView.IXListViewListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * @author slioe shu
 */
public class MoreSubjectActivity extends BaseTitleActivity implements IXListViewListener, OnItemClickListener {
    public static final String MORE_ID = "id";
    public static final String MORE_TITLE = "title";
    private XListView xlvContent;
    private FinalHttp http;
    private FinalBitmap loader;
    private JsonParser parser;
    private MoreSubjectAdapter adapter;
    private String item_tid, lastnewsid = "";

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle(getIntent().getStringExtra(MORE_TITLE));
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_more_subject);

        http = new FinalHttp();
        parser = JsonParser.getInstance();
        loader = FinalBitmap.create(getActivity());

        item_tid = getIntent().getStringExtra(MORE_ID);

        xlvContent = findView(R.id.xlvContent);
        xlvContent.setXListViewConfig(true, true, true);
        xlvContent.setXListViewListener(this);
        adapter = new MoreSubjectAdapter(getActivity());
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);

        loadData1();
    }

    private void loadData1() {
        http.post(TYUrls.SUBJECT_MORE, new HttpParams("item_tid", item_tid, "lastnewsid", lastnewsid), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Type listType = new TypeToken<LinkedList<NewsItem>>() {
                    }.getType();
                    List<NewsItem> cities = parser.parseFromJson(json.optString("item_news"), listType);

                    xlvContent.stopLoadMore();
                    xlvContent.stopRefresh();
                    if (cities == null || cities.isEmpty()) {
                        xlvContent.setEnableLoad(false);
                        return;
                    }

                    if (TextUtils.isEmpty(lastnewsid)) {
                        adapter.setData(cities);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.addAll(cities);
                        adapter.notifyDataSetChanged();
                    }
                    lastnewsid = cities.get(cities.size() - 1).getNews_id();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        lastnewsid = "";
        loadData1();

    }

    @Override
    public void onLoadMore() {
        loadData1();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle normal = new Bundle();
        normal.putString("ID", adapter.getData().get(position - xlvContent.getHeaderViewsCount()).getNews_id());
        startActivityByUri(TYUris.NEWS_IMAGE, normal);
    }
}
