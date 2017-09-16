package com.recluse.base.view.listview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by recluse on 17-5-23.
 */

public class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseRecyclerViewHolder";

    protected T mData;
    protected int mPosition;
    protected View mItemView;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
    }

    @CallSuper
    public void onBindData(T data, int position) {
        mData = data;
        mPosition = position;
    }

    @CallSuper
    protected void onViewAttachedToWindow() {
    }

    @CallSuper
    protected void onViewDetachedFromWindow() {
    }

    @CallSuper
    protected void onViewRecycled() {
    }

    public static class EmptyViewHolder extends BaseRecyclerViewHolder<Object> {

        public EmptyViewHolder(View itemView) {
            super(itemView);
            itemView.setVisibility(View.GONE);
        }
    }
}
