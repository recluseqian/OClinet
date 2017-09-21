package com.recluse.oclient.ui.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.HomeSubModuleInfo;
import com.recluse.oclient.ui.activity.DetailActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageSubVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeSubModuleInfo> {

    private static final String TAG = "HomePageSubVHFactory";

    private static final int IMAGE_RADIUS_SIZE = 5; //dp

    HomePageSubVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.module_sub_item_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<HomeSubModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
        return new HomePageDefaultSubItemVH(itemView);
    }

    static class HomePageDefaultSubItemVH extends BaseRecyclerViewHolder<HomeSubModuleInfo> {

        @BindView(R.id.module_sub_item_image)
        ImageView mSubItemImageView;
        @BindView(R.id.module_sub_item_title)
        TextView mTitleView;
        @BindView(R.id.module_sub_item_meta_info)
        TextView mMetaInfoView;

        HomePageDefaultSubItemVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            ViewGroup.LayoutParams params = mSubItemImageView.getLayoutParams();
            int width = DisplayUtils.getWindowWidth(context) - (int) DisplayUtils.dp2px(context, 50);
            params.width = (int) (width / 2.0f);
            params.height = (int) (width / 2.0f * 0.618f);
            mSubItemImageView.setLayoutParams(params);
        }

        @Override
        public void onBindData(HomeSubModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, IMAGE_RADIUS_SIZE))))
                    .into(mSubItemImageView);
            mTitleView.setText(data.title);
            mMetaInfoView.setText(data.viewCount + "人观看");
        }

        @OnClick(R.id.module_sub_item_layout)
        public void onModuleSubItemClick(View view) {
            DetailActivity.startActivity(view.getContext());
        }
    }

    static class HomePagePlanSubVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeSubModuleInfo> {

        HomePagePlanSubVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_plan_sub_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeSubModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePagePlanSubVH(itemView);
        }
    }

    static class HomePagePlanSubVH extends BaseRecyclerViewHolder<HomeSubModuleInfo> {

        @BindView(R.id.module_sub_plan_item_image)
        ImageView mImageView;
        @BindView(R.id.module_sub_plan_item_title)
        TextView mTitleView;
        @BindView(R.id.module_sub_plan_item_sub_title)
        TextView mSubTitleView;
        @BindView(R.id.module_sub_plan_item_meta_info)
        TextView mMetaInfoView;
        @BindView(R.id.module_sub_plan_item_duration)
        TextView mDurationView;

        HomePagePlanSubVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(HomeSubModuleInfo data, int position) {
            super.onBindData(data, position);

            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.listImageUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, IMAGE_RADIUS_SIZE))))
                    .into(mImageView);
            mTitleView.setText(data.title);
            mSubTitleView.setText(data.sloganTitle);
            mMetaInfoView.setText(data.participantCount + "与您学");
            mDurationView.setText(data.duration + "分钟");
        }
    }

    static class HomePageSubscribeSubVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeSubModuleInfo> {

        HomePageSubscribeSubVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_sub_subscribe_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeSubModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageSubscribeSubVH(itemView);
        }

    }

    static class HomePageSubscribeSubVH extends BaseRecyclerViewHolder<HomeSubModuleInfo> {

        @BindView(R.id.module_sub_subscribe_item_image)
        ImageView mImageView;
        @BindView(R.id.module_sub_subscribe_item_title)
        TextView mTitleView;

        HomePageSubscribeSubVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            float width = DisplayUtils.getWindowWidth(context) - DisplayUtils.dp2px(context, 70);
            params.width = (int) (width / 3);
            params.height = (int) (width / 3);
            mImageView.setLayoutParams(params);
        }

        @Override
        public void onBindData(HomeSubModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.centerInsideTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, IMAGE_RADIUS_SIZE))))
                    .into(mImageView);
            mTitleView.setText(data.title);
        }
    }

    static class HomePageTopicSubVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeSubModuleInfo> {

        HomePageTopicSubVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.module_sub_topic_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<HomeSubModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
            return new HomePageTopicSubVH(itemView);
        }
    }

    static class HomePageTopicSubVH extends BaseRecyclerViewHolder<HomeSubModuleInfo> {

        @BindView(R.id.module_sub_topic_item_image)
        ImageView mImageView;
        @BindView(R.id.module_sub_topic_item_title)
        TextView mTitleView;
        @BindView(R.id.module_sub_topic_item_view_count)
        TextView mViewCountView;

        HomePageTopicSubVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(HomeSubModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, IMAGE_RADIUS_SIZE))))
                    .into(mImageView);

            mTitleView.setText(data.title);
            mViewCountView.setText(data.viewCount + "人阅读");
        }
    }
}
