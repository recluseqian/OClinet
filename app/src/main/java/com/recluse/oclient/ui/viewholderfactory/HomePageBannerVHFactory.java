package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.BannerInfo;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.ui.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomePageBannerVHFactory extends BaseViewHolderFactory.SimpleViewHolderFactory<HomeModuleInfo> {

    private static final String TAG = "HomePageBannerVHFactory";

    public HomePageBannerVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getDefaultLayoutRes() {
        return R.layout.home_page_header_layout;
    }

    @Override
    protected BaseRecyclerViewHolder<HomeModuleInfo> createDefaultViewHolder(@NonNull View itemView) {
        return new BannerVH(itemView);
    }

    @Override
    protected boolean isDefaultType(HomeModuleInfo data, int position) {
        return data != null && data.mBannerInfoList != null;
    }

    static class BannerVH extends BaseRecyclerViewHolder<HomeModuleInfo> {

        @BindView(R.id.home_top_banner)
        Banner mBanner;
        List<BannerInfo> mBannerInfoList;

        BannerVH(View itemView) {
            super(itemView);
            mBanner.setImageLoader(new GlideImageLoader());
            mBannerInfoList = new ArrayList<>();
            mBanner.setImages(mBannerInfoList);
        }

        @Override
        public void onBindData(HomeModuleInfo data, int position) {
            super.onBindData(data, position);
            if (data == null) {
                return;
            }
            if (mBannerInfoList == null) {
                Log.e(TAG, "onBindData: mBannerInfo do not init");
            }
            if (data.mBannerInfoList != null) {
                mBannerInfoList.clear();
                mBannerInfoList.addAll(data.mBannerInfoList);
                mBanner.update(data.mBannerInfoList);
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
