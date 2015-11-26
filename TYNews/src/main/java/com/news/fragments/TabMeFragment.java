package com.news.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.TYDaily.R;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.TYUrls;
import com.news.entities.MeInfoType;
import com.news.utils.HttpCallback;
import com.news.utils.HttpParams;
import com.news.utils.ShareUtils;
import com.news.views.MyCircleImageView;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import org.androidx.frames.JsonParser;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.base.BaseTitleFragment;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.libs.share.ShareInfoType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 我
 *
 * @author slioe shu
 */
public class TabMeFragment extends BaseTitleFragment implements OnClickListener {
    private LinearLayout llLogin, llUnLogin;
    private TYSettings settings;
    private TextView tvName, tvScore, tvSign;
    private MyCircleImageView cvhead;
    private FinalBitmap finalBitmap;
    private ImageView ivKnow, ivAdvert;
    private FinalHttp http;
    private JsonParser parser;
    private MeInfoType meInfo;
    private String signFlag;
    private final static int LOGOUT = 0xAA;
    private View viewComment, viewMsg;

    @Override
    protected void initTitles() {
        super.initTitles();
        addImageToRight(R.mipmap.me_setting, settingListener);
    }

    private OnClickListener settingListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResultByUri(TYUris.MINE_SETTING, LOGOUT);
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_tab_me);

        settings = TYAppliaction.getInstance().getSettings();
        finalBitmap = FinalBitmap.create(getActivity());
        http = new FinalHttp();
        parser = JsonParser.getInstance();
        Calendar calendar = Calendar.getInstance();
        signFlag = calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE) + "1";

        ScrollView svContent = findView(R.id.svContent);
        llLogin = findView(R.id.llLogin);
        llUnLogin = findView(R.id.llUnLogin);
        TextView tvLogin = findView(R.id.tvLogin);
        TextView tvRegist = findView(R.id.tvRegist);
        cvhead = findView(R.id.cvHead);
        tvName = findView(R.id.tvName);
        tvScore = findView(R.id.tvScore);
        ivKnow = findView(R.id.ivKnow);
        ivAdvert = findView(R.id.ivAdvert);
        tvSign = findView(R.id.tvSign);
        TextView tvData = findView(R.id.tvData);
        TextView tvCollect = findView(R.id.tvCollect);
        TextView tvDown = findView(R.id.tvDown);
        TextView tvComment = findView(R.id.tvComment);
        TextView tvMessage = findView(R.id.tvMessage);
        TextView tvInvite = findView(R.id.tvInvite);
        viewComment = findView(R.id.viewComment);
        viewMsg = findView(R.id.viewMessage);
        viewComment.setVisibility(View.INVISIBLE);
        viewMsg.setVisibility(View.INVISIBLE);

        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            svContent.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
        }
        String uid = settings.USER_ID.getValue();
        String nick = settings.USER_NICK.getValue();
        String uscore = settings.USER_SCORE.getValue();
        String uhead = settings.USER_HEAD.getValue();
        HttpParams params = new HttpParams();
        params.addCommonParam();
        if (TextUtils.isEmpty(uid)) {
            llLogin.setVisibility(View.INVISIBLE);
            llUnLogin.setVisibility(View.VISIBLE);
        } else {
            params.put("uid", uid);
            llLogin.setVisibility(View.VISIBLE);
            llUnLogin.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(nick)) {
                tvName.setText(nick);
            }
            if (!TextUtils.isEmpty(uscore)) {
                tvScore.setVisibility(View.VISIBLE);
                ivKnow.setVisibility(View.VISIBLE);
                tvScore.setText(String.format(getString(R.string.me_score), uscore));
            } else {
                ivKnow.setVisibility(View.GONE);
                tvScore.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(uhead)) {
                finalBitmap.display(cvhead, uhead);
            }
        }

        ((BaseActivity) getActivity()).showDialog();
        http.get(TYUrls.MINE_HEART + settings.USER_ID.getValue(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                ((BaseActivity) getActivity()).dismissDialog();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                ((BaseActivity) getActivity()).dismissDialog();
                try {
                    JSONObject json = new JSONObject(response);
                    String comment = json.optString("comment");
                    String sysmgs = json.optString("sysmgs");

                    if ("1".equals(comment)) {
                        viewComment.setVisibility(View.VISIBLE);
                    } else {
                        viewComment.setVisibility(View.INVISIBLE);
                    }
                    if ("1".equals(sysmgs)) {
                        viewMsg.setVisibility(View.VISIBLE);
                    } else {
                        viewMsg.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        setSign(!signFlag.equals(settings.USER_SIGN.getValue()));

        tvSign.setOnClickListener(this);
        cvhead.setOnClickListener(this);
        tvName.setOnClickListener(this);
        ivKnow.setOnClickListener(this);
        ivAdvert.setOnClickListener(this);
        tvData.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvDown.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvInvite.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        String uid = settings.USER_ID.getValue();
        HttpParams params = new HttpParams();
        params.addCommonParam();
        if (TextUtils.isEmpty(uid)) {
            llLogin.setVisibility(View.INVISIBLE);
            llUnLogin.setVisibility(View.VISIBLE);
        } else {
            params.put("uid", uid);
        }

        setSign(signFlag.equals(settings.USER_SIGN.getValue()));
        String nick = settings.USER_NICK.getValue();
        if (TextUtils.isEmpty(uid)) {
            llLogin.setVisibility(View.INVISIBLE);
            llUnLogin.setVisibility(View.VISIBLE);
        } else {
            llLogin.setVisibility(View.VISIBLE);
            llUnLogin.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(nick)) {
                tvName.setText(nick);
            }
        }

        http.post(TYUrls.ME_INFO, params, new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);

            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                meInfo = parser.parseFromJson(response, MeInfoType.class);
                tvScore.setVisibility(View.VISIBLE);
                ivKnow.setVisibility(View.VISIBLE);
                tvScore.setText(String.format(getString(R.string.me_score), meInfo.getCredit()));
                finalBitmap.display(ivAdvert, meInfo.getImg());
                if (TextUtils.isEmpty(meInfo.getUserheadimg())) {
                    cvhead.setImageResource(R.mipmap.user_default_head);
                } else {
                    finalBitmap.display(cvhead, meInfo.getUserheadimg());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvName:
                startActivityByUri(TYUris.USER_LOGIN);
                break;
            case R.id.ivKnow:
                startActivityByUri(TYUris.SCORE_INTRO);
                break;
            case R.id.tvData:
                startActivityByUri(TYUris.USER_INFO);
                break;
            case R.id.tvCollect:
                startActivityByUri(TYUris.MINE_COLLECTION);
                break;
            case R.id.tvDown:
                startActivityByUri(TYUris.MINE_DOWNLOAD);
                break;
            case R.id.tvComment:
                viewComment.setVisibility(View.INVISIBLE);
                startActivityByUri(TYUris.REPLY_COMMENT);
                break;
            case R.id.tvMessage:
                viewMsg.setVisibility(View.INVISIBLE);
                startActivityByUri(TYUris.MINE_MESSAGE);
                break;
            case R.id.tvInvite:
                invite();
                break;
            case R.id.tvLogin:
                startActivityByUri(TYUris.USER_LOGIN, BundleUtil.createBundle("NAME", "1"));
                break;
            case R.id.tvRegist:
                startActivityByUri(TYUris.USER_REGIST, BundleUtil.createBundle("NAME", "1"));
                break;
            case R.id.ivAdvert:
                if (meInfo != null && !TextUtils.isEmpty(meInfo.getType())) {
                    if ("2".equals(meInfo.getType())) {
                        startActivityByUri(TYUris.WEBVIEW, BundleUtil.createBundle("url", meInfo.getLink()));
                    } else if ("3".equals(meInfo.getType())) {
                        Bundle normal = new Bundle();
                        normal.putString("ID", meInfo.getNid());
                        startActivityByUri(TYUris.NEWS_IMAGE, normal);
                    } else {
                        ivAdvert.setOnClickListener(null);
                    }
                }
                break;
            case R.id.tvSign:
                sign();
                break;
            case R.id.cvHead:
                CameraSdkParameterInfo mCameraSdkParameterInfo = new CameraSdkParameterInfo();
                mCameraSdkParameterInfo.setSingle_mode(true);
                mCameraSdkParameterInfo.setShow_camera(true);
                mCameraSdkParameterInfo.setMax_image(1);
                mCameraSdkParameterInfo.setCroper_image(true);

                Intent intent = new Intent();
                intent.setClassName(getActivity(), "com.muzhi.camerasdk.PhotoPickActivity");
                Bundle b = new Bundle();
                b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
                intent.putExtras(b);
                startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
                break;
            default:
                break;
        }
    }

    private void invite() {
        http.post(TYUrls.INVITE_FRIEND, new HttpCallback() {
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
                        ShareInfoType shareInfo = new ShareInfoType();
                        shareInfo.setTitle(json.optString("title"));
                        shareInfo.setContent(json.optString("subtitle"));
                        shareInfo.setUrl(json.optString("link"));
                        shareInfo.setNetImage(json.optString("img"));
                        ShareUtils.showShare(getActivity(), shareInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sign() {
        ((BaseActivity) getActivity()).showDialog();
        http.post(TYUrls.CHECK_IN, new HttpParams("uid", settings.USER_ID.getValue()).addCommonParam(), new HttpCallback() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                ((BaseActivity) getActivity()).dismissDialog();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                ((BaseActivity) getActivity()).dismissDialog();
                setSign(true);
                Calendar calendar = Calendar.getInstance();
                settings.USER_SIGN.setValue(calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE) + "1");
            }
        });
    }

    private void setSign(boolean isSign) {
        tvSign.setSelected(isSign);
        tvSign.setText(isSign ? R.string.mine_signed : R.string.mine_nosign);
        tvSign.setEnabled(!isSign);
    }

    @Override
    public void onReceiveForUri(int request, int result, Bundle data) {
        if (result == Activity.RESULT_OK && request == LOGOUT) {
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        LogUtil.e(true, "TabMeFragment", "onActivityResult ===============" + data.getExtras() + "  code = " + requestCode);
//        if (requestCode == CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY) {
        if (data != null) {
            CameraSdkParameterInfo mCameraSdkParameterInfo = (CameraSdkParameterInfo) data.getExtras().getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            ArrayList<String> list = mCameraSdkParameterInfo.getImage_list();
            Bitmap b = BitmapFactory.decodeFile(list.get(0));
            cvhead.setImageBitmap(b);
            LogUtil.e(true, "TabMeFragment", "onActivityResult " + list);
            if (!TextUtils.isEmpty(settings.USER_ID.getValue())) {
                try {
                    String url = TYUrls.BASE_URL + "?r=site/pic";
                    HttpParams params = new HttpParams("uid", settings.USER_ID.getValue());
                    params.put("img", new File(list.get(0)));
                    http.post(url, params, new HttpCallback() {
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
                                    ToastUtil.ToastShort("上传头像成功");
                                } else {
                                    ToastUtil.ToastShort(json.optString("error_msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.ToastShort("请先登录");
            }
        }
//        }
    }
}
