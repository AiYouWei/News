package org.androidx.frames.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import org.androidx.frames.R;

/**
 * 可刷新和加载的ListView
 *
 * @author slioe shu
 */
public class XListView extends ListView implements OnScrollListener {
    private float lastY = -1;
    private Scroller scroller;
    private OnScrollListener mScrollListener;
    private IXListViewListener listener;
    private XListViewHeader headerView;
    private RelativeLayout headerViewContent;
    private int headerViewHeight;
    private boolean enableRefresh = true;
    private boolean isRefreshing = true;
    private XListViewFooter footerView;
    private boolean enableLoad;
    private boolean isLoading;
    private boolean isFooterReady = false;
    private int totalItemCount;
    private int scrollBack;
    private ListAdapter adapter;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull feature.
    private boolean isDirectLoading = false;// 是否在底部显示直接加载。

    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    public void setDirectLoading(boolean isDirectLoading) {
        this.isDirectLoading = isDirectLoading;
    }

    private void initWithContext(Context context) {
        scroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        headerView = new XListViewHeader(context);
        headerViewContent = (RelativeLayout) headerView
                .findViewById(R.id.xlistview_header_content);
        addHeaderView(headerView);
        footerView = new XListViewFooter(context);
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        headerViewHeight = headerViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }


    /**
     * 设置无下拉加载的更多的状态
     */
    public void setNoLoadFooterView(String showText) {
        isLoading = false;
        footerView.show();
        footerView.getHintView().setVisibility(View.GONE);
        footerView.getProgressBar().setVisibility(View.GONE);
        footerView.getHintView().setText(showText);
        footerView.getProgressBar().setVisibility(View.GONE);
        footerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
        if (!isFooterReady) {
            isFooterReady = true;
            addFooterView(footerView);
            if (isDirectLoading) {
                setNoLoadFooterView("加载中...");
            }
        }
        super.setAdapter(adapter);
    }

    /**
     * 设置XListView的加载和刷新
     *
     * @param enableAutoLoad 是否自动刷新
     * @param enableRefresh  是否下拉刷新
     * @param enableLoad     是否上拉加载
     */
    public void setXListViewConfig(boolean enableAutoLoad, boolean enableRefresh, boolean enableLoad) {
        setDirectLoading(enableAutoLoad);
        setEnableLoad(enableLoad);
        setPullRefreshEnable(enableRefresh);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        enableRefresh = enable;
        if (!enableRefresh) { // disable, hide the content
            headerViewContent.setVisibility(View.GONE);
            headerView.setVisiableHeight(0);
        } else {
            headerViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setEnableLoad(boolean enable) {
        enableLoad = enable;
        if (!enableLoad) {
            footerView.hide();
            footerView.setOnClickListener(null);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(false);
        } else {
            isLoading = false;
            footerView.show();
            footerView.setState(XListViewFooter.STATE_NORMAL);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(true);
            // both "pull up" and "click" will invoke load more.
            footerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (isRefreshing) {
            isRefreshing = false;
            headerView.setState(XListViewHeader.STATE_FINISH);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            }, 1000L);

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            resetHeaderHeight();
        }
    };

    private boolean isAuto = true;

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (isLoading) {
            isLoading = false;
            isAuto = true;
            footerView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        headerView.setVisiableHeight((int) delta
                + headerView.getVisiableHeight());
        if (enableRefresh && !isRefreshing) { // 未处于刷新状态，更新箭头
            if (headerView.getVisiableHeight() > headerViewHeight) {
                headerView.setState(XListViewHeader.STATE_READY);
            } else {
                headerView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = headerView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (isRefreshing && height <= headerViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (isRefreshing && height > headerViewHeight) {
            finalHeight = headerViewHeight;
        }
        scrollBack = SCROLLBACK_HEADER;
        scroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = footerView.getBottomMargin() + (int) delta;
        if (enableLoad && !isLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                footerView.setState(XListViewFooter.STATE_READY);
            } else {
                footerView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        footerView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = footerView.getBottomMargin();
        if (bottomMargin > 0) {
            scrollBack = SCROLLBACK_FOOTER;
            scroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        isLoading = true;
        footerView.setState(XListViewFooter.STATE_LOADING);
        if (listener != null) {
            listener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (lastY == -1) {
            lastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - lastY;
                lastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (headerView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == totalItemCount - 1
                        && (footerView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                lastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (enableRefresh
                            && headerView.getVisiableHeight() > headerViewHeight) {
                        isRefreshing = true;
                        headerView.setState(XListViewHeader.STATE_REFRESHING);
                        if (listener != null) {
                            listener.onRefresh();
                        }
                    }
                    resetHeaderHeight();

                } else if (getLastVisiblePosition() == totalItemCount - 1) {
                    // invoke load more.
                    if (enableLoad
                            && footerView.getBottomMargin() > PULL_LOAD_MORE_DELTA
                            && !isLoading) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            if (scrollBack == SCROLLBACK_HEADER) {
                headerView.setVisiableHeight(scroller.getCurrY());
            } else {
                footerView.setBottomMargin(scroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && isDirectLoading && view.getAdapter() != null && enableLoad && !isLoading && isAuto && view.getLastVisiblePosition() > view.getAdapter().getCount() - 3) {
            isAuto = false;
            startLoadMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        this.totalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }

    }

    public void setXListViewListener(IXListViewListener listener) {
        this.listener = listener;
    }

    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }
}
