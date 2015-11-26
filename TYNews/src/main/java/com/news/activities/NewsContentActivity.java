package com.news.activities;

import com.TYDaily.R;
import com.news.fragments.NewsContentFragment;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;

/**
 * @author slioe shu
 */
public class NewsContentActivity extends BaseTitleActivity {
    private NewsContentFragment fragment;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle(getIntent().getStringExtra(NewsContentFragment.KEY_NAME));
    }

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_news_content);

        fragment = new NewsContentFragment();
        fragment.setArguments(getIntent().getExtras());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().add(R.id.llContent, fragment).commit();
    }
}
