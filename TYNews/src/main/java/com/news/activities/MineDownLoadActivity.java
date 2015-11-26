package com.news.activities;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.TYAppliaction;
import com.news.TYSettings;
import com.news.adapters.MineDownAdapter;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.views.XListView;

import java.util.Arrays;

/**
 * 我的下载
 *
 * @author slioe shu
 */
public class MineDownLoadActivity extends BaseTitleActivity implements OnItemClickListener, OnClickListener {
    private MineDownAdapter adapter;
    private RelativeLayout rlDel;
    private TYSettings settings;


    @Override
    protected void initTitles() {
        super.initTitles();
        addDefaultLeft(null);
        addDefaultMiddle("我的收藏");
        addTextToRight("管理", managerListener);
    }

    private OnClickListener managerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.resetSelects();
            adapter.resetVisiable();
            adapter.setCheckBoxEnable(true);
            adapter.notifyDataSetChanged();
            rlDel.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        PushAgent.getInstance(getActivity()).onAppStart();
        setContentView(R.layout.activity_mine_collection);

        XListView xlvContent = findView(R.id.xlvContent);
        rlDel = findView(R.id.rlDel);
        TextView tvDel = findView(R.id.tvDel);

        settings = TYAppliaction.getInstance().getSettings();

        xlvContent.setXListViewConfig(false, false, false);
        adapter = new MineDownAdapter(this, R.layout.adapter_mine_download);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
        tvDel.setOnClickListener(this);
        rlDel.setVisibility(View.GONE);
    }

    @Override
    protected void loadData() {
        super.loadData();
        String down = settings.DOWN_LIST.getValue();
        if (!TextUtils.isEmpty(down)) {
            String[] s = down.split("#");
            adapter.setData(Arrays.asList(s));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDel:
                delItems();
                rlDel.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void delItems() {
        SparseArray<Boolean> items = adapter.getSelects();
        LogUtil.e(true, "MineCollectionActivity", "delItems items = " + items + "" + adapter.getCount());
        int key = 0;
        for (int i = 0; i < items.size(); i++) {
            key = items.keyAt(i);
            Boolean isCheck = items.get(key);
            if (isCheck) {
                String s = adapter.getItem(key);
                adapter.remove(s);
                String down = settings.DOWN_LIST.getValue();
                if (down.contains(s + "#")) {
                    down = down.replace(s + "#", "");
                } else if (down.contains(s)) {
                    down = down.replace(s, "");
                }
                settings.DOWN_LIST.setValue(down);
            }
        }
        adapter.resetSelects();
        adapter.resetVisiable();
        adapter.notifyDataSetChanged();
    }
}
