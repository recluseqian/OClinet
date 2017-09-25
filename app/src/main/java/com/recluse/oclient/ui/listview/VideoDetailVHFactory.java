package com.recluse.oclient.ui.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.model.event.ClickEvent;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.utils.TimerUtils;
import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.StartActivityUtils;
import com.recluse.oclient.data.VideoDetailEntity;
import com.recluse.oclient.data.VideoInfo;
import com.recluse.oclient.data.VideoSubscribeEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by recluse on 17-9-22.
 */

public class VideoDetailVHFactory extends BaseViewHolderFactory<VideoInfo> {

    private static final String TAG = "VideoDetailVHFactory";

    public static final int VIEW_TYPE_SUBSCRIBE_INTRO = 0;
    public static final int VIEW_TYPE_VIDEO_DESC = 1;
    public static final int VIEW_TYPE_VIDEO_SERIES = 2;
    public static final int VIEW_TYPE_SUBSCRIBE_RECOM = 3;
    public static final int VIEW_TYPE_VIDEO_RECOM = 4;
    public static final int VIEW_TYPE_VIDEO_PLACE_HOLDER = 5;

    private static final int TYPE_COUNT = 6;

    public VideoDetailVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(VideoInfo data, int position) {
        if (data != null) {
            return getBaseType() + data.type;
        }

        return BaseRecyclerItemAdapter.UNKNOWN_TYPE;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (inflater == null) {
            Log.e(TAG, "createViewHolder: inflate == null");
            return null;
        }

        View view;
        switch (viewType - getBaseType()) {
            case VIEW_TYPE_SUBSCRIBE_INTRO:
                view = inflater.inflate(R.layout.video_detail_subscribe_header_layout, parent, false);
                return new SubscribeDescVH(view);
            case VIEW_TYPE_VIDEO_DESC:
                view = inflater.inflate(R.layout.video_detail_description_layout, parent, false);
                return new VideoDescVH(view);
            case VIEW_TYPE_VIDEO_SERIES:
                view = inflater.inflate(R.layout.nested_list_layout, parent, false);
                return new VideoListVH(view);
            case VIEW_TYPE_SUBSCRIBE_RECOM:
                view = inflater.inflate(R.layout.nested_list_layout, parent, false);
                return new SubscribeRecomVH(view);
            case VIEW_TYPE_VIDEO_PLACE_HOLDER:
                view = inflater.inflate(R.layout.place_holder_layout, parent, false);
                return new PlaceHolderVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    static class SubscribeDescVH extends BaseRecyclerViewHolder<VideoInfo> {

        @BindView(R.id.video_subscribe_image)
        ImageView mImage;
        @BindView(R.id.video_subscribe_name)
        TextView mNameView;
        @BindView(R.id.video_subscribe_content)
        TextView mContentView;
        @BindView(R.id.video_subscribe_fans_count)
        TextView mFansCountView;
        @BindView(R.id.video_subscribe_model_content)
        TextView mModelCountView;

        VideoSubscribeEntity.DataBean mSubscribeInfo;

        public SubscribeDescVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(VideoInfo data, int position) {
            super.onBindData(data, position);
            if (data == null || data.mSubscribeInfo == null) {
                return;
            }

            mSubscribeInfo = data.mSubscribeInfo;
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(mSubscribeInfo.subscribeLogo)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mImage);

            mNameView.setText(mSubscribeInfo.subscribeName);
            mContentView.setText(mSubscribeInfo.description);
            mFansCountView.setText(mSubscribeInfo.subscribeCount + "人订阅");
            mModelCountView.setText(mSubscribeInfo.subscribeArticleCount + "条内容");
        }
    }

    static class VideoDescVH extends BaseRecyclerViewHolder<VideoInfo> {

        @BindView(R.id.video_desc_name)
        TextView mNameView;
        @BindView(R.id.video_desc_type)
        TextView mTypeView;
        @BindView(R.id.video_desc_content)
        TextView mContentView;

        VideoDetailEntity.DataBean mVideoDetailInfo;

        public VideoDescVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(VideoInfo data, int position) {
            super.onBindData(data, position);
            if (data == null || data.mVideoDescInfo == null) {
                return;
            }

            mVideoDetailInfo = data.mVideoDescInfo;
            mNameView.setText(mVideoDetailInfo.title);
            mTypeView.setText(getVideoType());
            mContentView.setText(mVideoDetailInfo.description);
        }

        private String getVideoType() {
            if (mVideoDetailInfo == null) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(mVideoDetailInfo.tags)) {
                String[] tags = mVideoDetailInfo.tags.split(",");
                if (tags != null && tags.length > 0) {
                    builder.append(tags[0]);
                }
            }

            if (!TextUtils.isEmpty(mVideoDetailInfo.school)) {
                builder.append("/").append(mVideoDetailInfo.school);
            }

            if (!TextUtils.isEmpty(mVideoDetailInfo.director)) {
                builder.append("/").append(mVideoDetailInfo.director);
            }

            return builder.toString();
        }
    }

