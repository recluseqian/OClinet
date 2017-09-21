package com.recluse.oclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.oclient.R;
import com.recluse.oclient.ui.fragment.VideoDetailFragment;

public class DetailActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager manager = super.getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, VideoDetailFragment.newInstance(super.getIntent().getExtras()))
                .commitAllowingStateLoss();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_detail;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
