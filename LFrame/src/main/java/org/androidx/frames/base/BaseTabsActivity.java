package org.androidx.frames.base;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import org.androidx.frames.R;
import org.androidx.frames.core.TabsBarCreated;
import org.androidx.frames.core.UISkipDispatcher;
import org.androidx.frames.entity.TabBarType;
import org.androidx.frames.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 带选项卡的Activity
 *
 * @author slioe shu
 */
public abstract class BaseTabsActivity extends BaseActivity {
    private TabsBarCreated barCreated;
    private static final long TIME_INTERVAL = 2000L;
    private static boolean isExit = true;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_base_tabs);

        LinearLayout llTabs = (LinearLayout) findViewById(R.id.llTabs);
        View viewLine = findViewById(R.id.viewLine);
        UISkipDispatcher.getInstance().setHomeTabs(this, R.id.flContent);

        barCreated = TabsBarCreated.getInstance(this);
        barCreated.setTabsBarView(llTabs, viewLine);
        String tabs = getTabBar();
        if (!TextUtils.isEmpty(tabs)) {
            TabBarType tabBar = jsonParser.parseFromJson(tabs, TabBarType.class);
            barCreated.createTabsBar(tabBar);
        }
    }

    @Override
    protected void loadData() {
    }

    protected abstract String getTabBar();

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            appExitByBackKey(getActivity());
        }
    }

    /**
     * 双击返回键退出应用
     */
    private void appExitByBackKey(final Activity activity) {
        Timer exitTimer = null;
        if (isExit) {
            isExit = false;
            ToastUtil.ToastShort("再按一次退出程序");
            exitTimer = new Timer();
            exitTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    isExit = true;
                }
            }, TIME_INTERVAL);
        } else {
            activity.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        barCreated.onDistory();
    }
}
