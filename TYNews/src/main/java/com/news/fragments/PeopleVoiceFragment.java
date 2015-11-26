package com.news.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.PeopleVoiceAdapter;
import com.news.entities.VoiceType;
import com.news.fragments.TabVoiceFragment.Category;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.views.XListView;
import org.androidx.frames.views.XListView.IXListViewListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻线索
 *
 * @author slioe shu
 */
public class PeopleVoiceFragment extends BaseFragment implements OnItemClickListener, IXListViewListener {
    public final static String KEY_CATEGORY = "category";
    private Category category;
    private XListView xlvContent;
    private String last = "";
    private PeopleVoiceAdapter adapter;
    private FinalHttp http;
    private TYSettings settings;
    private JsonParser parser;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_people_voice);

        category = (Category) getArguments().getSerializable(KEY_CATEGORY);
        http = new FinalHttp();
        settings = TYAppliaction.getInstance().getSettings();
        parser = JsonParser.getInstance();

        xlvContent = findView(R.id.xlvContent);
        xlvContent.setXListViewConfig(true, true, true);
        xlvContent.setXListViewListener(this);
        adapter = new PeopleVoiceAdapter(getActivity(), R.layout.adapter_people_voice, category);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        loadData("");
    }

    private void loadData(final String last) {
        String url;
        switch (category) {
            case CLUE:
                url = TYUrls.CLUE_LIST;
                break;
            case URBAN:
                url = TYUrls.URBAN_LIST;
                break;
            case SUP:
                url = TYUrls.SUP_LIST;
                break;
            default:
                url = TYUrls.CLUE_LIST;
                break;
        }
        final HttpParams params = new HttpParams().addCommonParam();
        if (!TextUtils.isEmpty(last)) {
            params.put("lastnewsid", last);
        }
        if (category == Category.SUP && !TextUtils.isEmpty(settings.USER_ID.getValue())) {
            params.put("uid", settings.USER_ID.getValue());
        }
        http.post(url, params, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Type listType = new TypeToken<LinkedList<VoiceType>>() {
                    }.getType();
                    List<VoiceType> vts = parser.parseFromJson(json.optString("articles"), listType);
                    if (vts.size() < 8) {
                        xlvContent.setEnableLoad(false);
                    }
                    if (TextUtils.isEmpty(last)) {
                        adapter.setData(vts);
                        xlvContent.stopRefresh();
                    } else {
                        adapter.addAll(vts);
                        xlvContent.stopLoadMore();
                    }
                    if (vts.isEmpty()) {

                    } else {
                        PeopleVoiceFragment.this.last = vts.get(vts.size() - 1).getId();
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CATEGORY, category);
        bundle.putString("ID", adapter.getItem(position - xlvContent.getHeaderViewsCount()).getId());
        startActivityByUri(TYUris.VOICE_DETAIL, bundle);
    }

    @Override
    public void onRefresh() {
        loadData("");
    }

    @Override
    public void onLoadMore() {
        loadData(last);
    }
}
