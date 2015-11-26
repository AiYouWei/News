package org.androidx.frames.activities;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

import org.androidx.frames.R;
import org.androidx.frames.adapters.ShareItemAdapter;
import org.androidx.frames.base.BaseActivity;
import org.androidx.frames.entity.ShareItemType;
import org.androidx.frames.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享界面
 *
 * @author slioe shu
 */
public class ShareChannelActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
    private final static int[] shareIcons = {R.mipmap.channel_sinaweibo, R.mipmap.channel_wechat, R.mipmap.channel_wechatgroup,
            R.mipmap.channel_qq, R.mipmap.channel_qzone, R.mipmap.channel_message};
    private final static String[] shareNames = {"微博", "微信", "朋友圈", "QQ", "QQ空间", "短信"};
    private ShareItemAdapter adapter;
    private RelativeLayout rlContent;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_share_channel);

        GridView gvShare = findView(R.id.gvShare);
        rlContent = findView(R.id.rlContent);


        adapter = new ShareItemAdapter(getActivity(), R.layout.adapter_share_item);

        gvShare.setNumColumns(4);
        gvShare.setAdapter(adapter);
        gvShare.setOnItemClickListener(this);
        rlContent.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        List<ShareItemType> items = new ArrayList<>();
        for (int i = 0; i < shareIcons.length; i++) {
            ShareItemType sit = new ShareItemType();
            sit.setIcon(shareIcons[i]);
            sit.setName(shareNames[i]);
            items.add(sit);
        }
        adapter.setData(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.ToastShort(shareNames[position]);
    }

    @Override
    public void onClick(View v) {
        if (v == rlContent) {
            finish();
        }
    }
}
