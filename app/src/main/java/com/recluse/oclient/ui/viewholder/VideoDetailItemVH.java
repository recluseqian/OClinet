package com.recluse.oclient.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.data.VideoDetailInfo;
import com.recluse.oclient.data.VideoDetailItemInfo;
import com.recluse.oclient.data.VideoDetailSubItemInfo;
import com.recluse.oclient.ui.viewholderfactory.VideoDetailSubItemFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class VideoDetailItemVH<T> extends BaseRecyclerViewHolder<VideoDetailItemInfo<T>> {

    private static final String TAG = "VideoDetailItemVH";

    T mVideoItemData;

    VideoDetailItemVH(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(VideoDetailItemInfo<T> data, int position) {
        super.onBindData(data, position);
        if (data == null || data.data == null) {
            return;
        }

        mVideoItemData = data.data;
        onBindVideoItemData(data.data, position);
    }

    abstract void onBindVideoItemData(T data, int position);

    public static class VideoSubscribeInfoVH extends VideoDetailItemVH<SubscribeModuleInfo> {

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

        public VideoSubscribeInfoVH(View itemView) {
            super(itemView);
        }

        @Override
        void onBindVideoItemData(SubscribeModuleInfo data, int position) {
            Context context = mItemView.getContext();
            Glide.with(context)
                    .load(data.subscribeLogo)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners((int) DisplayUtils.dp2px(context, 5))))
                    .into(mImage);

            mNameView.setText(data.subscribeName);
            mContentView.setText(data.description);
            mFansCountView.setText(data.subscribeCount + "人订阅");
            mModelCountView.setText(data.subscribeArticleCount + "条内容");
        }
    }

    public static class VideoDetailInfoVH extends VideoDetailItemVH<VideoDetailInfo> {

        @BindView(R.id.video_desc_name)
        TextView mNameView;
        @BindView(R.id.video_desc_type)
        TextView mTypeView;
        @BindView(R.id.video_desc_content)
        TextView mContentView;

        public VideoDetailInfoVH(View itemView) {
            super(itemView);
        }

        @Override
        void onBindVideoItemData(VideoDetailInfo data, int position) {
            mNameView.setText(data.title);
            mTypeView.setText(getVideoType());
            mContentView.setText(data.description);
        }

        private String getVideoType() {
            if (mData == null) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(mVideoItemData.tags)) {
                String[] tags = mVideoItemData.tags.split(",");
                if (tags.length > 0) {
                    builder.append(tags[0]);
                }
            }

            if (!TextUtils.isEmpty(mVideoItemData.school)) {
                builder.append("/").append(mVideoItemData.school);
            }

            if (!TextUtils.isEmpty(mVideoItemData.director)) {
                builder.append("/").append(mVideoItemData.director);
            }

            return builder.toString();
        }
    }

    public static class VideoVerticalRecomListVH extends VideoDetailItemVH<List<VideoDetailSubItemInfo<?>>> {

        @BindView(R.id.nested_list_title)
        TextView mTitleView;
        @BindView(R.id.nested_recycler_view)
        RecyclerView mRecyclerView;

        List<VideoDetailSubItemInfo<?>> mDataList;
        LinearLayoutManager mLayoutManager;
        BaseRecyclerItemAdapter<VideoDetailSubItemInfo<?>> mItemAdapter;

        public VideoVerticalRecomListVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            if (context == null) {
                Log.e(TAG, "VideoRecomListVH: failed to get context");
                return;
            }
            mDataList = new ArrayList<>();
            mLayoutManager = createLayoutManager(context);
            mItemAdapter = new BaseRecyclerItemAdapter<>(context, mDataList, createSubItemVHFactory(context));

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mItemAdapter);
        }

        List<BaseViewHolderFactory<VideoDetailSubItemInfo<?>>> createSubItemVHFactory(@NonNull Context context) {
            List<BaseViewHolderFactory<VideoDetailSubItemInfo<?>>> list = new ArrayList<>();
            list.add(new VideoDetailSubItemFactory(context));
            return list;
        }

        protected LinearLayoutManager createLayoutManager(@NonNull Context context) {
            return new LinearLayoutManager(context);
        }


        @Override
        void onBindVideoItemData(List<VideoDetailSubItemInfo<?>> data, int position) {
            if (mDataList == null) {
                Log.e(TAG, "onBindData: data list is null");
                return;
            }
            mTitleView.setText(mData.title);
            mDataList.clear();
            mDataList.addAll(data);
            if (mItemAdapter != null) {
                mItemAdapter.notifyDataSetChanged();
            }
        }
    }

    public static class VideoHorizontalRecomListVH extends VideoVerticalRecomListVH {

        public VideoHorizontalRecomListVH(View itemView) {
            super(itemView);
        }

        @Override
        protected LinearLayoutManager createLayoutManager(@NonNull Context context) {
            return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        public void smoothScrollToPosition(int position) {
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    public static class PlaceHolderVH extends BaseRecyclerViewHolder<VideoDetailInfo> {

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
