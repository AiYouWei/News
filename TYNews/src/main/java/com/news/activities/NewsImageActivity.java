package com.news.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.entities.Comment;
import com.news.entities.NewsNormalType;
import com.news.entities.NewsNormalType.Recommend;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.news.utils.ShareUtils;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.libs.share.ShareInfoType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 新闻详情大图
 *
 * @author slioe shu
 */
public class NewsImageActivity extends BaseTitleActivity implements OnClickListener {
    private FinalHttp http;
    private JsonParser parser;
    private TextView tvNTitle, tvNTime, tvNFrom, tvNAuthor, tvTitle, tvRContent,
            tvName, tvTime, tvContent, tvComment, tvPraise, tvName1, tvCity1, tvTime1,
            tvContent1, tvComment1, tvPraise1, tvNDesc;
    private ImageView ivContent, ivLink, ivHead1, ivHead;
    private FinalBitmap loader;
    private LinearLayout llRecommend, llComment1, llComment, llLink, llCommentAll;
    private WebView wvContent;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_news_image);

        http = new FinalHttp();
        parser = JsonParser.getInstance();
        loader = FinalBitmap.create(this);

        TextView tvCommentMore = findView(R.id.tvCommentMore);
        ImageView ivShare = findView(R.id.ivShare);
//        tvNTitle, tvNTime, tvNContent, tvNFrom, tvNAuthor,
        tvNTitle = findView(R.id.tvNTitle);
        tvNTime = findView(R.id.tvNTime);
        tvNDesc = findView(R.id.tvNDesc);
        wvContent = findView(R.id.wvContent);
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        tvNFrom = findView(R.id.tvNFrom);
        tvNAuthor = findView(R.id.tvNAuthor);
        ivContent = findView(R.id.ivContent);
        tvTitle = findView(R.id.tvTitle);
        tvRContent = findView(R.id.tvRContent);
        llRecommend = findView(R.id.llRecommend);
        llComment = findView(R.id.llComment);
        llComment1 = findView(R.id.llComment1);
        llCommentAll = findView(R.id.llCommentAll);
        llLink = findView(R.id.llLink);

        ivLink = findView(R.id.ivLink);
        //tvName1, tvCity1, tvTime1, tvContent1, tvComment1, tvPraise1;
        tvName1 = findView(R.id.tvName1);
        tvCity1 = findView(R.id.tvCity1);
        tvTime1 = findView(R.id.tvTime1);
        tvContent1 = findView(R.id.tvContent1);
        tvComment1 = findView(R.id.tvComment1);
        tvPraise1 = findView(R.id.tvPraise1);
        tvName = findView(R.id.tvName);
        tvTime = findView(R.id.tvTime);
        tvContent = findView(R.id.tvContent);
        tvComment = findView(R.id.tvComment);
        tvPraise = findView(R.id.tvPraise);
        ivHead = findView(R.id.ivHead);
        ivHead1 = findView(R.id.ivHead1);


        tvCommentMore.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    private NewsNormalType nnt;

    @Override
    protected void loadData() {
        super.loadData();
        http.post(TYUrls.NORMAL_DETAIL, new HttpParams("newsid", getIntent().getStringExtra("ID")).addCommonParam(), new HttpCallback() {
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
                        nnt = parser.parseFromJson(response, NewsNormalType.class);

                        tvNTitle.setText(nnt.getNewstitle());
                        tvNTime.setText(nnt.getCreatetime());

                        if (!TextUtils.isEmpty(nnt.getPageno()) && !TextUtils.isEmpty(nnt.getPagename())) {
                            tvNDesc.setText(nnt.getPageno() + "版" + nnt.getPagename());
                            tvNDesc.setVisibility(View.VISIBLE);
                        } else {
                            tvNDesc.setVisibility(View.GONE);
                        }

                        wvContent.loadData(nnt.getNewscontent(), "text/html; charset=UTF-8", null);
                        if (TextUtils.isEmpty(nnt.getNewsfrom())) {
                            tvNFrom.setVisibility(View.INVISIBLE);
                        } else {
                            tvNFrom.setText("来源：" + nnt.getNewsfrom());
                        }

                        if (TextUtils.isEmpty(nnt.getAuthor())) {
                            tvNAuthor.setVisibility(View.INVISIBLE);
                        } else {
                            tvNAuthor.setText("责任编辑：" + nnt.getAuthor());
                        }
                        List<Recommend> rs = nnt.getRecommends();
                        if (rs == null || rs.isEmpty()) {
                            llRecommend.setVisibility(View.GONE);
                        } else {
                            llRecommend.setVisibility(View.VISIBLE);
                            loader.display(ivContent, rs.get(0).getRecommendicon());
                            tvTitle.setText(rs.get(0).getRecommendtitle());
                            tvRContent.setText(rs.get(0).getRecommendsubtitle());
                        }
                        if (nnt.getLink() != null && !TextUtils.isEmpty(nnt.getLink().getLinkimg())) {
                            llLink.setVisibility(View.VISIBLE);
                            loader.display(ivLink, nnt.getLink().getLinkimg());
                        } else {
                            llLink.setVisibility(View.GONE);
                        }

                        if (nnt.getComtlist() == null || nnt.getComtlist().isEmpty()) {
                            llCommentAll.setVisibility(View.GONE);
                            return;
                        }

                        List<Comment> cs = nnt.getComtlist();
                        if (cs == null || cs.isEmpty()) {
                            llComment.setVisibility(View.GONE);
                            llComment1.setVisibility(View.GONE);
                        } else if (cs.size() == 1) {
                            llComment1.setVisibility(View.VISIBLE);
                            llComment.setVisibility(View.GONE);
                            Comment c = cs.get(0);
                            tvName1.setText(c.getComtusername());
                            tvTime1.setText(c.getComttime());
                            tvContent1.setText(c.getComtcontent());
                            tvPraise1.setText(c.getComtpraise());
                            loader.display(ivHead1, c.getComtuserheadimg());
                        } else {
                            llComment.setVisibility(View.VISIBLE);
                            llComment1.setVisibility(View.VISIBLE);
                            Comment c = cs.get(1);
                            tvName.setText(c.getComtusername());
                            tvTime.setText(c.getComttime().substring(0, 10));
                            tvContent.setText(c.getComtcontent());
                            tvPraise.setText(c.getComtpraise());
                            loader.display(ivHead, c.getComtuserheadimg());
                            Comment c1 = cs.get(0);
                            tvName1.setText(c1.getComtusername());
                            tvTime1.setText(c1.getComttime().substring(0, 10));
                            tvContent1.setText(c1.getComtcontent());
                            tvPraise1.setText(c1.getComtpraise());
                            loader.display(ivHead1, c1.getComtuserheadimg());
                        }


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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCommentMore:
                Bundle bundle = new Bundle();
                bundle.putString("T", nnt.getNewstitle());
                bundle.putString("A", nnt.getCreatetime());
                bundle.putString("B", nnt.getNewsid());
                bundle.putSerializable("NAME", nnt.getComtlist());
                startActivityByUri(TYUris.NEWS_COMMENT, bundle);
                break;
            case R.id.ivShare:
                ShareInfoType shareInfo = new ShareInfoType();
                shareInfo.setTitle(nnt.getNewstitle());
                shareInfo.setContent(nnt.getNewssubtitle());
                shareInfo.setUrl(nnt.getShareurl());
                shareInfo.setNetImage(nnt.getShareimg());
                ShareUtils.showShare(getActivity(), shareInfo);
                break;
            default:
                break;
        }
    }
}
