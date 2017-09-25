package com.recluse.oclient.ui.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.recluse.base.view.listview.BaseRecyclerItemAdapter;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public abstract class BaseNestedListViewHolder<T, S> extends BaseRecyclerViewHolder<T> {

    private static final String TAG = "NestedListViewHolder";

    @BindView(R.id.nested_list_title)
    TextView mTitleView;
    @BindView(R.id.nested_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.divider)
    View mDivider;

    protected List<S> mDataList;

    LinearLayoutManager mLayoutManager;
    BaseRecyclerItemAdapter<S> mItemAdapter;

    public BaseNestedListViewHolder(View itemView) {
        super(itemView);
        Context context = itemView.getContext();
        if (context == null) {
            Log.e(TAG, "BaseNestedListViewHolder: failed to get context");
            return;
        }
        mDataList = new ArrayList<>();
        mLayoutManager = createLayoutManager(context);
        mItemAdapter = new BaseRecyclerItemAdapter<>(context, mDataList, createSubItemVHFactory(context));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mItemAdapter);
    }

    protected LinearLayoutManager createLayoutManager(@NonNull Context context) {
        return new LinearLayoutManager(context);
    }

    protected abstract List<BaseViewHolderFactory<S>> createSubItemVHFactory(@NonNull Context context);
}
