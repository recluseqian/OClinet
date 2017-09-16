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
import com.recluse.base.utils.DateUtils;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.SubscribeInfo;

import butterknife.BindView;

/**
 * Created by recluse on 17-9-15.
 */

public class SubscribePageVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<SubscribeInfo> {

    private static final String TAG = "SubscribePageVHFactory";

    public SubscribePageVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.subscirbe_item_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<SubscribeInfo> createDefaultViewHolder(@NonNull View itemView) {
        return new SubscribeInfoVH(itemView);
    }

    static class SubscribeInfoVH extends BaseRecyclerViewHolder<SubscribeInfo> {

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

        public SubscribeInfoVH(View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            float width = DisplayUtils.getWindowWidth(context) - DisplayUtils.dp2px(context, 30);
            ViewGroup.LayoutParams params = mContentView.getLayoutParams();
            params.width = (int) width;
            params.height = (int) (width * DisplayUtils.GOLDEN_RATIO);
            mContentView.setLayoutParams(params);
        }

        @Override
        public void onBindData(SubscribeInfo data, int position) {
            super.onBindData(data, position);
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
    }
}
