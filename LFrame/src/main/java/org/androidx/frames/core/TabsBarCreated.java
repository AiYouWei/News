package org.androidx.frames.core;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.androidx.frames.entity.GroupType;
import org.androidx.frames.entity.TabBarType;
import org.androidx.frames.entity.TabItemType;
import org.androidx.frames.utils.DeviceUtil;
import org.androidx.frames.views.TabItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Tab的解析和生成
 *
 * @author slioe shu
 */
public class TabsBarCreated {
    private static TabsBarCreated instance;
    private LinearLayout llTabs;
    private View viewLine;
    private Context context;
    private List<View> tabViews = new ArrayList<>();

    private TabsBarCreated(Context context) {
        this.context = context;
    }

    public static TabsBarCreated getInstance(Context context) {
        return instance == null ? instance = new TabsBarCreated(context) : instance;
    }

    /**
     * 设置Tab的容器
     *
     * @param llTabs Tab的容器
     */
    public void setTabsBarView(LinearLayout llTabs, View viewLine) {
        this.llTabs = llTabs;
        this.viewLine = viewLine;
    }

    public List<View> getTabViews() {
        return tabViews;
    }

    /**
     * 生成Tab控件
     *
     * @param tabBar 选项卡对象
     */
    public void createTabsBar(TabBarType tabBar) {
        // 背景
        if (!TextUtils.isEmpty(tabBar.getColor())) {
            llTabs.setBackgroundColor(Color.parseColor(tabBar.getColor()));
        }
        // 高度
        if (tabBar.getHight() > 0) {
            LayoutParams params = llTabs.getLayoutParams();
            params.height = DeviceUtil.dp2px(tabBar.getHight());
            llTabs.setLayoutParams(params);
        }
        // 分割线
        if ("-1".equals(tabBar.getLineColor())) {
            viewLine.setVisibility(View.GONE);
        } else {
            viewLine.setBackgroundColor(Color.parseColor(tabBar.getLineColor()));
            LayoutParams params = viewLine.getLayoutParams();
            params.height = (DeviceUtil.dp2px(1) + 1) / 2; // TODO: 2015-09-03 需优化适配， 在1080P中为2px， 小于1080P为1px
            viewLine.setLayoutParams(params);
        }
        // TabItem
        GroupType<TabItemType> items = tabBar.getItems();
        for (int i = 0; i < items.size(); i++) {
            TabItemType tabItem = items.get(i);
            TabItemView tabView = new TabItemView(context);
            // 文字
            if (!TextUtils.isEmpty(tabItem.getText())) {
                tabView.setText(tabItem.getText());
            }
            if (tabItem.getTextSize() > 0) {
                tabView.setTextSize(tabItem.getTextSize());
            }
            if (tabItem.getTextColor() > 0) {
                tabView.setTextColor(tabItem.getTextColor());
            }
            // 图片
            if (tabItem.getImage() > 0) {
                tabView.setImage(tabItem.getImage());
            }
            // TODO: 2015-09-03 红点
            // LayoutParams
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            params.weight = 1.0f;

            tabView.setOnClickListener(UISkipDispatcher.getTabClickListener(context, tabItem.getUri()));
            tabViews.add(tabView);

            llTabs.addView(tabView, params);
        }
        // 默认选中
        tabViews.get(tabBar.getDefaultItem()).performClick();
    }

    public void onDistory() {
        tabViews.clear();
        instance = null;
    }
}
