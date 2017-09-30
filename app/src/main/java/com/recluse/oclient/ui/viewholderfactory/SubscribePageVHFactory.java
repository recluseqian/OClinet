package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.ui.viewholder.SubscribePageItemVH;

public class SubscribePageVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<SubscribeModuleInfo> {

    private static final String TAG = "SubscribePageVHFactory";

    public SubscribePageVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.subscirbe_item_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<SubscribeModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
        return new SubscribePageItemVH(itemView);
    }
}

