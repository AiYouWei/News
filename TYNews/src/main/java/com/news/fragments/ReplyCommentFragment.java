package com.news.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.activities.ReplyCommentActivity.Category;
import com.news.adapters.ReplyCommentAdapter;
import com.news.entities.ReplyCommentType;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseFragment;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.frames.views.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 评论我的/回复我的
 *
 * @author slioe shu
 */
public class ReplyCommentFragment extends BaseFragment implements OnItemClickListener {
    public final static String KEY_CATEGORY = "category";
    private Category category;
    private ReplyCommentAdapter adapter;
    private FinalHttp http;
    private TYSettings settings;
    private JsonParser parser;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_reply_comment);

        category = (Category) getArguments().getSerializable(KEY_CATEGORY);
        http = new FinalHttp();
        settings = TYAppliaction.getInstance().getSettings();
        parser = JsonParser.getInstance();

        XListView xlvContent = findView(R.id.xlvContent);
        xlvContent.setXListViewConfig(false, false, false);
        adapter = new ReplyCommentAdapter(getActivity(), R.layout.adapter_reply_comment, category);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        String uid = settings.USER_ID.getValue();
        if (TextUtils.isEmpty(uid)) {
            ToastUtil.ToastShort("请先登录");
            return;
        }
        http.post(TYUrls.MINE_COMMENT, new HttpParams("uid", settings.USER_ID.getValue()).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                try {
                    JSONObject json = new JSONObject(response);
                    String result;
                    switch (category) {
                        case REPLY:
                            result = json.optString("mySends");
                            break;
                        case COMMENT:
                            result = json.optString("myRecvs");
                            break;
                        default:
                            result = json.optString("mySends");
                            break;
                    }

                    Type listType = new TypeToken<LinkedList<ReplyCommentType>>() {
                    }.getType();
                    List<ReplyCommentType> vts = parser.parseFromJson(result, listType);
                    adapter.setData(vts);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
