package com.recluse.oclient.data;

import java.io.Serializable;

public class VideoDetailItemInfo<T> implements Serializable {

    public static class Type {
        public static final int SUBSCRIBE_INFO = 1;
        public static final int VIDEO_DETAIL_INFO = 2;
        public static final int VIDEO_LIST = 3;
        public static final int SUBSCRIBE_RECOM_LIST = 4;
        public static final int VIDEO_RECOM_LIST = 5;
    }

    public int type;
    public String title;
    public T data;

    public VideoDetailItemInfo() {

    }

    public VideoDetailItemInfo(int type, T data) {
        this.type = type;
        this.data = data;
    }

}
