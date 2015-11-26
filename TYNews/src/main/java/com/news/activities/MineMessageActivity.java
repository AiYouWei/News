package com.news.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.adapters.MineMessageAdapter;
import com.news.entities.CollectionType;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.frames.views.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 我的消息
 *
 * @author slioe shu
 */
public class MineMessageActivity extends BaseTitleActivity implements OnItemClickListener {
    private MineMessageAdapter adapter;
    private JsonParser parser;
    private FinalHttp http;
    private TYSettings settings;


    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("我的信息");
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_mine_message);

        parser = JsonParser.getInstance();
        http = new FinalHttp();
        settings = TYAppliaction.getInstance().getSettings();

        XListView xlvContent = findView(R.id.xlvContent);

        xlvContent.setXListViewConfig(false, false, false);
        adapter = new MineMessageAdapter(this, R.layout.adapter_mine_message);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();

        http.post(TYUrls.MINE_MSG, new HttpParams("uid", settings.USER_ID.getValue()).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    if ("0".equals(json.optString("error_code"))) {
                        Type listType = new TypeToken<LinkedList<CollectionType>>() {
                        }.getType();
                        List<CollectionType> cts = parser.parseFromJson(json.optString("list"), listType);
                        adapter.setData(cts);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.ToastShort(json.optString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.ToastShort("position = " + position);
    }
}
