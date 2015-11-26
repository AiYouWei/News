package com.news.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.TYDaily.R;
import com.google.gson.reflect.TypeToken;
import com.news.TYSettings;
import com.news.TYUris;
import com.news.adapters.DragAdapter;
import com.news.adapters.NewsFragmentPagerAdapter;
import com.news.adapters.OtherAdapter;
import com.news.entities.ColumnsType;
import com.news.views.ColumnScrollView;
import com.news.views.DragGridView;
import com.news.views.OtherGridView;

import org.androidx.frames.base.BaseTitleFragment;
import org.androidx.frames.utils.BundleUtil;
import org.androidx.frames.utils.DeviceUtil;
import org.androidx.frames.utils.ImageUtil;
import org.androidx.frames.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻列表
 *
 * @author slioe shu
 */
public class TabNewsFragment extends BaseTitleFragment implements OnItemClickListener, OnClickListener {
    private TYSettings settings;
    private ImageView ivWeather;
    private PopupWindow weatherUI, columnUI;
    private ColumnScrollView csvColumn;
    private LinearLayout llColumns;
    private int columnSelectIndex = 0;
    private List<ColumnsType> columns, customColumns, remainingColumns;
    private RelativeLayout rlColumn;
    private ImageView ivColumnMore;
    private ViewPager vpContent;
    private NewsFragmentPagerAdapter adapetr;
    //===栏目管理===
    private DragGridView dgvCustom; // 已选栏目的GridView
    private OtherGridView ogvRemaining;
    private DragAdapter customAdapter;
    private OtherAdapter remainingAdapter;
    private boolean isMove = false; // 移动的标志，避免操作太频繁造成的数据错乱

    @Override
    protected void initTitles() {
        super.initTitles();
        addImageToLeft(R.mipmap.news_weather, weatherListener);
        addImageToMiddle(R.mipmap.news_title, null);
        addImageToRight(R.mipmap.news_search, searchListener);
    }

