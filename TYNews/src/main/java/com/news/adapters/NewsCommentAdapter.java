package com.news.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.entities.Comment;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;

import net.tsz.afinal.FinalBitmap;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author slioe shu
 */
public class NewsCommentAdapter extends BaseListAdapter<Comment> {
    private FinalBitmap loader;
    private TYSettings settings;

    public NewsCommentAdapter(Context context, int layoutId) {
        super(context, layoutId);
        loader = FinalBitmap.create(context);
        settings = TYAppliaction.getInstance().getSettings();
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, Comment data) {
        holder.setText(R.id.tvName, data.getComtusername());
        holder.setText(R.id.tvTime, data.getComttime().substring(0, 10));
        holder.setText(R.id.tvPraise, data.getComtpraise());
        holder.setText(R.id.tvContent, data.getComtcontent());
        TextView tv = holder.findView(R.id.tvPraise);
        tv.setOnClickListener(new praiseClick(getPosition(), data));
        if (data.getChildcomtlist() == null || data.getChildcomtlist().isEmpty()) {
            holder.setViewVisibility(R.id.ll1, View.GONE);
            holder.setViewVisibility(R.id.ll2, View.GONE);
            holder.setViewVisibility(R.id.tvMore, View.GONE);
        } else if (data.getChildcomtlist().size() == 1) {
            holder.setViewVisibility(R.id.ll1, View.VISIBLE);
            holder.setViewVisibility(R.id.ll2, View.GONE);
            holder.setViewVisibility(R.id.tvMore, View.GONE);
            holder.setText(R.id.tvName1, data.getChildcomtlist().get(0).getComtusername());
            holder.setText(R.id.tvContent1, data.getChildcomtlist().get(0).getComtcontent());
            holder.setText(R.id.tvTime1, data.getChildcomtlist().get(0).getComttime());
        } else if (data.getChildcomtlist().size() == 2) {
            holder.setViewVisibility(R.id.ll1, View.VISIBLE);
            holder.setViewVisibility(R.id.ll2, View.VISIBLE);
            holder.setViewVisibility(R.id.tvMore, View.VISIBLE);
            holder.setText(R.id.tvName1, data.getChildcomtlist().get(0).getComtusername());
            holder.setText(R.id.tvContent1, data.getChildcomtlist().get(0).getComtcontent());
            holder.setText(R.id.tvTime1, data.getChildcomtlist().get(0).getComttime());

            holder.setText(R.id.tvName2, data.getChildcomtlist().get(1).getComtusername());
            holder.setText(R.id.tvContent2, data.getChildcomtlist().get(1).getComtcontent());
            holder.setText(R.id.tvTime2, data.getChildcomtlist().get(1).getComttime());
        }
        ImageView ivHead = holder.findView(R.id.ivHead);
        loader.display(ivHead, data.getComtuserheadimg());

    }

    private class praiseClick implements OnClickListener {
        private int position;
        private Comment data;

        public praiseClick(int position, Comment data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public void onClick(final View v) {
            LogUtil.e(true, "praiseClick", "onClick =========");
            String url = TYUrls.BASE_URL + "?r=news/addpraise";
            TYAppliaction.getInstance().getHttp().post(url, new HttpParams("uid", settings.USER_ID.getValue(), "cid", data.getComtid()).addCommonParam(), new HttpCallback() {
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
                            ToastUtil.ToastShort("点赞成功");
                            ((TextView) v).setText((Integer.parseInt(data.getComtpraise()) + 1) + "");
                        } else {
                            ToastUtil.ToastShort(json.optString("error_msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
