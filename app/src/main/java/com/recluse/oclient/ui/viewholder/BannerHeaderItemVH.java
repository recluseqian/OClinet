package com.recluse.oclient.ui.viewholder;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.oclient.R;
import com.recluse.oclient.data.BannerInfo;
import com.recluse.oclient.data.BannerListProvider;
import com.recluse.oclient.data.ModuleInfo;
import com.recluse.oclient.ui.GlideImageLoader;
import com.recluse.oclient.utils.StartActivityUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BannerHeaderItemVH extends BaseModuleViewHolder<BannerListProvider> implements OnBannerListener {

    private static final String TAG = "BannerHeaderItemVH";

    @BindView(R.id.home_top_banner)
    Banner mBanner;

    private List<BannerInfo> mBannerInfoList;

    public BannerHeaderItemVH(View itemView) {
        super(itemView);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setOnBannerListener(this);
    }

    @Override
    public void onBindData(BannerListProvider data, int position) {
        super.onBindData(data, position);

        if (mBannerInfoList == data.getBannerList()) {
            return;
        }

        mBannerInfoList = data.getBannerList();
        mBanner.update(data.getBannerList());
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
