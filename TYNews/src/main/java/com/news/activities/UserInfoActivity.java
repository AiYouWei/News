package com.news.activities;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUrls;
import com.news.entities.UserInfoType;
import com.news.utils.DialogUtil;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我的资料
 *
 * @author slioe shu
 */
public class UserInfoActivity extends BaseTitleActivity implements OnClickListener {
    private EditText etName, etAge, etPhone, etWork, etHome, etReal;
    private ImageView ivSpinner;
    private TextView tvSex;
    private FinalHttp http;
    private TYSettings settings;
    private JsonParser parser;
    private RelativeLayout rlSex;
    private AlertDialog dialog;
    private PopupWindow sexUI;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("我的资料");
        addTextToRight("编辑", editListener);
    }

    private OnClickListener editListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            if ("编辑".equals(text)) {
                setEnable(true);
                ((TextView) v).setText("完成");
            } else {
                setEnable(false);
                ((TextView) v).setText("编辑");
                updateInfo();
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_user_info);
        sexUI = DialogUtil.showListWindow(getActivity(), this);

        http = new FinalHttp();
        settings = TYAppliaction.getInstance().getSettings();
        parser = JsonParser.getInstance();

        etName = findView(R.id.etName);
        etReal = findView(R.id.etRealName);
        etAge = findView(R.id.etAge);
        etPhone = findView(R.id.etPhone);
        tvSex = findView(R.id.tvSex);
        etWork = findView(R.id.etWork);
        etHome = findView(R.id.etHome);
        ivSpinner = findView(R.id.ivSpinner);
        rlSex = findView(R.id.rlSex);

        setEnable(false);
    }

    @Override
    protected void loadData() {
        super.loadData();
        showDialog();
        http.post(TYUrls.USER_INFO, new HttpParams("uid", settings.USER_ID.getValue()).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                dismissDialog();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                dismissDialog();
                UserInfoType userInfo = parser.parseFromJson(response, UserInfoType.class);
                etName.setText(userInfo.getNick());
                etReal.setText(userInfo.getName());
                etAge.setText(userInfo.getAge());
                etPhone.setText(userInfo.getPhone());
                tvSex.setText(userInfo.getSex());
                etWork.setText(userInfo.getWork());
                etHome.setText(userInfo.getHome());
            }
        });
    }

    private void setEnable(boolean enable) {
        etName.setEnabled(enable);
        etPhone.setEnabled(enable);
        etAge.setEnabled(enable);
        ivSpinner.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
        etWork.setEnabled(enable);
        etHome.setEnabled(enable);
        rlSex.setOnClickListener(enable ? this : null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSex:
                chooseSex();
                break;
            case R.id.tvMen:
                tvSex.setText(R.string.choose_sexM);
                sexUI.dismiss();
                break;
            case R.id.tvFemen:
                tvSex.setText(R.string.choose_sexF);
                sexUI.dismiss();
                break;
            default:
                break;
        }
    }

    private void chooseSex() {
        sexUI.showAsDropDown(barView);
    }

    private void updateInfo() {
        showDialog();
        HttpParams params = new HttpParams("uid", settings.USER_ID.getValue(), "nick", etName.getText().toString().trim()).addCommonParam();
        params.put("age", etAge.getText().toString().trim());
        params.put("contact", etPhone.getText().toString().trim());
        params.put("work", etWork.getText().toString().trim());
        params.put("home", etHome.getText().toString().trim());
        params.put("name", etReal.getText().toString().trim());
        http.post(TYUrls.UPDATE_INFO, params, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                dismissDialog();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                dismissDialog();
                try {
                    JSONObject json = new JSONObject(response);
                    if ("0".equals(json.optString("error_code"))) {
                        ToastUtil.ToastShort(json.optString("error_msg"));
                        settings.USER_NICK.setValue(etName.getText().toString().trim());
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
