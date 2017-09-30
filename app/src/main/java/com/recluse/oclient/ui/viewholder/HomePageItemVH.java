package com.recluse.oclient.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.recluse.base.utils.ViewsUtils;
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.data.HomeModuleSubInfo;
import com.recluse.oclient.ui.viewholderfactory.HomePageSubItemVHFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class HomePageItemVH extends BaseModuleViewHolder<HomeModuleInfo> {

    private static final String TAG = "HomeModuleVH";

    @BindView(R.id.module_item_title)
    TextView mTitleView;
    @BindView(R.id.module_item_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.module_item_footer)
    View mFooterView;

    List<HomeModuleSubInfo> mItemDataList;
    LinearLayoutManager mLayoutManager;
    BaseRecyclerItemAdapter<HomeModuleSubInfo> mAdapter;

    HomePageItemVH(View itemView) {
        super(itemView);
        mItemDataList = new ArrayList<>();
        Context context = itemView.getContext();
        mLayoutManager = createLayoutManager(context);
        mAdapter = new BaseRecyclerItemAdapter<>(context, mItemDataList, createVHFactoryList(context));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    abstract LinearLayoutManager createLayoutManager(Context context);

    abstract List<BaseViewHolderFactory<HomeModuleSubInfo>> createVHFactoryList(Context context);

    @Override
    public void onBindData(HomeModuleInfo data, int position) {
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

    public static class ModuleItemVH extends HomePageItemVH {

        public ModuleItemVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new GridLayoutManager(context, 2);
        }

        @Override
        List<BaseViewHolderFactory<HomeModuleSubInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeModuleSubInfo>> list = new ArrayList<>();
            list.add(new HomePageSubItemVHFactory.ModuleSubItemVHFactory(context));
            return list;
        }
    }

    public static class PlanItemVH extends HomePageItemVH {

        public PlanItemVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        }

        @Override
        List<BaseViewHolderFactory<HomeModuleSubInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeModuleSubInfo>> list = new ArrayList<>();
            list.add(new HomePageSubItemVHFactory.PlanSubItemVHFactory(context));
            return list;
        }
    }

    public static class SubscribeItemVH extends HomePageItemVH {

        public SubscribeItemVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new GridLayoutManager(context, 3);
        }

        @Override
        List<BaseViewHolderFactory<HomeModuleSubInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeModuleSubInfo>> list = new ArrayList<>();
            list.add(new HomePageSubItemVHFactory.SubscribeSubItemVHFactory(context));
            return list;
        }
    }

    public static class ModuleItemTopicVH extends HomePageItemVH {

        public ModuleItemTopicVH(View itemView) {
            super(itemView);
        }

        @Override
        LinearLayoutManager createLayoutManager(Context context) {
            return new LinearLayoutManager(context);
        }

        @Override
        List<BaseViewHolderFactory<HomeModuleSubInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeModuleSubInfo>> list = new ArrayList<>();
            list.add(new HomePageSubItemVHFactory.TopicSubItemVHFactory(context));
            return list;
        }
    }

}
