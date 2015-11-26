package com.news.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.TYDaily.R;

import java.util.List;

/**
 * 可滑动的标签View
 *
 * @author slioe shu
 */
public class SlidingTabView extends RelativeLayout implements OnCheckedChangeListener {
    private List<String> tags;
    private Context context;
    private int bgResID;
    private ViewPager vpPager;
    private RadioButton rbTag2, rbTag1, rbTag3;

    public SlidingTabView(List<String> tags, Context context, int bgResID, ViewPager vpPager) {
        super(context);
        this.tags = tags;
        this.context = context;
        this.bgResID = bgResID;
        this.vpPager = vpPager;

        initView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.view_sliding_tab, null);
        addView(view);

        RadioGroup rgTags = (RadioGroup) view.findViewById(R.id.rgTags);
        rbTag1 = (RadioButton) view.findViewById(R.id.rbTag1);
        rbTag2 = (RadioButton) view.findViewById(R.id.rbTag2);
        rbTag3 = (RadioButton) view.findViewById(R.id.rbTag3);
        View slider = findViewById(R.id.viewSlider);
        rbTag1.setText(tags.get(0));
        rbTag2.setText(tags.get(1));
        if (tags.size() == 2) {
            rbTag3.setVisibility(View.GONE);
        } else {
            rbTag3.setVisibility(View.VISIBLE);
            rbTag3.setText(tags.get(2));
        }
        rgTags.setBackgroundResource(bgResID);
        rgTags.setOnCheckedChangeListener(this);
    }

    public void setCheck(int position) {
        switch (position) {
            case 0:
                rbTag1.setChecked(true);
                break;
            case 1:
                rbTag2.setChecked(true);
                break;
            case 2:
                rbTag3.setChecked(true);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbTag1:
                vpPager.setCurrentItem(0);
                break;
            case R.id.rbTag2:
                vpPager.setCurrentItem(1);
                break;
            case R.id.rbTag3:
                vpPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}
