package com.recluse.oclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.view.fragment.BaseListFragment;
import com.recluse.base.view.listview.BaseDividerDecoration;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.ModuleInfo;
import com.recluse.oclient.presenter.HomePagePresenter;
import com.recluse.oclient.ui.activity.MainActivity;

/**
 * Created by recluse on 17-9-14.
 */

public class HomePageListFragment extends BaseListFragment<ModuleInfo> {

    private static final String TAG = "ModuleListFragment";

    public static final String FRAGMENT_TYPE_KEY = "fragment_type_key";

    public static HomePageListFragment newInstance(Bundle bundle) {
        HomePageListFragment fragment = new HomePageListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "create the module list fragment");
    }

    @Override
    protected boolean supportRefresh() {
        return true;
    }

    @NonNull
    @Override
    protected IListPresenter<ModuleInfo> createPresenter() {
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
