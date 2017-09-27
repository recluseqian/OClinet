package com.recluse.oclient.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.model.event.ClickEvent;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.utils.TimerUtils;
import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.R;
import com.recluse.oclient.utils.StartActivityUtils;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.data.VideoDetailInfo;
import com.recluse.oclient.data.VideoDetailSubItemInfo;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class VideoDetailSubItemVH<T> extends BaseRecyclerViewHolder<VideoDetailSubItemInfo<T>>
        implements View.OnClickListener {

    private static final String TAG = "VideoDetailSubItemVH";

    T mSubItemData;

    VideoDetailSubItemVH(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBindData(VideoDetailSubItemInfo<T> data, int position) {
        super.onBindData(data, position);
        if (data == null || data.data == null) {
            return;
        }
        mSubItemData = data.data;
        onBindSubItemData(data.data, position);
    }

    protected abstract void onBindSubItemData(@NonNull T subItemData, int position);

    public static class VideoListSubItemVH extends VideoDetailSubItemVH<VideoDetailInfo.VideoListBean> {

        @BindView(R.id.video_h_list_item_image)
        ImageView mVideoImageView;
        @BindView(R.id.video_h_list_item_title)
        TextView mTitleView;
        @BindView(R.id.video_h_list_item_duration)
        TextView mDurationView;

        public VideoListSubItemVH(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindSubItemData(@NonNull VideoDetailInfo.VideoListBean subItemData, int position) {
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(subItemData.imgPath)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mVideoImageView);

            mTitleView.setText(subItemData.title);
            mDurationView.setText(TimerUtils.getDurationString(subItemData.mLength * 1000));
        }

        @Override
        public void onClick(View view) {
            if (mData == null) {
                return;
            }

            Context context = view.getContext();
            if (context instanceof BaseAppCompatActivity) {
                int uniqueId = ((BaseAppCompatActivity) context).getUniqueId();
                EventBus.getDefault().post(new ClickEvent<>(uniqueId, mSubItemData, view, mPosition));
            }
        }
    }

    public static class SubscribeListSubItemVH extends VideoDetailSubItemVH<SubscribeModuleInfo> {

        @BindView(R.id.video_subscribe_recom_item_image)
        ImageView mImageView;
        @BindView(R.id.video_subscribe_recom_item_title)
        TextView mTitleView;

        public SubscribeListSubItemVH(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindSubItemData(@NonNull SubscribeModuleInfo subItemData, int position) {
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(subItemData.image)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mImageView);

            mTitleView.setText(subItemData.contentTitle);
        }

        @Override
        public void onClick(View view) {
            if (mData == null) {
                return;
            }

            StartActivityUtils.startVideoActivity(
                    view.getContext(),
                    "",
                    mSubItemData.plid,
                    mSubItemData.subscribeContentId);
        }
    }

    public static class VideoRecomSubItemVH extends VideoDetailSubItemVH<VideoDetailInfo.RecommendListBean> {

        @BindView(R.id.video_recom_content_layout)
        View mContentLayout;
        @BindView(R.id.video_recom_content_image)
        ImageView mImageView;
        @BindView(R.id.video_recom_content_title)
        TextView mTitleView;
        @BindView(R.id.video_recom_content_sub_title)
        TextView mSubTitleView;

        public VideoRecomSubItemVH(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindSubItemData(@NonNull VideoDetailInfo.RecommendListBean subItemData, int position) {
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(subItemData.picUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mImageView);

            mTitleView.setText(subItemData.title);
            mSubTitleView.setText(subItemData.description);
        }

        @Override
        public void onClick(View view) {
            if (mSubItemData == null) {
                return;
            }

            StartActivityUtils.startVideoActivity(
                    view.getContext(),
                    "",
                    mSubItemData.plid,
                    mSubItemData.rid);
        }
    }
}
