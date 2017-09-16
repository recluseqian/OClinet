package com.recluse.base.view.listview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseViewHolderFactory<T> {

    private static final String TAG = "BaseViewHolderFactory";

    private int mBaseType;
    protected Context mContext;

    public BaseViewHolderFactory(@NonNull Context context) {
        mContext = context;
    }

    public abstract int getViewType(T data, int position);

    public abstract BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType);

    /**
     * @return 返回由该factory创建的ViewHolder的类型的数量，用与计算在同一个列表中之后factory的base view type
     */
    public abstract int getViewTypeCount();

    public int getBaseType() {
        return mBaseType;
    }

    void setBaseType(int baseType) {
        mBaseType = baseType;
    }

    protected boolean isInRange(int viewType) {
        return viewType >= mBaseType && viewType < mBaseType + getViewTypeCount();
    }

    public abstract static class SimpleViewHolderFactory<T> extends BaseViewHolderFactory<T> {

        private static final int VIEW_TYPE_DEFAULT = 0;

        private static final int VIEW_TYPE_COUNT = 1;

        public SimpleViewHolderFactory(@NonNull Context context) {
            super(context);
        }

        @Override
        public int getViewType(T data, int position) {
            if (isDefaultType(data, position)) {
                return VIEW_TYPE_DEFAULT + getBaseType();
            }
            return BaseRecyclerItemAdapter.UNKNOWN_TYPE;
        }

        @Override
        public BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (inflater == null) {
                Log.e(TAG, "cannot get inflater");
                return null;
            }

            if (viewType - getBaseType() == VIEW_TYPE_DEFAULT) {
                View view = inflater.inflate(getDefaultLayoutRes(), parent, false);
                return createDefaultViewHolder(view);
            }
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        protected boolean isDefaultType(T data, int position) {
            return data != null;
        }

        @LayoutRes
        protected abstract int getDefaultLayoutRes();

        protected abstract BaseRecyclerViewHolder<T> createDefaultViewHolder(@NonNull View itemView);

    }
}
