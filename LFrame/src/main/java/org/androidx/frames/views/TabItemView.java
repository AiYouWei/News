package org.androidx.frames.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidx.frames.utils.DeviceUtil;

/**
 * 选项卡View
 *
 * @author slioe shu
 */
public class TabItemView extends RelativeLayout {
    private ImageView ivIcon; // 图片
    private TextView tvText; // 文字
    private LinearLayout llLayout; // 布局
    private Context context;

    public TabItemView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        llLayout = new LinearLayout(context);
        llLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT, TRUE);
        llLayout.setPadding(0, DeviceUtil.dp2px(3), 0, DeviceUtil.dp2px(3));
        addView(llLayout, params);

        // 初始化图片
        ivIcon = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageParams.gravity = Gravity.CENTER_HORIZONTAL;
        ivIcon.setFocusable(false);
        ivIcon.setClickable(false);
        llLayout.addView(ivIcon, imageParams);

        // 初始化文字
        tvText = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_HORIZONTAL;
        tvText.setFocusable(false);
        tvText.setClickable(false);
        llLayout.addView(tvText, textParams);
    }

    public void setImageRange(int w, int h) {
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(w, h);
        ivIcon.setLayoutParams(imageParams);
        invalidate();
    }

    public void setText(String text) {
        tvText.setText(text);
    }

    public String getText() {
        return tvText.getText().toString();
    }

    @SuppressWarnings("deprecation")
    public void setTextColor(int textColor) {
        tvText.setTextColor(context.getResources().getColorStateList(textColor));
    }

    public void setTextSize(int textSize) {
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setImage(int image) {
        ivIcon.setImageResource(image);
    }

    public void setLableColor(ColorStateList colors) {
        tvText.setTextColor(colors);
    }

    public void setLableColor(int color) {
        tvText.setTextColor(color);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        llLayout.setSelected(selected);
        ivIcon.setSelected(selected);
        tvText.setSelected(selected);
    }
}
