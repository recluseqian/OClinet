package com.recluse.oclient.ui;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.oclient.data.BannerInfo;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader{

    private static final String TAG = "GlideImageLoader";

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof BannerInfo) {
            BannerInfo info = (BannerInfo) path;

            Glide.with(context)
                    .load(info.image)
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView);
        }
    }
}
