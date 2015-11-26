package com.news.activities;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.umeng.message.PushAgent;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.DeviceUtil;
import org.androidx.frames.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户登录
 *
 * @author slioe shu
 */
public class UserLoginActivity extends BaseTitleActivity implements OnClickListener {
    private EditText etName, etPasswd;
    private String type;
    private FinalHttp http;
    private TYSettings settings;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("登录");
        addTextToRight("注册", registListener);
    }

    private OnClickListener registListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if ("1".equals(type)) {
                startActivityByUri(TYUris.USER_REGIST, BundleUtil.createBundle("NAME", "2"));
            } else {
                finish();
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_user_login);

        type = getIntent().getStringExtra("NAME");
        http = TYAppliaction.getInstance().getHttp();
        settings = TYAppliaction.getInstance().getSettings();

        etName = findView(R.id.etName);
        etPasswd = findView(R.id.etPasswd);
        Button btnForget = findView(R.id.btnForget);
        Button btnLogin = findView(R.id.btnLogin);

        btnForget.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnForget:
                startActivityByUri(TYUris.USER_FORGET);
                break;
            default:
                break;
        }
    }

    private void login() {
        String phone = etName.getText().toString().trim();
        String pwd = etPasswd.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !DeviceUtil.isPhoneNo(phone)) {
            ToastUtil.ToastShort("请输入有效的手机号码");
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtil.ToastShort("请输入密码");
        } else {
            showDialog();
            http.post(TYUrls.USET_LOGIN, new HttpParams("phone", phone, "psw", pwd).addCommonParam(), new HttpCallback() {
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
                            ToastUtil.ToastShort("用户登录成功");
                            settings.USER_ID.setValue(json.optString("uid"));
                            settings.USER_NICK.setValue(json.optString("nick"));
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
