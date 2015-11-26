package org.androidx.frames.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.androidx.frames.R;

/**
 * 自定义对话框
 *
 * @author slioe shu
 */
public class DialogUtil {
    /**
     * 显示选择布局对话框
     *
     * @param context         context
     * @param msg             对话框信息
     * @param cancel          取消按钮显示
     * @param confirm         确定按钮显示
     * @param cancelListener  取消事件监听
     * @param confirmListener 确定事件监听
     * @return 自定义对话框
     */
    public static Dialog showNormalDialog(Context context, String msg, String cancel, String confirm, OnClickListener cancelListener, OnClickListener confirmListener) {
        Dialog dialog = new Dialog(context, R.style.normal_dialog);
        View view = View.inflate(context, R.layout.dialog_select_layout, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

        tvMsg.setText(msg);
        btnCancel.setText(cancel);
        btnConfirm.setText(confirm);
        btnCancel.setOnClickListener(cancelListener);
        btnConfirm.setOnClickListener(confirmListener);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (DeviceUtil.getScreenWidth() * 0.85);
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(false);
        return dialog;
    }
}
