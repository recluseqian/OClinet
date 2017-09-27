package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.BannerInfo;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.ui.GlideImageLoader;
import com.recluse.oclient.utils.StartActivityUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SubscribePageBannerVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<SubscribeModuleInfo> {

    private static final String TAG = "HomePageBannerVHFactory";

    public SubscribePageBannerVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.subscribe_page_header_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<SubscribeModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
        return new BannerVH(itemView);
    }

    @Override
    protected boolean isDefaultType(SubscribeModuleInfo data, int position) {
        return data != null && data.mBannerInfoList != null;
    }

    static class BannerVH extends BaseRecyclerViewHolder<SubscribeModuleInfo> implements OnBannerListener{

        @BindView(R.id.subscribe_top_banner)
        Banner mBanner;
        List<BannerInfo> mBannerInfoList;

        BannerVH(View itemView) {
            super(itemView);
            mBanner.setImageLoader(new GlideImageLoader());
            mBannerInfoList = new ArrayList<>();
            mBanner.setImages(mBannerInfoList);
            mBanner.setOnBannerListener(this);
        }

        @Override
        public void onBindData(SubscribeModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }
            if (data.mBannerInfoList != null) {
                mBanner.update(data.mBannerInfoList);
                mBannerInfoList = data.mBannerInfoList;
            }
        }

        @Override
        protected void onViewAttachedToWindow() {
            super.onViewAttachedToWindow();
            if (mBanner != null) {
                mBanner.start();
            }
        }

        @Override
        protected void onViewDetachedFromWindow() {
            super.onViewDetachedFromWindow();
            if (mBanner != null) {
                mBanner.stopAutoPlay();
            }
        }

        @Override
        public void OnBannerClick(int position) {
            if (mBannerInfoList != null && position >= 0 && position < mBannerInfoList.size()) {
                BannerInfo bannerInfo = mBannerInfoList.get(position);
                if (!TextUtils.isEmpty(bannerInfo.plid) && !TextUtils.isEmpty(bannerInfo.typeid)) {
                    StartActivityUtils.startVideoActivity(
                            mItemView.getContext(),
                            "",
                            bannerInfo.plid,
                            bannerInfo.typeid);
                }
            }
        }
    }
}
