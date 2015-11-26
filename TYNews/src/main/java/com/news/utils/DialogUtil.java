package com.news.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.TYDaily.R;

/**
 * 自定义对话框
 *
 * @author slioe shu
 */
public class DialogUtil {

    /**
     * 显示弹出框
     *
     * @return 弹出框
     */
    public static PopupWindow showPopupWindow(View view) {
        final PopupWindow popupUI = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        popupUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupUI.setFocusable(true);
        popupUI.setOutsideTouchable(true);
        popupUI.setTouchable(true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupUI.dismiss();
            }
        });
        return popupUI;
    }

    public static PopupWindow showListWindow(Context context, OnClickListener listener) {
        View view = View.inflate(context, R.layout.dialog_item_layout, null);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        TextView tvM = (TextView) view.findViewById(R.id.tvMen);
        TextView tvF = (TextView) view.findViewById(R.id.tvFemen);
        TextView tvS = (TextView) view.findViewById(R.id.tvSecret);
        tvM.setOnClickListener(listener);
        tvF.setOnClickListener(listener);
        tvS.setOnClickListener(listener);
        return showPopupWindow(view);
    }
}
