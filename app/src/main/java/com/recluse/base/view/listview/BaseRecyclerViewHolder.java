package com.recluse.base.view.listview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;

public class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseRecyclerViewHolder";

    protected T mData;
    protected int mPosition;
    protected View mItemView;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
        Log.d(TAG, "BaseRecyclerViewHolder: " + this.getClass().getSimpleName());
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
