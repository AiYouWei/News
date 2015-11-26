package com.news.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.TYDaily.R;
import com.news.adapters.NewsFragmentPagerAdapter;
import com.news.fragments.ReplyCommentFragment;
import com.news.views.SlidingTabView;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论我的/回复我的
 *
 * @author slioe shu
 */
public class ReplyCommentActivity extends BaseTitleActivity {
    public enum Category {
        REPLY, COMMENT
    }

    private ViewPager vpContent;
    private SlidingTabView stvTabs;

    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        List<String> tags = new ArrayList<>();
        tags.add("我评论的");
        tags.add("回复我的");
        stvTabs = new SlidingTabView(tags, getActivity(), R.drawable.slider_tabs_bg, vpContent);
        stvTabs.setCheck(0);
        addViewToMiddle(stvTabs, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_reply_comment);

        vpContent = findView(R.id.vpContent);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ReplyCommentFragment reply = new ReplyCommentFragment();
        Bundle replyBundle = new Bundle();
        replyBundle.putSerializable(ReplyCommentFragment.KEY_CATEGORY, Category.REPLY);
        reply.setArguments(replyBundle);
        fragments.add(reply);
        ReplyCommentFragment comment = new ReplyCommentFragment();
        Bundle commentBundle = new Bundle();
        commentBundle.putSerializable(ReplyCommentFragment.KEY_CATEGORY, Category.COMMENT);
        comment.setArguments(commentBundle);
        fragments.add(comment);

        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());
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
