package com.news.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.activities.ManagerEditActivity;
import com.news.adapters.NewsFragmentPagerAdapter;
import com.news.views.SlidingTabView;

import org.androidx.frames.base.BaseTitleFragment;
import org.androidx.frames.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 民声列表
 *
 * @author slioe shu
 */
@SuppressWarnings("deprecation")
public class TabVoiceFragment extends BaseTitleFragment implements OnClickListener {
    public enum Category {
        CLUE, URBAN, SUP
    }

    private ViewPager vpContent;
    private SlidingTabView stvTabs;
    private PopupWindow helpUI, publishUI;
    private ImageView ivHelp, ivPublish;
    private RelativeLayout rlClue, rlManager, rlControl;
    private TYSettings settings;

    @Override
    protected void initTitles() {
        super.initTitles();
        List<String> tags = new ArrayList<>();
        tags.add("新闻线索");
        tags.add("全民城管");
        tags.add("舆情监督");
        stvTabs = new SlidingTabView(tags, getActivity(), R.drawable.slider_tabs_bg, vpContent);
        stvTabs.setCheck(0);
        addViewToMiddle(stvTabs, null);
        addImageToLeft(R.mipmap.know_more, helpListener);
        addImageToRight(R.mipmap.voice_public, publishListener);
    }

    private OnClickListener helpListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (helpUI.isShowing()) {
                helpUI.dismiss();
                ivHelp.setVisibility(View.GONE);
            } else {
                helpUI.showAsDropDown(ivHelp);
                ivHelp.setVisibility(View.VISIBLE);
            }
        }
    };

    private OnClickListener publishListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (publishUI.isShowing()) {
                publishUI.dismiss();
                ivPublish.setVisibility(View.GONE);
            } else {
                publishUI.showAsDropDown(ivPublish);
                ivPublish.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_tab_voice);

        vpContent = findView(R.id.vpContent);
        ivHelp = findView(R.id.ivArrow);
        ivPublish = findView(R.id.ivRightArrow);
        settings = TYAppliaction.getInstance().getSettings();

        ArrayList<Fragment> fragments = new ArrayList<>();
        PeopleVoiceFragment clue = new PeopleVoiceFragment();
        Bundle clueBundle = new Bundle();
        clueBundle.putSerializable(PeopleVoiceFragment.KEY_CATEGORY, Category.CLUE);
        clue.setArguments(clueBundle);
        fragments.add(clue);

        PeopleVoiceFragment manager = new PeopleVoiceFragment();
        Bundle managerBundle = new Bundle();
        managerBundle.putSerializable(PeopleVoiceFragment.KEY_CATEGORY, Category.URBAN);
        manager.setArguments(managerBundle);
        fragments.add(manager);

        PeopleVoiceFragment control = new PeopleVoiceFragment();
        Bundle controlBundle = new Bundle();
        controlBundle.putSerializable(PeopleVoiceFragment.KEY_CATEGORY, Category.SUP);
        control.setArguments(controlBundle);
        fragments.add(control);

        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        adapter.appendList(fragments);
        vpContent.setOffscreenPageLimit(1);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOnPageChangeListener(pageListener);
        ivHelp.setVisibility(View.INVISIBLE);
        ivPublish.setVisibility(View.INVISIBLE);

        initHelpUI();
        initPublishUI();
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

    private void initPublishUI() {
        final View filter = View.inflate(getActivity(), R.layout.popup_window_publish, null);
        rlClue = (RelativeLayout) filter.findViewById(R.id.rlClue);
        rlManager = (RelativeLayout) filter.findViewById(R.id.rlManager);
        rlControl = (RelativeLayout) filter.findViewById(R.id.rlControl);

        rlClue.setOnClickListener(this);
        rlManager.setOnClickListener(this);
        rlControl.setOnClickListener(this);

        publishUI = new PopupWindow(filter, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        publishUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        publishUI.setFocusable(true);
        publishUI.setOutsideTouchable(true);
        publishUI.setTouchable(true);
        filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                publishUI.dismiss();
                ivPublish.setVisibility(View.GONE);
            }
        });
        publishUI.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ivPublish.setVisibility(View.GONE);
            }
        });
    }

    private void initHelpUI() {
        final View filter = View.inflate(getActivity(), R.layout.popup_window_help, null);
        helpUI = new PopupWindow(filter, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        helpUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpUI.setFocusable(true);
        helpUI.setOutsideTouchable(true);
        helpUI.setTouchable(true);
        filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                helpUI.dismiss();
                ivHelp.setVisibility(View.GONE);
            }
        });
        helpUI.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ivHelp.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlClue:
                if (TextUtils.isEmpty(settings.USER_ID.getValue())) {
                    ToastUtil.ToastShort("请先登录");
                    publishUI.dismiss();
                    return;
                }
                Bundle clue = new Bundle();
                clue.putSerializable(ManagerEditActivity.VOICE_EDIT, Category.CLUE);
                startActivityByUri(TYUris.MANAGER_EDIT, clue);
                publishUI.dismiss();
                ivPublish.setVisibility(View.GONE);
                break;
            case R.id.rlManager:
                if (TextUtils.isEmpty(settings.USER_ID.getValue())) {
                    ToastUtil.ToastShort("请先登录");
                    publishUI.dismiss();
                    return;
                }
                Bundle manager = new Bundle();
                manager.putSerializable(ManagerEditActivity.VOICE_EDIT, Category.URBAN);
                startActivityByUri(TYUris.MANAGER_EDIT, manager);
                publishUI.dismiss();
                ivPublish.setVisibility(View.GONE);
                break;
            case R.id.rlControl:
                if (TextUtils.isEmpty(settings.USER_ID.getValue())) {
                    ToastUtil.ToastShort("请先登录");
                    publishUI.dismiss();
                    return;
                }
                Bundle sup = new Bundle();
                sup.putSerializable(ManagerEditActivity.VOICE_EDIT, Category.SUP);
                startActivityByUri(TYUris.MANAGER_EDIT, sup);
                publishUI.dismiss();
                ivPublish.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
