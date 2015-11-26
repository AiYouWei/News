package com.news.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.SubjectAdapter;
import com.news.entities.SubjectType;
import com.news.utils.HttpCallback;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.views.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 专题速递
 *
 * @author slioe shu
 */
public class SubjectContentFragment extends BaseFragment implements OnItemClickListener {
    private SubjectAdapter adapter;
    private FinalHttp http;
    private JsonParser parser;
    private XListView xlvContent;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_subject_content);

        http = new FinalHttp();
        parser = JsonParser.getInstance();

        xlvContent = findView(R.id.xlvContent);
        xlvContent.setXListViewConfig(false, false, false);

        adapter = new SubjectAdapter(getActivity(), R.layout.adapter_subject_list);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }


    @Override
    protected void loadData() {
        super.loadData();

        http.post(TYUrls.COLUMN_LIST, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Type listType = new TypeToken<LinkedList<SubjectType>>() {
                    }.getType();
                    List<SubjectType> vts = parser.parseFromJson(json.optString("speciallist"), listType);
                    adapter.setData(vts);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SubjectType st = adapter.getItem(position - xlvContent.getHeaderViewsCount());
        if ("0".equals(st.getSpecialtype())) {
            Bundle web = new Bundle();
            web.putString("url", st.getSpeciallink());
            web.putString("title", st.getSpecialtitle());
            startActivityByUri(TYUris.WEBVIEW, web);
        } else if ("1".equals(st.getSpecialtype())) {
            Bundle news = new Bundle();
            news.putString("ID", st.getSpecialid());
            news.putString("title", st.getSpecialtitle());
            startActivityByUri(TYUris.COLUMN_NEWS);
        } else if ("2".equals(st.getSpecialtype())) {
            Bundle web = new Bundle();
            web.putString("url", TYUrls.BASE_URL + "?r=vote/share&sid=" + st.getSpecialid());
            web.putString("id", st.getSpecialid());
            web.putString("title", st.getSpecialtitle());
            startActivityByUri(TYUris.SUBJECT_VOTE, web);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", st.getSpecialid());
            bundle.putString("title", st.getSpecialtitle());
            startActivityByUri(TYUris.SUBJECT_DETAIL, bundle);
        }
    }
}
