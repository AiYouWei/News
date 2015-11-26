package com.news.activities;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.TYDaily.R;
import com.news.TYAppliaction;
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
 * 用户注册
 *
 * @author slioe shu
 */
public class UserRegistActivity extends BaseTitleActivity implements OnClickListener {
    private Button btnCode;
    private String type;
    private FinalHttp http;
    private EditText etNick, etPhone, etCode, etPwd, etRePwd;
    private int time = 60;
    private Handler handler;
    private CodeHandler codeHandler;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("注册");
        addTextToRight("登录", loginListener);
    }

    private OnClickListener loginListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (type.equals("2")) {
                finish();
            } else {
                startActivityByUri(TYUris.USER_LOGIN, BundleUtil.createBundle("NAME", "2"));
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_user_regist);

        type = getIntent().getStringExtra("NAME");
        http = TYAppliaction.getInstance().getHttp();
        codeHandler = new CodeHandler();
        handler = new Handler();

        btnCode = findView(R.id.btnGetCode);
        Button btnRegist = findView(R.id.btnRegist);
        etNick = findView(R.id.etNick);
        etPhone = findView(R.id.etName);
        etCode = findView(R.id.etCode);
        etPwd = findView(R.id.etPasswd);
        etRePwd = findView(R.id.etRepasswd);

        btnCode.setOnClickListener(this);
        btnRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nick = etNick.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String rePwd = etRePwd.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btnGetCode:
                getCode(phone);
                break;
            case R.id.btnRegist:
                regist(nick, phone, code, pwd, rePwd);
                break;
            default:
                break;
        }
    }

    private void getCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.ToastShort("请输入手机号");
        } else if (!DeviceUtil.isPhoneNo(phone)) {
            ToastUtil.ToastShort("请输入有效手机号");
        } else {
            showDialog();
            handler.postDelayed(timeRun, 1000L);
            http.post(TYUrls.GET_VERIFY, new HttpParams("phone", phone).addCommonParam(), new HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    super.onSuccess(response);
                    dismissDialog();
                    ToastUtil.ToastShort("验证码已发送至手机，请稍等");
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    dismissDialog();
                }
            });
        }
    }

    private void regist(String nick, String phone, String code, String pwd, String rePwd) {
        if (TextUtils.isEmpty(nick)) {
            ToastUtil.ToastShort("请输入您的昵称");
        } else if (TextUtils.isEmpty(phone) || !DeviceUtil.isPhoneNo(phone)) {
            ToastUtil.ToastShort("请输入有效的手机号码");
        } else if (TextUtils.isEmpty(code)) {
            ToastUtil.ToastShort("请输入验证码");
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtil.ToastShort("请输入密码");
        } else if (pwd.length() < 6) {
            ToastUtil.ToastShort("密码长度至少为6位");
        } else if (TextUtils.isEmpty(rePwd)) {
            ToastUtil.ToastShort("请输入确认密码");
        } else if (!pwd.equals(rePwd)) {
            ToastUtil.ToastShort("两次输入的密码不一致, 请再次输入");
        } else {
            showDialog();
            http.post(TYUrls.USER_REGIST, new HttpParams("phone", phone, "psw", pwd, "code", code, "nick", nick).addCommonParam(), new HttpCallback() {
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
                            ToastUtil.ToastShort("用户注册成功");
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

    private Runnable timeRun = new Runnable() {
        @Override
        public void run() {
            codeHandler.sendEmptyMessage(--time);
            handler.postDelayed(timeRun, 1000L);
        }
    };

    private class CodeHandler extends Handler {
        public CodeHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                btnCode.setText(String.format(getString(R.string.me_recode), msg.what));
                btnCode.setEnabled(false);
            } else {
                btnCode.setText(R.string.user_getCode);
                btnCode.setEnabled(true);
                handler.removeCallbacks(timeRun);
            }
        }
    }
}
