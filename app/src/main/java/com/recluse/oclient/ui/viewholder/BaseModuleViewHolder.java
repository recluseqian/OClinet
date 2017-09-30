package com.recluse.oclient.ui.viewholder;

import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.data.ModuleInfo;

public class BaseModuleViewHolder<T extends ModuleInfo> extends BaseRecyclerViewHolder<T> {

    public BaseModuleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(T data, int position) {
        super.onBindData(data, position);
    }

    public int getDividerType() {
        if (mData != null) {
            return mData.getLocalInfo().mDividerType;
        }
        return LocalInfo.DIVIDER_NORMAL;
    }
}
