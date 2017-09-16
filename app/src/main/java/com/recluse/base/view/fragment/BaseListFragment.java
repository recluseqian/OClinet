package com.recluse.base.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.liaoinstan.springview.container.BaseFooter;
import com.liaoinstan.springview.container.BaseHeader;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseDividerDecoration;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.R;
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;

import butterknife.BindView;

public abstract class BaseListFragment<T> extends BaseFragment implements IListView {

    private static final String TAG = "BaseListFragment";

    @BindView(R.id.spring_view)
    protected SpringView mSpringView;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    protected BaseHeader mHeader;
    protected BaseFooter mFooter;

    protected IListPresenter<T> mPresenter;
    protected BaseRecyclerItemAdapter<T> mAdapter;
    protected LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initSpringView();
        initRecyclerView();

        mPresenter.requestData();
    }

    private void initPresenter() {
        mPresenter = createPresenter();
        mPresenter.onCreate();
        Bundle bundle = super.getArguments();
        if (bundle != null) {
            mPresenter.initData(bundle);
        }
    }

    protected void initSpringView() {
        mSpringView.setType(SpringView.Type.FOLLOW);
        if (supportRefresh()) {
            mHeader = new DefaultHeader(super.getContext());
            mSpringView.setHeader(mHeader);
        }
        if (supportLoadMore()) {
            mFooter = new DefaultFooter(super.getContext());
            mSpringView.setFooter(mFooter);
        }

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (supportRefresh() && mPresenter != null) {
                    mPresenter.onRefresh();
                    if (supportLoadMore()) {
                        reEnableLoadMore();
                    }
                } else {
                    Log.e(TAG, "do not support refresh on "
                            + BaseListFragment.this.getClass().getSimpleName());
                }
            }

            @Override
            public void onLoadmore() {
                if (supportLoadMore() && mPresenter != null) {
                    mPresenter.onLoadMore();
                } else {
                    Log.e(TAG, "do not support load more on "
                            + BaseListFragment.this.getClass().getSimpleName());
                }
            }
        });
    }

    protected void initRecyclerView() {
        if (mPresenter == null) {
            Log.e(TAG, "do not init the presenter properly");
            return;
        }
        mLayoutManager = new LinearLayoutManager(super.getContext());
        mAdapter = createAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        BaseDividerDecoration divider = createDividerDecoration();
        if (divider != null) {
            mRecyclerView.addItemDecoration(divider);
        }
    }

    @NonNull
    protected abstract IListPresenter<T> createPresenter();

    @NonNull
    protected BaseRecyclerItemAdapter<T> createAdapter() {
        return new BaseRecyclerItemAdapter<>(
                super.getContext(),
                mPresenter.getDataSet(),
                mPresenter.getFactoryList(super.getContext()));
    }

    protected BaseDividerDecoration createDividerDecoration() {
        return null;
    }

    protected boolean supportRefresh() {
        return false;
    }

    protected boolean supportLoadMore() {
        return false;
    }

    @Override
    public void onDataSetChanged() {
        if (mSpringView != null) {
            mSpringView.onFinishFreshAndLoad();
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNoMoreContent() {
        if (mSpringView != null) {
            mSpringView.onFinishFreshAndLoad();
            mSpringView.removeView(mSpringView.getFooterView());
        }
    }

    void reEnableLoadMore() {
        if (mFooter == null) {
            mFooter = new DefaultFooter(super.getContext());
        }
        mSpringView.setFooter(mFooter);
    }

    @Override
    public void onFailed(int type) {
        if (mSpringView != null) {
            mSpringView.setEnable(false);
        }
    }

    @Override
    public int getUniqueId() {
        return mUniqueId;
    }

    @Override
    public Context getCustomContext() {
        return super.getContext();
    }
}
