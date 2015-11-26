package com.news.fragments;

import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.adapters.PaperContentAdapter;
import com.news.entities.ContentType;
import com.news.utils.HttpCallback;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 本期目录
 *
 * @author slioe shu
 */
public class CurrentContentFragment extends BaseFragment implements OnGroupClickListener, OnClickListener {
    private PaperContentAdapter adapter;
    private String date;
    private TextView tvTime, tvDown;
    private FinalHttp http;
    private JsonParser parser;
    private ExpandableListView elvContent;
    private List<ContentType> banners;
    private TYSettings settings;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_current_content);

        http = new FinalHttp();
        parser = JsonParser.getInstance();
        settings = TYAppliaction.getInstance().getSettings();

        elvContent = findView(R.id.elvContent);

        tvDown = findView(R.id.tvDown);
        tvTime = findView(R.id.tvTime);

        adapter = new PaperContentAdapter(getActivity());
        elvContent.setAdapter(adapter);
        elvContent.setOnGroupClickListener(this);

        tvDown.setOnClickListener(this);

        update(getArguments().getString("date"));
    }

    public void update(String date) {
        this.date = date;
        tvTime.setText(date);
        String time = date.replace("年", "").replace("月", "").replace("日", "").substring(0, 8);
        String url = TYUrls.BASE_URL + "?r=news/listall&date=" + time;
        // 获取版面信息
        http.post(url, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    Type listType = new TypeToken<LinkedList<ContentType>>() {
                    }.getType();
                    banners = jsonParser.parseFromJson(json.optString("pages"), listType);
                    for (int i = 0; i < banners.size(); i++) {
                        elvContent.collapseGroup(i);
                    }
                    adapter.setDatas(banners);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        for (int i = 0; i < banners.size(); i++) {
            if (groupPosition != i) {
                elvContent.collapseGroup(i);
            } else {
                elvContent.expandGroup(groupPosition);
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        tvDown.setEnabled(false);

        String down = settings.DOWN_LIST.getValue();
        if (TextUtils.isEmpty(down)) {
            down = date;
        } else if (!down.contains(date)) {
            down = down.concat("#").concat(date);
        }
        settings.DOWN_LIST.setValue(down);

        ToastUtil.ToastShort("开始下载" + tvTime.getText().toString().substring(0, 11) + " 的报纸");
        final String time = date.replace("年", "").replace("月", "").replace("日", "").substring(0, 8);
        String url = TYUrls.BASE_URL + "/assets/pdf/" + time + ".pdf ";
        LogUtil.e(true, "CurrentContentFragment", "onClick 下载地址 = " + url);
        String path = Environment.getExternalStorageDirectory().getPath() + "/TYNews/papers/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        http.download(url, path + time + ".pdf", new AjaxCallBack<File>() {
            @Override
            public AjaxCallBack<File> progress(boolean progress, int rate) {
                return super.progress(true, 1);
            }

            @Override
            public void onLoading(long count, long current) {
            }

            @Override
            public void onSuccess(File file) {
                super.onSuccess(file);
                ToastUtil.ToastShort("报纸下载完成");
                tvDown.setEnabled(true);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                tvDown.setEnabled(true);
            }
        });
    }
}
