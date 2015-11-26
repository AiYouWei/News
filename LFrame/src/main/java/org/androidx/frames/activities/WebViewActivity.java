package org.androidx.frames.activities;

import org.androidx.frames.R;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.fragments.WebViewFragment;

/**
 * 显示网页
 *
 * @author slioe shu
 */
public class WebViewActivity extends BaseActivity {
    private WebViewFragment fragment;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activtiy_webview_layout);

        fragment = new WebViewFragment();
        fragment.setArguments(getIntent().getExtras());


    }

    @Override
    public void onBackPressed() {
        fragment.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().add(R.id.llContent, fragment).commit();
    }
}
