package com.recluse.oclient.ui.viewholder;

import android.content.Context;
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
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.R;
import com.recluse.oclient.utils.ModuleUtils;
import com.recluse.oclient.utils.StartActivityUtils;
import com.recluse.oclient.data.SubscribeModuleInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class SubscribePageItemVH extends BaseModuleViewHolder<SubscribeModuleInfo> {

    private static final String TAG = "SubscribeModuleVH";

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
    @BindView(R.id.subscribe_item_meta_info)
    TextView mSubscribeContentMetaInfo;
    @BindView(R.id.subscribe_content_type)
    TextView mContentTypeView;

    public SubscribePageItemVH(View itemView) {
        super(itemView);

        Context context = itemView.getContext();
        float width = DisplayUtils.getWindowWidth(context) - DisplayUtils.dp2px(context, 30);
        ViewGroup.LayoutParams params = mContentView.getLayoutParams();
        params.width = (int) width;
        params.height = (int) (width * 0.5f);
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
        mSubscribeContentMetaInfo.setText(data.quantity + " | " + ModuleUtils.getCountString(data.viewCount, "", "人观看"));
        mContentTypeView.setText("#" + ModuleUtils.getModuleType(data.subscribeContentType));
    }

    @OnClick(R.id.subscribe_item_layout)
    public void onItemClick(View view) {
        if (mData == null) {
            return;
        }

        StartActivityUtils.startVideoActivity(view.getContext(), "", mData.plid, mData.subscribeContentId);
    }

}
