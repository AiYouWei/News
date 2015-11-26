package com.news.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.fragments.TabVoiceFragment.Category;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * 全民城管发布
 *
 * @author slioe shu
 */
public class ManagerEditActivity extends BaseTitleActivity implements OnClickListener {
    public final static String VOICE_EDIT = "edit";
    private Category category;
    private EditText etName, etPhone, etTime, etAddr, etTitle, etContent, etImage;
    private FinalHttp http;
    private TYSettings settings;
    private String image = "";
    private ImageView ivImage;
    private RelativeLayout rlImage;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        switch (category) {
            case CLUE:
                addDefaultMiddle("新闻线索");
                break;
            case URBAN:
                addDefaultMiddle("全民城管");
                break;
            case SUP:
                addDefaultMiddle("舆情监督");
                break;
            default:
                break;
        }
        addTextToRight("提交", submitListener);
    }

    private OnClickListener submitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            publish();
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_manager_edit);

        settings = TYAppliaction.getInstance().getSettings();
        http = new FinalHttp();
        category = (Category) getIntent().getSerializableExtra(VOICE_EDIT);
        if (category == null) {
            category = Category.URBAN;
        }

        etName = findView(R.id.etName);
        etPhone = findView(R.id.etPhone);
        etTime = findView(R.id.etTime);
        etAddr = findView(R.id.etAddr);
        etTitle = findView(R.id.etTitle);
        etContent = findView(R.id.etContent);
        etImage = findView(R.id.etImage);
        ivImage = findView(R.id.ivImage);
        rlImage = findView(R.id.rlImage);
        TextView tvC = findView(R.id.tvContent);

        switch (category) {
            case CLUE:
            case SUP:
                etTime.setVisibility(View.GONE);
                etAddr.setVisibility(View.GONE);
                break;
            case URBAN:
                etTime.setVisibility(View.VISIBLE);
                etAddr.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        ivImage.setVisibility(View.GONE);
        rlImage.setOnClickListener(this);
        tvC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rlImage) {
            CameraSdkParameterInfo mCameraSdkParameterInfo = new CameraSdkParameterInfo();
            mCameraSdkParameterInfo.setSingle_mode(true);
            mCameraSdkParameterInfo.setShow_camera(true);
            mCameraSdkParameterInfo.setMax_image(1);
            mCameraSdkParameterInfo.setCroper_image(false);

            Intent intent = new Intent();
            intent.setClassName(getActivity(), "com.muzhi.camerasdk.PhotoPickActivity");
            Bundle b = new Bundle();
            b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
            intent.putExtras(b);
            startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
        } else if (v.getId() == R.id.tvContent) {
            etContent.setFocusable(true);
            etContent.requestFocus();//获得焦点\
            InputMethodManager inputManager = (InputMethodManager) etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(etContent, 0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            CameraSdkParameterInfo mCameraSdkParameterInfo = (CameraSdkParameterInfo) data.getExtras().getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            ArrayList<String> list = mCameraSdkParameterInfo.getImage_list();
            image = list.get(0);
            Bitmap b = BitmapFactory.decodeFile(list.get(0));
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageBitmap(b);
            LogUtil.e(true, "ManagerEditActivity", "onActivityResult path = " + image);
        }
    }

    private void publish() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String time = etTime.getText().toString();
        String addr = etAddr.getText().toString();
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String uid = settings.USER_ID.getValue();
        if (TextUtils.isEmpty(uid)) {
            ToastUtil.ToastShort("请先登陆");
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtil.ToastShort(etPhone.getHint().toString());
        } else if (category == Category.URBAN && TextUtils.isEmpty(time)) {
            ToastUtil.ToastShort(etTime.getHint().toString());
        } else if (category == Category.URBAN && TextUtils.isEmpty(addr)) {
            ToastUtil.ToastShort(etAddr.getHint().toString());
        } else if (TextUtils.isEmpty(title)) {
            ToastUtil.ToastShort(etTitle.getHint().toString());
        } else if (TextUtils.isEmpty(content)) {
            ToastUtil.ToastShort("请输入您报料的内容");
        } else {
            showDialog();
            HttpParams params = new HttpParams("title", title, "content", content, "phone", phone, "uid", uid).addCommonParam();
            if (!TextUtils.isEmpty(name)) {
                params.put("name", name);
            }
            if (!TextUtils.isEmpty(image)) {
                try {
                    params.put("img", new File(image));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            String url;
            if (category == Category.URBAN) {
                params.put("place", addr);
                params.put("create_time", time);
                url = TYUrls.URBAN_CREATE;
            } else if (category == Category.CLUE) {
                url = TYUrls.CLUE_CREATE;
            } else {
                url = TYUrls.SUP_CREATE;
            }
            http.post(url, params, new HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    super.onSuccess(response);
                    dismissDialog();
                    try {
                        JSONObject json = new JSONObject(response);
                        if ("0".equals(json.optString("error_code"))) {
                            ToastUtil.ToastShort("提交成功");
                            finish();
                        } else {
                            ToastUtil.ToastShort(json.optString("error_msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    dismissDialog();
                }
            });
        }
    }
}
