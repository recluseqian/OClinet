package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.utils.DateUtils;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.utils.ViewsUtils;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.StartActivityUtils;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.ui.viewholder.SubscribePageItemVH;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

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

