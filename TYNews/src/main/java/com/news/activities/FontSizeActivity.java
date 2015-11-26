package com.news.activities;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.TYDaily.R;
import com.news.TYConstants.FontSize;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.BundleUtil;

/**
 * 字号选择
 *
 * @author slioe shu
 */
public class FontSizeActivity extends BaseTitleActivity implements OnCheckedChangeListener {
    public final static String KEY_FONTSIZE = "fontSize";

    @Override
    protected void initTitles() {
        super.initTitles();
        PushAgent.getInstance(getActivity()).onAppStart();
        addDefaultLeft(null);
        addDefaultMiddle("字号选择");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_font_size);

        int fontSize = getIntent().getIntExtra(KEY_FONTSIZE, FontSize.NORMAL.ordinal());

        RadioGroup rgFont = findView(R.id.rgFontSize);
        RadioButton rbLarge = findView(R.id.rbLarge);
        RadioButton rbBig = findView(R.id.rbBig);
        RadioButton rbNormal = findView(R.id.rbMiddle);
        RadioButton rbSmall = findView(R.id.rbSmall);

        if (fontSize == FontSize.LARGE.ordinal()) {
            rbLarge.setChecked(true);
        } else if (fontSize == FontSize.BIG.ordinal()) {
            rbBig.setChecked(true);
        } else if (fontSize == FontSize.NORMAL.ordinal()) {
            rbNormal.setChecked(true);
        } else if (fontSize == FontSize.SMALL.ordinal()) {
            rbSmall.setChecked(true);
        } else {
            rbNormal.setChecked(true);
        }
        rgFont.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FontSize fontSize;
        switch (checkedId) {
            case R.id.rbLarge:
                fontSize = FontSize.LARGE;
                break;
            case R.id.rbBig:
                fontSize = FontSize.BIG;
                break;
            case R.id.rbMiddle:
                fontSize = FontSize.NORMAL;
                break;
            case R.id.rbSmall:
                fontSize = FontSize.SMALL;
                break;
            default:
                fontSize = FontSize.NORMAL;
                break;
        }
        setResultForUri(RESULT_OK, BundleUtil.createBundle(KEY_FONTSIZE, fontSize.ordinal()));
        finish();
    }
}
