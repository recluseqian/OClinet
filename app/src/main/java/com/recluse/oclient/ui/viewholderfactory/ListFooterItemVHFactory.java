package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.data.ModuleInfo;
import com.recluse.oclient.ui.viewholder.ListFooterItemVH;

public class ListFooterItemVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<ModuleInfo> {

    private static final String TAG = "LoadMoreItemVHFactory";

    public ListFooterItemVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.load_more_item_layout;
    }

    @Override
    protected BaseRecyclerViewHolder createDefaultViewHolder(@NonNull View itemView) {
        return new ListFooterItemVH(itemView);
    }

    @Override
    protected boolean isDefaultType(ModuleInfo data, int position) {
        return data != null &&
                (data.getLocalInfo().mDataType == LocalInfo.LOCAL_DATA_TYPE_LOADING
                        || data.getLocalInfo().mDataType == LocalInfo.LOCAL_DATA_TYPE_NO_MORE);
    }
}
