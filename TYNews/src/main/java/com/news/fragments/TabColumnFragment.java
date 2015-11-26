package com.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.TYDaily.R;
import com.news.adapters.NewsFragmentPagerAdapter;
import com.news.views.SlidingTabView;

import org.androidx.frames.base.BaseTitleFragment;
import org.androidx.frames.utils.BundleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 专栏列表
 *
 * @author slioe shu
 */
public class TabColumnFragment extends BaseTitleFragment {
    private ViewPager vpContent;
    private SlidingTabView stvTabs;

    @Override
    protected void initTitles() {
        super.initTitles();
        List<String> tags = new ArrayList<>();
        tags.add("专题速递");
        tags.add("区县市局");
        tags.add("商务合作");
        stvTabs = new SlidingTabView(tags, getActivity(), R.drawable.slider_tabs_bg, vpContent);
        stvTabs.setCheck(0);
        addViewToMiddle(stvTabs, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_tab_column);

        vpContent = findView(R.id.vpContent);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new SubjectContentFragment());
        fragments.add(new GovernmentContentFragment());
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle bundle = BundleUtil.createBundle(NewsContentFragment.KEY_NAME, "商务合作", NewsContentFragment.KEY_TID, "58");
        fragment.setArguments(bundle);
        fragments.add(fragment);

        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        adapter.appendList(fragments);
        vpContent.setOffscreenPageLimit(1);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOnPageChangeListener(pageListener);
    }

    /**
     * ViewPager切换监听方法
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            vpContent.setCurrentItem(position);
            stvTabs.setCheck(position);
        }
    };
}
