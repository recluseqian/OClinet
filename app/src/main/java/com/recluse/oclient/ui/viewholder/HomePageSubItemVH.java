package com.recluse.oclient.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.utils.SystemUtils;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.R;
import com.recluse.oclient.StartActivityUtils;
import com.recluse.oclient.data.HomeModuleSubInfo;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class HomePageSubItemVH extends BaseRecyclerViewHolder<HomeModuleSubInfo> {

    private static final String TAG = "HomeModuleSubItemVH";

    private HomePageSubItemVH(View itemView) {
        super(itemView);
    }

    public static class ModuleSubItemVH extends HomePageSubItemVH {

        @BindView(R.id.module_sub_item_image)
        ImageView mSubItemImageView;
        @BindView(R.id.module_sub_item_title)
        TextView mTitleView;
        @BindView(R.id.module_sub_item_meta_info)
        TextView mMetaInfoView;

        public ModuleSubItemVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            ViewGroup.LayoutParams params = mSubItemImageView.getLayoutParams();
            int width = DisplayUtils.getWindowWidth(context) - (int) DisplayUtils.dp2px(context, 50);
            params.width = (int) (width / 2.0f);
            params.height = (int) (width / 2.0f * 0.618f);
            mSubItemImageView.setLayoutParams(params);
        }

        @Override
        public void onBindData(HomeModuleSubInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.default_place_holder))
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(SystemUtils.IMAGE_ROUND_RADIUS)))
                    .into(mSubItemImageView);
            mTitleView.setText(data.title);
            mMetaInfoView.setText(data.viewCount + "人观看");
        }

        @OnClick(R.id.module_sub_item_layout)
        public void onModuleSubItemClick(View view) {
            if (mData == null) {
                return;
            }
            if (mData.contentType == 2) {
                StartActivityUtils.startVideoActivity(view.getContext(), "", mData.plid, mData.rid);
            }
        }
    }

    public static class PlanSubItemVH extends BaseRecyclerViewHolder<HomeModuleSubInfo> {

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

        public PlanSubItemVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(HomeModuleSubInfo data, int position) {
            super.onBindData(data, position);

            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.listImageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.default_place_holder))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(SystemUtils.IMAGE_ROUND_RADIUS)))
                    .into(mImageView);
            mTitleView.setText(data.title);
            mSubTitleView.setText(data.sloganTitle);
            mMetaInfoView.setText(data.participantCount + "与您学");
            mDurationView.setText(data.duration + "分钟");
        }
    }

    public static class SubscribeSubItemVH extends BaseRecyclerViewHolder<HomeModuleSubInfo> {

        @BindView(R.id.module_sub_subscribe_item_image)
        ImageView mImageView;
        @BindView(R.id.module_sub_subscribe_item_title)
        TextView mTitleView;

        public SubscribeSubItemVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            float width = DisplayUtils.getWindowWidth(context) - DisplayUtils.dp2px(context, 70);
            params.width = (int) (width / 3);
            params.height = (int) (width / 3);
            mImageView.setLayoutParams(params);
        }

        @Override
        public void onBindData(HomeModuleSubInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.default_place_holder))
                    .apply(RequestOptions.centerInsideTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(SystemUtils.IMAGE_ROUND_RADIUS)))
                    .into(mImageView);
            mTitleView.setText(data.title);
        }
    }

    public static class TopicSubItemVH extends BaseRecyclerViewHolder<HomeModuleSubInfo> {

        @BindView(R.id.module_sub_topic_item_image)
        ImageView mImageView;
        @BindView(R.id.module_sub_topic_item_title)
        TextView mTitleView;
        @BindView(R.id.module_sub_topic_item_view_count)
        TextView mViewCountView;

        public TopicSubItemVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(HomeModuleSubInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.default_place_holder))
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(SystemUtils.IMAGE_ROUND_RADIUS)))
                    .into(mImageView);

            mTitleView.setText(data.title);
            mViewCountView.setText(data.viewCount + "人阅读");
        }
    }
}
