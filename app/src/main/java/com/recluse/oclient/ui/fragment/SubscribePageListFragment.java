package com.recluse.oclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.base.view.listview.BaseDividerDecoration;
import com.recluse.oclient.data.SubscribeInfo;
import com.recluse.oclient.presenter.SubscribePagePresenter;

/**
 * Created by recluse on 17-9-15.
 */

public class SubscribePageListFragment extends BaseListFragment<SubscribeInfo> {

    private static final String TAG = "SubscribePageListFragment";

    public static SubscribePageListFragment newInstance(Bundle bundle) {
        SubscribePageListFragment fragment = new SubscribePageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected IListPresenter<SubscribeInfo> createPresenter() {
        return new SubscribePagePresenter(this);
    }

    @Override
    protected BaseDividerDecoration createDividerDecoration() {
        return new BaseDividerDecoration.Builder()
                .setWidth((int) DisplayUtils.dp2px(super.getContext(), 5))
                .build();
    }

    @Override
    protected boolean supportRefresh() {
        return true;
    }

    @Override
    protected boolean supportLoadMore() {
        return true;
    }

    @Override
    public void onFailed(int type) {
        super.onFailed(type);
        if (mSpringView != null) {
            mSpringView.onFinishFreshAndLoad();
        }
    }
}
