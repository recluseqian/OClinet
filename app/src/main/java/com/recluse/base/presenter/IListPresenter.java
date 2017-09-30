package com.recluse.base.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.base.model.event.NullEvent;
import com.recluse.base.utils.SystemUtils;
import com.recluse.base.view.IListView;
import com.recluse.base.view.listview.BaseViewHolderFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public interface IListPresenter<D> extends IPresenter<List<D>> {

    List<? extends BaseViewHolderFactory<?>> getFactoryList(Context context);

    void onRefresh();

    void onLoadMore();

    class State {
        public static final int TYPE_IDEAL = 0;
        public static final int TYPE_REFRESHING = 1;
        public static final int TYPE_LOADING = 2;
    }

    abstract class SimpleListPresenter<D> implements IListPresenter<D> {

        private static final String TAG = "Stub";

        private List<BaseViewHolderFactory<?>> mFactoryList;

        protected List<D> mDataList;
        protected List<D> mHeaderList;
        protected List<D> mFooterList;

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
            mHeaderList = createHeaderList();
            mFooterList = createFooterList();
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
        public List<BaseViewHolderFactory<?>> getFactoryList(Context context) {
            if (mFactoryList == null) {
                mFactoryList = createFactoryList(context);
            }
            return mFactoryList;
        }

        public abstract List<BaseViewHolderFactory<?>> createFactoryList(Context context);

        @Override
        public void initData(Bundle bundle) {
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


        protected List<D> createHeaderList() {
            return null;
        }

        protected List<D> createFooterList() {
            return null;
        }

        protected void updateList(@NonNull List<D> list, boolean isFromRefresh, boolean processForeach) {
            if (mDataList == null) {
                Log.e(TAG, "do not init data list when the presenter is created in "
                        + getClass().getSimpleName());
                return;
            }

            int start = 0;
            if (isFromRefresh) {
                mDataList.clear();
                if (!SystemUtils.isListEmtpy(mHeaderList)) {
                    mDataList.addAll(mHeaderList);
                }
            } else {
                if (!SystemUtils.isListEmtpy(mFooterList)) {
                    mDataList.removeAll(mFooterList);
                }
                start = mDataList.size();
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

            if (!SystemUtils.isListEmtpy(mFooterList)) {
                mDataList.addAll(mFooterList);
            }

            if (isFromRefresh) {
                mCallback.onDataSetChanged();
            } else {
                mCallback.onUpdateList(start, list.size());
            }
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
        public List<BaseViewHolderFactory<?>> createFactoryList(Context context) {
            return null;
        }
    }
}
