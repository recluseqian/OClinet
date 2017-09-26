package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.BannerInfo;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.ui.GlideImageLoader;
import com.youth.banner.Banner;

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

    static class BannerVH extends BaseRecyclerViewHolder<SubscribeModuleInfo> {

        @BindView(R.id.subscribe_top_banner)
        Banner mBanner;
        List<BannerInfo> mBannerInfoList;

        BannerVH(View itemView) {
            super(itemView);
            mBanner.setImageLoader(new GlideImageLoader());
            mBannerInfoList = new ArrayList<>();
            mBanner.setImages(mBannerInfoList);
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
    }
}
