package com.recluse.oclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.oclient.network.event.BaseNetEvent;
import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.IListView;
import com.recluse.oclient.data.BaseDataEntity;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.data.LocalInfo;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.viewholderfactory.BannerHeaderVHFactory;
import com.recluse.oclient.ui.viewholderfactory.HomePageItemVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class HomePagePresenter extends IListPresenter.SimpleListPresenter<HomeModuleInfo> {

    private static final String TAG = "HomePagePresenter";

    public HomePagePresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public void requestData() {
        super.requestData();
        RxOClient.getBannerInfo(mUniqueId, 1);
        RxOClient.getHomePageModule(mUniqueId);
    }

    @Override
    public List<BaseViewHolderFactory<?>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<?>> list = new ArrayList<>();
        list.add(new BannerHeaderVHFactory(context));
        list.add(new HomePageItemVHFactory(context));
        return list;
    }

    @Override
    protected List<HomeModuleInfo> createHeaderList() {
        HomeModuleInfo header = new HomeModuleInfo();
        header.getLocalInfo().mDataType = LocalInfo.LOCAL_DATA_TYPE_HEADER;
        header.getLocalInfo().mDividerType = LocalInfo.DIVIDER_HIDE_SELF;
        List<HomeModuleInfo> list = new ArrayList<>();
        list.add(header);
        return list;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        RxOClient.getHomePageModule(mCallback.getUniqueId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveModuleEntity(BaseNetEvent.HomePageModuleEvent event) {
        if (mCallback != null && event.uniqueId != mCallback.getUniqueId()) {
            return;
        }

        if (event.data != null && event.data.data != null && !event.data.data.isEmpty()) {
            updateList(event.data.data, true, false);
        } else {
            mCallback.onFailed(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetBannerInfo(BaseNetEvent.BannerInfoEvent event) {
        if (mUniqueId != event.uniqueId) {
            return;
        }

        if (event.data != null && event.data.data != null) {
            HomeModuleInfo header;
            if (mHeaderList == null
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
