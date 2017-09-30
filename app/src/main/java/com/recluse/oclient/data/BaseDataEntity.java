package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

public class BaseDataEntity<T> implements Serializable {

    public String message;
    public int code;
    public T data;

    public static class BannerInfoEntity extends BaseDataEntity<List<BannerInfo>> {
    }

    public static class HomeModuleEntity extends BaseDataEntity<List<HomeModuleInfo>> {
    }

    public static class SubscribeEntity extends BaseDataEntity<List<SubscribeModuleInfo>> {
    }

    public static class VideoDetailEntity extends BaseDataEntity<VideoDetailInfo> {
    }

    public static class VideoSubscribeEntity extends BaseDataEntity<SubscribeModuleInfo> {
    }
}
