package com.news.activities;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUrls;
import com.news.adapters.VoiceDetailAdapter;
import com.news.entities.VoiceComtType;
import com.news.entities.VoiceDetailType;
import com.news.fragments.PeopleVoiceFragment;
import com.news.fragments.TabVoiceFragment.Category;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.views.XListView;

import java.util.List;

/**
 * 民声详情
 *
 * @author slioe shu
 */
public class VoiceDetailActivity extends BaseTitleActivity {
    private VoiceDetailAdapter adapter;
    private Category category;
    private FinalHttp http;
    private JsonParser parser;
    private String newsid;
    private TextView tvTitle, tvAddr, tvTime, tvName, tvContent;
    private ImageView ivContent;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        switch (category) {
            case CLUE:
                addDefaultMiddle("新闻线索详情");
                break;
            case URBAN:
                addDefaultMiddle("全民城管详情");
                break;
            case SUP:
                addDefaultMiddle("舆论监督详情");
                break;
            default:
                addDefaultMiddle("新闻线索详情");
                break;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_voice_detail);

        View header = View.inflate(getActivity(), R.layout.voice_detail_header, null);
        category = (Category) getIntent().getSerializableExtra(PeopleVoiceFragment.KEY_CATEGORY);
        newsid = getIntent().getStringExtra("ID");
        http = new FinalHttp();
        parser = JsonParser.getInstance();

        XListView xlvContent = findView(R.id.xlvContent);
        tvTitle = (TextView) header.findViewById(R.id.tvTitle);
        tvAddr = (TextView) header.findViewById(R.id.tvAddr);
        tvTime = (TextView) header.findViewById(R.id.tvTime);
        tvName = (TextView) header.findViewById(R.id.tvName);
        tvContent = (TextView) header.findViewById(R.id.tvContent);
        ivContent = (ImageView) header.findViewById(R.id.ivContent);
        xlvContent.setXListViewConfig(false, false, false);

        xlvContent.addHeaderView(header);
        adapter = new VoiceDetailAdapter(getActivity(), R.layout.adapter_voice_detail, category);
        xlvContent.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        String url;
        switch (category) {
            case CLUE:
                url = TYUrls.CLUE_DETAIL;
                tvAddr.setVisibility(View.GONE);
                break;
            case URBAN:
                url = TYUrls.URBAN_DETAIL;
                tvAddr.setVisibility(View.VISIBLE);
                break;
            case SUP:
                url = TYUrls.SUP_DETAIL;
                tvAddr.setVisibility(View.VISIBLE);
                break;
            default:
                url = TYUrls.CLUE_DETAIL;
                tvAddr.setVisibility(View.GONE);
                break;
        }
        showDialog();
        http.post(url, new HttpParams("newsid", newsid).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                dismissDialog();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                dismissDialog();
                VoiceDetailType detail = parser.parseFromJson(response, VoiceDetailType.class);
                tvTitle.setText(detail.getTitle());
                if (!TextUtils.isEmpty(detail.getPlace())) {
                    tvAddr.setText("地点：" + detail.getPlace());
                } else {
                    tvAddr.setVisibility(View.GONE);
                }
                tvTime.setText("时间：" + detail.getCreatetime());
                tvName.setText("报料人：" + detail.getSrc());
                tvContent.setText(detail.getContent());
                List<VoiceComtType> vcs = detail.getComtlist();
                if (vcs != null && !vcs.isEmpty()) {
                    adapter.setData(vcs);
                    adapter.notifyDataSetChanged();
                }
                if (TextUtils.isEmpty(detail.getImg())) {
                    ivContent.setVisibility(View.GONE);
                } else {
                    ivContent.setVisibility(View.VISIBLE);
                    FinalBitmap.create(getActivity()).display(ivContent, detail.getImg());
                }
            }
        });
    }
}
