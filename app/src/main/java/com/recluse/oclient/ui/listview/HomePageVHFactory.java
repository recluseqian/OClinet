package com.recluse.oclient.ui.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.ModuleInfo;
import com.recluse.oclient.data.ModuleItemInfo;
import com.recluse.base.utils.ViewsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePageVHFactory extends BaseViewHolderFactory<ModuleInfo> {

    private static final String TAG = "HomePageVHFactory";


    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_PLAN = 1;
    private static final int VIEW_TYPE_SUBSCRIBE = 2;
    private static final int VIEW_TYPE_TOPIC = 3;

    private static final int VIEW_TYPE_COUNT = 4;

    public HomePageVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(ModuleInfo data, int position) {
        int type = VIEW_TYPE_DEFAULT;
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
            }

            return type + getBaseType();
        }
        return BaseRecyclerItemAdapter.UNKNOWN_TYPE;
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
                return new ModuleItemDefaultVH(view);
            case VIEW_TYPE_PLAN:
                return new ModuleItemPlanVH(view);
            case VIEW_TYPE_SUBSCRIBE:
                return new ModuleItemSubscribeVH(view);
            case VIEW_TYPE_TOPIC:
                return new ModuleItemTopicVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    static class ModuleItemDefaultVH extends BaseRecyclerViewHolder<ModuleInfo> {

        @BindView(R.id.module_item_title)
        TextView mTitleView;
        @BindView(R.id.module_item_recycler_view)
        RecyclerView mRecyclerView;
        @BindView(R.id.module_item_footer)
        View mFooterView;

        List<ModuleItemInfo> mItemDataList;
        LinearLayoutManager mLayoutManager;
        BaseRecyclerItemAdapter<ModuleItemInfo> mAdapter;

        ModuleItemDefaultVH(View itemView) {
            super(itemView);
            mItemDataList = new ArrayList<>();
            Context context = itemView.getContext();
            mLayoutManager = createLayoutManager(context);
            mAdapter = createAdapter(context, mItemDataList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        BaseRecyclerItemAdapter<ModuleItemInfo> createAdapter(Context context, List<ModuleItemInfo> list) {
            return new BaseRecyclerItemAdapter<>(context, list, createVHFactoryList(context));
        }

        LinearLayoutManager createLayoutManager(Context context) {
            return new GridLayoutManager(context, 2);
        }

        List<BaseViewHolderFactory<ModuleItemInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<ModuleItemInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory(context));
            return list;
        }

        @Override
        public void onBindData(ModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            mTitleView.setText(data.moduleName);
            if (mItemDataList == null) {
                Log.e(TAG, "onBindData: mItemDataList is null");
                return;
            }

            mItemDataList.clear();
            if (data.items != null) {
                mItemDataList.addAll(data.items);
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }

            if (data.hasMore()) {
                ViewsUtils.setViewVisibility(mFooterView, View.VISIBLE);
            } else {
                ViewsUtils.setViewVisibility(mFooterView, View.GONE);
            }
        }
    }

    static class ModuleItemPlanVH extends ModuleItemDefaultVH {

        ModuleItemPlanVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        }

        @Override
        List<BaseViewHolderFactory<ModuleItemInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<ModuleItemInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory.HomePagePlanSubVHFactory(context));
            return list;
        }
    }

    static class ModuleItemSubscribeVH extends ModuleItemDefaultVH {

        ModuleItemSubscribeVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new GridLayoutManager(context, 3);
        }

        @Override
        List<BaseViewHolderFactory<ModuleItemInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<ModuleItemInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory.HomePageSubscribeSubVHFactory(context));
            return list;
        }
    }

    static class ModuleItemTopicVH extends ModuleItemDefaultVH {

        ModuleItemTopicVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new LinearLayoutManager(context);
        }

        @Override
        List<BaseViewHolderFactory<ModuleItemInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<ModuleItemInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory.HomePageTopicSubVHFactory(context));
            return list;
        }
    }
}
