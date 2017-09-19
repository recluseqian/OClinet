package com.recluse.oclient.network;

import android.util.Log;

import com.recluse.oclient.data.BannerInfoEntity;
import com.recluse.oclient.data.HomeModuleEntity;
import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.SubscribeEntity;
import com.recluse.oclient.event.BannerInfoEvent;
import com.recluse.oclient.event.HomePageModuleEvent;
import com.recluse.oclient.event.SubscribePageInfoEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by recluse on 17-9-13.
 */

public class RxOClient {

    private static final String TAG = "RxOClient";

    public static void getHomePageModule(final int uniqueId) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getHomePageModule()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<HomeModuleEntity>() {
                    @Override
                    public void accept(HomeModuleEntity moduleEntity) throws Exception {
                        Log.d(TAG, "receive data of " + moduleEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(
                                new HomePageModuleEvent(uniqueId, BaseEvent.CODE_SUCCESS, moduleEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(
                                new HomePageModuleEvent(uniqueId, BaseEvent.CODE_FAILED, null));
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
    }

    public static void getSubscribeInfo(final int uniqueId, int pageSize, String cursor) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getSubscribePageInfo(cursor, pageSize)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<SubscribeEntity>() {
                    @Override
                    public void accept(SubscribeEntity subscribeEntity) throws Exception {
                        Log.d(TAG, "receive data of " + subscribeEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(
                                new SubscribePageInfoEvent(uniqueId, BaseEvent.CODE_SUCCESS, subscribeEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(
                                new SubscribePageInfoEvent(uniqueId, BaseEvent.CODE_FAILED, null));
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
    }

    public static void getBannerInfo(final int uniqueId, int position) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getBannerInfo(position, OClientApi.BANNER_TYPES)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BannerInfoEntity>() {
                    @Override
                    public void accept(BannerInfoEntity bannerInfoEntity) throws Exception {
                        Log.d(TAG, "receive data of " + bannerInfoEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(new BannerInfoEvent(uniqueId, BaseEvent.CODE_SUCCESS, bannerInfoEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                        EventBus.getDefault().post(new BannerInfoEvent(uniqueId, BaseEvent.CODE_FAILED, null));
                    }
                });
    }
}
