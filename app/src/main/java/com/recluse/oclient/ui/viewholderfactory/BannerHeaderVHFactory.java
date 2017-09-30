package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.BannerListProvider;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.ui.viewholder.BannerHeaderItemVH;

public class BannerHeaderVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<BannerListProvider> {

    private static final String TAG = "BannerHeaderVHFactory";

    public BannerHeaderVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.home_page_header_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<BannerListProvider> createDefaultViewHolder(@NonNull View itemView) {
        return new BannerHeaderItemVH(itemView);
    }

    @Override
    protected boolean isDefaultType(BannerListProvider data, int position) {
        return data != null && data.getLocalInfo().mDataType == LocalInfo.LOCAL_DATA_TYPE_HEADER;
    }
}
