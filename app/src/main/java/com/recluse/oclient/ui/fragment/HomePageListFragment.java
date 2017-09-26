package com.recluse.oclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.base.view.listview.BaseDividerDecoration;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.presenter.HomePagePresenter;

/**
 * Created by recluse on 17-9-14.
 */

public class HomePageListFragment extends BaseListFragment<HomeModuleInfo> {

    private static final String TAG = "ModuleListFragment";

    public static HomePageListFragment newInstance(Bundle bundle) {
        HomePageListFragment fragment = new HomePageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean supportRefresh() {
        return true;
    }

    @NonNull
    @Override
    protected IListPresenter<HomeModuleInfo> createPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected BaseDividerDecoration createDividerDecoration() {
        return new BaseDividerDecoration.Builder()
                .setWidth((int) DisplayUtils.dp2px(super.getContext(), 5))
                .build();
    }

    @Override
    public void onFailed(int type) {
        super.onFailed(type);
        if (mSpringView != null) {
            mSpringView.onFinishFreshAndLoad();
        }
    }

}
