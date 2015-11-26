package com.news.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.GovernmentAdapter;
import com.news.entities.GovernmentType;
import com.news.utils.HttpCallback;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 县区市局
 *
 * @author slioe shu
 */
public class GovernmentContentFragment extends BaseFragment implements OnItemClickListener {
    private FinalHttp http;
    private JsonParser parser;
    private GovernmentAdapter cityAdapter, orgenAdapter;


    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_government_content);

        http = new FinalHttp();
        parser = JsonParser.getInstance();

        GridView gvCity = findView(R.id.gvCity);
        GridView gvOrgen = findView(R.id.gvOrgan);

        cityAdapter = new GovernmentAdapter(getActivity(), R.layout.adapter_government_grid);
        gvCity.setAdapter(cityAdapter);
        orgenAdapter = new GovernmentAdapter(getActivity(), R.layout.adapter_government_grid);
        gvOrgen.setAdapter(orgenAdapter);

        gvCity.setOnItemClickListener(this);
        gvOrgen.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();

        http.get(TYUrls.COLUMN_CITY, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Type listType = new TypeToken<LinkedList<GovernmentType>>() {
                    }.getType();
                    List<GovernmentType> cities = parser.parseFromJson(json.optString("citys"), listType);
                    List<GovernmentType> orgens = parser.parseFromJson(json.optString("institutions"), listType);
                    cityAdapter.setData(cities);
                    cityAdapter.notifyDataSetChanged();
                    orgenAdapter.setData(orgens);
                    orgenAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.e(true, "GovernmentContentFragment", "onItemClick id = " + parent.getId());
        LogUtil.e(true, "GovernmentContentFragment", "onItemClick gvCityid = " + R.id.gvCity);
        LogUtil.e(true, "GovernmentContentFragment", "onItemClick gvOrganid = " + R.id.gvOrgan);

        GovernmentType gt;
        if (R.id.gvCity == parent.getId()) {
            gt = cityAdapter.getData().get(position);
        } else {
            gt = orgenAdapter.getData().get(position);
        }

        Bundle bundle = BundleUtil.createBundle(NewsContentFragment.KEY_NAME, gt.getName(), NewsContentFragment.KEY_TID, gt.getTid());
        startActivityByUri(TYUris.NEWS_CONTENT, bundle);
    }
}
