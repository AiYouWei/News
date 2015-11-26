package com.news.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.TYDaily.R;

import org.androidx.frames.adapters.BaseListAdapter;
import org.androidx.frames.core.UniversalViewHolder;
import org.androidx.frames.utils.LogUtil;

/**
 * 我的下载
 *
 * @author slioe shu
 */
public class MineDownAdapter extends BaseListAdapter<String> {
    private SparseArray<Boolean> selects;
    private SparseArray<Boolean> visibles;

    public MineDownAdapter(Context context, int layoutId) {
        super(context, layoutId);
        selects = new SparseArray<>();
        visibles = new SparseArray<>();
    }

    @Override
    public void setViewValue(UniversalViewHolder holder, String data) {
        holder.setText(R.id.tvTittle, data);
        CheckBox cbSelect = holder.findView(R.id.cbSelect);
        boolean visiable = false;
        if (visibles.get(getPosition()) == null) {
            visiable = false;
        } else {
            visiable = visibles.get(getPosition());
        }
        boolean check = false;
        if (selects.get(getPosition()) == null) {
            check = false;
        } else {
            check = selects.get(getPosition());
        }
        cbSelect.setVisibility(visiable ? View.VISIBLE : View.INVISIBLE);
        cbSelect.setChecked(check);

        cbSelect.setOnCheckedChangeListener(new CheckListener(holder.getPosition()));
    }

    public SparseArray<Boolean> getSelects() {
        return selects;
    }

    public void resetSelects() {
        selects.clear();
    }

    public void resetVisiable() {
        visibles.clear();
    }

    public void setCheckBoxEnable(boolean enable) {
        visibles.clear();
        for (int i = 0; i < getCount(); i++) {
            visibles.append(i, enable);
        }
    }


    private class CheckListener implements OnCheckedChangeListener {
        private int position;

        public CheckListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            LogUtil.e(true, "MineCollectionAdapter", "onCheckedChanged " + position + "   " + isChecked);
            selects.append(position, isChecked);
        }
    }
}
