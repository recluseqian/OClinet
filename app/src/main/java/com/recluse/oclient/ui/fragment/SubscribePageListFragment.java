package com.recluse.oclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.oclient.ui.BaseDividerDecoration;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.presenter.SubscribePagePresenter;

public class SubscribePageListFragment extends BaseListFragment<SubscribeModuleInfo> {

    private static final String TAG = "SubscribePageListFragment";

    public static SubscribePageListFragment newInstance(Bundle bundle) {
        SubscribePageListFragment fragment = new SubscribePageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected IListPresenter<SubscribeModuleInfo> createPresenter() {
        return new SubscribePagePresenter(this);
    }

    @Override
    protected BaseDividerDecoration createDividerDecoration() {
        return new BaseDividerDecoration.Builder()
                .setWidth((int) DisplayUtils.dp2px(super.getContext(), 5))
                .build();
    }

    @Override
    public boolean supportRefresh() {
        return true;
    }

    @Override
    public boolean supportLoadMore() {
        return true;
    }

    @Override
    public void onFailed(int type) {
        super.onFailed(type);
    }
}
