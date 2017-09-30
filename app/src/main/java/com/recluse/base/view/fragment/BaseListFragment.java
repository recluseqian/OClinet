package com.recluse.base.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.oclient.ui.BaseDividerDecoration;
import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.IListView;
import com.recluse.oclient.R;
import com.recluse.oclient.ui.viewholder.ListFooterItemVH;

import butterknife.BindView;

public abstract class BaseListFragment<T> extends BaseFragment implements IListView {

    private static final String TAG = "BaseListFragment";

    @BindView(R.id.base_list_view_stub)
    ViewStub mBaseListViewStub;

    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;

    protected IListPresenter<T> mPresenter;
    protected BaseRecyclerItemAdapter<T> mAdapter;
    protected LinearLayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener mOnScrollListener;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initView();

        mPresenter.requestData();
    }

    private void initPresenter() {
        mPresenter = createPresenter();
        mPresenter.onCreate();
        Bundle bundle = super.getArguments();
        mPresenter.initData(bundle);
    }

    private void initView() {
        if (supportRefresh()) {
            mBaseListViewStub.setLayoutResource(R.layout.base_refresh_list_layout);
        } else {
            mBaseListViewStub.setLayoutResource(R.layout.base_list_layout);
        }

        View view = mBaseListViewStub.inflate();

        if (supportRefresh()) {
            mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
            if (mRefreshLayout != null) {
                initSwipeRefreshLayout();
            } else {
                Log.e(TAG, "initView: can not find the swipe refresh layout");
            }
        }

        mRecyclerView = view.findViewById(R.id.recycler_view);
        if (mRecyclerView != null) {
            initRecyclerView();
        } else {
            Log.e(TAG, "initView: can not find the recycler view");
        }
    }

    private void initSwipeRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(super.getResources().getColor(R.color.colorPrimaryDark));
        mRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter != null) {
                    mPresenter.onRefresh();
                }
            }
        });
    }

    private void initRecyclerView() {
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
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                BaseListFragment.this.onScrollStateChanged(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                BaseListFragment.this.onScrolled(dx, dy);
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    @CallSuper
    protected void onScrollStateChanged(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            try {
                RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(
                        mAdapter.getItemCount() - 1);
                if (holder instanceof ListFooterItemVH && mPresenter != null) {
                    mPresenter.onLoadMore();
                }
            } catch (Exception e) {
                Log.e(TAG, "can not get the holder");
            }
        }
    }

    protected void onScrolled(int dx, int dy) {

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

    @Override
    public boolean supportRefresh() {
        return false;
    }

    @Override
    public boolean supportLoadMore() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mOnScrollListener);
        }
    }

    @Override
    public void onDataSetChanged() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUpdateList(int positionStart, int itemCount) {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        if (mAdapter != null) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }
    }

    @Override
    public void onNoMoreContent() {

    }

    @Override
    public void onFailed(int type) {

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
