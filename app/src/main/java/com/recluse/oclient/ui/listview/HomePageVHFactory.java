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
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.data.HomeSubModuleInfo;
import com.recluse.base.utils.ViewsUtils;
import com.recluse.oclient.ui.BannerViewPagerHelper;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePageVHFactory extends BaseViewHolderFactory<HomeModuleInfo> {

    private static final String TAG = "HomePageVHFactory";


    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_PLAN = 1;
    private static final int VIEW_TYPE_SUBSCRIBE = 2;
    private static final int VIEW_TYPE_TOPIC = 3;
    private static final int VIEW_TYPE_BANNER = 4;

    private static final int VIEW_TYPE_COUNT = 5;

    public HomePageVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(HomeModuleInfo data, int position) {
        int type = VIEW_TYPE_DEFAULT;
        if (data != null) {
            if (data.mBannerInfoList != null) {
                type = VIEW_TYPE_BANNER;
            } else {
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
            case VIEW_TYPE_BANNER:
                view = inflater.inflate(R.layout.home_page_header_layout, parent, false);
                return new HomePageBannerVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    static class ModuleItemDefaultVH extends BaseRecyclerViewHolder<HomeModuleInfo> {

        @BindView(R.id.module_item_title)
        TextView mTitleView;
        @BindView(R.id.module_item_recycler_view)
        RecyclerView mRecyclerView;
        @BindView(R.id.module_item_footer)
        View mFooterView;

        List<HomeSubModuleInfo> mItemDataList;
        LinearLayoutManager mLayoutManager;
        BaseRecyclerItemAdapter<HomeSubModuleInfo> mAdapter;

        ModuleItemDefaultVH(View itemView) {
            super(itemView);
            mItemDataList = new ArrayList<>();
            Context context = itemView.getContext();
            mLayoutManager = createLayoutManager(context);
            mAdapter = createAdapter(context, mItemDataList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        BaseRecyclerItemAdapter<HomeSubModuleInfo> createAdapter(Context context, List<HomeSubModuleInfo> list) {
            return new BaseRecyclerItemAdapter<>(context, list, createVHFactoryList(context));
        }

        LinearLayoutManager createLayoutManager(Context context) {
            return new GridLayoutManager(context, 2);
        }

        List<BaseViewHolderFactory<HomeSubModuleInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeSubModuleInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory(context));
            return list;
        }

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
        List<BaseViewHolderFactory<HomeSubModuleInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeSubModuleInfo>> list = new ArrayList<>();
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
        List<BaseViewHolderFactory<HomeSubModuleInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeSubModuleInfo>> list = new ArrayList<>();
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
        List<BaseViewHolderFactory<HomeSubModuleInfo>> createVHFactoryList(Context context) {
            List<BaseViewHolderFactory<HomeSubModuleInfo>> list = new ArrayList<>();
            list.add(new HomePageSubVHFactory.HomePageTopicSubVHFactory(context));
            return list;
        }
    }

    static class HomePageBannerVH extends BaseRecyclerViewHolder<HomeModuleInfo> {

        @BindView(R.id.home_top_banner)
        Banner mBanner;

        BannerViewPagerHelper mBannerHelper;

        public HomePageBannerVH(View itemView) {
            super(itemView);

            mBannerHelper = new BannerViewPagerHelper(mBanner);
        }

        @Override
        public void onBindData(HomeModuleInfo data, int position) {
            super.onBindData(data, position);
            Log.d(TAG, "onBindData: banner view holder");
            if (data == null) {
                return;
            }
            if (data.mBannerInfoList != null) {
                mBannerHelper.updateImageList(data.mBannerInfoList);
            }
        }
    }
}
