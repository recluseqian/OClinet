package com.recluse.oclient;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.recluse.oclient.ui.activity.DetailActivity;

public class StartActivityUtils {


    public static void startVideoActivity(Context context, String url, String plid, String mid) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(url)) {
            bundle.putString(DetailActivity.Const.INTENT_VIDEO_URL, url);
        }
        bundle.putString(DetailActivity.Const.INTENT_VIDEO_PLID, plid);
        bundle.putString(DetailActivity.Const.INTENT_VIDEO_MID, mid);
        DetailActivity.startDetailActivity(context, DetailActivity.Const.VIDEO_DETAIL_FRAGMENT, bundle);
    }

}
