package org.androidx.frames.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import org.androidx.frames.R;
import org.androidx.frames.core.SystemBarTintManager;
import org.androidx.frames.utils.ResUtil;
import org.androidx.frames.utils.ResUtil.ResType;

/**
 * 状态对话框，用于初始化加载，网络请求加载和各种错误界面
 *
 * @author slioe shu
 */
public class StatusDialogFrament extends DialogFragment {

    /**
     * 添加沉浸式状态栏
     */
    private void addImmersiveBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getActivity().getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(ResUtil.getResIdByType(ResType.COLOR, "navi_default_bg"));
        tintManager.setNavigationBarTintEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return keyCode == KeyEvent.KEYCODE_BACK;
//            }
//        });
        getDialog().setCanceledOnTouchOutside(false); // 点击屏幕不消失

        return inflater.inflate(R.layout.fragment_status_dialog, container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addImmersiveBar();// 添加沉浸式状态栏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.status_dialog);

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = LayoutParams.MATCH_PARENT;
        params.width = LayoutParams.MATCH_PARENT;
        params.dimAmount = 0f;
        window.setAttributes(params);
    }
}