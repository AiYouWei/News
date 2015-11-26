package com.news.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.TYDaily.R;
import com.news.TYUris;

import org.androidx.frames.base.BaseTabsActivity;

import java.util.List;

/**
 * 带Tab选项卡的Activity
 *
 * @author slioe shu
 */
public class HomeTabsActivity extends BaseTabsActivity {

    @Override
    protected String getTabBar() {
        return "{\"barHeight\":\"50\", \"barColor\":\"#FFFFFF\", \"defaultItem\":\"0\", \"lineColor\":\"#D8D9DA\", \"items\":" +
                "[{\"index\":\"1\", \"text\":\"新闻\", \"textSize\":\"14\", \"textColor\":\"" + R.drawable.tab_text_selector + "\", \"uri\":\"" + TYUris.TAB_NEWS + "\", \"redNum\":\"-1\", \"image\":\"" + R.drawable.tab_news_selector + "\"}," +
                "{\"index\":\"2\", \"text\":\"报纸\", \"textSize\":\"14\", \"textColor\":\"" + R.drawable.tab_text_selector + "\", \"uri\":\"" + TYUris.TAB_PAPER + "\", \"redNum\":\"-1\", \"image\":\"" + R.drawable.tab_paper_selector + "\"}," +
                "{\"index\":\"3\", \"text\":\"专栏\", \"textSize\":\"14\", \"textColor\":\"" + R.drawable.tab_text_selector + "\", \"uri\":\"" + TYUris.TAB_COLUMN + "\", \"redNum\":\"-1\", \"image\":\"" + R.drawable.tab_column_selector + "\"}," +
                "{\"index\":\"4\", \"text\":\"民声\", \"textSize\":\"14\", \"textColor\":\"" + R.drawable.tab_text_selector + "\", \"uri\":\"" + TYUris.TAB_VOICE + "\", \"redNum\":\"-1\", \"image\":\"" + R.drawable.tab_voice_selector + "\"}," +
                "{\"index\":\"5\", \"text\":\"我\", \"textSize\":\"14\", \"textColor\":\"" + R.drawable.tab_text_selector + "\", \"uri\":\"" + TYUris.TAB_ME + "\", \"redNum\":\"-1\", \"image\":\"" + R.drawable.tab_me_selector + "\"}]}";
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if (f != null) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
