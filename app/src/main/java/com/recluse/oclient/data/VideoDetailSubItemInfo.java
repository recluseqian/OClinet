package com.recluse.oclient.data;

import java.io.Serializable;

public class VideoDetailSubItemInfo<T> implements Serializable {

    public int type;
    public T data;

    public VideoDetailSubItemInfo(int type, T data) {
        this.type = type;
        this.data = data;
    }
}
