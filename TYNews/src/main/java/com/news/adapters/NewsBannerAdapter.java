package com.news.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.news.entities.NewsBannerType;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * 新闻详情Banner适配器
 *
 * @author slioe shu
 */
public class NewsBannerAdapter extends PagerAdapter {
    private List<ImageView> views;
    private List<NewsBannerType> banners;
    private FinalBitmap loader;

    public NewsBannerAdapter(List<ImageView> views, List<NewsBannerType> banners, FinalBitmap loader) {
        this.views = views;
        this.banners = banners;
        this.loader = loader;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = views.get(position);
        int size = banners.size();
        if (position == 0) {
            loader.display(iv, banners.get(size - 1).getTopimag());
        } else if (position == (views.size() - 1)) {
            loader.display(iv, banners.get(0).getTopimag());
        } else {
            loader.display(iv, banners.get(position - 1).getTopimag());
        }
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView iv = views.get(position);
        container.removeView(iv);
        iv.setImageBitmap(null);
    }
}