package com.recluse.oclient.ui.listview;

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
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.StartActivityUtils;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.ui.BannerViewPagerHelper;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

public class SubscribePageVHFactory extends BaseViewHolderFactory<SubscribeModuleInfo> {

    private static final String TAG = "SubscribePageVHFactory";

    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_BANNER = 1;

    private static final int TYPE_COUNT = 2;

    public SubscribePageVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(SubscribeModuleInfo data, int position) {
        int type = VIEW_TYPE_DEFAULT;
        if (data != null) {
            if (data.mBannerInfoList != null) {
                type = VIEW_TYPE_BANNER;
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

        View view;
        switch (viewType - getBaseType()) {
            case VIEW_TYPE_DEFAULT:
                view = inflater.inflate(R.layout.subscirbe_item_layout, parent, false);
                return new SubscribeInfoVH(view);
            case VIEW_TYPE_BANNER:
                view = inflater.inflate(R.layout.subscribe_page_header_layout, parent, false);
                return new SubscribeBannerVH(view);

            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    static class SubscribeInfoVH extends BaseRecyclerViewHolder<SubscribeModuleInfo> {

        @BindView(R.id.subscribe_media_info_image)
        ImageView mMediaInfoImageView;
        @BindView(R.id.subscribe_media_info_name)
        TextView mMediaInfoNameView;
        @BindView(R.id.subscribe_media_info_release_date)
        TextView mReleaseDateView;
        @BindView(R.id.subscribe_content_info_layout)
        View mContentView;
        @BindView(R.id.subscribe_content_info_image)
        ImageView mContentInfoImageView;
        @BindView(R.id.subscribe_content_info_title)
        TextView mContentTitleView;
        @BindView(R.id.subscribe_content_info_sub_title)
        TextView mContentSubTitleView;
        @BindView(R.id.subscribe_item_header_view_stub)
        ViewStub mHeaderViewStub;
        View mHeaderView;


        SubscribeInfoVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            float width = DisplayUtils.getWindowWidth(context) - DisplayUtils.dp2px(context, 30);
            ViewGroup.LayoutParams params = mContentView.getLayoutParams();
            params.width = (int) width;
            params.height = (int) (width * DisplayUtils.GOLDEN_RATIO);
            mContentView.setLayoutParams(params);
        }

        @Override
        public void onBindData(SubscribeModuleInfo data, int position) {
            super.onBindData(data, position);

            if (position == 1) {
                if (mHeaderView == null) {
                    mHeaderView = mHeaderViewStub.inflate();
                } else {
                    ViewsUtils.setViewVisibility(mHeaderView, View.VISIBLE);
                }
            } else if (mHeaderView != null) {
                ViewsUtils.setViewVisibility(mHeaderView, View.GONE);
            }

            if (data == null) {
                return;
            }
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.subscribeLogo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mMediaInfoImageView);

            mMediaInfoNameView.setText(data.subscribeName);
            mReleaseDateView.setText(DateUtils.getPublishTime(data.publishTime, "yyyy/MM/dd"));
            Glide.with(context)
                    .load(data.image)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mContentInfoImageView);
            mContentTitleView.setText(data.contentTitle);
            mContentSubTitleView.setText(data.contentDesc);
        }

        @OnClick(R.id.subscribe_item_layout)
        public void onItemClick(View view) {
            if (mData == null) {
                return;
            }

            StartActivityUtils.startVideoActivity(view.getContext(), "", mData.plid, mData.subscribeContentId);
        }
    }

    static class SubscribeBannerVH extends BaseRecyclerViewHolder<SubscribeModuleInfo> {

        @BindView(R.id.subscribe_top_banner)
        Banner mBanner;

        BannerViewPagerHelper mBannerHelper;

        SubscribeBannerVH(View itemView) {
            super(itemView);

            mBannerHelper = new BannerViewPagerHelper(mBanner);
        }

        @Override
        public void onBindData(SubscribeModuleInfo data, int position) {
            super.onBindData(data, position);

            if (data == null) {
                return;
            }
            if (data.mBannerInfoList != null) {
                mBannerHelper.updateImageList(data.mBannerInfoList);
            }
        }

    }
}

