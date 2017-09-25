package com.recluse.oclient.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.fragment.BaseFragment;
import com.recluse.oclient.R;
import com.recluse.oclient.ui.fragment.VideoDetailFragment;

public class DetailActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = super.getIntent().getExtras();
        if (bundle != null) {
            int type = bundle.getInt(Const.INTENT_FRAGMENT_TYPE);
            BaseFragment fragment = FragmentFactory.createFragment(type, bundle);
            if (fragment != null) {
                FragmentManager manager = super.getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commitAllowingStateLoss();
            }
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_detail;
    }

    public static void startDetailActivity(@NonNull Context context, int type, Bundle bundle) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Const.INTENT_FRAGMENT_TYPE, type);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }


    public static class FragmentFactory {
        public static BaseFragment createFragment(int type, Bundle bundle) {
            switch (type) {
                case Const.VIDEO_DETAIL_FRAGMENT:
                    return VideoDetailFragment.newInstance(bundle);
                default:
                    return null;
            }
        }
    }

    public static class Const {

        public static final int VIDEO_DETAIL_FRAGMENT = 1;

        public static final String INTENT_FRAGMENT_TYPE = "intent_fragment_type";

        public static final String INTENT_VIDEO_URL = "intent_video_url";
        public static final String INTENT_VIDEO_PLID = "intent_video_plid";
        public static final String INTENT_VIDEO_MID = "intent_video_mid";

    }
}
