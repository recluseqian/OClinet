package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.data.ModuleInfo;

/**
 * Created by recluse on 17-9-29.
 */

public class BannerHeaderItemVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<ModuleInfo> {

    private static final String TAG = "BannerHeaderVHFactory";

    public BannerHeaderItemVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return 0;
    }

    @Override
    protected BaseRecyclerViewHolder<ModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
        return null;
    }
}
