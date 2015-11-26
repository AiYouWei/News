package com.news.activities;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.TYDaily.R;
import com.news.TYUrls;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author slioe shu
 */
public class FeedbackActivity extends BaseTitleActivity {
    private FinalHttp http;
    private EditText etPhone, etTitle, etContent;

    @Override
    protected void initTitles() {
        super.initTitles();
        PushAgent.getInstance(getActivity()).onAppStart();
        addDefaultLeft(null);
        addDefaultMiddle("意见反馈");
        addTextToRight("提交", rightListener);
    }

    private OnClickListener rightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            submit();
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_feed_back);

        http = new FinalHttp();

        etPhone = findView(R.id.etPhone);
        etTitle = findView(R.id.etTitle);
        etContent = findView(R.id.etContent);
    }

    private void submit() {
        String phone = etPhone.getText().toString();
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.ToastShort("请输入您的联系电话");
        } else if (TextUtils.isEmpty(title)) {
            ToastUtil.ToastShort("请输入您反馈的标题");
        } else if (TextUtils.isEmpty(content)) {
            ToastUtil.ToastShort("请输入您反馈的内容");
        } else {
            http.post(TYUrls.FEEDBACK, new HttpParams("phone", phone, "title", title, "content", content).addCommonParam(), new HttpCallback() {
                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                }

                @Override
                public void onSuccess(String response) {
                    super.onSuccess(response);
                    LogUtil.e(true, "FeedbackActivity", "onSuccess == " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if ("0".equals(json.optString("error_code"))) {
                            ToastUtil.ToastShort("反馈信息提交成功");
                            finish();
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
