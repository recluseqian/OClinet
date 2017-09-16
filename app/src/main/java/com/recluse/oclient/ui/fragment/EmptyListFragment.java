package com.recluse.oclient.ui.fragment;

import android.support.annotation.NonNull;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.fragment.BaseListFragment;

/**
 * Created by recluse on 17-9-15.
 */

public class EmptyListFragment extends BaseListFragment {

    public static EmptyListFragment newInstance() {
        EmptyListFragment fragment = new EmptyListFragment();
        return fragment;
    }

    @NonNull
    @Override
    protected IListPresenter createPresenter() {
        return new IListPresenter.NullDataListPresenter(this);
    }
}
