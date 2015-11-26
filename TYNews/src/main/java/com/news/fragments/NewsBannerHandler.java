package com.news.fragments;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.entities.NewsBannerType;

import java.util.List;

/**
 * Banner切换时UI的变化线程
 *
 * @author slioe shu
 */
public class NewsBannerHandler extends Handler {
    private TextView tvDesc;
    private RadioGroup rgGuide;
    private ViewPager viewPager;
    private List<NewsBannerType> banners;

    public NewsBannerHandler(ViewPager viewPager, TextView tvDesc, RadioGroup rgGuide, List<NewsBannerType> banners) {
        this.viewPager = viewPager;
        this.tvDesc = tvDesc;
        this.rgGuide = rgGuide;
        this.banners = banners;
        rgGuide.findViewById(R.id.banner_rb_1).setVisibility(View.GONE);
        rgGuide.findViewById(R.id.banner_rb_2).setVisibility(View.GONE);
        rgGuide.findViewById(R.id.banner_rb_3).setVisibility(View.GONE);
        rgGuide.findViewById(R.id.banner_rb_4).setVisibility(View.GONE);
        rgGuide.findViewById(R.id.banner_rb_5).setVisibility(View.GONE);
        for (int i = 0; i < banners.size(); i++) {
            switch (i) {
                case 0:
                    rgGuide.findViewById(R.id.banner_rb_1).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    rgGuide.findViewById(R.id.banner_rb_2).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    rgGuide.findViewById(R.id.banner_rb_3).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    rgGuide.findViewById(R.id.banner_rb_4).setVisibility(View.VISIBLE);
                    break;
                case 4:
                    rgGuide.findViewById(R.id.banner_rb_5).setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int index = msg.what;
        tvDesc.setText(banners.get(index - 1).getToptitle());
        switch (index) {
            case 1:
                rgGuide.check(R.id.banner_rb_1);
                break;
            case 2:
                rgGuide.check(R.id.banner_rb_2);
                break;
            case 3:
                rgGuide.check(R.id.banner_rb_3);
                break;
            case 4:
                rgGuide.check(R.id.banner_rb_4);
                break;
            case 5:
                rgGuide.check(R.id.banner_rb_5);
                break;
            default:
                break;
        }
    }
}
