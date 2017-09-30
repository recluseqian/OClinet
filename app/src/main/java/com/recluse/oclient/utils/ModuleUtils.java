package com.recluse.oclient.utils;

import android.text.TextUtils;

import com.recluse.oclient.data.VideoDetailInfo;
import com.recluse.oclient.ui.fragment.VideoDetailFragment;

public class ModuleUtils {

    public static String getModuleType(int type) {
        switch (type) {
            case 2:
                return "视频";
            case 4:
                return "文章";
            case 6:
                return "音频";

            default:
                return "";
        }
    }

    public static String getVideoUrl(VideoDetailInfo.VideoListBean video) {
        if (video == null) {
            return VideoDetailFragment.URL;
        }

        if (!TextUtils.isEmpty(video.m3u8HdUrl)) {
            return video.m3u8HdUrl;
        }
        if (!TextUtils.isEmpty(video.m3u8SdUrl)) {
            return video.m3u8SdUrl;
        }
        if (!TextUtils.isEmpty(video.m3u8HdUrlOrign)) {
            return video.m3u8HdUrlOrign;
        }
        if (!TextUtils.isEmpty(video.m3u8SdUrlOrign)) {
            return video.m3u8SdUrlOrign;
        }

        if (!TextUtils.isEmpty(video.mp4HdUrl)) {
            return video.mp4HdUrl;
        }
        if (!TextUtils.isEmpty(video.mp4SdUrl)) {
            return video.mp4SdUrl;
        }
        if (!TextUtils.isEmpty(video.mp4HdUrlOrign)) {
            return video.mp4HdUrlOrign;
        }
        if (!TextUtils.isEmpty(video.mp4SdUrlOrign)) {
            return video.mp4SdUrlOrign;
        }

        return VideoDetailFragment.URL;
    }


    private static final int _k = 1000;
    private static final int _10k = 10000;

    public static String getCountString(long count, String prefix, String suffix) {

        StringBuilder builder = new StringBuilder(prefix);
        if (count < 10 * _k) {
            builder.append(count);
        } else if (count < 10 * _10k) {
            builder.append(String.format("%.1f", ((float) count / _k))).append("k");
        } else {
            builder.append(String.format("%.1f", ((float) count / _10k))).append("万");
        }
        return builder.append(suffix).toString();
    }
}
