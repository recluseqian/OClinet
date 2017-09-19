package com.recluse.oclient.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.BannerInfo;
import com.recluse.oclient.data.HomeModuleInfo;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.event.BannerInfoEvent;
import com.recluse.oclient.event.HomePageModuleEvent;
import com.recluse.oclient.network.RxOClient;
import com.recluse.oclient.ui.fragment.HomePageListFragment;
import com.recluse.oclient.ui.listview.HomePageVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePagePresenter extends IListPresenter.SimpleListPresenter<HomeModuleInfo> {

    private static final String TAG = "HomePagePresenter";

    private HomeModuleInfo mHeaderModule;

    public HomePagePresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public List<BaseViewHolderFactory<HomeModuleInfo>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<HomeModuleInfo>> list = new ArrayList<>();
        list.add(new HomePageVHFactory(context));
        return list;
    }

    @Override
    public void initData(Bundle bundle) {
        super.initData(bundle);
        mHeaderModule = new HomeModuleInfo();
        mHeaderModule.mBannerInfoList = new ArrayList<>();
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        } else {
            mDataList.clear();
        }
        mDataList.add(mHeaderModule);
    }

    @Override
    public void requestData() {
        super.requestData();
        Log.d(TAG, "home page request data");

        RxOClient.getBannerInfo(mUniqueId, 1);
        RxOClient.getHomePageModule(mUniqueId);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Log.d(TAG, "home page on refresh");

        RxOClient.getHomePageModule(mCallback.getUniqueId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveModuleEntity(HomePageModuleEvent event) {
        if (mCallback != null && event.uniqueId != mCallback.getUniqueId()) {
            return;
        }
        Log.d(TAG, "onReceiveModuleEntity: ");

        if (event.data != null && event.data.data != null && !event.data.data.isEmpty()) {
            List<HomeModuleInfo> list = new ArrayList<>(event.data.data.size() + 1);
            list.add(mHeaderModule);
            list.addAll(event.data.data);
            updateList(list, true, false);
        } else {
            mCallback.onFailed(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetBannerInfo(BannerInfoEvent event) {
        if (mCallback == null || mUniqueId != event.uniqueId) {
            return;
        }

        Log.d(TAG, "onGetBannerInfo: ");
        if (event.data != null && event.data.data != null) {
            if (mHeaderModule == null || mHeaderModule.mBannerInfoList == null) {
                Log.e(TAG, "onGetBannerInfo: do not init header module");
                return;
            }
            mHeaderModule.mBannerInfoList.clear();
            mHeaderModule.mBannerInfoList.addAll(event.data.data);

            mCallback.onUpdateList(0, 1);
        }
    }
}
