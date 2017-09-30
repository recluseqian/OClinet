package com.recluse.oclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.oclient.network.event.BaseNetEvent;
import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.utils.SystemUtils;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.IListView;
import com.recluse.oclient.data.BaseDataEntity;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.viewholderfactory.BannerHeaderVHFactory;
import com.recluse.oclient.ui.viewholderfactory.ListFooterItemVHFactory;
import com.recluse.oclient.ui.viewholderfactory.SubscribePageVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SubscribePagePresenter extends IListPresenter.SimpleListPresenter<SubscribeModuleInfo> {

    private static final String TAG = "SubscribePagePresenter";
    private static final int PAGE_SIZE = 10;

    private int mState = State.TYPE_IDEAL;

    public SubscribePagePresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public void requestData() {
        super.requestData();
        if (mState != State.TYPE_IDEAL) {
            return;
        }
        RxOClient.getBannerInfo(mUniqueId, 2);
        RxOClient.getSubscribeInfo(mUniqueId, PAGE_SIZE, "");
        mState = State.TYPE_REFRESHING;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mState != State.TYPE_IDEAL) {
            return;
        }
        RxOClient.getBannerInfo(mUniqueId, 2);
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
        if (mDataList != null && mDataList.size() > 2) {
            cursor = String.valueOf(mDataList.get(mDataList.size() - 2).score);
        }
        RxOClient.getSubscribeInfo(mUniqueId, PAGE_SIZE, cursor);
        mState = State.TYPE_LOADING;
    }

    @Override
    public List<BaseViewHolderFactory<?>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<?>> list = new ArrayList<>();
        list.add(new ListFooterItemVHFactory(context));
        list.add(new BannerHeaderVHFactory(context));
        list.add(new SubscribePageVHFactory(context));
        return list;
    }

    @Override
    protected List<SubscribeModuleInfo> createHeaderList() {
        SubscribeModuleInfo header = new SubscribeModuleInfo();
        header.getLocalInfo().mDataType = LocalInfo.LOCAL_DATA_TYPE_HEADER;
        header.getLocalInfo().mDividerType = LocalInfo.DIVIDER_HIDE_SELF;
        List<SubscribeModuleInfo> list = new ArrayList<>();
        list.add(header);
        return list;
    }

    @Override
    protected List<SubscribeModuleInfo> createFooterList() {
        SubscribeModuleInfo footer = new SubscribeModuleInfo();
        footer.getLocalInfo().mDataType = LocalInfo.LOCAL_DATA_TYPE_LOADING;
        footer.getLocalInfo().mDividerType = LocalInfo.DIVIDER_HIDE_SELF | LocalInfo.DIVIDER_HIDE_PRE;
        List<SubscribeModuleInfo> list = new ArrayList<>();
        list.add(footer);
        return list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSubscribePageInfoEvent(BaseNetEvent.SubscribePageInfoEvent event) {
        if (mUniqueId != event.uniqueId) {
            return;
        }

        if (event.data != null && event.data.data != null) {
            updateList(event.data.data, mState == State.TYPE_REFRESHING, false);
        } else {
            mCallback.onFailed(0);
        }

        mState = State.TYPE_IDEAL;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetBannerInfo(BaseNetEvent.BannerInfoEvent event) {
        if (mUniqueId != event.uniqueId) {
            return;
        }

        if (event.data != null && event.data.data != null) {
            SubscribeModuleInfo header;
            if (SystemUtils.isListEmtpy(mHeaderList)
                    || (header = mHeaderList.get(0)) == null) {
                Log.e(TAG, "onGetBannerInfo: do not init header module");
                return;
            }

            header.getBannerList().clear();
            header.getBannerList().addAll(event.data.data);

            mCallback.onUpdateList(0, 1);
        }
    }
}
