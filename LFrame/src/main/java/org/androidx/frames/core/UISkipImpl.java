package org.androidx.frames.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;

import org.androidx.frames.base.BaseActivity;

/**
 * UI跳转的实现
 *
 * @author slioe shu
 */
public class UISkipImpl implements UISkipable {
    private BaseActivity activity;
    private int REQUEST_DISABLE = -1;
    private String KEY_REQUEST = "xrequest";
    private String KEY_RECEIVER = "xreceiver";

    private UISkipImpl() {
    }

    public static UISkipImpl getInstance() {
        return new UISkipImpl();
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    private ResultReceiver receiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle data) {
            int request = REQUEST_DISABLE;
            if (data != null && data.containsKey(KEY_REQUEST)) {
                request = data.getInt(KEY_REQUEST);
            }
            onReceiveForUri(request, resultCode, data);
        }
    };

    @Override
    public BaseActivity getActivity() {
        return activity;
    }

    @Override
    public void startActivityByUri(@NonNull String uri) {
        startActivityForResultByUri(uri, REQUEST_DISABLE, null);
    }

    @Override
    public void startActivityByUri(String uri, Bundle data) {
        startActivityForResultByUri(uri, REQUEST_DISABLE, data);
    }

    @Override
    public void startActivityForResultByUri(String uri, int request) {
        startActivityForResultByUri(uri, request, null);
    }

    @Override
    public void startActivityForResultByUri(String uri, int request, Bundle data) {
        data = data == null ? new Bundle() : data;
        if (request != REQUEST_DISABLE) {
            data.putInt(KEY_REQUEST, request);
        }
        data.putParcelable(KEY_RECEIVER, receiver);
        UISkipDispatcher.getInstance().dispachetr(uri, data);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onReceiveForUri(int request, int result, Bundle data) {
        activity.onReceiveForUri(request, result, data);
    }

    @Override
    public void setResultForUri(int result, Bundle data) {
        Bundle request = activity.getIntent().getExtras();
        if (request != null && request.containsKey(KEY_RECEIVER)) {
            ResultReceiver receiver = request.getParcelable(KEY_RECEIVER);
            if (request.containsKey(KEY_REQUEST) && data != null) {
                int requestCode = request.getInt(KEY_REQUEST);
                data.putInt(KEY_REQUEST, requestCode);
            }
            if (receiver != null && data != null) {
                receiver.send(result, data);
            }
        }
    }
}
