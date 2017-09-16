package com.recluse.base.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.base.model.event.NullEvent;
import com.recluse.base.view.listview.IListView;
import com.recluse.base.view.listview.BaseViewHolderFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public interface IListPresenter<D> extends IPresenter<List<D>> {

    List<? extends BaseViewHolderFactory<D>> getFactoryList(Context context);

    void onRefresh();

    void onLoadMore();

    class State {
        public static final int TYPE_IDEAL = 0;
        public static final int TYPE_REFRESHING = 1;
        public static final int TYPE_LOADING = 2;
    }

    abstract class SimpleListPresenter<D> implements IListPresenter<D> {

        private static final String TAG = "Stub";

        protected List<D> mDataList;
        protected List<BaseViewHolderFactory<D>> mFactoryList;
        protected int mPageIndex;
        protected int mState = State.TYPE_IDEAL;

        @NonNull
        protected IListView mCallback;
        protected int mUniqueId;

        public SimpleListPresenter(@NonNull IListView callback) {
            mCallback = callback;
            mUniqueId = mCallback.getUniqueId();
        }

        @Override
        public void onCreate() {
            EventBus.getDefault().register(this);
            mDataList = new ArrayList<>();
            mPageIndex = 1;
        }

        @Override
        public void onDestroy() {
            EventBus.getDefault().unregister(this);
        }

        @Override
        public List<D> getDataSet() {
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }

            return mDataList;
        }

        @Override
        public List<BaseViewHolderFactory<D>> getFactoryList(Context context) {
            if (mFactoryList == null) {
                mFactoryList = createFactoryList(context);
            }
            return mFactoryList;
        }

        public abstract List<BaseViewHolderFactory<D>> createFactoryList(Context context);

        @Override
        public void initData(@NonNull Bundle bundle) {
            //nothing to do
        }

        @Override
        public void requestData() {
            //nothing to do
        }

        @Override
        public void onRefresh() {
            //nothing to do
        }

        @Override
        public void onLoadMore() {
            //nothing to do
        }

        protected void updateList(@NonNull List<D> list, boolean isFromRefresh, boolean processForeach) {
            if (mDataList == null) {
                Log.e(TAG, "do not init data list when the presenter is created in "
                        + getClass().getSimpleName());
                return;
            }

            if (isFromRefresh) {
                mDataList.clear();
            }

            if (processForeach) {
                for (D data : list) {
                    if (processData(data, mDataList.size())) {
                        mDataList.add(data);
                    }
                }
            } else {
                mDataList.addAll(list);
            }

            mCallback.onDataSetChanged();
        }

        /**
         * @return 返回值决定是否将该项加入到列表，默认为true， 加入列表
         */
        protected boolean processData(D data, int position) {
            // nothing to do
            return true;
        }

        @Subscribe(threadMode = ThreadMode.POSTING)
        public void onNullEvent(NullEvent event) {

        }
    }

    class NullDataListPresenter<D> extends SimpleListPresenter<D> {

        public NullDataListPresenter(@NonNull IListView callback) {
            super(callback);
        }

        @Override
        public List<BaseViewHolderFactory<D>> createFactoryList(Context context) {
            return null;
        }
    }
}