    static class VideoListVH extends BaseNestedListViewHolder<VideoInfo, VideoDetailEntity.VideoListBean> {

        public VideoListVH(View itemView) {
            super(itemView);
        }

        @Override
        protected LinearLayoutManager createLayoutManager(@NonNull Context context) {
            return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        @Override
        protected List<BaseViewHolderFactory<VideoDetailEntity.VideoListBean>> createSubItemVHFactory(@NonNull Context context) {
            List<BaseViewHolderFactory<VideoDetailEntity.VideoListBean>> list = new ArrayList<>();
            list.add(new VideoListSubVHFactory(context));
            return list;
        }

        @Override
        public void onBindData(VideoInfo data, int position) {
            super.onBindData(data, position);

            if (data == null || mDataList == null) {
                return;
            }

            mDataList.clear();
            mDataList.addAll(data.mVideoList);
            mTitleView.setText(data.title);
            if (mItemAdapter != null) {
                mItemAdapter.notifyDataSetChanged();
            }
        }
    }

    static class VideoListSubVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<VideoDetailEntity.VideoListBean> {

        public VideoListSubVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.video_horizontal_list_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<VideoDetailEntity.VideoListBean> createDefaultViewHolder(@NonNull View itemView) {
            return new VideoListSubItemVH(itemView);
        }
    }

    static class VideoListSubItemVH extends BaseRecyclerViewHolder<VideoDetailEntity.VideoListBean> {

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
        public void onBindData(VideoDetailEntity.VideoListBean data, int position) {
            super.onBindData(data, position);

            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.imgPath)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mVideoImageView);

            mTitleView.setText(data.title);
            mDurationView.setText(TimerUtils.getDurationString(data.mLength * 1000));
        }

        @OnClick(R.id.video_h_list_item_layout)
        public void onItemClick(View view) {
            if (mData == null) {
                return;
            }

            Context context = view.getContext();
            if (context instanceof BaseAppCompatActivity) {
                int uniqueId = ((BaseAppCompatActivity) context).getUniqueId();
                EventBus.getDefault().post(new ClickEvent<>(uniqueId, mData, view, 0));
            }
        }
    }

    static class SubscribeRecomVH extends BaseNestedListViewHolder<VideoInfo, VideoSubscribeEntity.SubscribeListBean> {

        public SubscribeRecomVH(View itemView) {
            super(itemView);
        }

        @Override
        protected List<BaseViewHolderFactory<VideoSubscribeEntity.SubscribeListBean>> createSubItemVHFactory(@NonNull Context context) {
            List<BaseViewHolderFactory<VideoSubscribeEntity.SubscribeListBean>> list = new ArrayList<>();
            list.add(new SubscribeRecomItemVHFactory(context));
            return list;
        }

        @Override
        protected LinearLayoutManager createLayoutManager(@NonNull Context context) {
            return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        @Override
        public void onBindData(VideoInfo data, int position) {
            super.onBindData(data, position);
            Log.e("recluseguo", "onBindData: sub_recom");
            if (data == null) {
                return;
            }
            if (mDataList == null) {
                Log.e(TAG, "onBindData: data list is null");
                return;
            }
            mTitleView.setText(data.title);

            mDataList.clear();
            mDataList.addAll(data.mSubscribeRecomList);
            if (mItemAdapter != null) {
                mItemAdapter.notifyDataSetChanged();
            }
        }
    }

    static class SubscribeRecomItemVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<VideoSubscribeEntity.SubscribeListBean> {

        public SubscribeRecomItemVHFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getDefaultLayoutRes() {
            return R.layout.video_horizontal_subscribe_recom_item_layout;
        }

        @Override
        protected BaseRecyclerViewHolder<VideoSubscribeEntity.SubscribeListBean> createDefaultViewHolder(@NonNull View itemView) {
            return new SubscribeRecomItemVH(itemView);
        }
    }

    static class SubscribeRecomItemVH extends BaseRecyclerViewHolder<VideoSubscribeEntity.SubscribeListBean> {

        @BindView(R.id.video_subscribe_recom_item_image)
        ImageView mImageView;
        @BindView(R.id.video_subscribe_recom_item_title)
        TextView mTitleView;

        public SubscribeRecomItemVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(VideoSubscribeEntity.SubscribeListBean data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }

            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.image)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mImageView);

            mTitleView.setText(data.contentTitle);
        }

        @OnClick(R.id.video_subscribe_recom_item_layout)
        public void onItemClick(View view) {
            if (mData == null) {
                return;
            }

            StartActivityUtils.startVideoActivity(view.getContext(), "", mData.plid, mData.subscribeContentId);
        }

    }

    static class PlaceHolderVH extends BaseRecyclerViewHolder<VideoInfo> {

        public PlaceHolderVH(View itemView) {
            super(itemView);

            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            float width = DisplayUtils.getWindowWidth(itemView.getContext());
            params.width = (int) width;
            params.height = (int) (width * 9 / 16 + DisplayUtils.dp2px(itemView.getContext(), 42));
            itemView.setLayoutParams(params);
        }
    }
}
