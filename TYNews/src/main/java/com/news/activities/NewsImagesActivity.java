package com.news.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.entities.MultiImageType;
import com.news.entities.MultiImageType.ImageType;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.news.utils.ShareUtils;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.adapters.ViewsPagerAdapter;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.ImageUtil;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.libs.share.ShareInfoType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻详情多图
 *
 * @author slioe shu
 */
public class NewsImagesActivity extends BaseTitleActivity implements OnPageChangeListener, OnClickListener {
    private TextView tvTitle, tvIndex, tvDetail;
    private FinalHttp http;
    private String id;
    private JsonParser parser;
    private List<ImageView> views;
    private ViewsPagerAdapter adapter;
    private int index = 0, size = 0;
    private List<ImageType> its;
    private FinalBitmap loader;
    private MultiImageType mit;

    @Override
    protected void initTitles() {
        super.initTitles();
        barView.setBackgroundColor(Color.BLACK);
        addDefaultLeft(null);
        TextView tv = (TextView) View.inflate(this, R.layout.toolbar_publish_comment, null);
        addViewToRight(tv, publishListener);
    }

    private OnClickListener publishListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("T", mit.getNewstitle());
            bundle.putString("A", "");
            bundle.putString("B", mit.getNewsid());
            bundle.putSerializable("NAME", mit.getComtlist());
            startActivityByUri(TYUris.NEWS_COMMENT, bundle);

        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_news_images);

        http = new FinalHttp();
        id = getIntent().getStringExtra("ID");
        parser = JsonParser.getInstance();
        views = new ArrayList<>();
        adapter = new ViewsPagerAdapter();
        loader = FinalBitmap.create(this);

        tvTitle = findView(R.id.tvTitle);
        tvIndex = findView(R.id.tvIndex);
        tvDetail = findView(R.id.tvDetail);

        TextView tvCollect = findView(R.id.tvCollect);
        TextView tvDown = findView(R.id.tvDown);
        TextView tvShare = findView(R.id.tvShare);
        ViewPager vpContent = findView(R.id.vpContent);


        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(this);
        vpContent.setCurrentItem(index);

        tvCollect.setOnClickListener(this);
        tvDown.setOnClickListener(this);
        tvShare.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        http.post(TYUrls.MULTI_DETAIL, new HttpParams("newsid", id).addCommonParam(), new HttpCallback() {
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
                        mit = parser.parseFromJson(response, MultiImageType.class);
                        tvTitle.setText(mit.getNewstitle());
                        size = mit.getImages().size();
                        its = mit.getImages();

                        for (int i = 0; i < size; i++) {
                            ImageType is = its.get(i);
                            final ImageView iv = new ImageView(getActivity());
                            iv.setTag(i);
                            iv.setScaleType(ScaleType.CENTER_CROP);
                            loader.display(iv, is.getPic());
                            views.add(iv);
                        }
                        adapter.setData(views);
                        adapter.notifyDataSetChanged();
                        index = 1;
                        tvIndex.setText("1/" + size);
                        tvDetail.setText(its.get(0).getInfo());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position + 1;
        tvIndex.setText(index + "/" + size);
        tvDetail.setText(its.get(position).getInfo());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCollect:
                // TODO: 2015-11-21
                break;
            case R.id.tvDown:
                String url = mit.getImages().get(index - 1).getPic();
                String path = Environment.getExternalStorageDirectory().getPath() + "/TYNews/news/";
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                ImageUtil.saveImage(url, path);
                ToastUtil.ToastShort("正在保存图片");
                break;
            case R.id.tvShare:
                ShareInfoType shareInfo = new ShareInfoType();
                shareInfo.setTitle(mit.getNewstitle());
                shareInfo.setContent(mit.getNewssubtitle());
                shareInfo.setUrl(mit.getShareurl());
                shareInfo.setNetImage(mit.getShareimg());
                ShareUtils.showShare(getActivity(), shareInfo);
                break;
            default:
                break;
        }
    }


}
