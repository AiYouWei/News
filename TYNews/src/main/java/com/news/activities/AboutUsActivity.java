package com.news.activities;

import com.TYDaily.R;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;

/**
 * 关于我们
 *
 * @author slioe shu
 */
public class AboutUsActivity extends BaseTitleActivity {

    @Override
    protected void initTitles() {
        super.initTitles();
        PushAgent.getInstance(getActivity()).onAppStart();
        addDefaultLeft(null);
        addDefaultMiddle("关于我们");
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_about_us);
    }
}
