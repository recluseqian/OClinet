package com.recluse.base.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.recluse.base.model.event.NullEvent;
import com.recluse.base.utils.SystemUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseAppCompatActivity extends AppCompatActivity{

    protected Unbinder mUnbinder;
    protected int mUniqueId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutRes());
        mUniqueId = SystemUtils.getSystemUniqueId();
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNullEvent(NullEvent event) {
        //do nothing
    }
}
