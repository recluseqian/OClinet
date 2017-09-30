package com.recluse.base.view.listview;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BaseRecyclerItemAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {



    private Context mContext;
    private List<T> mDataList;
    private List<? extends BaseViewHolderFactory<?>> mFactoryList;

    public BaseRecyclerItemAdapter(Context context, List<T> dataList, List<? extends BaseViewHolderFactory<?>> factoryList) {
        mContext = context;
        mDataList = dataList;
        mFactoryList = factoryList;
        initFactoryList();
    }

    private void initFactoryList() {
        if (mFactoryList != null && mFactoryList.size() > 0) {
            mFactoryList.get(0).setBaseType(BaseViewHolderFactory.BASE_VIEW_TYPE);
            for (int i = 1; i < mFactoryList.size(); i++) {
                int baseType = mFactoryList.get(i - 1).getBaseType() + mFactoryList.get(i - 1).getViewTypeCount();
                mFactoryList.get(i).setBaseType(baseType);
            }
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseRecyclerViewHolder holder = null;
        if (mFactoryList != null) {
            for (BaseViewHolderFactory factory : mFactoryList) {
                if (factory.isInRange(viewType)) {
                    holder = factory.createViewHolder(parent, viewType);
                    break;
                }
            }
        }

        if (holder == null) {
            holder = new BaseRecyclerViewHolder.EmptyViewHolder(new View(mContext));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        T data = getData(position);
        holder.onBindData(data, position);
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerViewHolder holder) {
        if (holder != null) {
            holder.onViewAttachedToWindow();
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerViewHolder holder) {
        if (holder != null) {
            holder.onViewDetachedFromWindow();
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(BaseRecyclerViewHolder holder) {
        if (holder != null) {
            holder.onViewRecycled();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        T data = getData(position);
        boolean catchException = false;
        try {
            for (BaseViewHolderFactory factory : mFactoryList) {
                int type = factory.getViewType(data, position);
                if (type != BaseViewHolderFactory.UNKNOWN_TYPE) {
                    return type;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            catchException = true;
        } finally {
            if (catchException) {
                return BaseViewHolderFactory.UNKNOWN_TYPE;
            }
        }
        return BaseViewHolderFactory.UNKNOWN_TYPE;
    }

    private T getData(int position) {
        return mDataList == null || position < 0 || position >= mDataList.size()
                ? null
                : mDataList.get(position);
    }

    public List<T> getDataList() {
        return mDataList;
    }
}
