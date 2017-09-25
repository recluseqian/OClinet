package com.recluse.base.view.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recluse.base.model.event.NullEvent;
import com.recluse.base.utils.SystemUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by recluse on 17-9-13.
 */

public abstract class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;
    protected int mUniqueId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUniqueId = SystemUtils.getSystemUniqueId();
        if (isNeedRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected boolean isNeedRegisterEventBus() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (isNeedRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public int getUniqueId() {
        return mUniqueId;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNullEvent(NullEvent event) {
        //do nothing
    }
}
