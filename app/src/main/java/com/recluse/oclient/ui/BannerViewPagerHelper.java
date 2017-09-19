package com.recluse.oclient.ui;

import com.recluse.oclient.data.BannerInfo;
import com.youth.banner.Banner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by recluse on 17-9-18.
 */

public class BannerViewPagerHelper {

    private WeakReference<Banner> mBannerWeakReference;

    public BannerViewPagerHelper(Banner banner) {
        if (banner != null) {
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(new ArrayList<BannerInfo>());
        }
        mBannerWeakReference = new WeakReference<Banner>(banner);
    }

    public void updateImageList(List<?> list) {
        if (nonNullCheck()) {
            mBannerWeakReference.get().update(list);
        }
    }

    public void onStart() {
        if (nonNullCheck()) {
            mBannerWeakReference.get().start();
        }
    }

    public void onStop() {
        if (nonNullCheck()) {
            mBannerWeakReference.get().stopAutoPlay();
        }
    }

    private boolean nonNullCheck() {
        return mBannerWeakReference != null && mBannerWeakReference.get() != null;
    }
}
