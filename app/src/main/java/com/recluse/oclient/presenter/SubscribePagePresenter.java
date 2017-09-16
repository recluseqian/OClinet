package com.recluse.oclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.SubscribeInfo;
import com.recluse.oclient.event.SubscribePageInfoEvent;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.listview.SubscribePageVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by recluse on 17-9-15.
 */

public class SubscribePagePresenter extends IListPresenter.SimpleListPresenter<SubscribeInfo> {

    private static final String TAG = "SubscribePagePresenter";
    private static final int PAGE_SIZE = 10;

    public SubscribePagePresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public void requestData() {
        super.requestData();
        if (mState != State.TYPE_IDEAL) {
            return;
        }
        RxOClient.getSubscribeInfo(mUniqueId, PAGE_SIZE, "");
        mState = State.TYPE_REFRESHING;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mState != State.TYPE_IDEAL) {
            return;
        }
        RxOClient.getSubscribeInfo(mUniqueId, PAGE_SIZE, "");
        mState = State.TYPE_REFRESHING;
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (mState != State.TYPE_IDEAL) {
            return;
        }
        String cursor = "";
        if (mDataList != null && !mDataList.isEmpty()) {
            cursor = String.valueOf(mDataList.get(mDataList.size() - 1).score);
        }
        RxOClient.getSubscribeInfo(mUniqueId, PAGE_SIZE, cursor);
        mState = State.TYPE_LOADING;
    }

    @Override
    public List<BaseViewHolderFactory<SubscribeInfo>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<SubscribeInfo>> list = new ArrayList<>();
        list.add(new SubscribePageVHFactory(context));
        return list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSubscribePageInfoEvent(SubscribePageInfoEvent event) {
        if (mCallback == null || mUniqueId != event.uniqueId) {
            return;
        }

        if (event.data != null && event.data.data != null) {
            Log.d(TAG, "onGetSubscribePageInfoEvent: " + event.data.data.size());
            updateList(event.data.data, mState == State.TYPE_REFRESHING, false);
        } else {
            mCallback.onFailed(0);
        }

        mState = State.TYPE_IDEAL;
    }

}
