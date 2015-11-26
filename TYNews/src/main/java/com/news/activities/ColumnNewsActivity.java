package com.news.activities;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.adapters.NewsBannerAdapter;
import com.news.adapters.NewsListAdapter;
import com.news.adapters.ViewType;
import com.news.entities.NewsBannerType;
import com.news.entities.NewsListType;
import com.news.fragments.NewsBannerHandler;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.views.XListView;
import org.androidx.frames.views.XListView.IXListViewListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author slioe shu
 */
public class ColumnNewsActivity extends BaseTitleActivity implements IXListViewListener, OnItemClickListener {
    private XListView xlvContent;
    private int lastnewsid = 0;
    private View headBanner;
    private ViewPager vpContent;
    private Handler banner;
    private final static long BANNER_DELAYMILLIS = 3000L;
    private NewsBannerHandler bannerHandler;
    private int position = 1;
    private NewsListAdapter adapter;
    private FinalHttp http;
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
        setContentView(R.layout.activity_column_news);

        headBanner = View.inflate(getActivity(), R.layout.view_banner_news, null);
        loader = FinalBitmap.create(getActivity());
        http = new FinalHttp();
        banner = new Handler();

        xlvContent = findView(R.id.xlvContent);
        xlvContent.setXListViewConfig(true, true, true);
        xlvContent.setXListViewListener(this);
        if (xlvContent.getHeaderViewsCount() < 2) {
            xlvContent.addHeaderView(headBanner);
        }

        SparseArray<Integer> views = new SparseArray<>();
        views.append(ViewType.NEWS_IMAGE, R.layout.adapter_news_image);
        views.append(ViewType.NEWS_SPACIAL, R.layout.adapter_news_image);
        views.append(ViewType.NEWS_IMAGES, R.layout.adapter_news_images);
        views.append(ViewType.NEWS_TEXT, R.layout.adapter_news_text);
        adapter = new NewsListAdapter(getActivity(), views, imageLoader);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        loadData(lastnewsid);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.removeCallbacks(bannerRun);
            banner.postDelayed(bannerRun, BANNER_DELAYMILLIS);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.removeCallbacks(bannerRun);
        }
    }

    private void loadData(final int lastnewsid) {
        HttpParams params = new HttpParams("specialid", getIntent().getStringExtra("ID")).addCommonParam();
        if (lastnewsid != 0) {
            params.put("lastnewsid", lastnewsid + "");
        }
        http.post(TYUrls.NEWS_LISTS, params, new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    // 顶部Banner
                    String topbanner = json.optString("topbanner");
                    if (!TextUtils.isEmpty(topbanner)) {
                        Type listType = new TypeToken<LinkedList<NewsBannerType>>() {
                        }.getType();
                        List<NewsBannerType> banners = jsonParser.parseFromJson(topbanner, listType);
                        initBanner(banners);
                    }

                    // 新闻列表
                    String newslist = json.optString("newslist");
                    String speciallist = json.optString("speciallist");
                    List<NewsListType> nlts = new ArrayList<>();
                    Type listType = new TypeToken<LinkedList<NewsListType>>() {
                    }.getType();
                    String lastId = "0";
                    if (!TextUtils.isEmpty(newslist)) {
                        List<NewsListType> nlt = jsonParser.parseFromJson(newslist, listType);
                        if (!nlt.isEmpty()) {
                            nlts.addAll(nlt);
                            lastId = nlt.get(nlt.size() - 1).getNewsid();
                        }
                    }

                    if (!TextUtils.isEmpty(speciallist)) {
                        List<NewsListType> nlt = jsonParser.parseFromJson(speciallist, listType);
                        if (!nlt.isEmpty()) {
                            nlts.addAll(nlt);
                        }
                    }

                    if (nlts.isEmpty()) {
                        xlvContent.stopLoadMore();
                    } else {
                        adapter.setData(nlts);
                        adapter.notifyDataSetChanged();
                        if (lastnewsid == 0) {
                            xlvContent.stopRefresh();
                        } else {
                            xlvContent.stopLoadMore();
                        }
                        ColumnNewsActivity.this.lastnewsid = Integer.parseInt(lastId);
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 初始化Banner信息
     *
     * @param banners Banner内容
     */
    private void initBanner(final List<NewsBannerType> banners) {
        List<ImageView> views = new ArrayList<>();
        vpContent = (ViewPager) headBanner.findViewById(R.id.vpContent);
        for (int i = 0; i < banners.size() + 2; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ScaleType.FIT_XY);
            views.add(iv);
        }
        vpContent.setAdapter(new NewsBannerAdapter(views, banners, loader));
        vpContent.setCurrentItem(1);
        TextView tvDesc = (TextView) headBanner.findViewById(R.id.tvDesc);
        RadioGroup rgGuide = (RadioGroup) headBanner.findViewById(R.id.rgGuide);
        bannerHandler = new NewsBannerHandler(vpContent, tvDesc, rgGuide, banners);
        bannerHandler.sendEmptyMessage(1);
        vpContent.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int index = position;
                int size = banners.size();
                if (position == 0) {
                    index = size;
                } else if (position == size + 1) {
                    index = 1;
                }
                if (position != index) {
                    vpContent.setCurrentItem(index, false);
                } else {
                    vpContent.setCurrentItem(index, true);
                    ColumnNewsActivity.this.position = index;
                    bannerHandler.sendEmptyMessage(index);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private Runnable bannerRun = new Runnable() {
        @Override
        public void run() {
            try {
                vpContent.setCurrentItem(position + 1);
                banner.postDelayed(bannerRun, BANNER_DELAYMILLIS);
            } catch (Exception e) {
            }
        }
    };


    @Override
    public void onRefresh() {
        loadData(0);
    }

    @Override
    public void onLoadMore() {
        loadData(lastnewsid);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int type = Integer.parseInt(adapter.getData().get(position - xlvContent.getHeaderViewsCount()).getNewstype());
        LogUtil.e(true, "NewsContentFragment", "onItemClick type = " + type);
        switch (type) {
            case ViewType.NEWS_IMAGE: // 3
                startActivityByUri(TYUris.NEWS_IMAGE);
                break;
            case ViewType.NEWS_IMAGES: // 2
                startActivityByUri(TYUris.NEWS_IMAGES);
                break;
            default:
                break;
        }
    }
}