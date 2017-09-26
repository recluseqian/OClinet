package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.HomeModuleSubInfo;
import com.recluse.oclient.ui.viewholder.HomePageSubItemVH;

public abstract class HomePageSubItemVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeModuleSubInfo> {

    private static final String TAG = "HomePageSubVHFactory";

    private HomePageSubItemVHFactory(@NonNull Context context) {
        super(context);
    }

    public static class ModuleSubItemVHFactory extends HomePageSubItemVHFactory {

        public ModuleSubItemVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_sub_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeModuleSubInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageSubItemVH.ModuleSubItemVH(itemView);
        }
    }

    public static class PlanSubItemVHFactory extends HomePageSubItemVHFactory {

        public PlanSubItemVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_plan_sub_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeModuleSubInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageSubItemVH.PlanSubItemVH(itemView);
        }
    }

    public static class SubscribeSubItemVHFactory extends HomePageSubItemVHFactory {

        public SubscribeSubItemVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_sub_subscribe_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeModuleSubInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageSubItemVH.SubscribeSubItemVH(itemView);
        }

    }

    public static class TopicSubItemVHFactory extends HomePageSubItemVHFactory {

        public TopicSubItemVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_sub_topic_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeModuleSubInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageSubItemVH.TopicSubItemVH(itemView);
        }
    }

}
