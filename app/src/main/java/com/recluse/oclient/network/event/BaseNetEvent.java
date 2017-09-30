package com.recluse.oclient.network.event;

import com.recluse.oclient.data.BaseDataEntity;

public class BaseNetEvent<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;

    public int uniqueId;
    public int code;
    public T data;

    public BaseNetEvent() {

    }

    public BaseNetEvent(int uniqueId, int code, T data) {
        this.uniqueId = uniqueId;
        this.code = code;
        this.data = data;
    }

    public static class HomePageModuleEvent extends BaseNetEvent<BaseDataEntity.HomeModuleEntity> {

        public HomePageModuleEvent(int uniqueId, int code, BaseDataEntity.HomeModuleEntity data) {
            super(uniqueId, code, data);
        }
    }

    public static class BannerInfoEvent extends BaseNetEvent<BaseDataEntity.BannerInfoEntity> {

        public BannerInfoEvent(int uniqueId, int code, BaseDataEntity.BannerInfoEntity data) {
            super(uniqueId, code, data);
        }
    }

    public static class SubscribePageInfoEvent extends BaseNetEvent<BaseDataEntity.SubscribeEntity> {

        public SubscribePageInfoEvent(int uniqueId, int code, BaseDataEntity.SubscribeEntity data) {
            super(uniqueId, code, data);
        }
    }

    public static class VideoDetailEvent extends BaseNetEvent<BaseDataEntity.VideoDetailEntity> {

        public VideoDetailEvent(int uniqueId, int code, BaseDataEntity.VideoDetailEntity data) {
            super(uniqueId, code, data);
        }
    }

    public static class VideoSubscribeEvent extends BaseNetEvent<BaseDataEntity.VideoSubscribeEntity> {

        public VideoSubscribeEvent(int uniqueId, int code, BaseDataEntity.VideoSubscribeEntity data) {
            super(uniqueId, code, data);
        }
    }
}
