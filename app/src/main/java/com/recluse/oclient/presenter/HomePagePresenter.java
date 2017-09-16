package com.recluse.oclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.recluse.base.presenter.IListPresenter;
import com.recluse.base.view.listview.IListView;
import com.recluse.oclient.data.ModuleInfo;
import com.recluse.oclient.event.HomePageModuleEvent;
import com.recluse.oclient.network.RxOClient;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.ui.listview.HomePageVHFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePagePresenter extends IListPresenter.SimpleListPresenter<ModuleInfo> {

    private static final String TAG = "HomePagePresenter";

    public HomePagePresenter(@NonNull IListView callback) {
        super(callback);
    }

    @Override
    public List<BaseViewHolderFactory<ModuleInfo>> createFactoryList(Context context) {
        List<BaseViewHolderFactory<ModuleInfo>> list = new ArrayList<>();
        list.add(new HomePageVHFactory(context));
        return list;
    }

    @Override
    public void requestData() {
        super.requestData();
        Log.d(TAG, "home page request data");

        RxOClient.getHomePageModule(mCallback.getUniqueId());
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

        if (event.data != null && event.data.data != null && !event.data.data.isEmpty()) {
            updateList(event.data.data, true, false);
        } else {
            mCallback.onFailed(0);
        }
        Log.d(TAG, "onReceiveModuleEntity: ");
    }
}