    private OnClickListener weatherListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ivWeather.setVisibility(View.VISIBLE);
            initWeatherPopup();
            weatherUI.showAsDropDown(v, 0, DeviceUtil.dp2px(3));
        }
    };

    private OnClickListener searchListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityByUri(TYUris.NEWS_SEARCH);
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_tab_news);

        settings = application.getSettings();
        Type listType = new TypeToken<LinkedList<ColumnsType>>() {
        }.getType();
        columns = jsonParser.parseFromJson(settings.ALL_COLUMNS.getValue(), listType);
        customColumns = jsonParser.parseFromJson(settings.CUSTOM_COLUMNS.getValue(), listType);
        remainingColumns = jsonParser.parseFromJson(settings.REMAINING_COLUMNS.getValue(), listType);
        initWeatherPopup();
        initColumnPopup();
        columnUI.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                settings.CUSTOM_COLUMNS.setValue(jsonParser.parseToJson(customColumns));
                settings.REMAINING_COLUMNS.setValue(jsonParser.parseToJson(remainingColumns));
                initTabColumn();
            }
        });

        ivWeather = findView(R.id.ivWeatherArrow);
        ivWeather.setVisibility(View.GONE);
        ivColumnMore = findView(R.id.ivColumnMore);
        rlColumn = findView(R.id.rlColumn);
        csvColumn = findView(R.id.csvColumn);
        llColumns = findView(R.id.llColumns);
        vpContent = findView(R.id.vpContent);
        adapetr = new NewsFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        initFragments();
        vpContent.setOffscreenPageLimit(1);
        vpContent.setAdapter(adapetr);
        vpContent.setOnPageChangeListener(pageListener);
        initTabColumn();

        ivColumnMore.setOnClickListener(this);
    }

    /**
     * 初始化天气弹出框
     */
    private void initWeatherPopup() {
        final View filter = View.inflate(getActivity(), R.layout.view_weather_popup, null);
        ImageView ivTen = findView(filter, R.id.ivTen);
        ImageView ivBit = findView(filter, R.id.ivBit);
        ImageView ivTen1 = findView(filter, R.id.ivTen1);
        ImageView ivBit1 = findView(filter, R.id.ivBit1);
        ImageView ivPlusLeft = findView(filter, R.id.iv_plus_right);
        ImageView ivPlusRight = findView(filter, R.id.iv_plus_right);

        TextView tvTime = findView(filter, R.id.tvTime);
        ImageView ivWeather1 = findView(filter, R.id.ivWeather);
        TextView tvWeather = findView(filter, R.id.tvWeather);

        String weather = settings.WEATHER.getValue();
        if (!TextUtils.isEmpty(weather)) {
            String weathers[] = settings.WEATHER.getValue().split("#");
            LogUtil.e(true, "TabNewsFragment", "initWeatherPopup " + weathers[1]);
            tvTime.setText(weathers[0]);
            Calendar calendar = Calendar.getInstance();
            int time = calendar.get(Calendar.HOUR_OF_DAY);
            String t1 = weathers[1].substring(0, weathers[1].indexOf("°"));
            setTemp(t1, ivTen, ivBit);
            if (!t1.contains("--") && Integer.parseInt(t1) < 0) {
                ivPlusRight.setVisibility(View.VISIBLE);
            } else {
                ivPlusRight.setVisibility(View.GONE);
            }
            setWeather(weathers[2].split("@")[0], ivWeather1, tvWeather, false);
            String t2 = weathers[1].substring(weathers[1].indexOf("/") + 1, weathers[1].lastIndexOf("°"));
            if (Integer.parseInt(t2) < 0) {
                ivPlusRight.setVisibility(View.VISIBLE);
            } else {
                ivPlusRight.setVisibility(View.GONE);
            }
            setTemp(t2, ivTen1, ivBit1);
//            setWeather(weathers[2].split("@")[1], ivWeather1, tvWeather, true);
            for (String ws : weathers) {
                LogUtil.d(true, "\n TabNewsFragment:", ws);

            }
        }

        weatherUI = new PopupWindow(filter, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        weatherUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        weatherUI.setFocusable(true);
        weatherUI.setOutsideTouchable(true);
        weatherUI.setTouchable(true);
        // 添加灰色背景后点击消失
        filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherUI.dismiss();
                ivWeather.setVisibility(View.GONE);
            }
        });
    }

    private void setTemp(String t, ImageView ivTen, ImageView ivBit) {
        int num = 0;
        try {
            num = Integer.parseInt(t);
        } catch (NumberFormatException e) {
            num = -1;
        }
        setIv(num / 10, ivTen);
        setIv(num % 10, ivBit);
    }

    private void setIv(int t, ImageView iv) {
        switch (t) {
//            case -1:
//                iv.setImageResource(R.mipmap.weather_unknow);
//                break;
            case 0:
                iv.setImageResource(R.mipmap.weather_0);
                break;
            case 1:
                iv.setImageResource(R.mipmap.weather_1);
                break;
            case 2:
                iv.setImageResource(R.mipmap.weather_2);
                break;
            case 3:
                iv.setImageResource(R.mipmap.weather_3);
                break;
            case 4:
                iv.setImageResource(R.mipmap.weather_4);
                break;
            case 5:
                iv.setImageResource(R.mipmap.weather_5);
                break;
            case 6:
                iv.setImageResource(R.mipmap.weather_6);
                break;
            case 7:
                iv.setImageResource(R.mipmap.weather_7);
                break;
            case 8:
                iv.setImageResource(R.mipmap.weather_8);
                break;
            case 9:
                iv.setImageResource(R.mipmap.weather_9);
                break;
        }
    }

    private void setWeather(String weather, ImageView iv, TextView tv, boolean isNight) {
        String path = isNight ? "night" : "day";
        Bitmap bitmap = ImageUtil.getImageFromAssetsFile(getActivity(), path + "/" + weather + ".png");
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        }
        if (weather.equals("00")) {
            tv.setText("晴");
        } else if (weather.equals("01")) {
            tv.setText("多云");
        } else if (weather.equals("02")) {
            tv.setText("阴");
        } else if (weather.equals("03")) {
            tv.setText("阵雨");
        } else if (weather.equals("04")) {
            tv.setText("雷阵雨");
        } else if (weather.equals("05")) {
            tv.setText("雷阵雨伴有冰雹");
        } else if (weather.equals("06")) {
            tv.setText("雨夹雪");
        } else if (weather.equals("07")) {
            tv.setText("小雨");
        } else if (weather.equals("08")) {
            tv.setText("中雨");
        } else if (weather.equals("09")) {
            tv.setText("大雨");
        } else if (weather.equals("10")) {
            tv.setText("暴雨");
        } else if (weather.equals("11")) {
            tv.setText("大暴雨");
        } else if (weather.equals("12")) {
            tv.setText("特大暴雨");
        } else if (weather.equals("13")) {
            tv.setText("阵雪");
        } else if (weather.equals("14")) {
            tv.setText("小雪");
        } else if (weather.equals("15")) {
            tv.setText("中雪");
        } else if (weather.equals("16")) {
            tv.setText("大雪");
        } else if (weather.equals("17")) {
            tv.setText("暴雪");
        } else if (weather.equals("18")) {
            tv.setText("雾");
        } else if (weather.equals("19")) {
            tv.setText("冻雨");
        } else if (weather.equals("20")) {
            tv.setText("沙尘暴");
        } else if (weather.equals("21")) {
            tv.setText("小到中雨");
        } else if (weather.equals("22")) {
            tv.setText("中到大雨");
        } else if (weather.equals("23")) {
            tv.setText("大到暴雨");
        } else if (weather.equals("24")) {
            tv.setText("暴雨到大暴雨");
        } else if (weather.equals("25")) {
            tv.setText("大暴雨到特大暴雨");
        } else if (weather.equals("26")) {
            tv.setText("小到中雪");
        } else if (weather.equals("27")) {
            tv.setText("中到大雪");
        } else if (weather.equals("28")) {
            tv.setText("大到暴雪");
        } else if (weather.equals("29")) {
            tv.setText("浮尘");
        } else if (weather.equals("30")) {
            tv.setText("扬沙");
        } else if (weather.equals("31")) {
            tv.setText("强沙尘暴");
        } else if (weather.equals("53")) {
            tv.setText("霾");
        } else if (weather.equals("99")) {
            tv.setText("无");
        }
    }

    /**
     * 初始化栏目管理弹出框
     */
    private void initColumnPopup() {
        final View filter = View.inflate(getActivity(), R.layout.view_column_manager, null);
        columnUI = new PopupWindow(filter, ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT, true);
        columnUI.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        columnUI.setFocusable(true);
        columnUI.setOutsideTouchable(true);
        columnUI.setTouchable(true);
        ImageView ivDel = (ImageView) filter.findViewById(R.id.ivDel);
        dgvCustom = (DragGridView) filter.findViewById(R.id.dgvSelected);
        ogvRemaining = (OtherGridView) filter.findViewById(R.id.dgvOther);
        customAdapter = new DragAdapter(getActivity(), customColumns);
        dgvCustom.setAdapter(customAdapter);
        if (remainingColumns == null) {
            remainingColumns = new ArrayList<>();
        }
        remainingAdapter = new OtherAdapter(getActivity(), remainingColumns);
        ogvRemaining.setAdapter(remainingAdapter);
        // 设置GRIDVIEW的ITEM的点击监听
        ogvRemaining.setOnItemClickListener(this);
        dgvCustom.setOnItemClickListener(this);

        ivDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                columnUI.dismiss();
            }
        });
    }

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        int screenWidth = DeviceUtil.getScreenWidth();
        for (int i = 0; i < llColumns.getChildCount(); i++) {
            View checkView = llColumns.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - screenWidth / 7;
            csvColumn.smoothScrollTo(i2, 0);
        }
        // 判断是否选中
        for (int j = 0; j < llColumns.getChildCount(); j++) {
            View checkView = llColumns.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
                ((TextView) checkView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else {
                ischeck = false;
                ((TextView) checkView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     * 初始化Column栏目项
     */
    @SuppressWarnings("deprecation")
    private void initTabColumn() {
        llColumns.removeAllViews();
        int count = customColumns.size();
        int screenWidth = DeviceUtil.getScreenWidth();
        int mItemWidth = screenWidth / 7;
        csvColumn.setParam(getActivity(), screenWidth, llColumns, ivColumnMore, rlColumn);
        for (int i = 0; i < count; i++) {
            LayoutParams params = new LayoutParams(mItemWidth, LayoutParams.MATCH_PARENT);
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setTextColor(getResources().getColorStateList(R.color.column_text_selector));
            columnTextView.setText(customColumns.get(i).getName());
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
                columnTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else {
                columnTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            columnTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < llColumns.getChildCount(); i++) {
                        View localView = llColumns.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                            ((TextView) localView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        } else {
                            localView.setSelected(true);
                            ((TextView) localView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                            vpContent.setCurrentItem(i);
                        }
                    }
                }
            });
            llColumns.addView(columnTextView, i, params);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id) {
        if (isMove) {// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
            return;
        }
        switch (parent.getId()) {
            case R.id.dgvSelected:
                if (position != 0) { // position为 0的不进行任何操作
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ColumnsType channel = ((DragAdapter) parent.getAdapter()).getItem(position);// 获取点击的频道内容
                        remainingAdapter.setVisible(false);
                        remainingAdapter.addItem(channel);// 添加到最后一个
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    // 获取终点的坐标
                                    ogvRemaining.getChildAt(ogvRemaining.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    moveAnim(moveImageView, startLocation, endLocation, channel, dgvCustom);
                                    customAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.dgvOther:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ColumnsType channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    customAdapter.setVisible(false);
                    customAdapter.addItem(channel);// 添加到最后一个

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                // 获取终点的坐标
                                dgvCustom.getChildAt(dgvCustom.getLastVisiblePosition())
                                        .getLocationInWindow(endLocation);
                                moveAnim(moveImageView, startLocation, endLocation, channel,
                                        ogvRemaining);
                                remainingAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(cache);
        return iv;
    }

    private void moveAnim(View moveView, int[] startLocation, int[] endLocation, final ColumnsType moveChannel,
                          final GridView clickGridView) {
        // 将当前栏目增加到改变过的listview中 若栏目已经存在删除点，不存在添加进去
        int[] initLocation = new int[2];
        // 获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        // 得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        // 创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);// 动画时间
        // 动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGridView) {
                    remainingAdapter.setVisible(true);
                    remainingAdapter.notifyDataSetChanged();
                    customAdapter.remove();
                } else {
                    customAdapter.setVisible(true);
                    customAdapter.notifyDataSetChanged();
                    remainingAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LayoutParams mLayoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(getActivity());
        moveLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    private void initFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < customColumns.size(); i++) {
            NewsContentFragment fragment = new NewsContentFragment();
            Bundle bundle = BundleUtil.createBundle(NewsContentFragment.KEY_NAME, customColumns.get(i).getName(), NewsContentFragment.KEY_TID, customColumns.get(i).getTid());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapetr.appendList(fragments);
    }

    /**
     * ViewPager切换监听方法
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {
        private int position = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            NewsContentFragment fragmentFrom = (NewsContentFragment) adapetr.getItem(this.position);
            NewsContentFragment fragmentTo = (NewsContentFragment) adapetr.getItem(this.position);
            if (fragmentTo.isAdded()) {

            }
            vpContent.setCurrentItem(position);
            selectTab(position);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivColumnMore:
                columnUI.showAsDropDown(llColumns, 0, -DeviceUtil.dp2px(40));
                break;
            default:
                break;
        }
    }
}
