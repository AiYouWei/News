package com.news.activities;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.adapters.NewsSearchAdapter;
import com.news.entities.SearchType;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻搜索
 *
 * @author slioe shu
 */
public class NewsSearchActivity extends BaseTitleActivity implements OnClickListener, OnItemClickListener, OnEditorActionListener {
    private NewsSearchAdapter adapter;
    private XListView xlvContent;
    private EditText etSearch;
    private View footerView;
    private boolean hasFooter = true;
    private FinalHttp http;
    private JsonParser parser;
    private TYSettings settings;
    private final static String KEY = "[@]";

    @Override
    protected void initTitles() {
        super.initTitles();
        View view = View.inflate(this, R.layout.view_news_search, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.clearFocus();
        etSearch.setOnEditorActionListener(this);
        tvCancel.setOnClickListener(this);
        addViewToMiddle(view, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        http = new FinalHttp();
        parser = JsonParser.getInstance();
        settings = TYAppliaction.getInstance().getSettings();

        setContentView(R.layout.activity_news_search);
        xlvContent = (XListView) findViewById(R.id.xlvContent);
        xlvContent.setXListViewConfig(false, false, false);

        adapter = new NewsSearchAdapter(this, R.layout.adapter_news_search);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        String search = settings.NEWS_SEARCH.getValue();
        if (!TextUtils.isEmpty(search)) {
            List<SearchType> searchs = new ArrayList<>();
            search = search.substring(3);
            if (search.contains(KEY)) {
                String[] items = search.split(KEY);
                for (int i = 0; i < items.length; i++) {
                    searchs.add(new SearchType(items[i]));
                }
            } else {
                searchs.add(new SearchType(search));
            }
            adapter.setData(searchs);
            adapter.notifyDataSetChanged();

            // 添加底部删除按钮
            footerView = View.inflate(this, R.layout.adapter_search_foot, null);
            if (hasFooter) {
                listFooterView(adapter.getCount());
            }
            footerView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvClean:
                adapter.removeAll();
                listFooterView(0);
                settings.NEWS_SEARCH.setValue("");
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 添加搜索历史的底部清除按钮
     *
     * @param count adapter中item的数量
     */
    public void listFooterView(int count) {
        if (adapter.getCount() > 0) {
            xlvContent.addFooterView(footerView);
            hasFooter = false;
        } else {
            xlvContent.removeFooterView(footerView);
            hasFooter = true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SearchType st = adapter.getItem(position - xlvContent.getHeaderViewsCount());
        if (TextUtils.isEmpty(st.getNid())) {
            adapter.remove(st);
            String s = settings.NEWS_SEARCH.getValue();
            if (s.contains(KEY + st.getTitle())) {
                s = s.replace(KEY + st.getTitle(), "");
                settings.NEWS_SEARCH.setValue(s);
            }
            if (adapter.getCount() == 0) {
                listFooterView(0);
            }
            adapter.notifyDataSetChanged();
        } else {
            // TODO: 2015-11-01 新闻详情 只有 1 普通新闻 2多图新闻
            ToastUtil.ToastShort("position = " + position);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        final String search = etSearch.getText().toString();
        if (!TextUtils.isEmpty(search) && (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NEXT)) {
            http.post(TYUrls.NEWS_SEARCH, new HttpParams("keyword", search).addCommonParam(), new HttpCallback() {
                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                }

                @Override
                public void onSuccess(String response) {
                    super.onSuccess(response);
                    settings.NEWS_SEARCH.setValue(KEY + search);
                    try {
                        JSONObject json = new JSONObject(response);
                        Type listType = new TypeToken<LinkedList<SearchType>>() {
                        }.getType();
                        List<SearchType> banners = parser.parseFromJson(json.optString("results"), listType);
                        adapter.setData(banners);
                        adapter.notifyDataSetChanged();
                        listFooterView(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return false;
    }
}
