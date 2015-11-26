package org.androidx.frames.adapters;

import android.content.Context;

import org.androidx.frames.R;
import org.androidx.frames.core.UniversalViewHolder;
import org.androidx.frames.entity.ShareItemType;

/**
 * 分享Item Adapter
 *
 * @author slioe shu
 */
public class ShareItemAdapter extends BaseListAdapter<ShareItemType> {

    public ShareItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, ShareItemType data) {
        holder.setImageRes(R.id.ivIcon, data.getIcon());
        holder.setText(R.id.tvName, data.getName());
    }
}
