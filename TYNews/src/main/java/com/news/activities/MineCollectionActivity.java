package com.news.activities;

import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TYDaily.R;
import com.news.adapters.MineCollectionAdapter;
import com.news.entities.CollectionType;
import com.umeng.message.PushAgent;

import org.androidx.frames.base.BaseTitleActivity;
import org.androidx.frames.utils.LogUtil;
import org.androidx.frames.utils.ToastUtil;
import org.androidx.frames.views.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 *
 * @author slioe shu
 */
public class MineCollectionActivity extends BaseTitleActivity implements OnItemClickListener, OnClickListener {
    private MineCollectionAdapter adapter;
    private RelativeLayout rlDel;


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

        xlvContent.setXListViewConfig(false, false, false);
        adapter = new MineCollectionAdapter(this, R.layout.adapter_mine_collection);
        xlvContent.setAdapter(adapter);
        xlvContent.setOnItemClickListener(this);
        tvDel.setOnClickListener(this);
        rlDel.setVisibility(View.GONE);
    }

    @Override
    protected void loadData() {
        super.loadData();

        List<CollectionType> cts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cts.add(new CollectionType());
        }
//        adapter.setData(cts);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.ToastShort("position = " + position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDel:
//                delItems();
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
                adapter.remove(adapter.getItem(key));
            }
        }
        adapter.resetSelects();
        adapter.resetVisiable();
        adapter.notifyDataSetChanged();
    }
}
