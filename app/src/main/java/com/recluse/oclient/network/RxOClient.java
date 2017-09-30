package com.recluse.oclient.network;

import android.util.Log;

import com.recluse.oclient.data.BaseDataEntity;
import com.recluse.oclient.network.event.BaseNetEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxOClient {

    private static final String TAG = "RxOClient";

    public static void getHomePageModule(final int uniqueId) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getHomePageModule()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseDataEntity.HomeModuleEntity>() {
                    @Override
                    public void accept(BaseDataEntity.HomeModuleEntity moduleEntity) throws Exception {
                        Log.d(TAG, "receive data of " + moduleEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(
                                new BaseNetEvent.HomePageModuleEvent(uniqueId, BaseNetEvent.CODE_SUCCESS, moduleEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(
                                new BaseNetEvent.HomePageModuleEvent(uniqueId, BaseNetEvent.CODE_FAILED, null));
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
                .subscribe(new Consumer<BaseDataEntity.SubscribeEntity>() {
                    @Override
                    public void accept(BaseDataEntity.SubscribeEntity subscribeEntity) throws Exception {
                        Log.d(TAG, "receive data of " + subscribeEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(
                                new BaseNetEvent.SubscribePageInfoEvent(uniqueId, BaseNetEvent.CODE_SUCCESS, subscribeEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(
                                new BaseNetEvent.SubscribePageInfoEvent(uniqueId, BaseNetEvent.CODE_FAILED, null));
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
                .subscribe(new Consumer<BaseDataEntity.BannerInfoEntity>() {
                    @Override
                    public void accept(BaseDataEntity.BannerInfoEntity bannerInfoEntity) throws Exception {
                        Log.d(TAG, "receive data of " + bannerInfoEntity.getClass().getSimpleName());
                        EventBus.getDefault().post(new BaseNetEvent.BannerInfoEvent(uniqueId, BaseNetEvent.CODE_SUCCESS, bannerInfoEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getLocalizedMessage());
                        EventBus.getDefault().post(new BaseNetEvent.BannerInfoEvent(uniqueId, BaseNetEvent.CODE_FAILED, null));
                    }
                });
    }

    public static void getVideoDetail(final int uniqueId, String plid) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getVideoDetail(plid)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseDataEntity.VideoDetailEntity>() {
                    @Override
                    public void accept(BaseDataEntity.VideoDetailEntity videoDetailEntity) throws Exception {
                        EventBus.getDefault().post(new BaseNetEvent.VideoDetailEvent(uniqueId, BaseNetEvent.CODE_SUCCESS, videoDetailEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(new BaseNetEvent.VideoDetailEvent(uniqueId, BaseNetEvent.CODE_FAILED, null));
                    }
                });
    }


    public static void getVideoSubscribe(final int uniqueId, String mid) {
        RetrofitManager.getRetrofit(RetrofitManager.BASE_URL)
                .create(OClientApi.class)
                .getVideoSubscribe(mid, OClientApi.VIDEO_SUBSCRIBE_R_TYPES)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseDataEntity.VideoSubscribeEntity>() {
                    @Override
                    public void accept(BaseDataEntity.VideoSubscribeEntity videoSubscribeEntity) throws Exception {
                        EventBus.getDefault().post(new BaseNetEvent.VideoSubscribeEvent(uniqueId, BaseNetEvent.CODE_SUCCESS, videoSubscribeEntity));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(new BaseNetEvent.VideoSubscribeEvent(uniqueId, BaseNetEvent.CODE_FAILED, null));
                    }
                });
    }
}
