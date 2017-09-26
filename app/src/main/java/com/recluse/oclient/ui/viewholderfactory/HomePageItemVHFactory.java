package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.ui.viewholder.HomePageItemVH;

public class HomePageItemVHFactory extends BaseViewHolderFactory<HomeModuleInfo> {

    private static final String TAG = "HomePageVHFactory";

    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_PLAN = 1;
    private static final int VIEW_TYPE_SUBSCRIBE = 2;
    private static final int VIEW_TYPE_TOPIC = 3;

    private static final int VIEW_TYPE_COUNT = 4;

    public HomePageItemVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(HomeModuleInfo data, int position) {
        int type;
        if (data != null) {
            switch (data.style) {
                case 1:
                    type = VIEW_TYPE_DEFAULT;
                    break;
                case 2:
                    type = VIEW_TYPE_SUBSCRIBE;
                    break;
                case 3:
                    type = VIEW_TYPE_TOPIC;
                    break;
                case 4:
                    type = VIEW_TYPE_PLAN;
                    break;
                default:
                    return super.getViewType(data, position);
            }
            return type + getBaseType();
        }
        return super.getViewType(null, position);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (inflater == null) {
            Log.e(TAG, "can not get the inflater");
            return null;
        }

        View view = inflater.inflate(R.layout.module_item_layout, parent, false);
        switch (viewType - getBaseType()) {
            case VIEW_TYPE_DEFAULT:
                return new HomePageItemVH.ModuleItemVH(view);
            case VIEW_TYPE_PLAN:
                return new HomePageItemVH.PlanItemVH(view);
            case VIEW_TYPE_SUBSCRIBE:
                return new HomePageItemVH.SubscribeItemVH(view);
            case VIEW_TYPE_TOPIC:
                return new HomePageItemVH.ModuleItemTopicVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

}
