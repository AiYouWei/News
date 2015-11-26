package com.news.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYUris;
import com.news.adapters.NewsFragmentPagerAdapter;
import com.news.views.CalendarView;
import com.news.views.CalendarView.OnItemClickListener;
import com.news.views.SlidingTabView;

import org.androidx.frames.base.BaseTitleFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 报纸列表
 *
 * @author slioe shu
 */
public class TabPaperFragment extends BaseTitleFragment {
    private ViewPager vpContent;
    private SlidingTabView stvTabs;
    private PopupWindow dataUI;
    private ImageView ivData;
    private PagerContentFragment pagerFragment;
    private CurrentContentFragment currentFragment;
    private int type;

    @Override
    protected void initTitles() {
        super.initTitles();
        addImageToLeft(R.mipmap.paper_weibo, weiboListener);
        addImageToRight(R.mipmap.paper_date, dateListener);
        List<String> tags = new ArrayList<>();
        tags.add("报纸版面");
        tags.add("本期目录");
        stvTabs = new SlidingTabView(tags, getActivity(), R.drawable.slider_tabs_bg, vpContent);
        stvTabs.setCheck(0);
        type = 0;
        addViewToMiddle(stvTabs, null);
    }

    private OnClickListener weiboListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle web = new Bundle();
            web.putString("url", "http://m.weibo.cn/d/tyrbgfwb");
            startActivityByUri(TYUris.WEBVIEW, web);
        }
    };

    private OnClickListener dateListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dataUI.isShowing()) {
                ivData.setVisibility(View.INVISIBLE);
                dataUI.dismiss();
            } else {
                ivData.setVisibility(View.VISIBLE);
                dataUI.showAsDropDown(ivData);
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_tab_paper);

        vpContent = findView(R.id.vpContent);
        ivData = findView(R.id.ivRightArrow);

        ivData.setVisibility(View.INVISIBLE);
        initDataUI();

        ArrayList<Fragment> fragments = new ArrayList<>();
        pagerFragment = new PagerContentFragment();
        currentFragment = new CurrentContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINESE).format(new Date()));
        bundle.putInt("pager", 1);
        pagerFragment.setArguments(bundle);
        currentFragment.setArguments(bundle);
        fragments.add(pagerFragment);
        fragments.add(currentFragment);

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
            type = position;
            vpContent.setCurrentItem(position);
            stvTabs.setCheck(position);
        }
    };

    private void initDataUI() {
        final View view = View.inflate(getActivity(), R.layout.popup_window_calendar, null);
        final CalendarView calendarView = (CalendarView) view.findViewById(R.id.cvData);
        ImageView ivPre = (ImageView) view.findViewById(R.id.ivPre);
        ImageView ivNext = (ImageView) view.findViewById(R.id.ivNext);
        final TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        dataUI = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        dataUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dataUI.setFocusable(true);
        dataUI.setOutsideTouchable(true);
        dataUI.setTouchable(true);
        calendarView.setCalendarData(new Date());
        tvTime.setText(calendarView.getYearAndmonth());
        ivPre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(calendarView.clickLeftMonth());
            }
        });
        ivNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(calendarView.clickRightMonth());
            }
        });
        calendarView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate) {
                dataUI.dismiss();
                ivData.setVisibility(View.INVISIBLE);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINESE);
                pagerFragment.update(format.format(downDate), "01");
                currentFragment.update(format.format(downDate));
            }
        });


        dataUI.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ivData.setVisibility(View.INVISIBLE);
            }
        });
    }
}
