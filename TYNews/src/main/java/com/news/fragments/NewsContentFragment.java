package com.news.fragments;

import android.app.Activity;
import android.os.Bundle;
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

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.libs.volley.HttpCallback;
import org.androidx.frames.libs.volley.HttpParams;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.frames.views.XListView;
import org.androidx.frames.views.XListView.IXListViewListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻列表展示
 *
 * @author slioe shu
 */
public class NewsContentFragment extends BaseFragment implements IXListViewListener, OnItemClickListener {
    public final static String KEY_NAME = "name";
    public final static String KEY_TID = "tid";
    private XListView xlvContent;
    private int lastnewsid = 0;
    private String tid = "";
    private View headBanner;
    private ViewPager vpContent;
    private Handler banner;
    private final static long BANNER_DELAYMILLIS = 3000L;
    private NewsBannerHandler bannerHandler;
    private int position = 1;
    private int urlIndex;
    private NewsListAdapter adapter;
    private String TAG = "NewsContentFragment";
    private FinalBitmap loader;

    private void setIndex(int index) {
        urlIndex = index;
    }

    private int getIndex() {
        return urlIndex;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headBanner = View.inflate(getActivity(), R.layout.view_banner_news, null);
        loader = FinalBitmap.create(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_news_content);

        tid = getArguments().getString(KEY_TID);
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
    public void onPause() {
        setIndex(0);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.removeCallbacks(bannerRun);
        }
    }

    private void loadData(final int lastnewsid) {
        HttpParams params = new HttpParams("tid", tid);
        if (lastnewsid != 0) {
            params.put("lastnewsid", lastnewsid + "");
        }
        httpRequester.post(TYUrls.NEWS_LISTS, params, new HttpCallback() {
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
                        NewsContentFragment.this.lastnewsid = Integer.parseInt(lastId);
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int errorCode, String msg, Throwable tr) {
                super.onFailure(errorCode, msg, tr);
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
            if (i == banners.size()) {
                setIndex(1);
            } else if (i == banners.size() + 1) {
                setIndex(0);
            } else {
                setIndex(i);
            }
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int j = getIndex();
                    String toplink = banners.get(getIndex()).getToplink();
                    ToastUtil.ToastShort(getIndex() + "");
                    LogUtil.d(true, TAG, getIndex() + "");
                }
            });
            views.add(iv);
        }
        vpContent.setAdapter(new NewsBannerAdapter(views, banners, loader));
        vpContent.setCurrentItem(1);
        TextView tvDesc = (TextView) headBanner.findViewById(R.id.tvDesc);
        RadioGroup rgGuide = (RadioGroup) headBanner.findViewById(R.id.rgGuide);
        bannerHandler = new NewsBannerHandler(vpContent, tvDesc, rgGuide, banners);
        bannerHandler.sendEmptyMessage(1);
        vpContent.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int index = position;
                int size = banners.size();
                if (position == 0) {
                    index = size - 1;
                } else if (position == size + 1) {
                    index = 1;
                }
                setIndex(index - 1);
                if (position != index) {
                    vpContent.setCurrentItem(index, false);
                } else {
                    vpContent.setCurrentItem(index, true);
                    NewsContentFragment.this.position = index;
                    bannerHandler.sendEmptyMessage(index);
                }
                LogUtil.d(true, TAG, index + "");
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
                setIndex(position - 1);
                LogUtil.d(true, TAG, position + "");
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
        NewsListType nlt = adapter.getData().get(position - xlvContent.getHeaderViewsCount());
        if (!TextUtils.isEmpty(nlt.getNewstype())) {
            int type = Integer.parseInt(nlt.getNewstype());
            switch (type) {
                case 1: // 普通新闻
                    Bundle normal = new Bundle();
                    normal.putString("ID", nlt.getNewsid());
                    startActivityByUri(TYUris.NEWS_IMAGE, normal);
                    break;
                case 2: // 多图新闻
                    Bundle multi = new Bundle();
                    multi.putString("ID", nlt.getNewsid());
                    startActivityByUri(TYUris.NEWS_IMAGES, multi);
                    break;
                case 3: // 外部链接
                    Bundle web = new Bundle();
                    web.putString("url", nlt.getNewslink());
                    startActivityByUri(TYUris.WEBVIEW, web);
                    break;
                case 4:
                    int st = Integer.parseInt(nlt.getSpecialtype());
                    switch (st) {
                        case 0:
                            Bundle web1 = new Bundle();
                            web1.putString("url", nlt.getSpeciallink());
                            startActivityByUri(TYUris.WEBVIEW, web1);
                            break;
                        case 1:
                            Bundle news = new Bundle();
                            news.putString("ID", nlt.getSpecialid());
                            startActivityByUri(TYUris.COLUMN_NEWS);
                            break;
                        case 2:
                            Bundle web2 = new Bundle();
                            web2.putString("url", TYUrls.BASE_URL + "?r=vote/share&sid=" + nlt.getSpecialid());
                            web2.putString("id", nlt.getSpecialid());
                            startActivityByUri(TYUris.SUBJECT_VOTE, web2);
                            break;
                        case 3:
                            Bundle bundle = new Bundle();
                            bundle.putString("TYPE", nlt.getSpecialid());
                            startActivityByUri(TYUris.SUBJECT_DETAIL, bundle);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
