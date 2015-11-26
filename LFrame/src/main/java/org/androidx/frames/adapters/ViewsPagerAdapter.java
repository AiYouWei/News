package org.androidx.frames.adapters;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * ViewPager的Adapter， ViewPager中的内容为View， 用于引导页面和Banner页面
 *
 * @author slioe shu
 */
public class ViewsPagerAdapter extends BasePagerAdapter<ImageView> {

    public ViewsPagerAdapter() {
        super();
    }

    public ViewsPagerAdapter(List<ImageView> datas) {
        super(datas);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = getItem(position);
        container.addView(view);
        return view;
    }
}
