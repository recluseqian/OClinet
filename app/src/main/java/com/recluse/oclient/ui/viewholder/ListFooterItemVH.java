package com.recluse.oclient.ui.viewholder;

import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.data.BaseModuleInfo;
import com.recluse.oclient.data.ModuleInfo;

public class ListFooterItemVH extends BaseModuleViewHolder<ModuleInfo> {

    private static final String TAG = "LoadMoreItemVH";

    public ListFooterItemVH(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(ModuleInfo data, int position) {
        super.onBindData(data, position);
    }
}
